package dataStructures;

import java.util.ArrayList;

public class Solution {

	/**
	 * Current instance
	 */
	private int instance;
	
	private ArrayList<Integer> tails;
	
	private ArrayList<Integer> heads;
	
	private ArrayList<Integer> types;
	
	private ArrayList<Integer> routes;
	
	private ArrayList<Integer> routesUnique;
	
	public Solution(int inst) {
		
		inst = instance;
		tails = new ArrayList<Integer>();
		heads = new ArrayList<Integer>();
		types = new ArrayList<Integer>();
		routes = new ArrayList<Integer>();
		routesUnique = new ArrayList<Integer>();
	}

	/**
	 * @return the instance
	 */
	public int getInstance() {
		return instance;
	}

	/**
	 * @param instance the instance to set
	 */
	public void setInstance(int instance) {
		this.instance = instance;
	}

	/**
	 * @return the tails
	 */
	public ArrayList<Integer> getTails() {
		return tails;
	}

	/**
	 * @param tails the tails to set
	 */
	public void setTails(ArrayList<Integer> tails) {
		this.tails = tails;
	}

	/**
	 * @return the heads
	 */
	public ArrayList<Integer> getHeads() {
		return heads;
	}

	/**
	 * @param heads the heads to set
	 */
	public void setHeads(ArrayList<Integer> heads) {
		this.heads = heads;
	}

	/**
	 * @return the types
	 */
	public ArrayList<Integer> getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(ArrayList<Integer> types) {
		this.types = types;
	}

	/**
	 * @return the routes
	 */
	public ArrayList<Integer> getRoutes() {
		return routes;
	}

	/**
	 * @param routes the routes to set
	 */
	public void setRoutes(ArrayList<Integer> routes) {
		this.routes = routes;
	}

	/**
	 * @return the routesUnique
	 */
	public ArrayList<Integer> getRoutesUnique() {
		return routesUnique;
	}

	/**
	 * @param routesUnique the routesUnique to set
	 */
	public void setRoutesUnique(ArrayList<Integer> routesUnique) {
		this.routesUnique = routesUnique;
	}
	
	
}
