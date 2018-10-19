package main;

import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("********START********");
		
		Main p = new Main();
		String[] code = null;
		
		try {
			code = p.getFileData("src/main/code.txt").replaceAll("\t","    ").replaceAll("\n", "").split(";");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Stack<String[]> stack = new Stack<String[]>();			// Tracks code loops
		Stack<Integer> lineTrace = new Stack<Integer>();		// Tracks end of loops
		Stack<Object[]> loopWatch = new Stack<Object[]>();		// Tracks loop conditions
		ArrayList<Variable> vars = new ArrayList<Variable>();	// Tracks variable states
		
		lineTrace.push(new Integer(0));
		
		stack.push(code);
		String[] block;
		
		// MAIN RUN LOOP
		while(!stack.isEmpty()) {
			block = stack.peek();
			
			if(lineTrace.peek() >= code.length)
				break;
			
			System.out.println();
			
			for(int i = lineTrace.peek(); i < block.length; i++) {
				String line = block[i].trim();
				
				// Comment
				if(line.length() > 1 && line.substring(0,2).equals("##"))
					continue;
				
				// Has to find all code in this WHILE block
				if(line.contains("while")) {
					int exists = p.varInStack(line.split(" ")[1], vars); 
					Variable v; 
					if(exists >= 0) {
						v = vars.get(exists);
					} else {
						v = new Variable(line.split(" ")[1], 0);
						vars.add(v);
					}
					
					// Adds variable reference and stop condition to stack
					loopWatch.push(new Object[] {v, Integer.parseInt(line.split(" ")[3])});
					
					// Find when the loop ends and make a new block of code to add to the stack
					int c = 1;	// This tracks the number of nested loops in the code
					ArrayList<String> newBlock = new ArrayList<String>();
					for(int n = i+1; n < block.length; n++) {
						// Would end the loop
						if(block[n].contains("end")) {
							c--;
						}
						
						// If all loops finished then it can stop finding new code
						if(c == 0) {
							lineTrace.pop();
							lineTrace.push(n+1);
							lineTrace.push(0);
							break;
						}
						
						// Add the line to the new block of code
						newBlock.add(block[n]);
						
						// If there is a nested loop then we know here
						if(block[n].contains("while")) {
							c++;
						}
					}
					
					// Add the code to the program stack
					// Has to be converted to static array because I suck
					stack.push(newBlock.toArray(new String[newBlock.size()]));
					block = new String[0];
				// CLEAR
				} else if(line.contains("clear")) {
					int exists = p.varInStack(line.split(" ")[1], vars); 
					if(exists >= 0) {
						vars.get(exists).clear();
					} else {
						vars.add(new Variable(line.split(" ")[1], 0));
					}
				
				// INCREMENT
				} else if (line.contains("incr")) {
					int exists = p.varInStack(line.split(" ")[1], vars); 
					if(exists >= 0) {
						vars.get(exists).incr();
					} else {
						vars.add(new Variable(line.split(" ")[1], 1));
					}
					
				// DECREMENT
				} else if (line.contains("decr")) {
					int exists = p.varInStack(line.split(" ")[1], vars); 
					if(exists >= 0) {
						vars.get(exists).decr();
					} else {
						vars.add(new Variable(line.split(" ")[1], 0));
					}
					
				// ADD by a factor
				} else if (line.contains("add")) {
					int exists = p.varInStack(line.split(" ")[1], vars); 
					if(exists >= 0) {
						vars.get(exists).add(Float.parseFloat(line.split(" ")[2]));
					} else {
						vars.add(new Variable(line.split(" ")[1], 0));
					}
					
				// SUBTRACT by a factor
				} else if (line.contains("sub")) {
					int exists = p.varInStack(line.split(" ")[1], vars); 
					if(exists >= 0) {
						vars.get(exists).sub(Float.parseFloat(line.split(" ")[2]));
					} else {
						vars.add(new Variable(line.split(" ")[1], 0));
					}
					
				// DIVIDE by a factor
				} else if (line.contains("div")) {
					int exists = p.varInStack(line.split(" ")[1], vars); 
					if(exists >= 0) {
						vars.get(exists).div(Float.parseFloat(line.split(" ")[2]));
					} else {
						vars.add(new Variable(line.split(" ")[1], 0));
					}
					
				// MULTIPLY by a factor
				} else if (line.contains("mul")) {
					int exists = p.varInStack(line.split(" ")[1], vars); 
					if(exists >= 0) {
						vars.get(exists).mul(Float.parseFloat(line.split(" ")[2]));
					} else {
						vars.add(new Variable(line.split(" ")[1], 0));
					}
				}
				
			}
			
			if(!loopWatch.isEmpty() && ((Variable) loopWatch.peek()[0]).value() == (Integer) loopWatch.peek()[1]) {
				// Remove done code from the top of the stack
				stack.pop();
				// Remove variable from stack
				loopWatch.pop();
				// Remove line reference from stack
				lineTrace.pop();
			} else {
				lineTrace.pop();
				lineTrace.push(0);
			}
			
		}
		
		System.out.println("****PROGRAM TERMINATED****");
		
		for(Variable v : vars) {
			System.out.println("## Variable "+v.name+": "+v.value());
		}
		
		System.out.println("********END********");
	}
	
	public int varInStack(String name, ArrayList<Variable> vars) {
		for(int i = 0; i < vars.size(); i++) {
			Variable v = vars.get(i);
			if(v.name.equals(name))
				return i;
		}
		
		return -1;
	}
	
	public String getFileData(String filename) throws IOException {
		
		File file = new File(filename);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String data = "";
		String line;
		
		while((line = br.readLine()) != null) {
			data += line;
		}
		br.close();
		return data;
	}

}
