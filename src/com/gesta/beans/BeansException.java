package com.gesta.beans;

public class BeansException extends RuntimeException {
	/*
     * Constructeurs
     */
    public BeansException( String message ) {
        super( message );
    }

    public BeansException( String message, Throwable cause ) {
        super( message, cause );
    }

    public BeansException( Throwable cause ) {
        super( cause );
    }
}
