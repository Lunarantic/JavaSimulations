package cpuscheduling;

import java.util.ArrayList;
import java.util.List;

import cpuscheduling.schedulers.FirstComeFirstServeScheduler;
import cpuscheduling.schedulers.RoundRobinScheduler;
import cpuscheduling.schedulers.ShortestJobFirstScheduler;
import cpuscheduling.schedulers.ShortestRemainingTimeFirstScheduler;
import datatypewrappers.FileUtility;

public class Sim2 {
	public static void main(String[] args) {
		FileUtility.deleteFile("Sim2.log");
		List<Process> processes = new ArrayList<>();
		for (double b = 0.025; processes.size() < 10; processes.add(new Process("P"+processes.size(), 0.0, RandomGenerator.random() * 120 + 120, 0.06, b += 0.005)));
		FileUtility.writeTotFile("Sim2.log", "First Come Serve Scheduler" + "\n");
		FileUtility.writeTotFile("Sim2.log", (new FirstComeFirstServeScheduler(false)).execute(processes) + "\n");
		processes = new ArrayList<>();
		RandomGenerator.reset();
		for (double b = 0.025; processes.size() < 10; processes.add(new Process("P"+processes.size(), 0.0, RandomGenerator.random() * 120 + 120, 0.06, b += 0.005)));
		FileUtility.writeTotFile("Sim2.log", "Shortest Job First Scheduler" + "\n");
		FileUtility.writeTotFile("Sim2.log", (new ShortestRemainingTimeFirstScheduler()).execute(processes) + "\n");
		processes = new ArrayList<>();
		RandomGenerator.reset();
		for (double b = 0.025; processes.size() < 10; processes.add(new Process("P"+processes.size(), 0.0, RandomGenerator.random() * 120 + 120, 0.06, b += 0.005)));
		FileUtility.writeTotFile("Sim2.log", "Round Robin Scheduler" + "\n");
		FileUtility.writeTotFile("Sim2.log", (new RoundRobinScheduler()).execute(processes) + "\n");
	}
}