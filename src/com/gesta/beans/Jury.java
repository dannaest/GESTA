package com.gesta.beans;

public class Jury {
	
	
	private Long id_stage;
	private Long id_contact;
	
	public Jury() {}
	public Jury(Long id_stage,Long id_contact){
		
		this.id_contact=id_contact;
		this.id_stage=id_stage;
		
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
