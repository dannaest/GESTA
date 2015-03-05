package com.gesta.beans;

public class Users_options {
	public Long id_user;
	public Long id_option;
	
	public Users_options() {}
	public Users_options(Long id_user,Long id_opiton){
		
		this.id_option=id_opiton;
		this.id_user=id_user;
				
		
	}
	
	
	public Long getId_user() {
		return id_user;
	}
	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}
	public Long getId_option() {
		return id_option;
	}
	public void setId_option(Long id_option) {
		this.id_option = id_option;
	}
	
}
