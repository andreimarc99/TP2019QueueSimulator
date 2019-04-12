package ro.tuc.tp.assig2;

public class Client {
	private int id;
	private int arrivalTime;
	private int serviceTime;
	
	public Client(int id, int arrivalTime, int serviceTime) {
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getArrivalTime() {
		return arrivalTime;
	}
	
	public void setArrivalTime(int arrivingTime) {
		this.arrivalTime = arrivingTime;
	}
	
	public int getServiceTime() {
		return serviceTime;
	}
	
	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}
	
}
