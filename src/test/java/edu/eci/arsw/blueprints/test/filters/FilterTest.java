/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.filters;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import edu.eci.arsw.blueprints.filters.impl.RedundancyFilterBlueprint;
import edu.eci.arsw.blueprints.filters.impl.SubsamplingFilterBlueprint;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

/**
 * Test cases for blueprint filters
 * @author hcadavid
 */
public class FilterTest {
    
    @Test
    public void testRedundancyFilter() {
        // Create blueprint with consecutive duplicate points
        Point[] originalPoints = {
            new Point(10, 10),  // Keep
            new Point(10, 10),  // Remove (duplicate)
            new Point(20, 20),  // Keep
            new Point(20, 20),  // Remove (duplicate)
            new Point(20, 20),  // Remove (duplicate)
            new Point(30, 30),  // Keep
            new Point(40, 40)   // Keep
        };
        
        Blueprint originalBp = new Blueprint("testAuthor", "testBlueprint", originalPoints);
        RedundancyFilterBlueprint filter = new RedundancyFilterBlueprint();
        
        Blueprint filteredBp = filter.filter(originalBp);
        
        // Expected points after filtering: (10,10), (20,20), (30,30), (40,40)
        assertEquals("Author should be preserved", "testAuthor", filteredBp.getAuthor());
        assertEquals("Name should be preserved", "testBlueprint", filteredBp.getName());
        assertEquals("Should have 4 points after removing duplicates", 4, filteredBp.getPoints().size());
        
        // Verify the filtered points
        assertEquals("First point should be (10,10)", 10, filteredBp.getPoints().get(0).getX());
        assertEquals("First point should be (10,10)", 10, filteredBp.getPoints().get(0).getY());
        
        assertEquals("Second point should be (20,20)", 20, filteredBp.getPoints().get(1).getX());
        assertEquals("Second point should be (20,20)", 20, filteredBp.getPoints().get(1).getY());
        
        assertEquals("Third point should be (30,30)", 30, filteredBp.getPoints().get(2).getX());
        assertEquals("Third point should be (30,30)", 30, filteredBp.getPoints().get(2).getY());
        
        assertEquals("Fourth point should be (40,40)", 40, filteredBp.getPoints().get(3).getX());
        assertEquals("Fourth point should be (40,40)", 40, filteredBp.getPoints().get(3).getY());
    }
    
    @Test
    public void testSubsamplingFilter() {
        // Create blueprint with 7 points
        Point[] originalPoints = {
            new Point(0, 0),   // Keep (index 0)
            new Point(10, 10), // Remove (index 1)
            new Point(20, 20), // Keep (index 2)
            new Point(30, 30), // Remove (index 3)
            new Point(40, 40), // Keep (index 4)
            new Point(50, 50), // Remove (index 5)
            new Point(60, 60)  // Keep (index 6)
        };
        
        Blueprint originalBp = new Blueprint("testAuthor", "testBlueprint", originalPoints);
        SubsamplingFilterBlueprint filter = new SubsamplingFilterBlueprint();
        
        Blueprint filteredBp = filter.filter(originalBp);
        
        // Expected points after subsampling: (0,0), (20,20), (40,40), (60,60) - indices 0, 2, 4, 6
        assertEquals("Author should be preserved", "testAuthor", filteredBp.getAuthor());
        assertEquals("Name should be preserved", "testBlueprint", filteredBp.getName());
        assertEquals("Should have 4 points after subsampling", 4, filteredBp.getPoints().size());
        
        // Verify the filtered points
        assertEquals("First point should be (0,0)", 0, filteredBp.getPoints().get(0).getX());
        assertEquals("First point should be (0,0)", 0, filteredBp.getPoints().get(0).getY());
        
        assertEquals("Second point should be (20,20)", 20, filteredBp.getPoints().get(1).getX());
        assertEquals("Second point should be (20,20)", 20, filteredBp.getPoints().get(1).getY());
        
        assertEquals("Third point should be (40,40)", 40, filteredBp.getPoints().get(2).getX());
        assertEquals("Third point should be (40,40)", 40, filteredBp.getPoints().get(2).getY());
        
        assertEquals("Fourth point should be (60,60)", 60, filteredBp.getPoints().get(3).getX());
        assertEquals("Fourth point should be (60,60)", 60, filteredBp.getPoints().get(3).getY());
    }
    
    @Test
    public void testRedundancyFilterEmptyBlueprint() {
        Point[] emptyPoints = {};
        Blueprint emptyBp = new Blueprint("author", "empty", emptyPoints);
        RedundancyFilterBlueprint filter = new RedundancyFilterBlueprint();
        
        Blueprint filteredBp = filter.filter(emptyBp);
        
        assertEquals("Empty blueprint should remain empty", 0, filteredBp.getPoints().size());
    }
    
    @Test
    public void testSubsamplingFilterSinglePoint() {
        Point[] singlePoint = {new Point(5, 5)};
        Blueprint singleBp = new Blueprint("author", "single", singlePoint);
        SubsamplingFilterBlueprint filter = new SubsamplingFilterBlueprint();
        
        Blueprint filteredBp = filter.filter(singleBp);
        
        assertEquals("Single point blueprint should keep the point", 1, filteredBp.getPoints().size());
        assertEquals("Point should be preserved", 5, filteredBp.getPoints().get(0).getX());
        assertEquals("Point should be preserved", 5, filteredBp.getPoints().get(0).getY());
    }
    
}
