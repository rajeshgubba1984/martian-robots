package com.martian.robots.robots;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.martian.robots.service.RobotProcessorService;

@SpringBootTest
class RobotsApplicationTests {

	RobotProcessorService sevice = new RobotProcessorService();
	
	@Test
	void testInput1() throws IOException, URISyntaxException {
		List<String> output =  RobotProcessorService.process("test1.txt");
		Assertions.assertTrue(output.contains("0 2 W"));
	}
	
	@Test
	void testInput2() throws IOException, URISyntaxException {
		List<String> output =  RobotProcessorService.process("input.txt");
		
		List<String> expected = new ArrayList<String>();
		expected.add("1 1 E");
		expected.add("3 3 N LOST");
		expected.add("2 3 S");
		
		Assertions.assertTrue(output.containsAll(expected));
	}
	
	@Test
	void testInput3() throws IOException, URISyntaxException {
		List<String> output =  RobotProcessorService.process("test2.txt");
		Assertions.assertTrue(output.contains("0 0 S LOST"));
	}

}
