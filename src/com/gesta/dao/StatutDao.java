package com.gesta.dao;

import java.util.List;
 
import com.gesta.beans.Statut;

public interface StatutDao {
	long creer( Statut statut ) throws DAOException;
	
	List<Statut> trouver( Long id ) throws DAOException;
	
	List<Statut> lister( String[] fields, String[] values ) throws DAOException;
	
	boolean mettreAJour(Long id) throws DAOException;
	
	boolean supprimer(Long id) throws DAOException;
	
	/**
	 * Ajoute une entrée à la table users_statuts. Cela permet d'attribuer le statut id_statut à l'utilisateur id
	 * @param id : l'id de l'utilisateur dans la table users
	 * @param id_statut : l'id du statut dans la table statuts
	 * @return Le statut d'exécution de la requête 
	 * @throws DAOException  En cas d'échec d'exécution de la requête SQL, l'interface DAO transmet l'erreur.
	 */
	int creerUserStatut(Long id, Long id_statut) throws DAOException ;
	
	/**
	 * Ajoute une entrée à la table statuts_actions. Cela permet d'attribuer à un statut id_statut une action id_aledroit
	 * @param id_aledroit : l'id de l'action dans la table aledroit
	 * @param id_statut : l'id du statut dans la table statuts
	 * @return Le statut d'exécution de la requête 
	 * @throws DAOException  En cas d'échec d'exécution de la requête SQL, l'interface DAO transmet l'erreur.
	 */
	int creerStatutAction(Long id_statut, Long id_aledroit) throws DAOException ;

}
