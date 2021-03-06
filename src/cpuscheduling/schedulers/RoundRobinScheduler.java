package cpuscheduling.schedulers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpuscheduling.Process;
import cpuscheduling.comparators.PidComparator;
import datatypewrappers.DoubleW;
import datatypewrappers.IntegerW;

public class RoundRobinScheduler implements Scheduler {
	
	private Double timer = 0.0;
	StringBuilder gcBuilder = new StringBuilder();
	
	public RoundRobinScheduler() { }

	@Override
	public String execute(List<Process> processes) {
		StringBuilder sBuilder = new StringBuilder();
		// To keep track of processes that have been executed
		List<Process> processesExecuted = new ArrayList<>();
		// Run till we have no more processes to execute
		while (processes.size() > 0) {
			// Get the process the which next line for execution as per round robin
			Process processInExecution = processes.get(0);
			// Time it will need in CPU to get executed
			DoubleW tu = new DoubleW(processInExecution.getCPUBurstTime());
			
			// Put waiting time for all other process and execute current process
			timer += processInExecution.getNextCPUBurstEndTime(null) - timer;
			for (Process process : processes) {
				if (process.equals(processInExecution)) {
					for (int i = 0; i < tu.getValue(); i++) gcBuilder.append(processInExecution.getProcessId()+" ");
					tu.set(processInExecution.timeUnitForProcessing());
				} else {
					process.timeUnitForWaiting(processInExecution.getNextCPUBurstEndTime(null), true);
				}
			}
			timer += tu.getValue();

			// Remove the current process if its execution is complete
			if (tu.getValue() <= 0) {
				processes.forEach(P -> {P.timeUnitForWaiting(tu.getValue(), true);});
				processesExecuted.add(processInExecution);
				processes.remove(processInExecution);
			} else {
				processes.remove(0);
				processes.add(processInExecution);
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

		if (!processesExecuted.get(0).isIOBased) {
			// Create Gantt chart of processes
			Map<String, StringBuilder> pGCMap = new HashMap<>();
			processesExecuted.forEach(P -> {pGCMap.put(P.getProcessId(), new StringBuilder());});
			for (String s : gcBuilder.toString().split(" ")) {
				pGCMap.forEach((K,V) -> {V.append(K.equals(s) ? "#" : " ");});
			}
			
			sBuilder.append("===========================================================" + "\n");
			pGCMap.forEach((K,V) -> {sBuilder.append(K+" "+V+"\n");});
			sBuilder.append("===========================================================" + "\n");
		}
		
		if (processesExecuted.get(0).isIOBased) {
			calculateCPUutilization(processesExecuted, sBuilder);
			calculateThroughput(processesExecuted, sBuilder);
		}

		return sBuilder.toString();
	}
	
	private void calculateCPUutilization(List<Process> processes, StringBuilder sBuilder) {
		DoubleW cpuTot = new DoubleW(0.0);
		processes.forEach(P->{cpuTot.add(P.getTimeRequiredForExecution());});
		sBuilder.append("CPU utilization :: " + ((1 - cpuTot.getValue() / timer) * 100) + " %\n");
	}
	
	private void calculateThroughput(List<Process> processes, StringBuilder sBuilder) {
		IntegerW pTot = new IntegerW(0);
		processes.forEach(P->{pTot.add(P.getNumberOfTimesExecuted());});
		sBuilder.append("Throughput :: " + (pTot.getValue() / (timer)) + " processes/sec\n");
	}
}