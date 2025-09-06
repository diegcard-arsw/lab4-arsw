/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence;

/**
 *
 * @author Diego Cardenas
 */
public class BlueprintPersistenceException extends Exception {

    /**
     * Constructs a new BlueprintPersistenceException with the specified detail
     * message.
     *
     * @param message the detail message explaining the reason for the
     * exception.
     */
    public BlueprintPersistenceException(String message) {
        super(message);
    }

    /**
     * Constructs a new BlueprintPersistenceException with the specified detail
     * message and cause.
     *
     * @param message the detail message explaining the reason for the
     * exception.
     * @param cause the underlying cause of the exception (which is saved for
     * later retrieval by the {@link Throwable#getCause()} method).
     */
    public BlueprintPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

}
