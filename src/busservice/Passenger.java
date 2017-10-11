package busservice;

/**
 * @author Lunarantic
 *
 */
public class Passenger {
	private Double time_of_arrival;
	public static Double timeRequiredToBoard = 0.0;
	
	public Passenger() {
		time_of_arrival = Sim.timer;
	}
	
	public Passenger(double time_of_arrival) {
		this();
		this.time_of_arrival = time_of_arrival;
	}
	
	public Double waiting_from() {
		return Sim.timer - time_of_arrival;
	}
}
