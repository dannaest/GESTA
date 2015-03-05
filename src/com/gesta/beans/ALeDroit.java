package com.gesta.beans;

public class ALeDroit {
	private Long id_aledroit = null;
	private String servlet = "";
	private String doMethod = "";
	private String action = ""; 
	
	public ALeDroit() {
		
	}
	public ALeDroit(Long id, String servlet, String doMethod, String action) {
		this.id_aledroit = id;
		this.servlet = servlet; 
		this.doMethod = doMethod; 
		this.action = action; 
	}

    public void setId(Long id) {
    	this.id_aledroit = id;
    }
    public Long getId() {
    	return this.id_aledroit;
    }
    public void setServlet(String servlet) {
    	this.servlet = servlet;
    }
    public String getServlet() {
    	return this.servlet;
    }
    public void setDoMethod(String doMethod) {
    	this.doMethod = doMethod;
    }
    public String getDoMethod() {
    	return this.doMethod;
    }
    public void setAction(String action) {
    	this.action = action;
    }
    public String getAction() {
    	return this.action;
    }
	     
}
