package datatypewrappers;

public class DoubleW {
	private double number;

	private DoubleW() {	}
	
	public DoubleW(double number) {
		this();
		this.number = number;
	}
	
	public Double getValue() {
		return number;
	}
	
	public void add(double number) {
		this.number += number;
	}
	
	public Double set(double number) {
		return this.number = number;
	}
}