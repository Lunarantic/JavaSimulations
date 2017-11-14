package cpuscheduling.schedulers;

import java.util.ArrayList;
import java.util.List;

import cpuscheduling.Process;
import cpuscheduling.comparators.ArrivalTimeComparator;
import cpuscheduling.comparators.PidComparator;
import cpuscheduling.comparators.PriorityComparator;
import datatypewrappers.DoubleW;

public class FirstComeFirstServeScheduler implements Scheduler {
	
	private boolean priority;
	private StringBuilder processExecutedOrder = new StringBuilder();
	
	private FirstComeFirstServeScheduler() {
		priority = false;
	}
	
	public FirstComeFirstServeScheduler(boolean prioritybased) {
		this();
		this.priority = prioritybased;
	}

	@Override
	public String execute(List<Process> processes) {
		StringBuilder sBuilder = new StringBuilder();
		List<Process> processesExecuted = new ArrayList<>();
		while (processes.size() > 0){
			if (priority) processes.sort(new PriorityComparator());
			else processes.sort(new ArrivalTimeComparator());
			
			Process processInExecution = processes.get(0);
			DoubleW tu = new DoubleW(processInExecution.getCPUBurstTime());
			processes.forEach(P -> {P.timeUnitForWaiting(tu.getValue(), false);});
			processInExecution.timeUnitForWaiting(-tu.getValue(), false);
			tu.set(processInExecution.timeUnitForProcessing());
			processExecutedOrder.append(processInExecution.getProcessId() + " ");
			
			if(tu.getValue() <= 0) {
				processes.forEach(P -> {P.timeUnitForWaiting(tu.getValue(), false);});
				processesExecuted.add(processInExecution);
				processes.remove(0);
			}
		}
		DoubleW tatTot = new DoubleW(0.0);
		DoubleW waitTot = new DoubleW(0.0);
		processesExecuted.sort(new PidComparator());
		processesExecuted.forEach(P->{sBuilder.append(P + "\n");
							  		  tatTot.add(P.getTurnAroundTime());
							  		  waitTot.add(P.getWaitingTime());});
		sBuilder.append("Avg TAT:" + tatTot.getValue()/processesExecuted.size() + "\n");
		sBuilder.append("WT:" + waitTot.getValue()/processesExecuted.size() + "\n");
		sBuilder.append("===========================================================" + "\n");
		processesExecuted.forEach(P->{sBuilder.append(P.getGanttChart() + "\n");});
		sBuilder.append("===========================================================" + "\n");
		
		return sBuilder.toString();
	}
}