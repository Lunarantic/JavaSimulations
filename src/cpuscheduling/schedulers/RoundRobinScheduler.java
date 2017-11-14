package cpuscheduling.schedulers;

import java.util.ArrayList;
import java.util.List;

import cpuscheduling.Process;
import cpuscheduling.comparators.PidComparator;
import datatypewrappers.DoubleW;

public class RoundRobinScheduler implements Scheduler {
	
	public RoundRobinScheduler() { }

	@Override
	public String execute(List<Process> processes) {
		StringBuilder sBuilder = new StringBuilder();
		List<Process> processesExecuted = new ArrayList<>();
		int icnt = 0;
		while (processes.size() > 0) {
			Process processInExecution = processes.get(icnt++ % processes.size());
			DoubleW tu = new DoubleW(processInExecution.getCPUBurstTime());
			
			processes.forEach(P -> {P.timeUnitForWaiting(tu.getValue(), true);});
			processInExecution.timeUnitForWaiting(-tu.getValue(), true);
			tu.set(processInExecution.timeUnitForProcessing());
			if (tu.getValue() <= 0) {
				processes.forEach(P -> {P.timeUnitForWaiting(tu.getValue(), true);});
				icnt = (icnt % processes.size())-1;
				icnt = icnt < 0 ? 0 : icnt;
				processesExecuted.add(processInExecution);
				processes.remove(processInExecution);
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