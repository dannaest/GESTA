package com.gesta.beans;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
 





import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder; 

import java.util.List;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

public class ParseXML { 
    
    
    public String findServletNameFromServletPath(String servletPath, ServletContext context){
        //String path = new File("").getAbsolutePath();
        String FILE_WEB_XML = "/WEB-INF/web.xml";
        Document document = null;
        Element racine = null;
        String servletName = "";
         
        
        SAXBuilder sxb = new SAXBuilder(); 
        try{
            //Path chemin3 = Paths.get();
            String realPath = context.getRealPath(FILE_WEB_XML);
            File file = new File(realPath);
            try{  
                    InputStream in = context.getResourceAsStream("FILE_WEB_XML");
                    document = sxb.build(file);
                
            }catch(SecurityException e){
                e.printStackTrace();
            }
            
        }catch( JDOMException e){
            e.printStackTrace();
        }catch( IOException e){
            e.printStackTrace();
        } 
        
        racine = (Element) document.getRootElement();
        
        //parcours de la liste
        List listServletsMapping = racine.getChildren();//.getChildren("servlet-mapping");
        Iterator i = listServletsMapping.iterator();
        while(i.hasNext()){
        	Element courant = (Element)i.next();
        	try {
        		//System.out.println(courant.getName());
        	if( courant.getName().equals("servlet-mapping")){
        		List<Element> children = courant.getChildren();
        		Iterator<Element> j = children.iterator();
        		String searchedName = "";
                while(j.hasNext()){
                	Element attr = (Element)j.next();
                	int size = attr.getText().length();
                	String pattern = attr.getName(); //System.out.println("--"+pattern);                	

                	if(pattern.equals("servlet-name") && size>0) 
                		searchedName = attr.getText();
                	if(pattern.equals("url-pattern") && attr.getText().length()>0) {
                		String buffer = attr.getText();
                		//System.out.println(buffer);
                		String []t = buffer.split("/");
                		buffer = (t.length>=3) ? "/"+t[1]+"/"+t[2] : (t.length==2) ? "/"+t[1] : "";
                		if(buffer.equals(servletPath)) {
                			servletName = searchedName;
                			break;
                		}
                	}
                } 
        	}  
        	}catch(NullPointerException e) {
        		System.out.println("exception");
        	}
        } 
        return servletName;
    }
}
