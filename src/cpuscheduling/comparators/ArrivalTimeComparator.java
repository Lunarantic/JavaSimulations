package cpuscheduling.comparators;

import java.util.Comparator;

import cpuscheduling.Process;

public class ArrivalTimeComparator implements Comparator<Process> {

	@Override
	public int compare(Process o1, Process o2) {
		return o1.getArrivalTime().compareTo(o2.getArrivalTime());
	}
}
