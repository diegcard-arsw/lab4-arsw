/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.filters.impl;

import java.util.ArrayList;
import java.util.List;

import edu.eci.arsw.blueprints.filters.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

/**
 * Filter that applies subsampling by removing every other point (1 of every 2)
 * @author hcadavid
 */
// @Component  // Commented out - only one filter active at a time
public class SubsamplingFilterBlueprint implements BlueprintFilter {

    @Override
    public Blueprint filter(Blueprint bp) {
        List<Point> originalPoints = bp.getPoints();
        List<Point> filteredPoints = new ArrayList<>();
        
        // Apply subsampling: keep points at even indices (0, 2, 4, ...)
        // This removes 1 of every 2 points in an intercalated manner
        for (int i = 0; i < originalPoints.size(); i += 2) {
            Point point = originalPoints.get(i);
            filteredPoints.add(new Point(point.getX(), point.getY()));
        }
        
        // Convert list back to array and create new blueprint
        Point[] filteredArray = filteredPoints.toArray(new Point[0]);
        return new Blueprint(bp.getAuthor(), bp.getName(), filteredArray);
    }
    
}
