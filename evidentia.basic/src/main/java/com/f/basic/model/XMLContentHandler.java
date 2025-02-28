package com.f.basic.model;

import java.lang.reflect.Method;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLContentHandler extends DefaultHandler {

	/*
	 * Helpers to traverse the tag's tree in mode
	 * "depth-first" (post-order)
	 * There should always be a root element
	 * 
	 * root     Root of the tags tree
	 * nodes    Paths of the traversed tags
	 * attribs  Attributes of each tag in the path 
	 * chars    Content associated to each tag in the path
	 * elements Association between XML tags and processing methods
	 */
	private TreeNode                root  = null;
	private Stack<MutableTreeNode> nodes  = null;
	private Stack<Attributes>     attribs = null;
	private Stack<StringBuilder>    chars  = null;
	private Map<String,Method>   elements = null;

	/*
	 *  Initial size of the content buffers. They will be 
	 *  dynamically updated if necessary 
	 */ 
	private static   final  int INITIAL_CONTENTS_SIZE = 500;


	/*
	 * log      Systems logger
	 * logHdler Logger Stream handler
	 */
	private static transient Logger        log      = LogHandler.getLoggerInstance();
	private static transient StreamHandler logHdler = LogHandler.getHandlerInstance();

	/*  Processor class for the XML tags */
	private XMLContentHandler.TagsProcessor tagsProcessor = null;

	// <::::::::::::::::::::::::::::::::::::::::::::::::::::::::::>
	// XML Tags processor

	/**
	 * Tagging interface that indicates that a class has the methods to process XML tags
	 * Methods are named "start_<methods_name>",  "end_<methods_name>"
	 */
	public interface TagsProcessor {
		// Methods begin with "start_" or "end_"
	}


	// <::::::::::::::::::::::::::::::::::::::::::::::::::::::::::>
	//  Init

	/**
	 * Builds an instance of the content handler
	 * @param tagsProcessor  Processor of the tags recognized by the parser
	 * @throws IllegalArgumentException When tags could not be associated to a method handler
	 */
	public XMLContentHandler( XMLContentHandler.TagsProcessor tagsProcessor)
			throws IllegalArgumentException {

		this.tagsProcessor = tagsProcessor;
		nodes   = new Stack<MutableTreeNode>();
		attribs = new Stack<Attributes>();
		chars   = new Stack<StringBuilder>();

		try {
			/*
			 * Builds the association between XML tags an the start/end methods that process them
			 * These methods will be called when the parser recognizes the 
			 * initial tag (start_<method>), or end tag (end_<method>)
			 */
			Method methods[] = tagsProcessor.getClass().getDeclaredMethods();
			elements    = new HashMap<String,Method>( (int)(methods.length * 1.30));
			for (int i= 0; i < methods.length; i++) {

				String methName = methods[i].getName();
				String prefix   = "start_";
				int    elemIdx  = methName.indexOf(prefix);
				if ( elemIdx < 0) {
					prefix = "end_";
					elemIdx = methName.indexOf(prefix);
				}

				if ( elemIdx >= 0) {
					String elementName= methName.substring( elemIdx+prefix.length()).trim();
					log.finest( ">>> element ["+ elementName+ "]");
					elements.put( prefix+ elementName.toLowerCase(), methods[i]);
				}
			}
		} catch (Throwable e) {
			String msg = "*** Could not build the method list to process the XML"; 
			log.log(Level.SEVERE,msg, e);
			throw new IllegalArgumentException( msg);
		}

	}//XMLContentHandler


	/**
	 * Structures initialization
	 * @throws SAXException
	 */
	@Override
	public void startDocument() throws SAXException
	{
		// The stack needs to be reinitialized for each document
		// because an exception might have interrupted parsing of a
		// previous document, leaving an unempty stack.
		nodes.clear();
		attribs.clear();
		chars.clear();

	}// startDocument


	/**
	 * Gets the attributes of the tag and includes the element in the visualization tree
	 * when the logging level is adequate
	 * @param namespaceURI  URI of namespace associated to the element
	 * @param localName     Element's name (without namespace)
	 * @param qualifiedName Element's name (with namespace)
	 * @param atts          list of attributes asssociated with the element
	 */
	@Override
	public void startElement( String namespaceURI, String localName,
			String qualifiedName, Attributes atts) {

		// Get the qualified name of the tag
		if (localName == null || localName.equals("")){
			localName = qualifiedName;
		}
		String data = (namespaceURI.equals("")) ?
				localName:	
		        '{'+ namespaceURI + "} "+ qualifiedName;

		// Process the attribute list
		try {
			for (int i=0;  i < atts.getLength(); i++)
				data += (" "+ atts.getLocalName(i)+ "="+ atts.getValue(i)+ "  ");

		} catch (Exception e) {
			log.log(Level.SEVERE, "*** Error processing attributes of element "+ localName, e);
		}

		/*
		 * Adds the tag name and its attributes to a TreeView in order to
		 * visualize parser's execution.  Needs an adequate logging level
		 */
		MutableTreeNode node = null;
		if ( log.isLoggable(Level.FINER)) {

			node = new DefaultMutableTreeNode(data);
			try {
				MutableTreeNode parent = nodes.peek();
				parent.insert(node, parent.getChildCount());
			} catch (EmptyStackException e) {
				root = node;
			}
		}

		// Process the element and its attributes
		try	{
			String  methodName = "start_"+localName.toLowerCase();
			Method  processor  = elements.get( methodName );
			if ( processor != null) {
				dump_start( methodName, atts);
				processor.invoke(tagsProcessor, new Object[] {atts});
			}
		} catch ( Exception e) {
			log.log(Level.WARNING, "*** Error processing the element "+ localName, e);
		}

		// Save the element and its attributes, clean the content
		attribs.push(atts);
		chars.push(new StringBuilder( INITIAL_CONTENTS_SIZE));
		if ( log.isLoggable(Level.FINER))
			nodes.push(node);

	}// startElement


	/**
	 * Process the content of an element
	 * @param ch  Array whith the (partial) character content of the element 
	 * @param start  Initial position in the element char array 
	 * @param length Number of chars to use from the char array of the element
	 */
	@Override
	public void characters(char[] ch,  int start, int length) {
		StringBuilder contents = chars.peek();
		contents.append( new String(ch, start, length));
	}// characters


	/**
	 * Finishes processing an element and adds the element to the tree for visualization
	 * @param namespaceURI  URI of namespace asociated to the element
	 * @param localName     Name of element (without namespace)
	 * @param qualifiedName Name of element (with namespace)
	 */
	@Override
	public void endElement( String namespaceURI, String localName, String qualifiedName) {
		
		String contents = chars.pop().toString().trim();

		// Add the content to the tag tree for visualization, if logging level is adequate
		if ( contents.length() > 0 && log.isLoggable(Level.FINEST))	{
			try	{
				MutableTreeNode node   = new DefaultMutableTreeNode(contents);
				MutableTreeNode parent = nodes.peek();
				parent.insert(node, parent.getChildCount());
			} catch (EmptyStackException e) { }
		}

		// Procese el elemento con sus atributos y contenido
		try	{
			if (localName == null || localName.equals("")){
				localName = qualifiedName;
			}
			String  methodName = "end_"+ localName.toLowerCase();
			Method  processor = elements.get( methodName);
			if ( processor != null)	{
				dump_end( methodName, attribs.peek(), contents);
				processor.invoke(tagsProcessor, new Object[] {attribs.pop(), contents});
			}
		} catch ( Exception e) {
			log.log(Level.SEVERE, "*** Error processing element "+ localName, e);
		}

		if ( log.isLoggable(Level.FINEST)) {
			nodes.pop();
		}
	}// endElement


	/**
	 * Ends the XML doc processing
	 */
	@Override
	public void endDocument() {
		logHdler.flush();

		if ( log.isLoggable(Level.FINEST)) {
			JTree tree           = new JTree(root);
			JScrollPane treeView = new JScrollPane(tree);
			JFrame      f        = new JFrame("Estructura del documento");

			f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
			f.getContentPane().add(treeView);
			f.pack();
			f.setVisible(true);
		}

	}// endDocument


	/**
	 * Displays the method's name and its attributes
	 * @param procName Name of executed method
	 * @param atts Attributes of the processed element
	 */
	private void dump_start ( String procName, Attributes atts) {
		if ( log.isLoggable(Level.FINEST)) {
			StringBuilder msg = new StringBuilder();
			msg.append( ">>> Method "+ procName+ "\n");

			for (int i= 0; i < atts.getLength(); i++) {
				msg.append( "\tAttr("+ i+")->"+ atts.getLocalName(i)+ "=["+ atts.getValue(i)+"]\n");
			}
			log.finer(msg.toString());
		}

	}// dump_start
	

	/**
	 * Displays the method's name, attributes and char content
	 * @param procName Name of the executed method
	 * @param atts Attributes of processed element
	 * @param contents Text contents of the element
	 */
	private void dump_end ( String procName, Attributes atts, String contents)	{
		if ( log.isLoggable(Level.FINEST)) {
			StringBuilder msg = new StringBuilder();
			msg.append( ">>> Method "+ procName+ "\n");

			for (int i= 0; i < atts.getLength(); i++) {
				msg.append( "\tAttr("+ i+")->"+ atts.getLocalName(i)+ "=["+ atts.getValue(i)+"]\n");
			}

			if ( contents.length() > 0) {
				msg.append( "\tContents["+ contents+ "]\n");
			}

			log.finer(msg.toString());
		}

	}// dump_end

}
