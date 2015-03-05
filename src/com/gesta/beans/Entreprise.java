package com.gesta.beans;
 

public class Entreprise { 
	private Long id_entreprise;
	private Long id_typeentreprise;
	private Long id_tailleentreprise;
	private String ent_nom;
	private String ent_siret;
	private String ent_secteur;
	private String ent_groupe;
	private Long id_ent_representant;//déprécié
	private Long id_ent_rh;//déprécié
	private Long id_lieu; //déprécié
	private Localisation lieu = null; 
	private Contact cnt_representant;
	private Contact cnt_rh;
	
	public Entreprise(Long id,Long id_type,Long id_tailleentreprise,Long id_ent_representant,Long id_ent_rh, Long id_lieu,String ent_nom,String ent_siret,String ent_secteur,String ent_groupe ) {
	    	this.id_entreprise = id;
	    	this.id_typeentreprise =id_type ;
	    	this.id_tailleentreprise =id_tailleentreprise ;
	    	//this.id_ent_representant= id_ent_representant;
	    	//this.id_ent_rh = id_ent_rh;
	    	//this.id_lieu = id_lieu ;
	    	this.ent_nom =ent_nom ;
	    	this.ent_siret =ent_siret ;
	    	this.ent_secteur =ent_secteur ;
	    	this.ent_groupe =ent_groupe ;
	    	
	    }  
	
	 public Entreprise(Entreprise ent ) {
	    	this.id_entreprise = ent.getId();
	    	this.id_typeentreprise =ent.getId_typeentreprise() ;
	    	this.id_tailleentreprise =ent.getId_tailleentreprise() ;
	    	//this.id_ent_representant= ent.getIdent_representant();
	    	//this.id_ent_rh = ent.getIdent_rh();
	    	//this.id_lieu = ent.getIdlieu();
	    	this.ent_nom =ent.getEnt_nom() ;
	    	this.ent_siret =ent.getEnt_siret() ;
	    	this.ent_secteur =ent.getEnt_secteur() ;
	    	this.ent_groupe =ent.getEnt_groupe() ;
	    	setLieu(ent.getLieu());
	    	setCnt_Representant(ent.getCnt_Representant());
	    	setCnt_Rh(ent.getCnt_Rh());
	    	
	    }  
	
    public Entreprise() {
		 
	}

	public Long getId_entreprise() {
		return id_entreprise;
	}
	
	public void setId_entreprise(Long id_entreprise) {
		this.id_entreprise = id_entreprise;
	}

	public Long getId_typeentreprise() {
		return id_typeentreprise;
	}

	public void setId_typeentreprise(Long id_typeentreprise) {
		this.id_typeentreprise = id_typeentreprise;
	}

	public Long getId_tailleentreprise() {
		return id_tailleentreprise;
	}

	public void setId_tailleentreprise(Long id_tailleentreprise) {
		this.id_tailleentreprise = id_tailleentreprise;
	}

	/**  @deprecated use {@link #getCnt_Representant()} instead  */
	public Long getId_ent_representant() {		return id_ent_representant;	}
	/** Remplace getId_ent_representant  */
	public Contact getCnt_Representant() {		return cnt_representant;	}
	/**  @deprecated use {@link #setCnt_Representant(Contact)} instead   */
	public void setId_ent_representant(Long id_ent_representant) {		this.id_ent_representant = id_ent_representant;	}
	/** Remplace setId_ent_representant  */
	public void setCnt_Representant(Contact cnt) { cnt_representant = cnt;	}
	
	/**  @deprecated use {@link #getCnt_Rh()} instead    */
	public Long getId_ent_rh() {		return id_ent_rh;	}
	/** Remplace getId_ent_rh  */
	public Contact getCnt_Rh() {		return cnt_rh;	} 
	/**  @deprecated  use {@link #setCnt_Rh(Contact)} instead   */
	public void setId_ent_rh(Long id_ent_rh) {		this.id_ent_rh = id_ent_rh;	}
	/** Remplace setId_ent_rh  */
	public void setCnt_Rh(Contact cnt) { cnt_rh = cnt;	}
	
	/**  @deprecated  use {@link #getLieu()} instead   */
	public Long getId_lieu() {		return id_lieu;	}
	/** Remplace getId_lieu  */
	public Localisation getLieu() {		return lieu;	} 
	/**  @deprecated use {@link #setLieu(Localisation)} instead    */
	public void setId_lieu(Long id_lieu) {		this.id_lieu = id_lieu;	}
	/** Remplace setId_lieu  */
	public void setLieu(Localisation lieu) {  this.lieu = lieu;	} 

	public String getEnt_nom() {
		return ent_nom;
	}

	public void setEnt_nom(String ent_nom) {
		this.ent_nom = ent_nom;
	}

	public String getEnt_siret() {
		return ent_siret;
	}

	public void setEnt_siret(String ent_siret) {
		this.ent_siret = ent_siret;
	}

	public String getEnt_secteur() {
		return ent_secteur;
	}

	public void setEnt_secteur(String ent_secteur) {
		this.ent_secteur = ent_secteur;
	}

	public String getEnt_groupe() {
		return ent_groupe;
	}

	public void setEnt_groupe(String ent_groupe) {
		this.ent_groupe = ent_groupe;
	}

	public void setId(Long id) {
    	this.id_entreprise = id;
    }
    public Long getId() {
    	return this.id_entreprise;
    }
    
	
	

}
