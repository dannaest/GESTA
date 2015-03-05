package com.gesta.beans;

public class Users_statuts {
	private Long id_user;
	private Long id_statut;
	
	public Users_statuts(Long id_user,Long id_statut){ 
		setId_user( id_user);
		setId_statut( id_statut);		
	}
	
	public Long getId_user() {
		return id_user;
	}
	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}
	public Long getId_statut() {
		return id_statut;
	}
	public void setId_statut(Long id_statut) {
		this.id_statut = id_statut;
	}
	
}
