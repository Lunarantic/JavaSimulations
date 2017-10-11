package busservice;

import java.util.LinkedList;

/**
 * @author Lunarantic
 *
 */
public class BusStop extends Event {
	private LinkedList<Passenger> passengers = new LinkedList<Passenger>();
	public static Double meanInterArrivalRate = 0.0;
	private Integer busStopNo;
	private LinkedList<Bus> busAtStop = new LinkedList<Bus>();
	
	private BusStop() {	}
	
	public BusStop(Integer busStopNo) {
		this();
		this.busStopNo = busStopNo;
	}

	public String getBusStatus() {
		return "Stop#" + busStopNo + "\tPass#" + passengers.size();
	}
	
	public StringBuilder getBusStopStatus() {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("[Stop#" + busStopNo + "\tPass#" + passengers.size());
		busAtStop.forEach((B)->{sBuilder.append(B.getBusStopStatus());});
		sBuilder.append("]");
		return sBuilder;
	}
	
	public Integer getPassengersCount(){
		return passengers.size();
	}
	
	public void executeEvent() {
		//Event of new passenger arrival
		passengers.add(new Passenger());
		eventPerformed();
		//Set time of next passenger arrival
		setTimeOfEvent(Sim.timer + meanInterArrivalRate * RandomGenerator.random());
		addEvent(this);
	}
	
	public void passengerBoardBus() {
		passengers.removeFirst();
	}
	
	public boolean passengersAvailable() {
		if (passengers.size() > 0) {
			return true;
		}
		return false;
	}
	
	public void busArrived(Bus bus) {
		busAtStop.add(bus);
	}
	
	public Bus busDeparting(Bus bus) {
		//If no bus ahead of it, bus will depart else will wait, to avoid crossing
		if (busAtStop.peekFirst().equals(bus)){
			busAtStop.remove(bus);
			return null;
		}
		
		return busAtStop.get(busAtStop.indexOf(bus) - 1);
	}
	
	public boolean isThisBusHere(Bus bus) {
		return busAtStop.contains(bus);
	}
}