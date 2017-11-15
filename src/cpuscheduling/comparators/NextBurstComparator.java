package cpuscheduling.comparators;

import java.util.Comparator;
import cpuscheduling.Process;

public class NextBurstComparator implements Comparator<Process>{
	private Double aplha = 0.0;
	
	private NextBurstComparator() { }
	public NextBurstComparator(Double alpha) { 
		this();
		this.aplha = alpha;
	}
	
	@Override
	public int compare(Process o1, Process o2) {
		return o1.getNextCPUBurstEndTime(aplha).compareTo(o2.getNextCPUBurstEndTime(aplha));
	}
}