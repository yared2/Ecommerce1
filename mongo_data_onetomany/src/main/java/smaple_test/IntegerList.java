package smaple_test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntegerList {

	
	
	public static void main(String[] args) {
	//	String numbers= "1,2,3,4";
		
//	List<Integer>num= Stream.of(numbers.split(","))
//				.map(String::trim)
//				.map(Integer::parseInt)
//				.collect(Collectors.toList());;
//				
//				System.out.println(num);
				
				
//				List<String>numes = Arrays.asList("xxx","ttt","yyy");
//				String fin= numes.stream()
//						.filter(s->s.equalsIgnoreCase("ttt"))
//						.findAny()
//						.orElse("no name");
//				
//				System.out.println(fin);
		
		List<Integer>numbers= Arrays.asList(1,2,3,4,4,5,5,5);
		
		List<Integer> collect = numbers.stream()
		.filter(x->Collections.frequency(numbers, x)>1)
		.distinct()
		.collect(Collectors.toList());
		
		System.out.println(collect);
	}
}
