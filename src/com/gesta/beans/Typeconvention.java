package com.gesta.beans;

public class Typeconvention {
	private Long id_typeconvention;
	private String tcv_nom;
	public Typeconvention() {}
	
	public Typeconvention(Long id_typeconvention,String tcv_nom){
		this.id_typeconvention=id_typeconvention;
		this.tcv_nom=tcv_nom;
	}
	
	public Long getId_typeconvention() {
		return id_typeconvention;
	}
	public void setId_typeconvention(Long id_typeconvention) {
		this.id_typeconvention = id_typeconvention;
	}
	public String getTcv_nom() {
		return tcv_nom;
	}
	public void setTcv_nom(String tcv_nom) {
		this.tcv_nom = tcv_nom;
	}
	

}
