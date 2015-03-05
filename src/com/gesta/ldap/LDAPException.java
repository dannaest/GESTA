package com.gesta.ldap;

public class LDAPException extends RuntimeException {
    /*
    * Constructeurs
    */
   public LDAPException( String message ) {
       super( message );
   }

   public LDAPException( String message, Throwable cause ) {
       super( message, cause );
   }

   public LDAPException( Throwable cause ) {
       super( cause );
   }
}
