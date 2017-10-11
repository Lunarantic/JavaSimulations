package busservice;

import java.util.LinkedList;

/**
 * @author Lunarantic
 *
 */
public class Bus extends Event{
	private LinkedList<BusStop> busStops = new LinkedList<BusStop>();
	public static Double timeToTravelToNextStop = 0.0;
	private Integer busNo;

	private Bus() {}
	
	public Integer getBusNo() {
		return busNo;
	}
	
	public Bus(LinkedList<BusStop> busStops, Integer busNo, Integer interval) {
		this();
		this.busNo = busNo;
		this.busStops.addAll(busStops);
		//Buses are required to be placed at equal distance hence rotating the list to change the current stop of bus
		for (int count = 0; count < (busNo - 1) * interval; count++) {
			this.busStops.addLast(this.busStops.removeFirst());
		}
	}
	
	public String getBusStatus() {
		return "{Bus#" + busNo + "\t" + busStops.getFirst().getBusStatus() + "}";
	}
	
	public String getBusStopStatus() {
		return "\tBus#" + busNo;
	}
	
	public void executeEvent() {
		//Bus will be at start of list
		BusStop currentStop = busStops.getFirst();
		
		//To check whether bus arrived or method call came after a passenger boarded
		if (!currentStop.isThisBusHere(this)) {
			currentStop.busArrived(this);
		}
		
		if (currentStop.passengersAvailable()) {
			//If passenger is available of stop, will board
			currentStop.passengerBoardBus();
			//Set time for next event
			setTimeOfEvent(Sim.timer + Passenger.timeRequiredToBoard);
		} else {
			//no passenger is there, hence departing but will depart in order of arrival
			Bus busAhead = currentStop.busDeparting(this);
			if (busAhead == null) {
				setTimeOfEvent(Sim.timer + timeToTravelToNextStop);
				busStops.removeFirst();
				busStops.addLast(currentStop);
			} else {
				setTimeOfEvent(busAhead.getTimeOfEvent());
			}
		}
		eventPerformed();
		addEvent(this);
	}
}
