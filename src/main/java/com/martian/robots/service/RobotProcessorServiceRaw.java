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

public class RobotProcessorServiceRaw {

	public static void main(String[] args) throws IOException, URISyntaxException {
		
		
		List<String> input = readLinesFromInputFile("input.txt");
		
		//This can also be replaced with simple int[]
		List<Integer> coordinates = new ArrayList<>();
		coordinates = parseLine(input.get(0), false);
		
		//read two lines in loop, for each robot process
		for( int i = 1 ; i < input.size(); i=i+3 ) {
			
			//i - position line
			//i+1 - instructions line
			//i+2 - new empty line
			
			List<Integer> initialPosition = parseLine(input.get(i), true);
			String orientation = input.get(i).charAt(input.get(i).length()-1)+"";
			//System.out.println(orientation);
			
			char[] instructions = input.get(i+1).toCharArray();
			
			
			processRobot(initialPosition, orientation, instructions);
			
			
			
			
			int up = 0;
			int down = 0;
			int left = 0;
			int right = 0;
			
			System.out.println(instructions);
			for(char c : instructions) {
				
				String s = c+"";
				
				
				switch(s) {

				case "L":
					orientation = updateDirection(orientation, s);
					break;
				case "R":
					orientation = updateDirection(orientation, s);
					break;
				case "F":
					
					switch(orientation) {
					case "N": 
						up++;
						break;
					case "E": 
						right++;
						break;
					case "W": 
						left++;
						break;
					case "S": 
						down++;
						break;
					}
					
					
					if((initialPosition.get(0)-left+right)>coordinates.get(0)) {
						System.out.println( (initialPosition.get(0)-left+right-1) + " " + (initialPosition.get(1)+up-down) + " " + orientation + " LOST" );
						break;
					}
					if((initialPosition.get(1)+up-down)>coordinates.get(1)) {
						System.out.println( (initialPosition.get(0)-left+right) + " " + (initialPosition.get(1)+up-down-1) + " " + orientation + " LOST" );
						break;
					}
					if((initialPosition.get(0)-left+right)<0) {
						System.out.println( (initialPosition.get(0)-left+right+1) + " " + (initialPosition.get(1)+up-down) + " " + orientation + " LOST" );
						break;
					}
					if((initialPosition.get(1)+up-down)<0) {
						System.out.println( (initialPosition.get(0)-left+right) + " " + (initialPosition.get(1)+up-down+1) + " " + orientation + " LOST" );
						break;
					}
					
					System.out.println( (initialPosition.get(0)-left+right) + " " + (initialPosition.get(1)+up-down) + " " + orientation );
					
					
					
					break;

				}
				
				
			}
			
			System.out.println("result:");
			System.out.println( (initialPosition.get(0)-left+right) + " " + (initialPosition.get(1)+up-down) + " " + orientation );
			
			System.out.println();
		}
		
		
	    
			    
	}
	
	private static void processRobot(List<Integer> initialPosition, String orientation, char[] instructions) {
		// TODO Auto-generated method stub
		
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
	private static String updateDirection(String currentDirection, String left_or_right) {
		String[] news = { "N", "E", "S", "W", "N", "E" };
		
		List<String> newsList = Arrays.asList(news);
		
		int index = 0;
		
		switch(currentDirection) {
		case "N": 
			index = newsList.lastIndexOf(currentDirection);
			break;
		case "E": 
			index = newsList.indexOf(currentDirection);
			break;
		case "W": 
			index = newsList.indexOf(currentDirection);
			break;
		case "S": 
			index = newsList.indexOf(currentDirection);
			break;
		}
		
		
		switch(left_or_right) {
		case "L": 
			return newsList.get(index-1);
			
		case "R": 
			return newsList.get(index+1);
			
		default:
			return "";
		}
		
	}
	
	
	//System.out.println("up: "+up);
	//System.out.println("down: "+down);
	//System.out.println("left: "+left);
	//System.out.println("right: "+right);
	
	//System.out.println("position:");
	//System.out.println(position.get(0) + " "+ position.get(1));
	
	//System.out.println("up-down:");
	/*
	 * System.out.println("up: "+up); System.out.println("down: "+down);
	 * System.out.println("left: "+left); System.out.println("right: "+right);
	 */
	//System.out.println((up-down) + " "+ (left-right));
	

}
