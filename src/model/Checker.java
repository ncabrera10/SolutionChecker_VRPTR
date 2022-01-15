package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import dataStructures.DataHandler;
import dataStructures.OriginalGraph;
import dataStructures.Solution;
import evaluators.ConstraintsEvaluator;
import evaluators.FOEvaluator;
import globalParameters.GlobalParameters;
import utilities.EuclideanCalculator;

public class Checker {

	public Checker(String solutionFileName) throws IOException {
		
		//Captures the current instance number:
		
		String solutionFileNameM = solutionFileName.replace(".txt", "");
		String[] parts = solutionFileNameM.split("-");
		int instance = Integer.parseInt(parts[1]);
		String algorithm = parts[0];
		
		//1. Read the data:
		
			DataHandler data = this.readDataInfo(instance);

		//2. Creates the graph:
			
			OriginalGraph graph = this.createGraph(data);

		//3. Reads the solution:
			
			Solution solution = this.readSolution(solutionFileName,instance);
		
		//4. Prints the report:
			
			printReport(algorithm,instance,solution,data,graph);
			
	}	
	
	
	public void printReport(String algorithm,int instance,Solution solution,DataHandler data,OriginalGraph graph) {
		
		
		//Creates a path to print the report:
		
			String ruta = "";
			String ruta_details = "";
			
			ruta = GlobalParameters.RESULT_FOLDER+"Report_"+algorithm+"_"+instance+".txt";
			ruta_details = GlobalParameters.RESULT_FOLDER+"DetailsReport_"+algorithm+"_"+instance+".txt";
			String ruta_summary = GlobalParameters.RESULT_FOLDER+"Summary_"+algorithm+"_"+instance+".txt";
			
		//Main logic:
		
			try {
				PrintWriter pw = new PrintWriter(new File(ruta));
				PrintWriter pw2 = new PrintWriter(new File(ruta_details));
				PrintWriter pw3 = new PrintWriter(new File(ruta_summary));
				
				//Headline:
				
				pw.println("Instance:"+instance);
				pw3.println("Parameter;Value");
				pw3.println("Instance;"+instance);
				pw3.println("Algorithm;"+algorithm);
				
				//Check if the solution complies with the constraints:
				
				ConstraintsEvaluator evaluator_cons = new ConstraintsEvaluator();
				boolean cons1 = evaluator_cons.evaluateRouteWalkingDistance(solution, data, graph, GlobalParameters.PRECISION, pw2);
				boolean cons2 = evaluator_cons.evaluateRouteDuration(solution, data, graph, GlobalParameters.PRECISION, pw2);
				boolean cons3 = evaluator_cons.evaluateStartEndDepot(solution, data, graph, GlobalParameters.PRECISION, pw2);
		
				pw.println("----------------Constraints----------------");
				pw.println("Route walking distance limit: "+cons1);
				pw.println("Route duration constraints: "+cons2);
				pw.println("Route start-end constraints: "+cons3);
				//pw.println("All customers must be visited constraints: "+cons4);
				//pw.println("Subtour duration limit: "+cons5);
				if(cons1 && cons2 && cons3) {
					pw.println("The solution is valid");
					pw3.println("Feasible;"+1);
				}else {
					System.out.println("Invalid "+algorithm+" - "+instance);
					pw.println("The solution is invalid");
					pw3.println("Feasible;"+0);
				}
				
			//Objective function for the solution:
				
				//The objective function:
				
				FOEvaluator fo_eva = new FOEvaluator();
				double objFO = fo_eva.evaluateOF(solution, data, graph);
				pw.println("----------------ObjectiveFunction----------------");
				pw.println("ObjectiveFunction: "+objFO);
				pw3.println("ObjectiveFunction;"+objFO);
				
				//Objective function for each route:
				pw.println("----------------Routes----------------");
				pw.println("RouteID - ObjectiveFunction");
				for(Integer route : solution.getRoutesUnique()) {
					pw.println(route+" - "+fo_eva.evaluateObjectiveFunctionRoute(route, solution, data, graph));
				}
				
				//Attributes for each route:
				pw.println("----------------Attributes----------------");
				pw.println("RouteID - ObjectiveFunction");
				for(Integer route : solution.getRoutesUnique()) {
					fo_eva.evaluateAttributes(route, solution, data, graph,pw);
				}
				
				pw.close();
				pw2.close();
				pw3.close();
				
			}
			catch(Exception e) {
				System.out.println("Problem while printing the report");
			}

	}
	
	public Solution readSolution(String solutionFileName,int in) throws IOException {
		
		//Creates a solution:
		
			Solution sol = new Solution(in);
			
		//Reads the solution file arcs:
			
			//0. Creates a buffered reader:
			
				BufferedReader buff = new BufferedReader(new FileReader(GlobalParameters.SOLUTIONS_FOLDER+solutionFileName));
		
			//1. Reads the header
				
				String line = buff.readLine();
			
			//2. Iterates:
				
				while(line != null) {
					String[] attrs = line.split(";");
					sol.getTails().add(Integer.parseInt(attrs[0]));
					sol.getHeads().add(Integer.parseInt(attrs[1]));
					sol.getTypes().add(Integer.parseInt(attrs[2]));
					if(!sol.getRoutes().contains(Integer.parseInt(attrs[3]))) {
						sol.getRoutesUnique().add(Integer.parseInt(attrs[3]));
					}
					sol.getRoutes().add(Integer.parseInt(attrs[3]));
					
					line = buff.readLine();
					
				}
				
			//4. Closes the buffered reader
			
				buff.close();
			
		//Returns the solution:
			
			return(sol);
	}
	
	
	/**
	 * Creates the data handler
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public DataHandler readDataInfo(int in) throws IOException {
		
		//1.0 Creates a data handler
		
		DataHandler data = new DataHandler(globalParameters.GlobalParameters.INSTANCE_FOLDER,in);
	
		//1.1 Read the Master file and stores the main parameters
	
		data.readMasterFile();
		
		//1.2 Read the graph and stores it.
	
		data.readCoordinates();
		
		//1.4 Returns the data handler
		
		return(data);
	}
	
	/**
	 * Creates the graph
	 * @param data
	 * @return
	 */
	public OriginalGraph createGraph(DataHandler data) {
		
	//2.1 Creates the original graph:
		
		
		OriginalGraph graph = new OriginalGraph(data.getxCoors().size(),data.getxCoors().size()*data.getxCoors().size());
		graph.createNodes(data.getxCoors().size());
	
		for(int i=0;i<data.getxCoors().size();i++) {
			data.getNodeRoutes().put((i),new ArrayList<Integer>());
		}
		
	//2.2 Calculates all arc's attributes:

			
		for(int tail=0;tail<data.getxCoors().size();tail++) {
			for(int head=0;head<data.getxCoors().size();head++) {
				double dis = EuclideanCalculator.calc(data.getxCoors().get(tail), data.getyCoors().get(tail), data.getxCoors().get(head), data.getyCoors().get(head));
				if(dis >  data.getMaxDistBTwoP()) {
					graph.addArc(tail, head, dis, 60*dis/(data.getDrivingSpeed()),  999999);
				}
				else {
					graph.addArc(tail, head, dis, 60*dis/(data.getDrivingSpeed()),  60*dis/(data.getWalkingSpeed()));
				}
			}
		}

	//2.3 Sorts all nodes
			
		graph.sortNodes();
		
		
	//2.4 Returns the graph
		
		return(graph);
	}
}
