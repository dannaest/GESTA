package com.gesta.beans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.gesta.beans.Contact;

public class User {
	private Long      id_user;
    private String    login;
    private String    mdp; 
    private Timestamp date_inscription; 
    private String affiliation;
    private String niveau;
    private String naissance; 
    private String numEtudiant;
    private String cursus;
    private String groupe;
    
    private Contact contact; 
    private List<Statut> statuts = new ArrayList<Statut>();
    
    public User() {
    	contact = new Contact(Long.getLong("0"),"","","","",""); 
    }
    public User (User u) {
    	id_user = (u.getId()!=null) ? u.getId() : null;
    	login = u.getLogin();
    	mdp = u.getMdp();
    	date_inscription = u.getDate_inscription();
    	contact = u.getContact();  
    	statuts = u.getStatuts();
    }
    public User get() {
    	return this;
    }
    
    public void setId(Long id) {
    	this.id_user = id;
    }
    public Long getId() {
    	return this.id_user;
    }
    public void setLogin(String login) {
    	this.login = login;
    }
    public String getLogin() {
    	return this.login;
    }
    public void setMdp(String mdp) {
    	this.mdp = mdp;
    }
    public String getMdp() {
    	return this.mdp;
    }
    public void setDate_inscription(Timestamp inscription) {
    	this.date_inscription = inscription;
    }
    public Timestamp getDate_inscription() {
    	return this.date_inscription;
    }
    public void setAffiliation(String affiliation) {
    	this.affiliation = affiliation;
    }
    public String getAffiliation() {
    	return this.affiliation;
    }
    public void setNiveau(String niveau) {
    	this.niveau = niveau;
    }
    public String getNiveau() {
    	return this.niveau;
    }
    public void setNaissance(String naissance) {
    	this.naissance = naissance;
    }
    public String getNaissance() {
    	return this.naissance;
    }
    public void setNumEtudiant(String numEtudiant) {
    	this.numEtudiant = numEtudiant;
    }
    public String getNumEtudiant() {
    	return this.numEtudiant;
    }
    public void setCursus(String cursus) {
    	this.cursus = cursus;
    }
    public String getCursus() {
    	return this.cursus;
    }
    public void setGroupe(String groupe) {
    	this.groupe = groupe;
    }
    public String getGroupe() {
    	return this.groupe;
    }
    
    // Gestion de la propriété Contact
    public void setContact(Contact contact) {
    	if(contact != null)
    		this.contact = contact;
    }
    public Contact getContact() {
    	return this.contact;
    }
    
    // Gestion de la propriété Statut
    public void setStatuts(List<Statut> statuts) {
    	this.statuts = statuts != null ? statuts : new ArrayList<Statut>();
    }
    public void addStatut(Statut statut) {
    	if(statut!=null)
    		this.statuts.add(statut);
    }
    public void deleteStatut(Statut statut) {    	
    	int index = 0;
    	try{
    		index = this.statuts.indexOf(statut);
    	}catch(ClassCastException e) {
    		throw new BeansException("Le type de l'objet statut n'est pas celui attendu.");
    	}catch(NullPointerException e) {
    		throw new BeansException("L'objet demandé vaut null, il n'a pas été initialisé. ");
		} 
    	this.statuts.remove(index);
    }
    public List<Statut> getStatuts() {
    	return this.statuts;
    }
}
