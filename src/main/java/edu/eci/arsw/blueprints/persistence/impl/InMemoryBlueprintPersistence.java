/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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

    private final Map<Tuple<String, String>, Blueprint> blueprints = new ConcurrentHashMap<>();

    /**
     * Constructs an instance of InMemoryBlueprintPersistence and loads stub
     * data into memory. Initializes the blueprints map with a sample Blueprint
     * object for testing or demonstration purposes. The sample Blueprint is
     * created with a predefined author, name, and a set of points.
     */
    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts1 = new Point[]{new Point(140, 140), new Point(115, 115)};
        Blueprint bp1 = new Blueprint("john", "house_plan", pts1);
        blueprints.put(new Tuple<>(bp1.getAuthor(), bp1.getName()), bp1);

        Point[] pts2 = new Point[]{new Point(20, 20), new Point(30, 30), new Point(40, 40)};
        Blueprint bp2 = new Blueprint("john", "office_building", pts2);
        blueprints.put(new Tuple<>(bp2.getAuthor(), bp2.getName()), bp2);

        Point[] pts3 = new Point[]{new Point(100, 200), new Point(150, 250), new Point(200, 300)};
        Blueprint bp3 = new Blueprint("maria", "school_design", pts3);
        blueprints.put(new Tuple<>(bp3.getAuthor(), bp3.getName()), bp3);

        Point[] pts4 = new Point[]{new Point(50, 60), new Point(70, 80), new Point(90, 100), new Point(110, 120)};
        Blueprint bp4 = new Blueprint("carlos", "park_layout", pts4);
        blueprints.put(new Tuple<>(bp4.getAuthor(), bp4.getName()), bp4);
    }

    /**
     * Saves a blueprint to the in-memory persistence storage.
     * <p>
     * If a blueprint with the same author and name already exists, this method
     * throws a {@link BlueprintPersistenceException}. This method is thread-safe
     * and uses atomic operations to prevent race conditions.
     *
     * @param bp the {@link Blueprint} to be saved
     * @throws BlueprintPersistenceException if a blueprint with the same author
     * and name already exists
     */
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        Tuple<String, String> key = new Tuple<>(bp.getAuthor(), bp.getName());
        Blueprint existingBlueprint = blueprints.putIfAbsent(key, bp);
        
        if (existingBlueprint != null) {
            throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
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
    @Override
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
