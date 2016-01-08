/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import geography.GeographicPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	private HashMap<GeographicPoint, MapNode> nodeMap; // maintains a collection of map nodes
	private HashSet<MapEdge> edgeSet; // maintains a collection of map edges
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph() {
	    nodeMap = new HashMap<>();
	    edgeSet = new HashSet<>();	    
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices() {
		return nodeMap.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices() {
		return nodeMap.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges() {
		return edgeSet.size();
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location) {
		if (location == null) { // input is null
	       return false; 
	    }
	    
	    MapNode node = nodeMap.get(location);
	    if (node != null) { // location already present
	        return false;
	    }
	    
	    node = new MapNode(location); // create a new MapNode
	    nodeMap.put(location, node); // insert the new MapNode into the hashmap
	    return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		if (length < 0) {
		    throw new IllegalArgumentException("Input parameter 'length' is less than 0");
		}
		
		if (isAnyParamNull(from, to, roadName, roadType, length)) {
		    throw new IllegalArgumentException("At least one of the input parameters is null");
		}
		
		if (!nodeMap.containsKey(from) || !nodeMap.containsKey(to)) {
		    throw new IllegalArgumentException("One of the end nodes not added to the graph");
		}
		
		MapNode fromNode = nodeMap.get(from);
		MapNode toNode = nodeMap.get(to);
		
		MapEdge edge = new MapEdge(fromNode, toNode, roadName, roadType, length);
		fromNode.addOutgoingEdge(edge);
		
		edgeSet.add(edge);
	}
	
	private boolean isAnyParamNull(Object... params) {
	    for(Object param: params) {
	        if (param == null) {
	            return true;
	        }
	    }
	    return false;
	}

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		HashSet<MapNode> visited = new HashSet<>(); // maintains a collection of visited map nodes while doing traversal
	    HashMap<MapNode, MapNode> parentMap = new HashMap<>(); // maintains a mapping between a node and its parent
	    
	    MapNode startNode = nodeMap.get(start);
	    Queue<MapNode> queue = new LinkedList<>();
	    queue.add(startNode); // Add the start node to the queue to being processing
	    
	    while (queue.peek() != null) {
	        MapNode currNode = queue.remove();
	        nodeSearched.accept(currNode.getLocation());
	        
	        if(currNode.getLocation().equals(goal)) { // Found the goal node
	            return getFinalPath(currNode, startNode, parentMap);
	        }
	        
	        visited.add(currNode); // add the current node to visited set
	        
	        List<MapEdge> outGoingEdges = currNode.getOutgoingEdges();
	        for (MapEdge e : outGoingEdges) {
	            MapNode endLocNode = e.getEndLocation();
	            if(!visited.contains(endLocNode)) { // if not present in the visited set, add to queue
	                queue.add(endLocNode);
	                parentMap.put(endLocNode, currNode); // map the currNode as the parent of its neighbours
	            }
	        }
	    }

		return null;
	}
	
	// Method to find the path from the goad node back to the start node
	private List<GeographicPoint> getFinalPath(MapNode goalNode, MapNode startNode, 
	        HashMap<MapNode, MapNode> parentMap) {
	    MapNode currNode = goalNode;
	    
	    List<GeographicPoint> result = new ArrayList<>();
	    result.add(currNode.getLocation());
	    
	    while(currNode != startNode) {
	        currNode = parentMap.get(currNode); // getting the parent
	        result.add(currNode.getLocation());	        
	    }
	    
	    // Reverse the result, as the path was found from goal back to the start
	    Collections.reverse(result);
	    
	    return result;
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");
		
		GeographicPoint start = new GeographicPoint(1.0, 1.0);
        GeographicPoint end = new GeographicPoint(6.5, 2.0);
		
        List<GeographicPoint> route = theMap.bfs(start,end);
        
        System.out.println(route);
        
		// You can use this method for testing.  
		
		/* Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}
