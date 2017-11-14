package datatypewrappers;

public class IntegerW {
	private int number;

	private IntegerW() {	}
	
	public IntegerW(int number) {
		this();
		this.number = number;
	}
	
	public Integer getValue() {
		return number;
	}
	
	public void add(double number) {
		this.number += number;
	}
}