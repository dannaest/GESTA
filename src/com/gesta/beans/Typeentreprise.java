package com.gesta.beans;

public class Typeentreprise {
	
	private Long id_typeentreprise;
	private String ten_nom;
	
	public Typeentreprise() {}
	
	public Typeentreprise(Long id_typeentreprise,String ten_nom){
		
		this.id_typeentreprise=id_typeentreprise;
		this.ten_nom=ten_nom;
		
	}
	public Long getId_typeentreprise() {
		return id_typeentreprise;
	}
	public void setId_typeentreprise(Long id_typeentreprise) {
		this.id_typeentreprise = id_typeentreprise;
	}
	public String getTen_nom() {
		return ten_nom;
	}
	public void setTen_nom(String ten_nom) {
		this.ten_nom = ten_nom;
	}
	

}
