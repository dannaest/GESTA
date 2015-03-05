package com.gesta.ldap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gesta.beans.Contact;
import com.gesta.beans.User; 

public class LDAPConfiguration {
	public static final String FICHIER_PROPERTIES = "../../../ldap.properties";
	private HashMap<String, String> theUser = new HashMap<String,String>();
	private User utilisateur = new User(); 
	
	public LDAPConfiguration() { 
	} 
	
	public User getUtilisateur() {
		return utilisateur;
	}
	
	public boolean identify(User utilisateur) throws LDAPException {
		//map(utilisateur);
		this.utilisateur = utilisateur;
		return getLDAPInfos();
	}
	
	private void map(User utilisateur) {
		theUser.put("uid", utilisateur.getLogin());
		theUser.put("pass", utilisateur.getMdp());
		theUser.put("mail", utilisateur.getContact().getMail());
	}
	
	/**
	 * effective test with LDAP server
	 *
	 * @param theUser
	 * @return returnedValue
	 */
	private boolean getLDAPInfos() throws LDAPException{
        boolean Authentified = false; 
        Properties env = new Properties();
        
        try {
            InputStream in = this.getClass().getResourceAsStream(FICHIER_PROPERTIES);
            env.load(in);
            in.close();
        }catch(FileNotFoundException e){
            System.out.print(e.getMessage());
        }catch(IOException e){
            System.out.print(e.getMessage());
        } 
           
        String LdapBasedn = env.getProperty("ldapbasedn");
        String LoginIdentifiant = utilisateur.getLogin();//theUser.get("uid");
        String LoginMdp = utilisateur.getMdp();//theUser.get("pass");
         
        Properties Env = new Properties();
        Env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        Env.put(Context.PROVIDER_URL, env.getProperty("ldaphost"));
        Env.put(Context.SECURITY_AUTHENTICATION, "simple");
        Env.put(Context.SECURITY_PRINCIPAL, "uid=" + LoginIdentifiant + "," + LdapBasedn);
        Env.put(Context.SECURITY_CREDENTIALS,  LoginMdp);
        
        try{
            if (LoginIdentifiant != null && LoginMdp != null) {

                DirContext Ctx = new InitialDirContext(Env);
                SearchControls Constraints = new SearchControls();
                Constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
                NamingEnumeration<SearchResult> Results = Ctx.search(LdapBasedn, "(uid=" + LoginIdentifiant + ")", Constraints);

                if (Results != null && Results.hasMore()) {
                    Authentified = true;
                    SearchResult sr = (SearchResult)Results.next();
                    Attributes attrs = sr.getAttributes();
                    String nom = getLDAPAttribute( attrs, "sn"); //sr.getAttributes().get("sn").get().toString();
                    String prenom = getLDAPAttribute( attrs, "givenname"); //sr.getAttributes().get("givenname").get().toString();
                    String mail = getLDAPAttribute( attrs, "edupersonprincipalname");// sr.getAttributes().get("edupersonprincipalname").get().toString();
                    String affiliation = getLDAPAttribute( attrs, "edupersonprimaryaffiliation");//sr.getAttributes().get("edupersonprimaryaffiliation").get().toString();
                    String niveau = getLDAPAttribute( attrs, "centraleetuformation");//sr.getAttributes().get("centraleetuformation").get().toString();
                    String naissance = getLDAPAttribute( attrs, "centraleetudatenaissance");//sr.getAttributes().get("centraleetudatenaissance").get().toString();
                    String civilite = getLDAPAttribute( attrs, "supanncivilite");//sr.getAttributes().get("supanncivilite").get().toString();
                    String numEtudiant = getLDAPAttribute( attrs, "supannetuid");//sr.getAttributes().get("supannetuid").get().toString();
                    String cursus = getLDAPAttribute( attrs, "centraleetucursus");//sr.getAttributes().get("centraleetucursus").get().toString();
                    String groupe = getLDAPAttribute( attrs, "centraleetugroupe");//sr.getAttributes().get("centraleetugroupe").get().toString();
                    
                    Contact infosUser = new Contact(null,civilite,nom,prenom,mail," "); 
                    utilisateur.setContact(infosUser);
                    utilisateur.setLogin(LoginIdentifiant);
                    utilisateur.setMdp(LoginMdp);
                    utilisateur.setAffiliation(affiliation);
                    utilisateur.setNiveau(niveau);
                    utilisateur.setNaissance(naissance);
                    utilisateur.setNumEtudiant(numEtudiant);
                    utilisateur.setCursus(cursus);
                    utilisateur.setGroupe(groupe);
                    
                    
                    System.out.println("Nom : "+nom);
                    System.out.println("Affiliation : "+affiliation);
                    System.out.println("Formation : "+niveau);
                    System.out.println("Date de naissance : "+naissance);
                    System.out.println("Civilté : "+civilite);
                    System.out.println("Numéro étudiant : "+numEtudiant);
                    System.out.println("Cursus : "+cursus);
                    System.out.println("Groupe : "+groupe);
                }
                Ctx.close();
            }
        } catch (CommunicationException ce) {
            throw new LDAPException("LDAP indisponible");
        } catch (NamingException ne) {
            throw new LDAPException("Login ou mot de passe incorrect");
        }  

        return Authentified;
    }

    /**
     * Get an LDAP attribute and put it into theUser
     *
     * @param theUser
     * @param attrs
     * @param element
     * @param savedElement
     */
    private static String getLDAPAttribute(Attributes attrs, String element) {
        Attribute attr = attrs.get(element); 
        if (attr != null) {
            try {
                String info = (String) (attr.get());
                if (info != null) {
                    return info;
                }
            } catch (NamingException ex) {
                Logger.getLogger(SecurityManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "";
    }

}
