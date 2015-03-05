package com.gesta.forms;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class GestionFormulaire {
    protected String              resultat;
    protected Map<String, String> erreurs      = new HashMap<String, String>();


    /*
     * M�thode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    protected static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
    protected static Hashtable<String,String> getValeurChampContact( HttpServletRequest request, String prefix ) {
        Hashtable<String,String> tab = new Hashtable<String,String>();
    	String valeur;  
    	
    	tab.put("prefix", prefix);
    	valeur = request.getParameter( prefix+"_cnt_nom" );
    	tab.put("cnt_nom",( valeur == null  ) ? "" : valeur);
    	
    	valeur = request.getParameter( prefix+"_cnt_prenom" );
    	tab.put("cnt_prenom",( valeur == null  ) ? "" : valeur);
    	
    	valeur = request.getParameter( prefix+"_cnt_mail" );
    	tab.put("cnt_mail",( valeur == null  ) ? "" : valeur);
    	
    	valeur = request.getParameter( prefix+"_cnt_tel" );
    	tab.put("cnt_tel",( valeur == null  ) ? "" : valeur);
    	return tab;
    }
    protected static Hashtable<String,String> getValeurChampLocalisation( HttpServletRequest request, String prefix ) {
        Hashtable<String,String> tab = new Hashtable<String,String>();
    	String valeur; 
    	
    	tab.put("prefix", prefix);
    	valeur = request.getParameter( prefix+"_lieu_numero" );
    	tab.put("lieu_numero",( valeur == null  ) ? "" : valeur);
    	
    	valeur = request.getParameter( prefix+"_lieu_voie" );
    	tab.put("lieu_voie",( valeur == null  ) ? "" : valeur);
    	
    	valeur = request.getParameter( prefix+"_lieu_ville" );
    	tab.put("lieu_ville",( valeur == null  ) ? "" : valeur);
    	
    	valeur = request.getParameter( prefix+"_lieu_cp" );
    	tab.put("lieu_cp",( valeur == null  ) ? "" : valeur);
    	
    	valeur = request.getParameter( prefix+"_lieu_pays" );
    	tab.put("lieu_pays",( valeur == null  ) ? "" : valeur);
    	return tab;
    }
    
    public void setResultat(String resultat) {
    	this.resultat = resultat;
    }
    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }/*
     * Ajoute un message correspondant au champ sp�cifi� � la map des erreurs.
     */
    protected void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    /**
     * 
     * @param stringSime chaine au format 
     * @return
     */
    public static Timestamp stringToTimestamp(String stringSime) {
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	Date date = new Date(0);
		try {
			date = (Date) dateFormat.parse(stringSime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	long time = date.getTime();
    	return new Timestamp(time);
    }
    /**
     * 
     * @param stringSime chaine au format 
     * @return
     * @throws FormValidationException 
     */
    public static java.sql.Date stringToDate(String stringtime) throws FormValidationException {
    	SimpleDateFormat  dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    	java.sql.Date date = null; 
    	if(stringtime!=null) {
			try {
				ParsePosition pp = new ParsePosition(0);
				date = new java.sql.Date( dateFormatter.parse(stringtime,pp).getTime()); 
			} catch (NullPointerException|IllegalArgumentException e) { 
				throw new FormValidationException("La date saisie est incorrecte.");
			}
    	}
		return date;
    }
    /**
     * 
     * @param stringSime chaine au format 
     * @return
     * @throws FormValidationException 
     */
    public static String DateToString(java.sql.Date date) throws Exception {
    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
    	if(date!=null) {
			try {
				return df.format(date);
			} catch (NullPointerException|IllegalArgumentException e) { 
				throw new FormValidationException("La date saisie est incorrecte.");
			}
    	}
		return "";
    }
    /** */
    public static Date TimestampToDate(Timestamp time) {
    	if(time!=null)
    		return new java.sql.Date(time.getTime()); 
    	return new java.sql.Date(0);
    }

}
