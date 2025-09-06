/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.filters.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import edu.eci.arsw.blueprints.filters.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

/**
 * Filter that removes consecutive duplicate points from blueprints
 * @author hcadavid
 */
@Component
public class RedundancyFilterBlueprint implements BlueprintFilter {

    @Override
    public Blueprint filter(Blueprint bp) {
        List<Point> originalPoints = bp.getPoints();
        List<Point> filteredPoints = new ArrayList<>();
        
        if (originalPoints.isEmpty()) {
            return new Blueprint(bp.getAuthor(), bp.getName(), new Point[0]);
        }
        
        // Always add the first point
        Point previousPoint = originalPoints.get(0);
        filteredPoints.add(new Point(previousPoint.getX(), previousPoint.getY()));
        
        // Add only non-consecutive duplicates
        for (int i = 1; i < originalPoints.size(); i++) {
            Point currentPoint = originalPoints.get(i);
            
            // Check if current point is different from previous point
            if (currentPoint.getX() != previousPoint.getX() || 
                currentPoint.getY() != previousPoint.getY()) {
                filteredPoints.add(new Point(currentPoint.getX(), currentPoint.getY()));
                previousPoint = currentPoint;
            }
            // If points are the same, skip (filter out the duplicate)
        }
        
        // Convert list back to array and create new blueprint
        Point[] filteredArray = filteredPoints.toArray(new Point[0]);
        return new Blueprint(bp.getAuthor(), bp.getName(), filteredArray);
    }
    
}
