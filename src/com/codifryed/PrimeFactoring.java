package com.codifryed;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrimeFactoring {

	public static void threadedFactoring(int numThreads) {
		
		// TODO initialize Executive threads with a thread pool
		
		// TODO method for running factors
		System.out.println(primeFactors(2_000_000_000_000L));
	}
	
	public static Set<Long> primeFactors(final long MAX_FACTOR) {
//        List<Integer> factors = new ArrayList<Integer>();
        Set<Long> factors = new HashSet<>();
        long processingFactor = MAX_FACTOR;
        for (long i = 2; i<= MAX_FACTOR;i++) {
        	for (;processingFactor%i==0;processingFactor /=i) 
        		factors.add(i);
        }
        return factors;
	}
	
}
