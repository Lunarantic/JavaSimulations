package cpuscheduling.schedulers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpuscheduling.Process;
import cpuscheduling.comparators.PidComparator;
import cpuscheduling.comparators.ProcessingTimeLeftComparator;
import datatypewrappers.DoubleW;

public class ShortestRemainingTimeFirstScheduler implements Scheduler {
	
	private Double timer = 0.0;
	StringBuilder gcBuilder = new StringBuilder();

	@Override
	public String execute(List<Process> processes) {
		StringBuilder sBuilder = new StringBuilder();
		List<Process> processesExecuted = new ArrayList<>();
		while (processes.size() > 0){
			processes.sort(new ProcessingTimeLeftComparator());
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
			gcBuilder.append(processInExecution.getProcessId()+" ");
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

		Map<String, StringBuilder> pGCMap = new HashMap<>();
		processesExecuted.forEach(P -> {pGCMap.put(P.getProcessId(), new StringBuilder());});
		for (String s : gcBuilder.toString().split(" ")) {
			pGCMap.forEach((K,V) -> {V.append(K.equals(s) ? "#" : " ");});
		}
		
		sBuilder.append("===========================================================" + "\n");
		pGCMap.forEach((K,V) -> {sBuilder.append(K+" "+V+"\n");});
		sBuilder.append("===========================================================" + "\n");
		return sBuilder.toString();
	}
}