package roadgraph;

import geography.GeographicPoint;

import java.util.ArrayList;
import java.util.List;

public class MapNode implements Comparable<MapNode>{
    private GeographicPoint location;    
    private List<MapEdge> outgoingEdges;
    private double distance = Double.POSITIVE_INFINITY;
    private double predictedDistance = Double.POSITIVE_INFINITY;

    public MapNode(GeographicPoint location) {
        this.location = location;
        outgoingEdges = new ArrayList<>();
    }
    
    @Override
    public boolean equals(Object locationObj) {
        if (locationObj == null) {
            return false;
        }
        
        if (!(locationObj instanceof MapNode)) {
            return false;
        }
        
        MapNode node = (MapNode) locationObj;
        return this.location.equals(node.location);
    }
    
    @Override
    public int hashCode() {
        return location.hashCode();
    }
 
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    public double getPredictedDistance() {
        return predictedDistance;
    }

    public void setPredictedDistance(double predictedDistance) {
        this.predictedDistance = predictedDistance;
    }
    
    // Add an outgoing edge (road) from the intersection
    public void addOutgoingEdge(MapEdge edge) {
        outgoingEdges.add(edge);
    }
    
    public List<MapEdge> getOutgoingEdges() {
        return outgoingEdges;
    }
    public void setEdges(List<MapEdge> edges) {
        this.outgoingEdges = edges;
    }
    public GeographicPoint getLocation() {
        return location;
    }
    public void setLocation(GeographicPoint location) {
        this.location = location;
    }
    
    @Override
    public String toString() {
        return location.toString();
    }

    @Override
    public int compareTo(MapNode inputNode) {
        return Double.compare(this.distance, inputNode.distance);
    }
}


