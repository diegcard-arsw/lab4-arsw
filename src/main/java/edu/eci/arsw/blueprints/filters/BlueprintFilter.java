/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;

/**
 * Interface for blueprint filtering strategies
 * @author hcadavid
 */
public interface BlueprintFilter {
    
    /**
     * Filters a blueprint according to the specific filtering strategy
     * @param bp the blueprint to be filtered
     * @return a new filtered blueprint
     */
    Blueprint filter(Blueprint bp);
    
}
