package com.f.basic.model;

/**
 * System parameters
 */
public final class Parm {

	public static enum          TYPE { INTEGER, STRING, BOOLEAN, DATE, LIST}
	public static final long    NULL_ID               = 2147483647;
	public static final int 	MAX_SECURITY_LEVEL    = 5;
	public static final int 	MIN_SECURITY_LEVEL    = 0;
	public static final int 	PUBLIC_SECURITY_LEVEL = 0;
	public static final int     ADMIN_SECURITY_LEVEL  = 3;
	public static final int     INDENT                = 3;
}
