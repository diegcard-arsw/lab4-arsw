/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

/**
 * In-memory implementation of the BlueprintsPersistence interface. This class
 * provides methods to store and retrieve blueprints from memory.
 *
 * @author Diego Cardenas
 */
@Repository
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final Map<Tuple<String, String>, Blueprint> blueprints = new HashMap<>();

    /**
     * Constructs an instance of InMemoryBlueprintPersistence and loads stub
     * data into memory. Initializes the blueprints map with a sample Blueprint
     * object for testing or demonstration purposes. The sample Blueprint is
     * created with a predefined author, name, and a set of points.
     */
    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts = new Point[]{new Point(140, 140), new Point(115, 115)};
        Blueprint bp = new Blueprint("_authorname_", "_bpname_ ", pts);
        blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);

    }

    /**
     * Saves a blueprint to the in-memory persistence storage.
     * <p>
     * If a blueprint with the same author and name already exists, this method
     * throws a {@link BlueprintPersistenceException}. Otherwise, it stores the
     * blueprint using a tuple of author and name as the key.
     *
     * @param bp the {@link Blueprint} to be saved
     * @throws BlueprintPersistenceException if a blueprint with the same author
     * and name already exists
     */
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
            throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
        } else {
            blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        }
    }

    /**
     * Retrieves a blueprint by the specified author and blueprint name.
     *
     * @param author the name of the blueprint's author.
     * @param bprintname the name of the blueprint.
     * @return the {@link Blueprint} object corresponding to the given author
     * and blueprint name.
     * @throws BlueprintNotFoundException if no blueprint is found for the
     * specified author and name.
     */
    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint bp = blueprints.get(new Tuple<>(author, bprintname));
        if (bp == null) {
            throw new BlueprintNotFoundException("Blueprint not found: " + author + ", " + bprintname);
        }
        return bp;
    }

    /**
     * Return all blueprints in the store (helper)
     */
    public Set<Blueprint> getAllBlueprints() {
        return new HashSet<>(blueprints.values());
    }

    /**
     * Get all the blueprints of an author
     *
     * @param author Author's name
     */
    @Override
    public Set<Blueprint> getBlueprintByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> blueprintsByAuthor = new HashSet<>();
        for (Tuple<String, String> key : blueprints.keySet()) {
            if (key.o1.equals(author)) {
                blueprintsByAuthor.add(blueprints.get(key));
            }
        }
        if (blueprintsByAuthor.isEmpty()) {
            throw new BlueprintNotFoundException("No blueprints found for author: " + author);
        }
        return blueprintsByAuthor;
    }
}
