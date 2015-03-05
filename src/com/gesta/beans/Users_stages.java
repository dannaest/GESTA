package com.gesta.beans;

public class Users_stages {
	
	private Long id_stage;
	private Long id_user;
	
	public Users_stages() {}
	public Users_stages(Long id_user,Long id_stage){
		
		this.id_stage=id_stage;
		this.id_user=id_user;
		
	}
	
	
	public Long getId_stage() {
		return id_stage;
	}
	public void setId_stage(Long id_stage) {
		this.id_stage = id_stage;
	}
	public Long getId_user() {
		return id_user;
	}
	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}
	
	
	
	
	

}
