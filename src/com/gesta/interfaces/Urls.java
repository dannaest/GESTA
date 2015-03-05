package com.gesta.interfaces;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest; 
 
 


import static com.gesta.constantes.Repertoires.*;

public class Urls {
	private HttpServletRequest request;
	private String url;
	private String[] parameters;
	public String[] parametersUri;
	private static String localpath = LOCAL_PATH;
	
	public Urls(HttpServletRequest request){
		this.request = request;
		String querystring = "";
		try {
			querystring = request.getQueryString().toString(); 
		}
		catch(Exception e) { 
			querystring = "";
		} 
		setUrl((querystring==null) ? "" : querystring);  
		this.parameters = explodeUrl("/");
		this.parametersUri = explodeUri("/");
		
	}
	private void setUrl(String url) {
		this.url = url;
	}
	public String getUrl(){		return this.url;	}
	public String[] explodeUrl(String separator){ 
		String[] param = {};  
		if(this.url.length()>0){
			param  = this.url.split(separator); 
		}
		return param;
	}
	public String[] explodeUri(String separator){ 
		String[] param = {};
		String uri = this.request.getRequestURI().toString();
		if(uri.length()>0){
			param  = uri.split(separator); 
		}
		return param;
	}
	public String getParam(int i) throws Exception{
		if(this.parameters == null || this.parameters.length == 0)
			throw new Exception("Aucun parametre d'url n'a �t� saisi.");
		if(i>this.parameters.length-1)
			throw new Exception("Ce param�tre est inacceptible.");
		if(this.parameters[i]!=null && this.parameters[i].length()>0){
			return this.parameters[i];
		}
		return "";
	}
	public String[] getParameters(){
		return this.parameters; 
	}
	public static String LocalPath(String url) {
		return localpath+url;
	}
	public String ServletContextPath(String url) {
		return request.getContextPath();
	}
	public void addLocalPath(String url) {
		setUrl(LocalPath(url));
	}
	
}
