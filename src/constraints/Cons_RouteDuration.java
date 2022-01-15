package constraints;

import java.io.PrintWriter;
import java.util.ArrayList;

import dataStructures.DataHandler;
import dataStructures.OriginalGraph;
import dataStructures.Solution;

public class Cons_RouteDuration {

	public Cons_RouteDuration() {
		
	}
	
	/**
	 * This method checks the constraint for all routes:
	 * @param solution
	 * @param instance
	 * @param output
	 * @param precision
	 * @return
	 */
	public boolean checkConstraint(Solution solution, DataHandler data, OriginalGraph graph, boolean output, int precision,PrintWriter pw) {
		for (Integer route : solution.getRoutesUnique()) {
			if (!checkConstraint(route,solution, data,graph, output, precision,pw)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method checks a route
	 * @param route
	 * @param instance
	 * @param output
	 * @param precision
	 * @return
	 */
	public boolean checkConstraint(int routeID,Solution sol, DataHandler data, OriginalGraph graph,boolean output, int precision,PrintWriter pw) {

		//Initialize the important values:
		
		Double duration=0.0,drivingTime=0.0,drivingDistance=0.0,walkingTime=0.0,walkingDistance=0.0,serviceTime = 0.0;
		ArrayList<Integer> nodesInRoute = new ArrayList<Integer>();
		
		//Iterate through the lists:
		
		int numArcs = sol.getHeads().size();
		for(int i = 0;i<numArcs;i++) {
			
			int tail = sol.getTails().get(i)-1;
			int head = sol.getHeads().get(i)-1;
			int type = sol.getTypes().get(i);
			int route = sol.getRoutes().get(i);
			
			if(routeID == route) {
				
				if(!nodesInRoute.contains(tail)) {
					nodesInRoute.add(tail);
				}
				if(type == 1) {
					
					drivingTime += graph.getdTimes()[tail][head];
					drivingDistance += graph.getDists()[tail][head];
					duration += graph.getdTimes()[tail][head];
					
				}else {
					
					walkingTime += graph.getwTimes()[tail][head];
					walkingDistance += graph.getDists()[tail][head];
					duration += graph.getwTimes()[tail][head];
					
				}
				 
			}
			
			
		}
		
		//Calculate the service time:
		
		for(int i = 0;i < nodesInRoute.size();i++) {
			
			int node = nodesInRoute.get(i);
			duration += data.getServicesTimes().get(node);
			serviceTime += data.getServicesTimes().get(node);
			
		}
		
		//Check the constraints:
		
		if(duration - Math.pow(10, -precision) > data.getlT()) {
			pw.println("Route "+routeID+" has a duration "+duration+" > "+data.getlT());
			return false;
		}
		
		//If nothing happened:
		
			return true;
	}
}
