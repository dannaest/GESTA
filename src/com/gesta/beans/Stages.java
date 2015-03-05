package com.gesta.beans;

import java.sql.Date; 
import java.sql.Timestamp;

public class Stages {
	
	private Long id_stage;
	private Long id_typestage;
	private Long id_entreprise;
	private Long id_typeconvention;
	private Long id_etatvalidation; 
	private String stg_sujet;
	private String stg_objet;
	private String stg_domaine;
	private String stg_resume;
	private Timestamp stg_datesoutenance;
	private Date stg_date_debut;
	private Date stg_date_fin;
	private int stg_duree;
	private String stg_gratification;
	private String stg_competences;
	private String stg_horaires;
	private String stg_jours;
	private int stg_h_week;
	private int stg_w_ferie;
	private int stg_nuit;
	private String stg_avantages; 
	private String jury;
	private Localisation lieu = null;  
	private Contact ENC = null;
	Entreprise entreprise = null;
	 
    public Stages() {  }
	   
	 public Stages(Stages stage){
		this.id_stage = stage.getId_stage();
    	this.id_typestage =stage.getId_typestage();
    	this.id_entreprise =stage.getId_entreprise() ;
    	this.stg_sujet= stage.get_sujet();
    	this.stg_objet = stage.get_objet();
    	this.stg_domaine =stage.get_domaine() ;
    	this.stg_resume =stage.get_resume() ;
    	this.stg_datesoutenance =stage.get_datesoutenance() ;
    	this.id_typeconvention =stage.getId_typeconvention() ;
    	this.id_etatvalidation =stage.getId_etatvalidation() ;
    	this.stg_date_debut=stage.get_date_debut();
    	this.stg_date_fin=stage.get_date_fin();
    	this.stg_duree=stage.get_duree();
    	this.stg_gratification=stage.get_gratification();
    	this.stg_competences=stage.get_competences();
    	this.stg_horaires=stage.get_horaires();
    	this.stg_jours=stage.get_jours();
    	this.stg_h_week=stage.get_h_week();
    	this.stg_w_ferie=stage.get_nuit();
    	this.stg_nuit=stage.get_nuit();
    	this.stg_avantages=stage.get_avantages();
    	lieu = stage.getLieu();
    	entreprise = stage.getEntreprise(); 
    	ENC = stage.getContactENC();
		 
	 }
	
	    
	public Date get_date_debut() {
		return stg_date_debut;
	}
	public void set_date_debut(Date stg_date_debut) {
		this.stg_date_debut = stg_date_debut;
	}
	public Date get_date_fin() {
		return stg_date_fin;
	}
	public void set_date_fin(Date stg_date_fin) {
		this.stg_date_fin = stg_date_fin;
	}
	public int get_duree() {
		return stg_duree;
	}
	public void set_duree(int stg_duree) {
		this.stg_duree = stg_duree;
	}
	public String get_gratification() {
		return stg_gratification;
	}
	public void set_gratification(String buffer) {
		this.stg_gratification = buffer;
	}
	public String get_competences() {
		return stg_competences;
	}
	public void set_competences(String stg_competences) {
		this.stg_competences = stg_competences;
	}
	public String get_horaires() {
		return stg_horaires;
	}
	public void set_horaires(String stg_horaires) {
		this.stg_horaires = stg_horaires;
	}
	public String get_jours() {
		return stg_jours;
	}
	public Long getId_stage() {
		return id_stage;
	}
	public void setId_stage(Long id_stage) {
		this.id_stage = id_stage;
	}
	public Long getId_typestage() {
		return id_typestage;
	}
	public void setId_typestage(Long id_typestage) {
		this.id_typestage = id_typestage;
	}
	public Long getId_entreprise() {
		return id_entreprise;
	}
	public void setId_entreprise(Long id_entreprise) {
		this.id_entreprise = id_entreprise;
	}
	public String get_sujet() {
		return stg_sujet;
	}
	public void set_sujet(String stg_sujet) {
		this.stg_sujet = stg_sujet;
	}
	public String get_objet() {
		return stg_objet;
	}
	public void set_objet(String stg_objet) {
		this.stg_objet = stg_objet;
	}
	public String get_domaine() {
		return stg_domaine;
	}
	public void set_domaine(String stg_domaine) {
		this.stg_domaine = stg_domaine;
	}
	public String get_resume() {
		return stg_resume;
	}
	public void set_resume(String stg_resume) {
		this.stg_resume = stg_resume;
	}
	public Timestamp get_datesoutenance() {
		return stg_datesoutenance;
	}
	public void set_datesoutenance(Timestamp stg_datesoutenance) {
		this.stg_datesoutenance = stg_datesoutenance;
	}
	public Long getId_typeconvention() {
		return id_typeconvention;
	}
	public void setId_typeconvention(Long id_typeconvention) {
		this.id_typeconvention = id_typeconvention;
	}
	public Long getId_etatvalidation() {
		return id_etatvalidation;
	}
	public void setId_etatvalidation(Long id_etatvalidation) {
		this.id_etatvalidation = id_etatvalidation;
	}
	public void set_jours(String stg_jours) {
		this.stg_jours = stg_jours;
	}
	public int get_h_week() {
		return stg_h_week;
	}
	public void set_h_week(int stg_h_week) {
		this.stg_h_week = stg_h_week;
	}
	public int get_w_ferie() {
		return stg_w_ferie;
	}
	public void set_w_ferie(int stg_w_ferie) {
		this.stg_w_ferie = stg_w_ferie;
	}
	public int get_nuit() {
		return stg_nuit;
	}
	public void set_nuit(int stg_nuit) {
		this.stg_nuit = stg_nuit;
	}
	public String get_avantages() {
		return stg_avantages;
	}
	public void set_avantages(String stg_avantages) {
		this.stg_avantages = stg_avantages;
	} 
	public String get_jury() {
		return jury;
	}
	public void set_jury(String jury) {
		this.jury = jury;
	}
	public Localisation getLieu() {
		return lieu;
	}
	public void setLieu(Localisation lieu) {
		this.lieu = lieu;
	} 
	public Contact getContactENC() {
		return ENC;
	}
	public void setContactENC(Contact enc) {
		ENC = enc;
	}
	public Entreprise getEntreprise() {return entreprise;}
	public void setEntreprise(Entreprise ent) { entreprise = ent;}
}
