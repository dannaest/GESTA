package com.gesta.beans;

import java.sql.Date;

public class Stage_lieu {
	private Long id_stage;
	private Long id_lieu;
	private Date stl_debut;
	private Date stl_fin;
	public Stage_lieu() {}
	public Stage_lieu(Long id_stage,Long id_lieu,Date stl_debut,Date stl_fin){
		
		this.id_lieu=id_lieu;
		this.id_stage=id_stage;
		this.stl_debut=stl_debut;
		this.stl_fin=stl_fin; 
	} 
	public Long getId_stage() {
		return id_stage;
	}
	public void setId_stage(Long id_stage) {
		this.id_stage = id_stage;
	}
	public Long getId_lieu() {
		return id_lieu;
	}
	public void setId_lieu(Long id_lieu) {
		this.id_lieu = id_lieu;
	}
	public Date getStl_debut() {
		return stl_debut;
	}
	public void setStl_debut(Date stl_debut) {
		this.stl_debut = stl_debut;
	}
	public Date getStl_fin() {
		return stl_fin;
	}
	public void setStl_fin(Date stl_fin) {
		this.stl_fin = stl_fin;
	}
 
}
