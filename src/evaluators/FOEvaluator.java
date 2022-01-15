package evaluators;

import java.io.PrintWriter;
import java.util.ArrayList;

import dataStructures.DataHandler;
import dataStructures.OriginalGraph;
import dataStructures.Solution;

public class FOEvaluator {
	
	public FOEvaluator() {
		
	}
	
	public double evaluateOF(Solution sol, DataHandler data, OriginalGraph graph) {
		
		//Initialize the objective function:
		
		double fo = 0;
		
		//Iterate through the solution:
		
		int numberOfArcs = sol.getHeads().size(); 
		for(int i=0;i<numberOfArcs;i++) {
			
			int tail = sol.getTails().get(i)-1;
			int head = sol.getHeads().get(i)-1;
			int type = sol.getTypes().get(i);
			//int route = sol.getRoutes().get(i);
		
			if(type == 1) {
				fo += graph.getDists()[tail][head];
			}
			
		}
		
		//Return the fo:
		
		return(fo);
	}
	
	public double evaluateObjectiveFunctionRoute(int routeID, Solution sol, DataHandler data, OriginalGraph graph) {
		
		//Initialize the objective function:
		
			double fo = 0;
			
			//Iterate through the solution:
			
			int numberOfArcs = sol.getHeads().size(); 
			for(int i=0;i<numberOfArcs;i++) {
				
				int tail = sol.getTails().get(i)-1;
				int head = sol.getHeads().get(i)-1;
				int type = sol.getTypes().get(i);
				int route = sol.getRoutes().get(i);
			
				if(type == 1 && route == routeID) {
					fo += graph.getDists()[tail][head];
				}
				
			}
			
			//Return the fo:
			
			return(fo);
	
	}
	
	public void evaluateAttributes(int routeID,Solution sol, DataHandler data, OriginalGraph graph,PrintWriter pw) {
		
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
				
				pw.println("Route "+routeID+":"+duration+" - "+drivingDistance+" - "+drivingTime+" - "+walkingDistance+" - "+walkingTime+" - "+serviceTime);
				
				
		
	}
}
