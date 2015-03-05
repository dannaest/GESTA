package com.gesta.servlets;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;

public class _Utilitaire {

	public Properties readProperties(String name) {
		Properties env = new Properties();
		try {
            InputStream in = this.getClass().getResourceAsStream(name);
            env.load(in);
            in.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        } 
		return env;
	}
	public List<String> getServletsList() {
		List<String> listeServlet = new ArrayList<String>();
		Properties prop = readProperties("listeServlets.properties");
		Enumeration<String> en = (Enumeration<String>) prop.propertyNames();

		while(en.hasMoreElements()) {			
			try {
				String key = en.nextElement().toString();
				if(key!=null && key.length()>0) {
					String element = prop.getProperty(key);
					listeServlet.add(element);
				}
			}catch(NoSuchElementException e) {
				e.printStackTrace();
			}catch(UnsupportedOperationException e) {
				e.printStackTrace();
			}catch(ClassCastException e) {
				e.printStackTrace();
			}catch(NullPointerException e) {
				e.printStackTrace();
			}catch(IllegalArgumentException e) {
				e.printStackTrace();
			} 
		}
		return listeServlet;
	} 
	public static Collection<ServletRegistration> getServletsCollection(HttpServlet hs){
		@SuppressWarnings("unchecked")
		Map<String,ServletRegistration> map = (Map<String, ServletRegistration>) hs.getServletContext().getServletRegistrations();
		return map.values(); 
	}
}

