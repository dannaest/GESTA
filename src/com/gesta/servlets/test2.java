package com.gesta.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gesta.beans.User;
import com.gesta.ldap.LDAPConfiguration;

public class test2 extends HttpServlet {
	/**
	* @see HttpServlet#HttpServlet()
    */
   public test2() {
       super();
       // TODO Auto-generated constructor stub
   }
   
   /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User utilisateur = new User();
		utilisateur.setLogin("angangou");
		utilisateur.setMdp("Machine1607");
		utilisateur.getContact().setMail("alain.ngangoue-nyangwile@eleves.ec-nantes.fr");
		
		LDAPConfiguration ldap = new LDAPConfiguration();
		
		String message = "";
		if(ldap.identify(utilisateur))
			message = "connexion ok";
		else
			message = "connexion ko";
		request.setAttribute("reponseLDAP", message);
		
		
		
		//this.getServletContext().getRequestDispatcher( "/WEB-INF/forms/userEdit.jsp" ).forward( request, response );
		//this.getServletContext().getRequestDispatcher( "/WEB-INF/forms/stage/stageEdit.jsp" ).forward( request, response );
		this.getServletContext().getRequestDispatcher( "/WEB-INF/testLDAP.jsp" ).forward( request, response );
	}
}
