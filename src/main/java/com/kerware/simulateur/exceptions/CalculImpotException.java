package com.kerware.simulateur.exceptions;

/**
 * Exception spécifique pour les erreurs de calcul d'impôt.
 */
public class CalculImpotException extends RuntimeException {
    public CalculImpotException(String message) {
        super(message);
    }
}