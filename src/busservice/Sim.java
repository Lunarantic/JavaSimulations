package busservice;

import java.util.LinkedList;

/**
 * @author Lunarantic
 *
 */
public class Sim {
	
	public static Double timer = 0.0;
	public static LinkedList<Event> events = new LinkedList<Event>();
	private static LinkedList<BusStop> busStops = new LinkedList<BusStop>();
	private static LinkedList<Bus> buses = new LinkedList<Bus>();

	public static void main(String[] args) {
		//Initialization variables for Simulation; as per question
		Double simulationTime = 8 * 60 * 60.0;
		Integer noOfBuses = 5;
		Bus.timeToTravelToNextStop = 5 * 60.0;
		Integer noOfBusStops = 15;
		Passenger.timeRequiredToBoard = 2.0;
		//5 person/min is equal to 1 person/12 secs
		BusStop.meanInterArrivalRate = 12.0; 
		
		//Creation of Buses and Stops with their next events
		createBusStops(noOfBusStops);
		createBus(noOfBuses, noOfBusStops);
		
		//Counter of time quantum for checking status of simulation
		long checkpoint = 0;
		
		StringBuilder busStatusBuilder = new StringBuilder();
		StringBuilder busStopStatusBuilder = new StringBuilder();
		
		System.out.println("***********Simulation Starts************");
		while (timer <= simulationTime) {
			//The event is being executed as the time has arrived
			Event currentEvent = events.removeFirst();
			timer = currentEvent.getTimeOfEvent();
			currentEvent.executeEvent();
			
			
			//Checking the status of simulation at point of time quantum
			if (timer >= checkpoint) {
				busStatusBuilder.append(getBusStatus() + "\n");
				busStopStatusBuilder.append(getBusStopStatus() + "\n");
				checkpoint += 60 * 60;
			}
		}
		System.out.println("***********Statuses of Buses************");
		System.out.println(busStatusBuilder.toString());
		System.out.println("***********Statuses of BusStops************");
		System.out.println(busStopStatusBuilder.toString());
		System.out.println("***********Simulation Complete************");
	}
	
	private static void createBus(Integer busCount, Integer stopCount) {
		for (int counter = 1; busCount >= counter; counter++) {
			buses.add(new Bus(busStops, counter, stopCount/busCount));
			//start execution of events
			buses.getLast().executeEvent();
		}
	}
	
	private static void createBusStops(Integer count) {
		for (int counter = 1; count >= counter; counter++) {
			busStops.add(new BusStop(counter));
			//start execution of events
			busStops.getLast().executeEvent();
		}
	}
	
	public static StringBuilder getBusStatus() {
		StringBuilder status = new StringBuilder();
		buses.forEach((B) -> {status.append(B.getBusStatus() + "\t\t");});
		return status;
	}
	
	public static StringBuilder getBusStopStatus() {
		StringBuilder status = new StringBuilder();
		busStops.forEach((B) -> {status.append(B.getBusStopStatus() + "\t\t");});
		return status;
	}
}
