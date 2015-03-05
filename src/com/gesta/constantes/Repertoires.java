package com.gesta.constantes;

public final class Repertoires {
	private Repertoires() {
        // restrict instantiation
	}
	public static final String  DS = "/";
	public static final String  Controller = "/WEB-INF/controller";
	public static final String  LOCAL_PATH = "/mGESTA";
	
	public static final String  CTRL_USER = Controller+DS+"users";
	
	public static final String CTRL_ERROR = Controller+DS+"errors";
	public static final String page_error = CTRL_ERROR+DS+"error.jsp";
}
