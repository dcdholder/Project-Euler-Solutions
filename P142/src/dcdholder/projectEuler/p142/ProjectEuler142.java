package dcdholder.projectEuler.p142;

import java.util.*;

public class ProjetEuler142 {
	static int maxTriple = 1000;

	public static void main(String[] args) {
		Set<Integer[]> abcValuesSet         = getAbcValuesSet();
		Set<Integer[]> filteredAbcValuesSet = filterAbcValuesSet(abcValuesSet);
		int minSum = getMinXyzSumFromAbcValues(filteredAbcValuesSet);
		
		System.out.println("Examined " + filteredAbcValuesSet.size() + " ABC combinations");
		System.out.println("The minimum sum found in the search was " + minSum);
	}

	private static Set<Integer[]> getAbcValuesSet() {
		Map<Integer,HashSet<Integer[]>> coupleMap = PythagoreanTriple.pythagoreanCouplesUpToNMapped(maxTriple);
		HashSet<Integer[]> abcValuesSet = new HashSet<Integer[]>();

		/*
		if(coupleMap.containsKey(34)) {
			System.out.println("Shit");
			for(Integer[] blach : coupleMap.get(34)) {
				for(int i=0;i<2;i++) {
					System.out.println(blach[i]);
				}
			}
		}
		*/
		
		/*
		for(Integer coupleKey : coupleMap.keySet()) {
			for(Integer[] couple : coupleMap.get(coupleKey)) {
				Integer[] abcValues = new Integer[3];

				abcValues[0] = couple[0];
				abcValues[1] = couple[1];
				if(coupleMap.containsKey(couple[1])) {
					for(Integer[] coupleB : coupleMap.get(couple[1])) {
						abcValues[2] = coupleB[1];
						abcValuesSet.add(abcValues.clone()); //create a copy!!!!!!!!!!!
					}
				}
			}
		}
		*/
		for(Integer a : coupleMap.keySet()) {
			HashSet<Integer> aToB = new HashSet<Integer>();
			for(Integer[] aCouple : coupleMap.get(a)) {
				aToB.add(aCouple[1]);
			}
			for(Integer b : aToB) {
				HashSet<Integer> bToC = new HashSet<Integer>();
				if(coupleMap.containsKey(b)) {
					for(Integer[] bCouple : coupleMap.get(b)) {
						bToC.add(bCouple[1]);
					}
				}
				for(Integer c : bToC) {
					if(a==34 && b==30) {System.out.println(c);}
					if(aToB.contains(c)) {
						Integer[] abcValues = new Integer[3];

						abcValues[0] = a;
						abcValues[1] = b;
						abcValues[2] = c;
						
						abcValuesSet.add(abcValues);
					}
				}
			}
		}
		
		return abcValuesSet;
	}

	//check the set of generated values arrays for the various tests
	private static Set<Integer[]> filterAbcValuesSet(Set<Integer[]> abcValuesSet) {
		Set<Integer[]> filteredAbcValuesSet = new HashSet<Integer[]>();
		
		for(Integer[] abcValues : abcValuesSet) {
			boolean addAbc = true;
			if((abcValues[1]*abcValues[1]+abcValues[2]*abcValues[2])<(abcValues[0]*abcValues[0])) {
				addAbc = false; //fails the b^2+c^2>a^2 test
			}
			if(abcValues[1]>=abcValues[0] || abcValues[2]>=abcValues[0]) {
				addAbc = false; //fails the a>b, a>c test
			}
			if(abcValues[2]>=abcValues[1]) {
				addAbc = false; //fails the b>c test
			}
			if((abcValues[0]*abcValues[0]+abcValues[1]*abcValues[1]+abcValues[2]*abcValues[2])%2!=0) {
				addAbc = false; //sum of squares is not divisible by 2
			}
			if(addAbc) {
				filteredAbcValuesSet.add(abcValues);
			}
		}
		
		return filteredAbcValuesSet;
	}

	private static int getMinXyzSumFromAbcValues(Set<Integer[]> filteredAbcValuesSet) {
		int smallestSum=0;

		boolean firstIteration = true;
		for(Integer[] abcValues : filteredAbcValuesSet) {
			//x+y+z = a^2+b^2+c^2 where x+y=a^2, x+z=b^2, y+z=c^2, x>y>z>0
			int currentSum = (abcValues[0]*abcValues[0]+abcValues[1]*abcValues[1]+abcValues[2]*abcValues[2])/2;
			
			if(currentSum<smallestSum || firstIteration) {
				smallestSum = currentSum;
				System.out.println(abcValues[0] + "," + abcValues[1] + "," + abcValues[2]);
			}
			firstIteration = false;
		}

		return smallestSum;
	}
}
