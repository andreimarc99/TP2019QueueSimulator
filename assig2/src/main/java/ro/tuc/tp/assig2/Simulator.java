package ro.tuc.tp.assig2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.JOptionPane;

import GUI.GUI;

public class Simulator extends Thread {

	private int minArrivalTime;
	private int maxArrivalTime;
	private int minServiceTime;
	private int maxServiceTime;
	private int numberOfQueues;
	private int numberOfClients;
	private int overallEmptyTime;
	private Random random = new Random();
	private static int currentTime;
	private static int avgWaitingTime;
	private float avgServiceTime;
	private int simTime;
	private LinkedList<Client> clients = new LinkedList<Client>();
	private HashMap<Integer, Queue> allTheQueues = new HashMap<Integer, Queue>();
	private static String eventLog = new String();

	public Simulator() {
	}

	public Simulator(int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime, int numberOfQueues,
			int simTime) {
		this.minArrivalTime = minArrivalTime;
		this.maxArrivalTime = maxArrivalTime;
		this.minServiceTime = minServiceTime;
		this.maxServiceTime = maxServiceTime;
		this.numberOfQueues = numberOfQueues;
		this.simTime = simTime;
		this.numberOfClients = random.nextInt((this.simTime - 2) - (this.simTime / 2 + 1)) + (this.simTime / 2 + 1);
	}

	public void initializeQueueList() {
		int i = 0;
		while (i < numberOfQueues) {
			Queue q = new Queue();
			allTheQueues.put(i++, q);
		}
	}

	public Queue getTheMinimalQueue() {
		int qNumber = 0;
		int minimalQ = Integer.MAX_VALUE;
		int i = 0;
		while (i < allTheQueues.size()) {
			if (allTheQueues.get(i).getWaitingTime() < minimalQ) {
				minimalQ = allTheQueues.get(i).getWaitingTime();
				qNumber = i;
			}
			i++;
		}
		return allTheQueues.get(qNumber);
	}

	public static void incrementAvgWaitingTime(int incrementValue) {
		avgWaitingTime += incrementValue;
	}

	public static void setLog(int id, int arrivalTime, int serviceTime) {
		String s = new String();
		s = ("CLIENT (" + id + ") - ARRIVED @ " + arrivalTime + " - SERVICE TIME: " + serviceTime + "\n");
		setEventsLog(s);
	}

	public static void setEventsLog(String s) {
		eventLog = eventLog + s;
	}

	public static String getEventLog() {
		return eventLog;
	}

	public static int getCurrentTime() {
		return currentTime;
	}

	public float getAvgServiceTime() {
		return avgServiceTime;
	}

	public void setOverallEmptyTime() {
		overallEmptyTime = 0;
		for (Entry<Integer, Queue> entry : allTheQueues.entrySet()) {
			overallEmptyTime = overallEmptyTime + entry.getValue().getEmptyTime();
		}
	}

	public int getOverallEmptyTime() {
		return overallEmptyTime;
	}

	public void generateRandomClients() {
		int i = 1;
		while (i <= numberOfClients) {
			int arrival = random.nextInt((maxArrivalTime - minArrivalTime) + 1) + minArrivalTime;
			int service = random.nextInt((maxServiceTime - minServiceTime) + 1) + minServiceTime;
			avgServiceTime += service;
			clients.add(new Client(i++, arrival, service));
		}
	}

	public int getMinimumQueueID() {
		int min = Integer.MAX_VALUE;
		int i = 0;
		while (i < allTheQueues.size()) {
			if (allTheQueues.get(i).getId() < min) {
				min = (int) allTheQueues.get(i).getId();
			}
			i++;
		}
		return min;
	}

	public void displayQueues() {
		String text = new String();
		for (Entry<Integer, Queue> entry : allTheQueues.entrySet()) {
			text += "[" + String.valueOf(entry.getValue().getId() - getMinimumQueueID() + 1) + "]   ";
			for (Client c : entry.getValue().getClients()) {
				text += "  <" + String.valueOf(c.getId()) + ">  ";
			}
			text += "\n\n";
		}
		GUI.getQueueView().setText(text);
	}

	public void run() {
		int clientCnt = 0;
		GUI.getEventsLog().setText(null);
		initializeQueueList();
		generateRandomClients();
		for (Entry<Integer, Queue> entry : allTheQueues.entrySet()) {
			if (!entry.getValue().isAlive()) {
				entry.getValue().start();
			}
		}
		while (currentTime < simTime || clients.size() > 0) {
			for (Client client : clients) {
				if (client.getArrivalTime() == currentTime) {
					++clientCnt;
					getTheMinimalQueue().addClient(client);
					setLog(client.getId(), client.getArrivalTime(), client.getServiceTime());
				}
			}
			displayQueues();
			try {
				GUI.getEventsLog().setText(getEventLog());
				sleep(1500);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "An error occurred");
			}
			currentTime++;
			if (clientCnt == numberOfClients && currentTime >= simTime) {
				break;
			}
		}
		GUI.getEventsLog().append("Finished simulating\nCheck the results nearby");
		setOverallEmptyTime();
		GUI.getLblQueueResult().setText(String.valueOf((float) getOverallEmptyTime() / numberOfQueues));
		GUI.getAvgWaitingResult().setText((String.valueOf((float) avgWaitingTime / numberOfClients)));
		GUI.getAvgServiceResult().setText((String.valueOf((float) avgServiceTime / numberOfClients)));
	}
}
