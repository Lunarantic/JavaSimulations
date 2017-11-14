package cpuscheduling.comparators;

import java.util.Comparator;

import cpuscheduling.Process;

public class ProcessingTimeRequiredComparator implements Comparator<Process> {

	@Override
	public int compare(Process o1, Process o2) {
		return o1.getTimeRequiredForExecution().compareTo(o2.getTimeRequiredForExecution());
	}

}