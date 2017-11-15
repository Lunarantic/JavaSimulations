package cpuscheduling;

import java.util.ArrayList;
import java.util.List;

import cpuscheduling.schedulers.FirstComeFirstServeScheduler;
import cpuscheduling.schedulers.RoundRobinScheduler;
import cpuscheduling.schedulers.ShortestJobFirstScheduler;
import cpuscheduling.schedulers.ShortestRemainingTimeFirstScheduler;
import datatypewrappers.FileUtility;

public class Sim5_12 {

	public static Double timer = 0.0;
	
	public static void main(String[] args) {
		FileUtility.deleteFile("Sim5_12.log");
		List<Process> processes = new ArrayList<>(); 
		processes.add(new Process("P1", 10.0, 3));
		processes.add(new Process("P2", 1.0, 1));
		processes.add(new Process("P3", 2.0, 3));
		processes.add(new Process("P4", 1.0, 4));
		processes.add(new Process("P5", 5.0, 2));
		FileUtility.writeTotFile("Sim5_12.log", "First Come First Serve" + "\n");
		FileUtility.writeTotFile("Sim5_12.log", (new FirstComeFirstServeScheduler(false)).execute(processes) + "\n");
		processes = new ArrayList<>(); 
		processes.add(new Process("P1", 10.0, 3, 10.0));
		processes.add(new Process("P2", 1.0, 1, 1.0));
		processes.add(new Process("P3", 2.0, 3, 2.0));
		processes.add(new Process("P4", 1.0, 4, 1.0));
		processes.add(new Process("P5", 5.0, 2, 5.0));
		FileUtility.writeTotFile("Sim5_12.log", "Shortest Job First Serve" + "\n");
		FileUtility.writeTotFile("Sim5_12.log", (new ShortestJobFirstScheduler()).execute(processes) + "\n");
		processes = new ArrayList<>(); 
		processes.add(new Process("P1", 10.0, 3));
		processes.add(new Process("P2", 1.0, 1));
		processes.add(new Process("P3", 2.0, 3));
		processes.add(new Process("P4", 1.0, 4));
		processes.add(new Process("P5", 5.0, 2));
		FileUtility.writeTotFile("Sim5_12.log", "Non-premptive Priority" + "\n");
		FileUtility.writeTotFile("Sim5_12.log", (new FirstComeFirstServeScheduler(true)).execute(processes) + "\n");
		processes = new ArrayList<>(); 
		processes.add(new Process("P1", 10.0, 3));
		processes.add(new Process("P2", 1.0, 1));
		processes.add(new Process("P3", 2.0, 3));
		processes.add(new Process("P4", 1.0, 4));
		processes.add(new Process("P5", 5.0, 2));
		FileUtility.writeTotFile("Sim5_12.log", "Round Robin" + "\n");
		FileUtility.writeTotFile("Sim5_12.log", (new RoundRobinScheduler()).execute(processes) + "\n");
	}
}