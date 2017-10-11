package busservice;

/**
 * @author Lunarantic
 *
 */
public abstract class Event {
	private Double timeOfEvent;
	private Integer position = -1;
	
	public abstract void executeEvent();
	
	protected void setTimeOfEvent(Double timeOfEvent) {
		this.timeOfEvent = timeOfEvent;
	}
	
	public Double getTimeOfEvent() {
		return timeOfEvent;
	}
	
	public void eventPerformed() {
		position = 0;
	}
	
	public void incrementPosition() {
		position++;
	}
	
	public Integer getPosition() {
		return position;
	}
	
	public void addEvent(Event event) {
		//Put new events as per their order in execution of time
		Sim.events.forEach((E) -> {if (E.getTimeOfEvent() <= event.getTimeOfEvent()) {
			event.incrementPosition();
		}});
		Sim.events.add(event.getPosition(), event);
	}
}
