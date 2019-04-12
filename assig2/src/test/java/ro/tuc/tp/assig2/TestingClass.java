package ro.tuc.tp.assig2;

import GUI.GUI;

public class TestingClass {

	public static void main(String[] args) {
		Simulator simulator = new Simulator(1, 10, 1, 6, 5, 25);
		GUI gui = new GUI();
		gui.setVisible(true);
		simulator.start();
	}
}
