package roadgraph;


public class MapEdge {
    private MapNode startLocation;
    private MapNode endLocation;
    private String roadName;
    private String roadType;
    private double length;
    
    public MapEdge(MapNode startLocation, MapNode endLocation,
                    String roadName, String roadType, double length) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.roadName = roadName;
        this.roadType = roadType;
        this.length = length;
    }
    
    @Override
    public boolean equals(Object edge2Obj) {
        if (edge2Obj == null) {
            return false;
        }
        
        if (!(edge2Obj instanceof MapEdge)) {
            return false;
        }
        
        MapEdge edge2 = (MapEdge) edge2Obj;
        return this.startLocation.equals(edge2.startLocation) &&
                this.endLocation.equals(edge2.endLocation);
    }
    
    @Override
    public int hashCode() {
        return startLocation.hashCode() + endLocation.hashCode();
    }
    
    public MapNode getStartLocation() {
        return startLocation;
    }
    public void setStartLocation(MapNode startLocation) {
        this.startLocation = startLocation;
    }
    public MapNode getEndLocation() {
        return endLocation;
    }
    public void setEndLocation(MapNode endLocation) {
        this.endLocation = endLocation;
    }
    public String getRoadName() {
        return roadName;
    }
    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }
    public String getRoadType() {
        return roadType;
    }
    public void setRoadType(String roadType) {
        this.roadType = roadType;
    }
    public double getLength() {
        return length;
    }
    public void setLength(double length) {
        this.length = length;
    }    
}
