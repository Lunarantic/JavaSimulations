package cpuscheduling;

/**
 * @author Lunarantic
 *
 */
public class RandomGenerator {

	private static double seed = 1000;
	
	public static Double random(){
		seed = (25173*seed + 13849) % 65536;
		return seed/65536;
	}
	
	public static void reset() {
		seed = 1000;
	}
}