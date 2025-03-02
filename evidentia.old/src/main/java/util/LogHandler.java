package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;
import java.util.logging.XMLFormatter;


/**
 * Represents a log stream
 *
 */
public final class LogHandler
{

    private static final String   LOGNAME        = "general";
    public  static final boolean  XML            = true;
    public  static final boolean  TEXT           = false;
    public  static final String   LOG_LEVEL_WARN = "WARN";
    
    /** loggers - registered loggers */
    private static Map<String, Object[]> loggers = new HashMap<String, Object[]>();
    

   /**
    * Configures a Logger and its associated StreamHandler
    * @param logName   Prefix of the logger's name. Name of log file will be "PrefixAAAA-MM-DD.log"
    * @param xmlFormat true (=LogHandler.XML): use a xml formatter/ false(=LogHandler.TEXT): use simple formatter
    * @param level     Logging level. Messages below this level are ignored
    */
   public static Object[] configLog( String logName, boolean xmlFormat, Level level) {
	   
	   if( logName == null) {
		   logName = "LOG";
	   }
	   
	   if( level == null) {
		   level = Level.WARNING;
	   }
	  
     
	   // Get Logger and Stream handler
      Logger  logger  = Logger.getLogger(logName);
      logger.setLevel( level);
      StreamHandler handler = null;

      // Install stream file del log
      OutputStream   logFile = null;
	  LocalDateTime now =  LocalDateTime.now();
	  String      today =  ""+ now.getYear()+ "-"+ now.getMonthValue()+ "-"+ now.getDayOfMonth();
      try {
         logFile = new FileOutputStream(logName.trim()+"."+ today+ ".log");
      } catch (IOException e) {
         logger.log(Level.WARNING, "*** Could not configure log file ["+ logName+ "] Reason\n", e);
         Handler  hdlers[] = logger.getHandlers();

         if (hdlers.length > 0) {
             handler = (StreamHandler)hdlers[0];
             logger.warning(">>> Using the existing handler log ["+ logName+ "]");
             return new Object[]{ logger, handler};
         }

         logger.warning( "*** Undefined handler for log["+ logName+ "]");
         return new Object[]{  logger, null};

      }


      // Installs the Stream handler
      handler= new StreamHandler(logFile,
            xmlFormat ? (Formatter)new XMLFormatter() : (Formatter)new SimpleFormatter());
      handler.setLevel( level);
      try {
        handler.setEncoding("UTF-8");
      }catch(UnsupportedEncodingException ue) {
        logger.warning(">>> Log handler des not soports UTF-8");
      }
      logger.addHandler( handler);

      return new Object[]{ logger, handler};

   }// configLog
   
   

   /**
    * Gets a logger instance
    * @param logName Logger's name
    * @return Logger Logger instance (if registered); otherwise returns the default logger
    */
   public static Logger getLoggerInstance( String logName){   
     Object[] theLog = loggers.get( logName);
     if ( theLog == null){
        theLog = configLog( logName, false, Level.FINER);
        loggers.put( logName,  theLog);
     }
     
     return (Logger)theLog[0];

   }//getLoggerInstance
   

   
   
   /**
    * Gets a logger instance of default logger
    * @return Default logger instance
    */
   public static Logger getLoggerInstance() {
     Object[] theLog = loggers.get( LOGNAME);
     if ( theLog == null) {
        theLog = configLog( LOGNAME, false, Level.FINER);
        loggers.put( LOGNAME,  theLog);
     }
     
     return (Logger)theLog[0];
     
   }//getLoggerInstance
   
   

   /**
    * Gets an instance of default's logger stream handler
    * @return Default logger's Stream Handler
    */
   public static StreamHandler getHandlerInstance()   {
     Object[] theLog = loggers.get( LOGNAME);
     if ( theLog == null){
        theLog = configLog( LOGNAME, false, Level.FINER);
        loggers.put( LOGNAME,  theLog);
     }
     
     return (StreamHandler)theLog[1];

   }//getHandlerInstance
   
   

   /**
    * Gets an instance of the Stream handler associated to a logger
    * @param logName Name of the logger
    * @return StreamHandler associated to the logger
    */
   public static StreamHandler getHandlerInstance( String logName)
   {
      Object[] theLog = loggers.get( logName);
      if ( theLog == null)
      {
         theLog = configLog( logName, false, Level.FINER);
         loggers.put( logName,  theLog);
      }
      return (StreamHandler)theLog[1];

   }//getLoggerInstance
   


   /**
    * Sets a new level to a logger
    * @param logName Name of the logger. If not registered, it does nothing
    * @param level New logger's level. In uppercase, according to
    *            SEVERE (highest value)
    *            WARNING
    *            INFO
    *            CONFIG
    *            FINE
    *            FINER
    *            FINEST (lowest value)
    */
   public static void setLevel( String logName, String level)   {
     if (logName == null) { 
    	 logName = LOGNAME;
     }
     Object[] theLog = loggers.get( logName);
     if (theLog == null) {
       return;
     }
     try {
       Level lev  = Level.parse(logName);
       Logger log = (Logger)theLog[0];
       log.setLevel( lev);
     }catch( Exception e)
     {}
   }//setLevel
   

}// LogHandler
