package evaluators;

import java.io.PrintWriter;

import constraints.Cons_MaxWalkDistance;
import constraints.Cons_RouteDuration;
import constraints.Cons_StartEndDepot;
import dataStructures.DataHandler;
import dataStructures.OriginalGraph;
import dataStructures.Solution;

public class ConstraintsEvaluator {

	public ConstraintsEvaluator() {
		
	}
	
	public boolean evaluateRouteWalkingDistance(Solution solution, DataHandler data,OriginalGraph graph, int precision,PrintWriter pw) {
		Cons_MaxWalkDistance constraint = new Cons_MaxWalkDistance();
		return(constraint.checkConstraint(solution, data,graph, true, precision,pw));
	}
	
	public boolean evaluateRouteDuration(Solution solution, DataHandler data,OriginalGraph graph, int precision,PrintWriter pw) {
		Cons_RouteDuration constraint = new Cons_RouteDuration();
		return(constraint.checkConstraint(solution, data,graph, true, precision,pw));
	}
	
	public boolean evaluateStartEndDepot(Solution solution, DataHandler data,OriginalGraph graph, int precision,PrintWriter pw) {
		Cons_StartEndDepot constraint = new Cons_StartEndDepot();
		return(constraint.checkConstraint(solution, data,graph, true, precision,pw));
	}
}
