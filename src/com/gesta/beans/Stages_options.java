package com.gesta.beans;

public class Stages_options {
	
	private Long id_stage;
	private Long id_option;
	
	public Stages_options() {}
	
	public Stages_options(Long id_stage, Long id_option){ 
		this.id_option =id_option;
		this.id_stage=id_stage; 
	}
	
	
	public Long getId_stage() {
		return id_stage;
	}
	public void setId_stage(Long id_stage) {
		this.id_stage = id_stage;
	}
	public Long getId_option() {
		return id_option;
	}
	public void setId_option(Long id_option) {
		this.id_option = id_option;
	}
	
	
	
	

}
