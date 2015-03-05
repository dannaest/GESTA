package com.gesta.constantes;

public class Constantes { 
	/*Affiliations renvoy�es par LDAP*/
	public static final String AFFILIATION_ELEVE = "student";
	public static final String AFFILIATION_ENSEIGNANT = "enseignant"; 
	public static final String AFFILIATION_RESPO_STAGE = "Responsable de Stage"; 
	public static final String AFFILIATION_ADMINISTRATEUR = "administrateur";
	
	/*Liste des diff�rents statuts*/
	public static final long STATUT_ADMINISTRATEUR = 1;
	public static final long STATUT_RESPO_STAGE = 2;
	public static final long STATUT_ENSEIGNANT = 6;
	public static final long STATUT_ELEVE = 7;
	
	/*Liste des m�thodes DO d'une servlet*/
	public static final String SERVLET_METHOD_GET = "do";
	public static final String SERVLET_METHOD_POST = "post";
	public static final String SERVLET_METHOD_HEAD = "head"; //Permet l'affichage des informations d'ent�tes de la requ�te.
	public static final String SERVLET_METHOD_DELETE = "delete"; // Permet la suppresion d'un document sur le serveur.
	public static final String SERVLET_METHOD_OPTIONS = "options"; // Options d�termine le niveau HTTP support� par le serveur.
	public static final String SERVLET_METHOD_PUT = "put"; // Chargement d'un fichier sur le serveur � partir du client
	public static final String SERVLET_METHOD_TRACE = "trace"; // Permet d'afficher la trace de la requ�te.
	
	public static final int ENTREPRISE_NUMERO_SIRET = 14;
	
	/*Liste des états de validations*/
	public static final long STAGE_VALIDATION_ENSAISIE = 1;
	public static final long STAGE_VALIDATION_ENCOURS = 2;
	public static final long STAGE_VALIDATION_PUBLIC = 3;
}
