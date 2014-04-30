package hadoop;

/**
 * Map-Reduce Output Generator
 * 
 * @author Vincent Lee
 * @author Will Henry
 * @since April 24, 2014
 * @version 1.0
 */

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class MapReduce {
	private FileStream stream;
	private String outputFolder;
	private boolean fileOutput;
	private Map<String, Set<String>> inMap;
	private Map<String, Set<String>> outMap;
	
	public MapReduce(String filename, String outputFolder) {
		this.stream = new FileStream();
		this.stream.openFile(filename);
		this.outputFolder = outputFolder;
		this.fileOutput = (outputFolder != null) ? true : false;
		this.inMap = new TreeMap<String, Set<String>>();
		this.outMap = new TreeMap<String, Set<String>>();
	}
	
	public void compute() {
		while (stream.next()) {
			List<String> tuple = stream.nextTuple();
			
			//Pair -> Key
			if (!inMap.containsKey(tuple.get(1)))
				inMap.put(tuple.get(1), new TreeSet<String>());
			inMap.get(tuple.get(1)).add(tuple.get(0));
			
			//Key -> Pair
			if (!outMap.containsKey(tuple.get(0)))
				outMap.put(tuple.get(0), new TreeSet<String>());
			outMap.get(tuple.get(0)).add(tuple.get(1));
		}
		
		//Part 1
		inOutPrinter();
		//Part 2
		mapSetPrinter(inMap);
	}
	
	public void inOutPrinter() {
		try {
			//File Stuff
			
			File log = null;
			PrintWriter out = null;
			if (fileOutput) {
				//create dir
				new File(outputFolder).mkdir();
				log = new File(Paths.get(outputFolder, "result1").toString());
				out = new PrintWriter(new FileWriter(log, true));
			}
			
			//Join both keySets - for zero in or zero out
			SortedSet<String> completeSet = new TreeSet<String>();
			completeSet.addAll(inMap.keySet());
			completeSet.addAll(outMap.keySet());
			
			for (String key : completeSet) {
				if (fileOutput) {
					out.append(key + " ");
					out.append((outMap.containsKey(key) ? outMap.get(key).size() : "0").toString());
					out.append(" ");
					out.append((inMap.containsKey(key) ? inMap.get(key).size() : "0").toString());
					out.append("\n");
				} else {
					//Document
					System.out.print(key + " ");
					System.out.print(outMap.containsKey(key) ? outMap.get(key).size() : "0"); //Out
					System.out.print(" ");
					System.out.println(); //In
				}
			}
			if (fileOutput)
				out.close();
			else
				System.out.println();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void mapSetPrinter(Map<String, Set<String>> map) {
		try {
			//File Stuff
			File log = null;
			PrintWriter out = null;
			if (fileOutput) {
				//create dir
				new File(outputFolder).mkdir();
				log = new File(Paths.get(outputFolder, "result2").toString());
				out = new PrintWriter(new FileWriter(log, true));
			}
			
			Iterator<Map.Entry<String, Set<String>>> entries = map.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String, Set<String>> entry = entries.next();
				
				if (fileOutput) {
					out.append(entry.getKey() + " " + entry.getValue().toString().replaceAll(",", ""));
					out.append("\n");
				} else
					System.out.println(entry.getKey() + " " + entry.getValue().toString().replaceAll(",", ""));
			}
			if (fileOutput)
				out.close();
			else
				System.out.println();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Runs the program
	 * @param args requred-[filename] optional-[folder to save to]
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("arguments: requred-[filename] optional-[folder to save to]");
			System.out.println("example: java MapReduce example/input");
			System.out.println("example: java MapReduce example/input example");
			return;
		}
		
		
		MapReduce mapReduce = new MapReduce(args[0], (args.length==1) ? null : args[1]);
		mapReduce.compute();
	}
}
