package com.gesta.beans;

/**
 * L'encadrant c'est la personne (Contact) qui g�re un stage (id_stage) au niveau de l'entreprise
 * @author Ahmed
 *
 */
public class Encadrant {
	
	private Long id_encadrant = null;
	private Long id_stage = null;
	private Long id_contact = null;
	
	public Encadrant() {	
		
	}
	public Encadrant(Long id_encadrant,Long id_stage,Long id_contact){ 
		this.id_contact=id_contact;
		this.id_encadrant=id_encadrant;
		this.id_stage=id_stage; 
	}
	
	
	
	public Long getId_encadrant() {
		return id_encadrant;
	}
	public void setId_encadrant(Long id_encadrant) {
		this.id_encadrant = id_encadrant;
	}
	public Long getId_stage() {
		return id_stage;
	}
	public void setId_stage(Long id_stage) {
		this.id_stage = id_stage;
	}
	public Long getId_contact() {
		return id_contact;
	}
	public void setId_contact(Long id_contact) {
		this.id_contact = id_contact;
	}
	

}
