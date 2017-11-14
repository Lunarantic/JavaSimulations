package cpuscheduling.schedulers;

import java.util.ArrayList;
import java.util.List;

import cpuscheduling.Process;
import cpuscheduling.comparators.PidComparator;
import cpuscheduling.comparators.ProcessingTimeRequiredComparator;
import datatypewrappers.DoubleW;

public class ShortestJobFirstScheduler implements Scheduler {
	
	private Double timer = 0.0;
	
	public ShortestJobFirstScheduler() { }
	
	public ShortestJobFirstScheduler(double timer) {
		this.timer = timer;
	}

	@Override
	public String execute(List<Process> processes) {
		StringBuilder sBuilder = new StringBuilder();
		List<Process> processesExecuted = new ArrayList<>();
		processes.sort(new ProcessingTimeRequiredComparator());
		while (processes.size() > 0){
			Process processInExecution = processes.get(0);
			while (timer < processInExecution.getArrivalTime()) {
				processes.remove(processInExecution);
				processes.add(processInExecution);
				processInExecution = processes.get(0);
			}
			DoubleW tu = new DoubleW(processInExecution.getCPUBurstTime());
			processes.forEach(P -> {P.timeUnitForWaiting(tu.getValue(), false);});
			processInExecution.timeUnitForWaiting(-tu.getValue(), false);
			tu.set(processInExecution.timeUnitForProcessing());
			timer += tu.getValue();
			if (tu.getValue() <= 0) {
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