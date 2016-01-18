//TODO: add the ability to create only one of the two maps to save on memory (new constructor)
//TODO: consider using an array for the primeMap

package dcdholder.projectEuler.p50;

import java.util.*;
import java.lang.Math.*;

public class Sieve {
	private static final int MAX_ALLOWABLE_SIZE = 100000000; //determines memory consumption of sieve
	
	private int maxPrime = 0;
	private int maxIndex = 0;
	private Map<Integer,Integer> primeMap      = new HashMap<Integer,Integer>();
	private Map<Integer,Integer> primesByValue = new HashMap<Integer,Integer>();
	
	private boolean[] generateSieve() {
		boolean[] primeSieve = new boolean[maxPrime+1]; //allows us to one-index everything
		int rootOfMax = (int)Math.floor(Math.sqrt((double) maxPrime));
		
		for(int j=2;j<=rootOfMax;j++) {
			for(int i=2;i*j<=maxPrime;i++) {
				primeSieve[i*j] = true;
			}
		}
		return primeSieve;
	}
	
	private void sieveToPrimeMap(boolean[] primeSieve) {
		int currentIndex = 1;
		
		for(int i=2;i<=maxPrime;i++) {
			if(!primeSieve[i]) {
				primeMap.put(currentIndex,i);
				primesByValue.put(i,currentIndex);
				currentIndex++;
			}
		}
		maxIndex = currentIndex-1;
	}
	
	public int getPrimeByIndex(int primeIndex) {
		if(primeIndex>maxIndex) {throw new IllegalArgumentException("Desired prime too large for list");}
		
		return primeMap.get(primeIndex);
	}

	public int getIndexByPrime(int someNum) {
		if(!isPrime(someNum)) {throw new IllegalArgumentException("That number is not prime.");}
		return primesByValue.get(someNum);
	}
	
	public boolean isPrime(int someNum) {
		if(someNum>maxPrime) {throw new IllegalArgumentException("Desired prime too large for list");}
		return primesByValue.containsKey(someNum);
	}
	
	Sieve(int maxPrime) {
		if(maxPrime>MAX_ALLOWABLE_SIZE) {
			throw new IllegalArgumentException("The specified sieve size is too large.");
		} else {
			this.maxPrime = maxPrime;
			sieveToPrimeMap(generateSieve()); //generate the prime map
		}
	}
}
