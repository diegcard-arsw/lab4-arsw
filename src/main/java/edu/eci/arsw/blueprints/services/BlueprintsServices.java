/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.arsw.blueprints.filters.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {

    @Autowired
    BlueprintsPersistence bpp = null;

    @Autowired
    BlueprintFilter blueprintFilter = null;

    public void addNewBlueprint(Blueprint bp) {
        try {
            bpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Blueprint> getAllBlueprints() {
        Set<Blueprint> allBlueprints = bpp.getAllBlueprints();
        Set<Blueprint> filteredBlueprints = new HashSet<>();

        for (Blueprint bp : allBlueprints) {
            filteredBlueprints.add(blueprintFilter.filter(bp));
        }

        return filteredBlueprints;
    }

    /**
     *
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * (filtered)
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        Blueprint originalBlueprint = bpp.getBlueprint(author, name);
        return blueprintFilter.filter(originalBlueprint);
    }

    /**
     *
     * @param author blueprint's author
     * @return all the blueprints of the given author (filtered)
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> originalBlueprints = bpp.getBlueprintByAuthor(author);
        Set<Blueprint> filteredBlueprints = new HashSet<>();

        for (Blueprint bp : originalBlueprints) {
            filteredBlueprints.add(blueprintFilter.filter(bp));
        }

        return filteredBlueprints;
    }

    /**
     * Updates an existing blueprint.
     * 
     * @param author the blueprint's author
     * @param bpname the blueprint's name  
     * @param updatedBlueprint the new blueprint data
     * @throws BlueprintNotFoundException if the blueprint doesn't exist
     */
    public void updateBlueprint(String author, String bpname, Blueprint updatedBlueprint) throws BlueprintNotFoundException {
        // First verify the blueprint exists
        getBlueprint(author, bpname);
        
        // For simplicity, we'll use the existing infrastructure:
        // Create a new blueprint with the correct author/name and updated points
        try {
            Blueprint newBlueprint = new Blueprint(author, bpname, 
                updatedBlueprint.getPoints().toArray(new Point[0]));
            bpp.saveBlueprint(newBlueprint); // This will replace if exists in our current implementation
        } catch (BlueprintPersistenceException e) {
            // Blueprint already exists, which is expected for updates
            // In a more sophisticated implementation, we'd have a true update method
            throw new RuntimeException("Error updating blueprint", e);
        }
    }

}
