package roadgraph;

import geography.GeographicPoint;

import java.util.ArrayList;
import java.util.List;

public class MapNode {
    private GeographicPoint location;    
    private List<MapEdge> outgoingEdges;

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
}


