package trouble;

import java.util.HashMap;
import java.util.Map;

public class Color {

	public static Map<Integer, String> colorMap = new HashMap<>();
	public static Map<Integer, String> colorNameMap = new HashMap<>();

	static enum Colors {RED, GREEN, YELLOW, BLUE}
	
	static {
		colorMap.put(0, "\u001b[31m"); //RED
		colorMap.put(1, "\u001b[32m"); //GREEN	
		colorMap.put(2, "\u001b[33m"); //YELLOW
		colorMap.put(3, "\u001b[34m"); //BLUE
	}
	
	public static String getColorEnd() {
		return "\u001B[0m";
	}
}
