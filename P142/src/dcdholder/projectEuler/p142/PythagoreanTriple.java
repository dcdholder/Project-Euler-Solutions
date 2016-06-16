package dcdholder.projectEuler.p142;

import java.util.*;

public class PythagoreanTriple {
	int largestSide;
	int middleSide;
	int smallestSide;

	int getSmallestSide() {return smallestSide;}
	int getMiddleSide()   {return middleSide;}
	int getLargestSide()  {return largestSide;}

	static public Set<PythagoreanTriple> generatePrimitiveTriplesUpToN(int maxValue) {
		Set<PythagoreanTriple> primitiveTriples = new HashSet<PythagoreanTriple>();

		infiniteLoop:
		for(int m=2; ; m++) {
			for(int n=1; n<m; n++) {
				if(m*m+n*n>maxValue) {
					if(n==1) {
						break infiniteLoop; //kill the infinite loop this is nested in
					} else {
						break;
					}
				} else {
					primitiveTriples.add(new PythagoreanTriple(new int[] {m*m-n*n,2*m*n,m*m+n*n})); //Euclid's formula
				}
			}
		}

		return primitiveTriples;
	}

	static public Set<PythagoreanTriple> generateAllTriplesUpToN(int maxValue) {
		Set<PythagoreanTriple> triples = new HashSet<PythagoreanTriple>();

		for(PythagoreanTriple primitiveTriple : generatePrimitiveTriplesUpToN(maxValue)) {			
			for(int i=2; ; i++) {
				int newSmallestSide = primitiveTriple.getSmallestSide() * i;
				int newMiddleSide   = primitiveTriple.getMiddleSide()   * i;
				int newLargestSide  = primitiveTriple.getLargestSide()  * i;

				if(newLargestSide>maxValue) {
					break;
				} else {
					triples.add(new PythagoreanTriple(new int[] {newSmallestSide,newMiddleSide,newLargestSide}));
				}
			}
		}

		return triples;
	}

	//only two elements in the triple are needed to identify it (the other can be derived) - two such pairs exist for each couple
	//largest value is at index 0, smaller value at index 1
	static private Set<Integer[]> pythagoreanCouplesUpToN(int maxValue) {
		Set<Integer[]> pythagoreanCouples = new HashSet<Integer[]>();

		for(PythagoreanTriple triple : generateAllTriplesUpToN(maxValue)) {
			pythagoreanCouples.add(new Integer[] {triple.getLargestSide(),triple.getMiddleSide()});
			pythagoreanCouples.add(new Integer[] {triple.getLargestSide(),triple.getSmallestSide()});
		}

		return pythagoreanCouples;
	}

	//multiple triples can share the same largest value
	static public Map<Integer,HashSet<Integer[]>> pythagoreanCouplesUpToNMapped(int maxValue) {
		Map<Integer,HashSet<Integer[]>> coupleMap = new HashMap<Integer,HashSet<Integer[]>>();

		for(Integer[] couple : pythagoreanCouplesUpToN(maxValue)) {
			if(coupleMap.keySet().contains(couple[0])) {
				HashSet<Integer[]> matchingCouples = coupleMap.get(couple[0]);
				matchingCouples.add(couple);
			} else {
				HashSet<Integer[]> matchingCouples = new HashSet<Integer[]>();
				matchingCouples.add(couple);
				coupleMap.put(couple[0],matchingCouples);
			}
		}

		return coupleMap;
	}

	public boolean equals(Object o) {
		if(!(o instanceof PythagoreanTriple)) {
			return false;
		} else {
			PythagoreanTriple tripleO = (PythagoreanTriple)o;
			if(tripleO.largestSide!=this.largestSide || tripleO.middleSide!=this.middleSide || tripleO.smallestSide!=this.smallestSide) {
				return false;
			} else {
				return true;
			}
		}
	}

	public int hashCode() {
		return largestSide*11 + middleSide*13 + smallestSide*17;
	}
	
	public String toString() {
		return "{" + largestSide + "," + middleSide + "," + smallestSide + "}";
	}

	PythagoreanTriple(int[] sides) {
		if(sides.length!=3) {
			throw new IllegalArgumentException("Incorrect number of entries in triple: " + sides.length);
		}

		largestSide  = 0;
		middleSide   = 0;
		smallestSide = 0;
		for(int i=0;i<3;i++) { //three entry sort lol
			if(sides[i]<1) {
				throw new IllegalArgumentException("One of the sides is shorter than 1 - its length is: " + sides[i]);
			}

			if(sides[i]>largestSide || largestSide==0) {
				largestSide = sides[i];
			}
			if(sides[i]<smallestSide || smallestSide==0) {
				smallestSide = sides[i];
			}
		}
		//very ugly hack
		boolean middleFound=false;
		for(int i=0;i<3;i++) {
			if(sides[i]!=largestSide && sides[i]!=smallestSide) {
				middleSide  = sides[i];
				middleFound = true;
			}
		}
		if(!middleFound) {
			middleSide = smallestSide;
		}

		System.out.println(this.toString());
		if(smallestSide*smallestSide+middleSide*middleSide!=largestSide*largestSide) {
			throw new IllegalArgumentException("Not a valid triple: {" + largestSide + "," + middleSide + "," + smallestSide + "}");
		}
	}
}

