package cpuscheduling.schedulers;

import java.util.List;

import cpuscheduling.Process;

public interface Scheduler {

	public String execute(List<Process> processes);
}
