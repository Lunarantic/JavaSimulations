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
	public boolean isIOBased;
	private static double io_timer;
	private static double previous_executions_time;
	private int num_exe;
	public static double cpu_timer;
	public Double alpha;
	
	private Process() {
		waiting_time = 0.0;
		time_processed = 0.0;
		priority = 0;
		arrival_time = 0.0;
		io_time_required = 0.0;
		intial_arrival_time = arrival_time;
		isIOBased = false;
		cpu_burst_time = 1.0;
		num_exe = 0;
		cpu_timer = 0.0;
		previous_executions_time = 0.0;
		io_timer = 0.0;
	}
	
	public Process(String process_id, double arrival_time, double time_to_process) {
		this();
		this.arrival_time = arrival_time;
		this.time_to_process = time_to_process;
		this.process_id = process_id;
		this.intial_arrival_time = arrival_time;
	}
	
	public Process(String process_id, double arrival_time, double time_to_process, double cpu_burst_time) {
		this();
		this.arrival_time = arrival_time;
		this.time_to_process = time_to_process;
		this.process_id = process_id;
		this.intial_arrival_time = arrival_time;
		this.cpu_burst_time = cpu_burst_time;
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
	
	public Process(String process_id, double time_to_process, int priority, double cpu_burst_time) {
		this();
		this.time_to_process = time_to_process;
		this.process_id = process_id;
		this.priority = priority;
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
		if (isRR && time_processed != time_to_process) {
			waiting_time += time_unit;
		}
		else if (time_processed == 0){
			waiting_time += time_unit;
		}
	}

	public Double timeUnitForProcessing() {
		num_exe++;
		cpu_timer += cpu_burst_time;
		if (isIOBased) {
			arrival_time = io_time_required + cpu_burst_time + io_timer;
			if (io_timer == 0.0) io_timer += io_time_required + cpu_burst_time;
			else io_timer += io_time_required;
			if (alpha != null) previous_executions_time = (Math.pow((1-alpha), num_exe) * cpu_burst_time) + (alpha * (previous_executions_time + (Math.pow((1-alpha), num_exe) * cpu_burst_time)));
			
			if (time_to_process - (time_processed += cpu_burst_time) <= 0){
				return time_to_process - time_processed;
			} else {
				return 1.0;
			}
		}
		return time_to_process - (time_processed += cpu_burst_time);
	}
	
	public Double getTurnAroundTime() {
		return waiting_time + time_processed - intial_arrival_time;
	}

	@Override
	public String toString() {
		return "Process:"+process_id+"\tArrived time:"+intial_arrival_time+"\tTurn Around time:"+getTurnAroundTime()+"\tWaiting time:"+getWaitingTime() + "\t" + time_processed + "\t" + time_to_process+"\t"+arrival_time;
	}

	public Double getTimeLeftForExecution() {
		return time_to_process - time_processed;
	}
	
	public Double getNextCPUBurstEndTime(Double alpha) {
		if (alpha == null || alpha == 1.0)
		{return cpu_burst_time + arrival_time;}
		else {
			double cur_pred = (Math.pow((1-alpha), num_exe) * cpu_burst_time) + (alpha * (previous_executions_time + (Math.pow((1-alpha), num_exe) * cpu_burst_time)));
			return cur_pred;
		}
	}
	
	public Integer getNumberOfTimesExecuted() {
		return num_exe;
	}
}