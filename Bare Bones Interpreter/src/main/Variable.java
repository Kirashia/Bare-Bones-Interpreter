package main;

public class Variable {

	public String name;
	private int value;
	
	public Variable(String nameT, int valueT) {
		name = nameT;
		value = valueT;
		
		System.out.println("Variable "+name+" declared ("+value+")");
		
		if(value < 0) {
			System.err.println("Cannot use non-negative variables");
			System.exit(0);
		}
	}
	
	public int value() {
		return value;
	}
	
	public void decr() {
		value--;
		System.out.println("Variable "+name+" decremented ("+value+")");

		if(value < 0) {
			System.out.println("Cannot use non-negative variables".toUpperCase());
			System.exit(0);
		}
	}
	

	public void incr() {
		value++;
		System.out.println("Variable "+name+" incremented ("+value+")");
	}
	
	public void clear() {
		value = 0;
		System.out.println("Variable "+name+" cleared ("+value+")");
	}
	
}
