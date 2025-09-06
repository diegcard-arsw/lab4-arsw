/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence;

import java.util.Set;

import edu.eci.arsw.blueprints.model.Blueprint;

/**
 * Interface defining the contract for blueprint persistence operations.
 *
 * @author Diego Cardenas
 */
public interface BlueprintsPersistence {

    /**
     *
     * @param bp the new blueprint
     * @throws BlueprintPersistenceException if a blueprint with the same name
     * already exists, or any other low-level persistence error occurs.
     */
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException;

    /**
     *
     * @param author blueprint's author
     * @param bprintname blueprint's author
     * @return the blueprint of the given name and author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException;

    /**
     * Retrieves all blueprints created by the specified author.
     *
     * @param author the name of the author whose blueprints are to be retrieved
     * @return a set of {@link Blueprint} objects associated with the given
     * author
     * @throws BlueprintNotFoundException if no blueprints are found for the
     * specified author
     */
    public Set<Blueprint> getBlueprintByAuthor(String author) throws BlueprintNotFoundException;
}
