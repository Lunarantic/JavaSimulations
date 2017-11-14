package cpuscheduling.comparators;

import java.util.Comparator;

import cpuscheduling.Process;

public class ProcessingTimeLeftComparator implements Comparator<Process> {

	@Override
	public int compare(Process o1, Process o2) {
		return o1.getTimeLeftForExecution().compareTo(o2.getTimeLeftForExecution());
	}

}