package constraints;

import java.io.PrintWriter;
import dataStructures.DataHandler;
import dataStructures.OriginalGraph;
import dataStructures.Solution;

public class Cons_StartEndDepot {

	public Cons_StartEndDepot() {
		
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

		//Iterate through the lists:
		
		int numArcs = sol.getHeads().size();
		boolean start = false;
		boolean end = false;
		
		for(int i = 0;i<numArcs;i++) {
			
			int tail = sol.getTails().get(i)-1;
			int head = sol.getHeads().get(i)-1;
			int route = sol.getRoutes().get(i);
			
			if(routeID == route) {
				
				if(tail == data.getNodes().size()-1) {
					start = true;
				}
				
				if(head == data.getNodes().size()-1) {
					end = true;
				}
				
				 
			}
			
			
		}
		
		
		//Check the constraints:
		
		if(!start || !end) {
			pw.println("Route "+routeID+" does not start/end at the depot");
			return false;
		}
		
		//If nothing happened:
		
			return true;
	}
}
