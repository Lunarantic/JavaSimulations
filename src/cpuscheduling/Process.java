package cpuscheduling;

public class Process {
	private String process_id;
	private double arrival_time;
	private double intial_arrival_time;
	private double cpu_burst_time;
	private double time_to_process;
	private double time_processed;
	private double waiting_time;
	private int priority;
	private double io_time_required;
	private boolean isIOBased;
	private StringBuilder gc = new StringBuilder();
	
	private Process() {
		waiting_time = 0.0;
		time_processed = 0.0;
		priority = 0;
		arrival_time = 0.0;
		io_time_required = 0.0;
		intial_arrival_time = arrival_time;
		isIOBased = false;
		cpu_burst_time = 1.0;
	}
	
	public Process(String process_id, double arrival_time, double time_to_process) {
		this();
		this.arrival_time = arrival_time;
		this.time_to_process = time_to_process;
		this.process_id = process_id;
		this.intial_arrival_time = arrival_time;
	}
	
	public Process(String process_id, double arrival_time, double time_to_process, double io_time_required, double cpu_burst_time) {
		this();
		this.arrival_time = arrival_time;
		this.time_to_process = time_to_process;
		this.process_id = process_id;
		this.intial_arrival_time = arrival_time;
		this.io_time_required = io_time_required;
		isIOBased = true;
		this.cpu_burst_time = cpu_burst_time;
	}
	
	public Process(String process_id, double time_to_process, int priority) {
		this();
		this.time_to_process = time_to_process;
		this.process_id = process_id;
		this.priority = priority;
	}
	
	public String getProcessId() {
		return process_id;
	}
	
	public String getGanttChart() {
		return process_id + " " + gc.toString();
	}
	
	public Double getArrivalTime() {
		if (isIOBased) return arrival_time;
		return intial_arrival_time;
	}
	
	public Double getCPUBurstTime() {
		return cpu_burst_time;
	}
	
	public Double getTimeRequiredForExecution() {
		return time_to_process;
	}
	
	public Double getWaitingTime() {
		return waiting_time;
	}
	
	public Integer getPriority() {
		return priority;
	}
	
	public void timeUnitForWaiting(Double time_unit, boolean isRR) {
		if (time_unit == 0) return;
		if (isIOBased) {
			isRR = true;
		}
		boolean g = false;
		if (isRR && time_processed != time_to_process) {
			waiting_time += time_unit;
			g = true;
		}
		else if (time_processed == 0){
			waiting_time += time_unit;
			g = true;
		}
		if (g) {
			if (time_unit > 0) {
				gc.append(" ");
			} else if (gc.length() > 0) {
				gc.deleteCharAt(gc.length() - 1);
			}
		}
	}

	public Double timeUnitForProcessing() {
		if (isIOBased) {
			arrival_time += io_time_required + cpu_burst_time;
		}
		gc.append("#");
		return time_to_process - (time_processed += cpu_burst_time);
	}
	
	public Double getTurnAroundTime() {
		return waiting_time + time_processed - intial_arrival_time;
	}

	@Override
	public String toString() {
		return "Process:"+process_id+"\tArrived time:"+intial_arrival_time+"\tTurn Around time:"+getTurnAroundTime()+"\tWaiting time:"+getWaitingTime();
	}

	public Double getTimeLeftForExecution() {
		return time_to_process - time_processed;
	}
}