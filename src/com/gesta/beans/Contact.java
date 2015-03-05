package com.gesta.beans;

import java.util.Hashtable;

public class Contact {
	private Long      id_contact; 
	private String 	  civilite; 
    private String    nom;
    private String    prenom;
    private String    mail;
    private String    tel;
    /**
     * 
     * @param id
     * @param civilite
     * @param nom
     * @param prenom
     * @param mail
     * @param tel
     */
    public Contact(Long id, String civilite, String nom, String prenom, String mail, String tel) {
    	this.id_contact = id;
    	this.civilite = civilite;
    	this.nom = nom;
    	this.prenom = prenom;
    	this.mail = mail;
    	this.tel = tel;
    }  
    public Contact(Hashtable<String,String> contact) { 
    	nom = contact.get("cnt_nom");
    	prenom = contact.get("cnt_prenom");
    	mail = contact.get("cnt_mail");
    	tel = contact.get("cnt_tel");
    }
    
    public void setId(Long id) {
    	this.id_contact = id;
    }
    public Long getId() {
    	return this.id_contact;
    }
    public void setCivilite(String civilite) {
    	this.civilite = civilite;
    }
    public String getCivilite() {
    	return this.civilite;
    }
    public void setNom(String nom) {
    	this.nom = nom;
    }
    public String getNom() {
    	return this.nom;
    }
    public void setPrenom(String prenom) {
    	this.prenom = prenom;
    }
    public String getPrenom() {
    	return this.prenom;
    }
    public void setMail(String mail) {
    	this.mail = mail;
    }
    public String getMail() {
    	return this.mail;
    }
    public void setTel(String tel) {
    	this.tel = tel;
    }
    public String getTel() {
    	return this.tel;
    } 
}
