package edu.eci.arsw.blueprints.controllers;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

/**
 * REST API Controller for managing architectural blueprints.
 * Provides endpoints to retrieve, create, and update blueprints.
 * 
 * @author Diego Cardenas
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    private BlueprintsServices blueprintServices;

    /**
     * Retrieves all blueprints in the system.
     * 
     * @return ResponseEntity containing all blueprints if successful,
     *         or error message with NOT_FOUND status if an error occurs
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllBlueprints() {
        try {
            Set<Blueprint> blueprints = blueprintServices.getAllBlueprints();
            return new ResponseEntity<>(blueprints, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error retrieving blueprints", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves all blueprints created by a specific author.
     * 
     * @param author the name of the author whose blueprints to retrieve
     * @return ResponseEntity containing the author's blueprints if successful,
     *         or 404 NOT_FOUND if the author doesn't exist
     */
    @RequestMapping(value = "/{author}", method = RequestMethod.GET)
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable String author) {
        try {
            Set<Blueprint> blueprints = blueprintServices.getBlueprintsByAuthor(author);
            return new ResponseEntity<>(blueprints, HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Author not found: " + author, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves a specific blueprint by author and blueprint name.
     * 
     * @param author the name of the blueprint's author
     * @param bpname the name of the blueprint
     * @return ResponseEntity containing the blueprint if found,
     *         or 404 NOT_FOUND if the blueprint doesn't exist
     */
    @RequestMapping(value = "/{author}/{bpname}", method = RequestMethod.GET)
    public ResponseEntity<?> getBlueprint(@PathVariable String author, @PathVariable String bpname) {
        try {
            Blueprint blueprint = blueprintServices.getBlueprint(author, bpname);
            return new ResponseEntity<>(blueprint, HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Blueprint not found: " + author + "/" + bpname, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates a new blueprint.
     * 
     * @param blueprint the Blueprint object to be created (from JSON request body)
     * @return ResponseEntity with CREATED status if successful,
     *         or FORBIDDEN status if blueprint already exists
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createBlueprint(@RequestBody Blueprint blueprint) {
        try {
            blueprintServices.addNewBlueprint(blueprint);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error creating blueprint: blueprint may already exist", HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Updates an existing blueprint.
     * 
     * @param author the name of the blueprint's author
     * @param bpname the name of the blueprint to update
     * @param blueprint the updated Blueprint object (from JSON request body)
     * @return ResponseEntity with ACCEPTED status if successful,
     *         or NOT_FOUND if the blueprint doesn't exist
     */
    @RequestMapping(value = "/{author}/{bpname}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateBlueprint(@PathVariable String author, 
                                           @PathVariable String bpname, 
                                           @RequestBody Blueprint blueprint) {
        try {
            blueprintServices.updateBlueprint(author, bpname, blueprint);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Blueprint not found: " + author + "/" + bpname, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error updating blueprint", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}