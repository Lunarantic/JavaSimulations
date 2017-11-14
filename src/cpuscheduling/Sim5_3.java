package cpuscheduling;

import java.util.ArrayList;
import java.util.List;

import cpuscheduling.schedulers.FirstComeFirstServeScheduler;
import cpuscheduling.schedulers.ShortestJobFirstScheduler;
import cpuscheduling.schedulers.ShortestRemainingTimeFirstScheduler;
import datatypewrappers.FileUtility;

public class Sim5_3 {

	public static Double timer = 0.0;
	
	public static void main(String[] args) {
		FileUtility.deleteFile("Sim5_3.log");
		List<Process> processes = new ArrayList<>();
		processes.add(new Process("P1", 0.0, 8.0));
		processes.add(new Process("P2", 0.4, 4.0));
		processes.add(new Process("P3", 1.0, 1.0));
		FileUtility.writeTotFile("Sim5_3.log", "First Come First Serve" + "\n");
		FileUtility.writeTotFile("Sim5_3.log", (new FirstComeFirstServeScheduler(false)).execute(processes) + "\n");
		processes = new ArrayList<>(); 
		processes.add(new Process("P1", 0.0, 8.0));
		processes.add(new Process("P2", 0.4, 4.0));
		processes.add(new Process("P3", 1.0, 1.0));
		FileUtility.writeTotFile("Sim5_3.log", "Shortest Job First Serve" + "\n");
		FileUtility.writeTotFile("Sim5_3.log", (new ShortestJobFirstScheduler()).execute(processes) + "\n");
		processes = new ArrayList<>(); 
		processes.add(new Process("P1", 0.0, 8.0));
		processes.add(new Process("P2", 0.4, 4.0));
		processes.add(new Process("P3", 1.0, 1.0));
		FileUtility.writeTotFile("Sim5_3.log", "Shortest  Remaining time Job First Serve" + "\n");
		FileUtility.writeTotFile("Sim5_3.log", (new ShortestJobFirstScheduler(1.0)).execute(processes) + "\n");
	}
}