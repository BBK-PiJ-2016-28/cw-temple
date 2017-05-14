package student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

import game.Edge;
import game.EscapeState;
import game.ExplorationState;
import game.Node;
import game.NodeStatus;
//import game.GameState;

public class Explorer {
	ArrayList discovered = new ArrayList<>();  
	Stack visited = new Stack();
	Stack breadcrumbs = new Stack();
  /**
   * Explore the cavern, trying to find the orb in as few steps as possible.
   * Once you find the orb, you must return from the function in order to pick
   * it up. If you continue to move after finding the orb rather
   * than returning, it will not count.
   * If you return from this function while not standing on top of the orb,
   * it will count as a failure.
   *   
   * <p>There is no limit to how many steps you can take, but you will receive
   * a score bonus multiplier for finding the orb in fewer steps.</p>
   * 
   * <p>At every step, you only know your current tile's ID and the ID of all
   * open neighbor tiles, as well as the distance to the orb at each of these tiles
   * (ignoring walls and obstacles).</p>
   * 
   * <p>To get information about the current state, use functions
   * getCurrentLocation(),
   * getNeighbours(), and
   * getDistanceToTarget()
   * in ExplorationState.
   * You know you are standing on the orb when getDistanceToTarget() is 0.</p>
   *
   * <p>Use function moveTo(long id) in ExplorationState to move to a neighboring
   * tile by its ID. Doing this will change state to reflect your new position.</p>
   *
   * <p>A suggested first implementation that will always find the orb, but likely won't
   * receive a large bonus multiplier, is a depth-first search.</p>
   *
   * @param state the information available at the current state
   */
	
	/*
	 * STUDENT NOTE: Any commented out code was used for testing and is retained here  for
	 * ease of reading. I coded the entire thing using the TXTmain.java class because
	 * my GUI would not work - I tried to address this through Keith, but I was unable to resolve the issue.
	 * Therefore, I had to print out almost every block of code that had important operations to perform,
	 * check to ensure this worked, and then continue. I have retained this code in order to help with marking.
	 * This is relevant for both methods. 
	 */
	
  public void explore(ExplorationState state) { 
	  while(state.getDistanceToTarget() != 0){
		  discovered.add(state.getCurrentLocation());
	  //GET NEIGHBOURS
		//  state.getNeighbours().stream().forEach(n -> System.out.println("Neighbour is: " + n.getId()));
		  //Print statement over. Code follows.
		 long count = state.getNeighbours().stream().filter(n -> !discovered.contains(n.getId())).count();
			 if(count >= 1){
				 state.getNeighbours().stream().filter(n -> !discovered.contains(n.getId())).forEach(n -> visited.push(n.getId()));
				//System.out.println("-------------STACK PRINT------------");
				// visited.stream().forEach(System.out::println);
				// System.out.println("-------------END------------");
				 breadcrumbs.push(state.getCurrentLocation());
				 state.moveTo((long)visited.peek());
				// System.out.println("Moved to " + state.getCurrentLocation());
				 visited.pop();
			 }
			 if(count == 0){
				 state.moveTo((long)breadcrumbs.peek()); 
				 breadcrumbs.pop();
			 }	 
	  }
	  return;
  }
  

  /**
   * Escape from the cavern before the ceiling collapses, trying to collect as much
   * gold as possible along the way. Your solution must ALWAYS escape before time runs
   * out, and this should be prioritized above collecting gold.
   *
   * <p>You now have access to the entire underlying graph, which can be accessed 
   * through EscapeState.
   * getCurrentNode() and getExit() will return you Node objects of interest, and getVertices()
   * will return a collection of all nodes on the graph.</p>
   * 
   * <p>Note that time is measured entirely in the number of steps taken, and for each step
   * the time remaining is decremented by the weight of the edge taken. You can use
   * getTimeRemaining() to get the time still remaining, pickUpGold() to pick up any gold
   * on your current tile (this will fail if no such gold exists), and moveTo() to move
   * to a destination node adjacent to your current node.</p>
   * 
   * <p>You must return from this function while standing at the exit. Failing to do so before time
   * runs out or returning from the wrong location will be considered a failed run.</p>
   * 
   * <p>You will always have enough time to escape using the shortest path from the starting
   * position to the exit, although this will not collect much gold.</p>
   *
   * @param state the information available at the current state
   */
  
  /*
	 * STUDENT NOTE: Any commented out code was used for testing and is retained here  for
	 * ease of reading. I coded the entire thing using the TXTmain.java class because
	 * my GUI would not work - I tried to address this through Keith, but I was unable to resolve the issue.
	 * Therefore, I had to print out almost every block of code that had important operations to perform,
	 * check to ensure this worked, and then continue. I have retained this code in order to help with marking.
	 * This is relevant for both methods. 
	 */
  
  public void escape(EscapeState state) {
	/*
	 * Initialise all data structures required
	 */
	Map<Node, Node> traversal = new HashMap<>();
	Map <Node, Integer> spTree = new HashMap<>();
	int steps = 0;
	Set<Node> visited = new HashSet<>();
	Map<Node, Integer> unvisited = new HashMap<>();
	ArrayList<Node> IDs = new ArrayList<>();
	Stack<Node> breadcrumbs = new Stack<>();
	Node neighbour = null;
	Node pretendPlayer;
	/*
	 * Ready the full set of unvisited and set starting node as weight 0
	 * and setting up the pretendPlayer to mimic the starting node.
	 */
	state.getVertices().forEach(n -> unvisited.put(n, Integer.MAX_VALUE));
	unvisited.put(state.getCurrentNode(), 0);
	pretendPlayer = state.getCurrentNode();
	//System.out.println("Start point = " + state.getCurrentNode().getId());
	//System.out.println("Exit = " + state.getExit().getId());
	
	/*
	 * Start DFS of graph using the pretendPlayer node mimicking each node traversed to
	 * This node then reports its traversal to the IDs ArrayList, ignoring backtracking
	 * in order to reduce the number of steps and time.
	 */
	while(!pretendPlayer.equals(state.getExit())){
		//System.out.println("Adding to visited: " + pretendPlayer.getId());
		if(!IDs.contains(pretendPlayer)){
		IDs.add(pretendPlayer);
		}
		visited.add(pretendPlayer);
		//pretendPlayer.getNeighbours().forEach(n -> System.out.println("neighbours of current node = " + n.getId()));
		long count = pretendPlayer.getNeighbours().stream().filter(n -> !visited.contains(n)).count();
		if(count >= 1){
			for(Node n : pretendPlayer.getNeighbours()){
				if(!visited.contains(n)){
					breadcrumbs.push(pretendPlayer);
					pretendPlayer = n;
					//System.out.println("Moved to: " + pretendPlayer.getId() + " " + pretendPlayer);
					IDs.add(pretendPlayer);
					//System.out.println("And the breadcrumbs lead back to: " + breadcrumbs.peek().getId());
					break;
				}
			}
		} 
		if(count == 0){
			//System.out.println("Moving back up to " + breadcrumbs.peek().getId());
			pretendPlayer = breadcrumbs.peek();
			IDs.remove(IDs.size()-1);
			breadcrumbs.pop();
			//System.out.println("pretendPlayer now points to " + pretendPlayer.getId());
			
			
		}
	}
	 // IDs.forEach(n -> System.out.println(n.getId()));
	
	/*Now exit and report back to our avatar so he can navigate through the maze
	 * collecting gold as he traverses.
	 */
	while(!state.getCurrentNode().equals(state.getExit())){
		for(int i = 1; i < IDs.size(); i++){
			state.moveTo(IDs.get(i));
			try{
			state.pickUpGold();
			} catch(Exception e){
				
			}
		}
	}
	
	
	  return;
  }
}
