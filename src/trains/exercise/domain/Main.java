package trains.exercise.domain;

import trains.exercise.domain.controller.Controller;

public class Main {

	/**
	 * Main of the progam
	 * @param args
	 */
	public static void main(String[] args) {
		
		Controller c = new Controller();
		
		System.out.println("Welcome to the local commuter railroad services information");
		c.start();
	}

}
