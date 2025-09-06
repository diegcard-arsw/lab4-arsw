/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.services;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.eci.arsw.blueprints.AppConfig;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

/**
 * Integration test for BlueprintsServices with filters
 * @author hcadavid
 */
public class BlueprintsServicesFilterTest {
    
    @Test
    public void testBlueprintsServicesWithRedundancyFilter() throws BlueprintNotFoundException {
        // Test with RedundancyFilter (currently active with @Component)
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        BlueprintsServices svc = ctx.getBean(BlueprintsServices.class);
        
        // Create blueprint with duplicate consecutive points
        Point[] pointsWithDuplicates = {
            new Point(10, 10),
            new Point(10, 10), // Duplicate
            new Point(20, 20),
            new Point(20, 20), // Duplicate
            new Point(30, 30)
        };
        
        Blueprint bpWithDuplicates = new Blueprint("testUser", "duplicateTest", pointsWithDuplicates);
        svc.addNewBlueprint(bpWithDuplicates);
        
        // Retrieve and check filtering
        Blueprint retrievedBp = svc.getBlueprint("testUser", "duplicateTest");
        
        assertEquals("Author should be preserved", "testUser", retrievedBp.getAuthor());
        assertEquals("Name should be preserved", "duplicateTest", retrievedBp.getName());
        assertEquals("Duplicates should be filtered out", 3, retrievedBp.getPoints().size());
        
        // Verify points are correct after redundancy filtering
        assertEquals(10, retrievedBp.getPoints().get(0).getX());
        assertEquals(10, retrievedBp.getPoints().get(0).getY());
        assertEquals(20, retrievedBp.getPoints().get(1).getX());
        assertEquals(20, retrievedBp.getPoints().get(1).getY());
        assertEquals(30, retrievedBp.getPoints().get(2).getX());
        assertEquals(30, retrievedBp.getPoints().get(2).getY());
        
        ctx.close();
    }
    
    @Test
    public void testBlueprintsServicesByAuthorWithFilter() throws BlueprintNotFoundException {
        // Test filtering when getting blueprints by author
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        BlueprintsServices svc = ctx.getBean(BlueprintsServices.class);
        
        // Create two blueprints with duplicates for the same author
        Point[] bp1Points = {
            new Point(1, 1),
            new Point(1, 1), // Duplicate
            new Point(2, 2)
        };
        
        Point[] bp2Points = {
            new Point(5, 5),
            new Point(5, 5), // Duplicate
            new Point(5, 5), // Another duplicate
            new Point(6, 6)
        };
        
        Blueprint bp1 = new Blueprint("filterUser", "blueprint1", bp1Points);
        Blueprint bp2 = new Blueprint("filterUser", "blueprint2", bp2Points);
        
        svc.addNewBlueprint(bp1);
        svc.addNewBlueprint(bp2);
        
        // Get all blueprints by author (should be filtered)
        Set<Blueprint> blueprints = svc.getBlueprintsByAuthor("filterUser");
        
        assertEquals("Should get 2 blueprints", 2, blueprints.size());
        
        // Check that both blueprints were filtered
        for (Blueprint bp : blueprints) {
            if (bp.getName().equals("blueprint1")) {
                assertEquals("Blueprint1 should have 2 points after filtering", 2, bp.getPoints().size());
            } else if (bp.getName().equals("blueprint2")) {
                assertEquals("Blueprint2 should have 2 points after filtering", 2, bp.getPoints().size());
            }
        }
        
        ctx.close();
    }
    
}
