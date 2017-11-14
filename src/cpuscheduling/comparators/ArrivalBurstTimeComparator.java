package cpuscheduling.comparators;

import java.util.Comparator;

import cpuscheduling.Process;

public class ArrivalBurstTimeComparator implements Comparator<Process> {

	@Override
	public int compare(Process o1, Process o2) {
		return o1.getArrivalTime().equals(o2.getArrivalTime()) ? o1.getTimeRequiredForExecution().compareTo(o2.getTimeRequiredForExecution()) : o1.getArrivalTime().compareTo(o2.getArrivalTime());
	}
}