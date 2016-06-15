package dcdholder.projectEuler.p142;

class PythagoreanTriple {
	int largestSide;
	int middleSide;
	int smallestSide;

	int getSmallestSide() {return smallestSide;}
	int getMiddleSide()   {return middleSide;}
	int getLargestSide()  {return largestSide;}

	static public Set<PythagoreanTriple> generatePrimitiveTriplesUpToN(int maxValue) {
		Set<PythagoreanTriple> primitiveTriples = new HashSet<PythagoreanTriple>();

		for(int m=2; ; m++) {
			for(int n=1; n<m; n++) {
				if(m*m+n*n>maxValue) {
					if(n=1) {
						break 2; //kill the infinite loop this is nested in if you're already blowing the limit by n=1
					} else {
						break 1;
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
				int newSmallestSide = primitiveTriple.getSmallestSide() * k;
				int newMiddleSide   = primitiveTriple.getMiddleSide()   * k;
				int newLargestSide  = primitiveTriple.getLargestSide()  * k;

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
	static private Set<Integer[2]> pythagoreanCouplesUpToN(int maxValue) {
		Set<Integer[2]> pythagoreanCouples = new HashSet<Integer[2]>();

		for(PythagoreanTriple triple : generateAllTriplesUpToN(maxValue)) {
			pythagoreanCouples.add(new int[2] {triple.getLargestSide(),triple.getMiddleSide()});
			pythagoreanCouples.add(new int[2] {triple.getLargestSide(),triple.getSmallestSide()});
		}

		return pythagoreanCouples;
	}

	//multiple triples can share the same largest value
	static public Map<Integer,HashSet<Integer[2]>> pythagoreanCouplesUpToNMapped(int maxValue) {
		Map<Integer,HashSet<Integer[2]>> coupleMap = new HashMap<Integer,Integer[2]>();

		for(int[2] couple : pythagoreanCouplesUpToN(maxValue)) {
			if(coupleMap.keySet().contains(couple[0])) {
				HashSet<int[2]> matchingCouples = coupleMap.get(couple[0]);
				matchingCouples.add(couple);
			} else {
				HashSet<int[2]> matchingCouples = new HashSet<int[2]>();
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
			if(largestSide.o!=this.largestSide || middleSide.o!=this.middleSide || smallestSide.o!=this.smallestSide) {
				return false;
			} else {
				return true;
			}
		}
	}

	public int hashCode() {
		return largestSide*11 + middleSide*13 + smallestSide*17;
	}

	PythagoreanTriple(int[] sides) {
		if(sides.length!=3) {
			throw new IllegalArgumentException("Incorrect number of entries in triple: " + sides.length);
		}

		largestSide  = sides[0];
		middleSide   = sides[0];
		smallestSide = sides[0];
		for(int i=0;i<3;i++) { //three entry sort lol
			if(sides[i]<1) {
				throw new IllegalArgumentException("One of the sides is shorter than 1 - its length is: " + sides[i]);
			}

			if(sides[i]>largestSide) {
				largestSide = sides[i];
			}
			if(sides[i]<smallestSide) {
				largestSide = sides[i];
			}
			if(sides[i]<largestSide && sides[i]>smallestSide) {
				middleSide  = sides[i];
			}
		}

		if(smallestSide*smallestSide+middleSide*middleSide!=largestSide*largestSide) {
			throw new IllegalArgumentException("Not a valid triple");
		}
	}
}
