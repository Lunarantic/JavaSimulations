package memorymanagement;

public class Sim {
	
	public static long K = 1024;
	public static long M = 1024 * 1024;

	public static void main(String[] args) {
		BuddySystem bs = new BuddySystem(2*M);
		
		Process pA = new Process("A", 20*K);
		Process pB = new Process("B", 35*K);
		Process pC = new Process("C", 90*K);
		Process pD = new Process("D", 40*K);
		Process pE = new Process("E", 240*K);
		
		bs.action(pA, true);
		bs.action(pB, true);
		bs.action(pC, true);
		bs.action(pD, true);
		bs.action(pE, true);
		bs.action(pD, false);
		bs.action(pA, false);
		bs.action(pC, false);
		bs.action(pB, false);
		bs.action(pE, false);
	}
}