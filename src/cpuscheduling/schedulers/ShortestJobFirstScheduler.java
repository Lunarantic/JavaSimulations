package cpuscheduling.schedulers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpuscheduling.Process;
import cpuscheduling.comparators.NextBurstComparator;
import cpuscheduling.comparators.PidComparator;
import cpuscheduling.comparators.ProcessingTimeRequiredComparator;
import datatypewrappers.DoubleW;
import datatypewrappers.IntegerW;

public class ShortestJobFirstScheduler implements Scheduler {
	
	private Double timer = 0.0;
	StringBuilder gcBuilder = new StringBuilder();
	private Double alpha;
	
	public ShortestJobFirstScheduler() {
		alpha = null;
	}
	
	public ShortestJobFirstScheduler(double timer) {
		this.timer = timer;
	}
	
	public ShortestJobFirstScheduler(double timer, double alpha) {
		this.timer = timer;
		this.alpha = alpha;
	}

	@Override
	public String execute(List<Process> processes) {
		StringBuilder sBuilder = new StringBuilder();
		// To keep track of processes that have been executed
		List<Process> processesExecuted = new ArrayList<>();
		
		if (processes.get(0).isIOBased) {
			processes.forEach(P->{P.alpha=alpha;});
		}
		// Run till we have no more processes to execute
		while (processes.size() > 0){
			// Sort the processes based on when they will get complete in ascending order
			processes.sort(new NextBurstComparator(alpha));
			// Get the process with the least time required to get complete
			Process processInExecution = processes.get(0);
			
			// Check if it is available to execute, if not get next shortest process that is available
			for (int i = 0; timer < processInExecution.getArrivalTime() && i < processes.size(); i++) {
				processes.remove(processInExecution);
				processes.add(processInExecution);
				processInExecution = processes.get(0);
			}
			
			// Time it will need in CPU to get executed
			DoubleW tu = new DoubleW(processInExecution.getNextCPUBurstEndTime(alpha));
			// Put waiting time for all other process and execute current process
			for (Process process : processes) {
				if (process.equals(processInExecution)) {
					for (int i = 0; i < tu.getValue(); i++) {gcBuilder.append(processInExecution.getProcessId()+" ");}
					tu.set(processInExecution.timeUnitForProcessing());
				} else {
					process.timeUnitForWaiting(processInExecution.getCPUBurstTime(), false);
				}
			}
			timer = Process.cpu_timer;
			Process.cpu_timer = timer;
			
			// Remove the current process if its execution is complete
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
//		timer = processes.get(processes.size() -1).getArrivalTime();
		sBuilder.append("CPU utilization :: " + ((1 - cpuTot.getValue() / timer) * 100) + " %\n");
	}
	
	private void calculateThroughput(List<Process> processes, StringBuilder sBuilder) {
		IntegerW pTot = new IntegerW(0);
		processes.forEach(P->{pTot.add(P.getNumberOfTimesExecuted());});
		sBuilder.append("Throughput :: " + (pTot.getValue() / (timer)) + " processes/sec\n");
	}
}