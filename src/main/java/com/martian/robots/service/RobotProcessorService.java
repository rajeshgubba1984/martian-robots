package com.martian.robots.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RobotProcessorService {

	public static void main(String[] args) throws IOException, URISyntaxException {
		
		process("input.txt");
			    
	}
	
	public static List<String> process(String filename)  throws IOException, URISyntaxException {
		List<String> input = readLinesFromInputFile(filename);
		List<String> output = new ArrayList<String>();
		
		//This can also be replaced with simple int[]
		List<Integer> coordinates = parseLine(input.get(0), false);
		List<String> lostPositions = new ArrayList<String>();
		
		//read two lines in loop, for each robot process
		for( int i = 1 ; i < input.size(); i=i+3 ) {
			
			//i - position line
			//i+1 - instructions line
			//i+2 - new empty line
			
			List<Integer> initialPosition = parseLine(input.get(i), true);
			String orientation = input.get(i).charAt(input.get(i).length()-1)+"";
			char[] instructions = input.get(i+1).toCharArray();
			
			String res = processRobot(coordinates, initialPosition, orientation, instructions, lostPositions);
			System.out.println(res);
			output.add(res);
			
		}
		return output;
	}
	
	
	/**
	 * Process single robot
	 * @param coordinates
	 * @param initialPosition
	 * @param orientation
	 * @param instructions
	 * @param lostPositions
	 * @return
	 */
	private static String processRobot(List<Integer> coordinates, List<Integer> initialPosition, String orientation, char[] instructions, List<String> lostPositions) {
		
		Movements moves = new Movements(initialPosition.get(0), initialPosition.get(1), orientation);
		for(char c : instructions) {
			
			String s = c+"";
			
			
			switch(s) {

			case "L":
			case "R":
				updateDirection(moves, s);
				break;
				
			case "F":
				boolean isLost = false;
				isLost = checkIfThePositionLostBefore(lostPositions, moves.getPositionString());
				
				if(isLost) {
					//System.out.println("skip forward");
					continue;
				}
				
				if(checkIfOutOfCoordinates(moves, coordinates)) {
					lostPositions.add(moves.getPositionString());
					return moves.getPositionString() + " LOST";
				}
				
				break;

			}
			
			
		}
		
		return  moves.getPositionString();
	}

	/**
	 * Check if the one forward movement makes robot out of coordinates ? 
	 * @param moves
	 * @param coordinates
	 * @return true if yes
	 */
	private static boolean checkIfOutOfCoordinates(Movements moves, List<Integer> coordinates) {
		switch(moves.direction) {
		case "N": 
			if(moves.getYposition()+1 > coordinates.get(1)) {
				return true;
			}
			moves.up++;
			break;
		case "E": 
			if(moves.getXposition()+1 > coordinates.get(0)) {
				return true;
			}
			moves.right++;
			break;
		case "W": 
			if(moves.getXposition()-1 < 0) {
				return true;
			}
			moves.left++;
			break;
		case "S": 
			if(moves.getYposition()-1 < 0) {
				return true;
			}
			moves.down++;
			break;
		}
		
		return false;
	}

	private static boolean checkIfThePositionLostBefore(List<String> lostPositions, String string) {
		//System.out.println(lostPositions);
		//System.out.println("----->  "+string);
		return lostPositions.contains(string);
	}

	private static List<String> readLinesFromInputFile(String filename) throws URISyntaxException, IOException {

		Path path = Paths.get( RobotProcessorService.class.getClassLoader().getResource(filename).toURI());
		
		return Files.lines(path).collect(Collectors.toList());
		
	}

	/**
	 * Parse string format of position
	 * @param line
	 * @param hasOrientation
	 * @return
	 */
	private static List<Integer> parseLine( String line, boolean hasOrientation ) {
		
		if(hasOrientation) {
			line = line.substring(0, line.length()-1);
		}
		
		return Stream.of(line.trim().split(" ")).map(e -> Integer.valueOf(e)).collect(Collectors.toList());
	}
	
	/**
	 * This gives the new direction when you give turn command
	 * @param currentDirection current orientation of the robot
	 * @param left_or_right turn to left or right ?
	 * @return
	 */
	private static void updateDirection(Movements moves, String left_or_right) {
		String[] news = { "N", "E", "S", "W", "N", "E" };
		
		List<String> newsList = Arrays.asList(news);
		
		int index = 0;
		
		switch(moves.direction) {
		case "N": 
			index = newsList.lastIndexOf(moves.direction);
			break;
		case "E": 
			index = newsList.indexOf(moves.direction);
			break;
		case "W": 
			index = newsList.indexOf(moves.direction);
			break;
		case "S": 
			index = newsList.indexOf(moves.direction);
			break;
		}
		
		
		switch(left_or_right) {
		case "L": 
			moves.direction = newsList.get(index-1);
			break;
		case "R": 
			moves.direction = newsList.get(index+1);
			break;
		default:
			break;
		}
		
	}

}
