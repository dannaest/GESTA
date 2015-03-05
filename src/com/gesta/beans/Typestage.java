package com.gesta.beans;

public class Typestage {
	
	private Long id_typestage;
	private String tst_nom;
	
	public Typestage() {}
	public Typestage(Long id_typestage,String tst_nom){
	
	
	this.id_typestage=id_typestage;
	this.tst_nom=tst_nom;
	
	
}	
	
	public Long getId_typestage() {
		return id_typestage;
	}
	public void setId_typestage(Long id_typestage) {
		this.id_typestage = id_typestage;
	}
	public String getTst_nom() {
		return tst_nom;
	}
	public void setTst_nom(String tst_nom) {
		this.tst_nom = tst_nom;
	}
	

}
