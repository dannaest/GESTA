package com.gesta.dao;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;
/**
 * 
 * @author Nacder
 * @Role Interface avec la base de donn�es. Les 5 m�thodes ci-dessous, dites CRUD, d�finissent le contrat
 * entre l'application et le syst�me de stockage.
 */
public interface DAO {
	<T>long creerMere( T objet, String[] values, boolean returnGeneratedKeys) throws DAOException;
	
	<T>ResultSet trouverMere( String[] fields, String[] values ) throws DAOException;
	
	<T>ResultSet listerMere( String[] fields, String[] values ) throws DAOException;
	
	<T>int mettreAJourMere(Long id, String[] fields, String[] values ) throws DAOException;
	
	/*Pour les tables avec 2 cl�s primaires*/
	<T>int mettreAJourMere(Long id1, Long id2, String[] fields, String[] values, String[] whereFields ) throws DAOException;
	
	<T>int supprimerMere(Long id, String[] fields, String[] values ) throws DAOException;
	
	/*Pour les tables avec 2 cl�s primaires*/
	<T>int supprimerMere(Long id1, Long id2, String[] fields, String[] values ) throws DAOException;
	
	public <T>ResultSet customised(Hashtable<String, String> hash) throws DAOException;
}
