package dcdholder.projectEuler.p142;

class ProjectEulerP142 {
	static int maxTriple = 1000;

	public static void main(String[] args) {
		Set<Integer[3]> abcValuesSet = getAbcValuesSet();
		filterAbcValuesSet(abcValuesSet);
		int minSum = getMinXyzSumFromAbcValues(abcValuesSet);
		
		System.out.println("Examined " + getAbcValues.size() + " ABC combinations");
		System.out.println("The minimum sum found in the search was" + minSum);
	}

	private static Set<Integer[3]> getAbcValuesSet() {
		Map<Integer,HashSet<Integer[2]>> coupleMap = PythagoreanTriples.pythagoreanCouplesUpToNMapped(maxTriple);
		HashSet<Integer[3]> abcValuesSet = new HashSet<Integer[3]>();

		for(Integer coupleKey : coupleMap.keySet()) {
			for(int[2] couple : coupleMap.get(coupleKey)) {
				int[] abcValues = new int[3];

				abcValues[0] = couple[0];
				abcValues[1] = couple[1];

				for(int[2] couple : coupleMap.get(coupleKey)) {
					abcValues[2] = couple[1];
					abcValuesSet.add(abcValues);
				}
			}
		}
		
		return abcValuesSet;
	}

	//check the set of generated values arrays for the various tests
	private static void filterAbcValues(Set<Integer[3]> abcValuesSet) {
		for(int[3] abcValues : abcValuesSet) {
			if(!abcValues[1]*abcValues[1]+abcValues[2]*abcValues[2]>abcValues[0]*abcValues[0]) {
				abcValues.remove(abcValues); //fails the b^2+c^2>a^2 test
			}
			if(abcValues[1]>abcValues[0] || !abcValues[2]>abcValues[0]) {
				abcValues.remove(abcValues); //fails the a>b, a>c test
			}
			if(abcValues[2]>abcValues[1]) {
				abcValues.remove(abcValues); //fails the b>c test
			}
			if(abcValues[0]*abcValues[0]+abcValues[1]*abcValues[1]+abcValues[2]*abcValues[2]%2!=0) {
				abcValues.remove(abcValues); //sum of squares is not divisible by 2
			}
		}
	}

	private static int getMinXyzSumFromAbcValues(Set<Integer[3]> filteredAbcValuesSet) {
		int smallestSum;

		boolean firstIteration = true;
		for(int[3] abcValues : filteredAbcValuesSet) {
			//x+y+z = a^2+b^2+c^2 where x+y=a^2, x+z=b^2, y+z=c^2, x>y>z>0
			int currentSum = (abcValues[0]*abcValues[0]+abcValues[1]*abcValues[1]+abcValues[2]*abcValues[2])/2;
			
			if(currentSum<smallestSum || firstIteration) {
				smallestSum = currentSum;
			}
			firstIteration = false;
		}

		return smallestSum;
	}
}
