
package edu.eci.arsw.blueprints.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * Represents a blueprint consisting of an author, a name, and a list of points.
 * <p>
 * Blueprints are used to model drawings or designs, where each point represents
 * a coordinate in the blueprint. The class provides constructors for creating
 * blueprints with or without initial points, and methods for accessing and
 * modifying blueprint properties.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 *     Point[] points = {new Point(1, 2), new Point(3, 4)};
 *     Blueprint bp = new Blueprint("AuthorName", "BlueprintName", points);
 * </pre>
 * </p>
 *
 * @author diego
 */
public class Blueprint {

    private String author=null;
    
    private List<Point> points=null;
    
    private String name=null;
            
    public Blueprint(String author,String name,Point[] pnts){
        this.author=author;
        this.name=name;
        points=Arrays.asList(pnts);
    }
         
    public Blueprint(String author, String name){
        this.name=name;
        points=new ArrayList<>();
    }

    public Blueprint() {
    }    
    
    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }
    
    public List<Point> getPoints() {
        return points;
    }
    
    public void addPoint(Point p){
        this.points.add(p);
    }

    @Override
    public String toString() {
        return "Blueprint{" + "author=" + author + ", name=" + name + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Blueprint other = (Blueprint) obj;
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.points.size()!=other.points.size()){
            return false;
        }
        for (int i=0;i<this.points.size();i++){
            if (this.points.get(i)!=other.points.get(i)){
                return false;
            }
        }
        
        return true;
    }
    
    
    
}
