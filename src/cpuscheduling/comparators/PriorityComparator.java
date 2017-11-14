package cpuscheduling.comparators;

import java.util.Comparator;

import cpuscheduling.Process;

public class PriorityComparator implements Comparator<Process> {

	@Override
	public int compare(Process o1, Process o2) {
		return o1.getPriority().equals(o2.getPriority()) ? o1.getTimeRequiredForExecution().compareTo(o2.getTimeRequiredForExecution()) : o1.getPriority().compareTo(o2.getPriority());
	}

}