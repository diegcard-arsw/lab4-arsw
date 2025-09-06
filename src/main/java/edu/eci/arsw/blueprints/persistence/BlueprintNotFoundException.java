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
public class BlueprintNotFoundException extends Exception {

    /**
     * Constructs a new BlueprintNotFoundException with the specified detail
     * message.
     *
     * @param message the detail message explaining the reason for the
     * exception.
     */
    public BlueprintNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception thrown when a requested blueprint cannot be found in the
     * persistence layer.
     *
     * @param message the detail message explaining the reason for the
     * exception.
     * @param cause the underlying cause of the exception.
     */
    public BlueprintNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
