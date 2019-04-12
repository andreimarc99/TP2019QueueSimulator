package ro.tuc.tp.assig2;

import java.util.LinkedList;

import javax.swing.JOptionPane;

public class Queue extends Thread {
	private LinkedList<Client> clients = new LinkedList<Client>();
	private int waitingTime;
	private int emptyTime;

	public void addClient(Client client) {
		clients.add(client);
	}

	public Client removeClient() {
		return clients.removeFirst();
	}

	public LinkedList<Client> getClients() {
		return clients;
	}

	public int getQueueLength() {
		return clients.size();
	}
	
	public int getWaitingTime() {
		for (Client client : clients) {
			waitingTime += client.getServiceTime();
		}
		return this.waitingTime;
	}

	public int getEmptyTime() {
		return emptyTime;
	}

	public void setEmptyTime(int emptyTime) {
		this.emptyTime = emptyTime;
	}

	public void run() {
		for (;;) {
			if (clients.isEmpty() == false) {
				try {
					if (clients.size() > 0) {
						sleep((clients.getFirst().getServiceTime()) * 1500);
						Simulator.setEventsLog("CLIENT (" + clients.getFirst().getId() + ") LEFT @ "
								+ (Simulator.getCurrentTime()) + "\n");
						Simulator.incrementAvgWaitingTime(
								Math.abs(Simulator.getCurrentTime() - clients.getFirst().getArrivalTime()));
						this.removeClient();
					}
				} catch (InterruptedException e) {
					JOptionPane.showMessageDialog(null, "Error");
				}
			} else {
				emptyTime += 1;
				try {
					sleep(1500);
				} catch (InterruptedException e) {
					JOptionPane.showMessageDialog(null, "Error");
				}
			}
			if (Simulator.currentThread().isAlive() == false) {
				break;
			}
		}
	}
}