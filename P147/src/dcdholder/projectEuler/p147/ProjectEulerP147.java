package dcdholder.projectEuler.p147;

class ProjectEulerP147 {
	private static int maxDimA = 47;
	private static int maxDimB = 43;

	public static void main(String[] args) {
		System.out.println("The sum of all possible rectangles is " + getIntegerRectangleTotal(maxDimA,maxDimB));
	}

	private static int getSubtotal(int n,int m) {

		//TODO: copy down the derivation for vertHorz
		int vertHorzSubtotal=0;
		for(int j=1;j<=m;j++) {
			vertHorzSubtotal+=(m-(j-1))*(n/2);
		}

		int diagonalSubtotal=0;
		for(int i=1;i<=n;i++) {
			for(int j=1;j<=m;j++) {
				//TODO: fill out the diagonal rectangle counting bit
			}
		}
	
		return vertHorzSubtotal+diagonalSubtotal;
	}

	private static int getIntegerRectangleTotal(int n, int m) {
		LimitedCombinationPairIterator pairIterator = new LimitedCombinationPairIterator(n,m);
		int[] pair = new int[2];

		int diagonalTotal    = 0;
		int nonDiagonalTotal = 0;
		int finalTotal       = 0;

		while(!pairIterator.completedAnIteration()) {
			pair = pairIterator.getNextPair();

			if(pair[0]==pair[1]) {
				diagonalTotal+=getSubtotal(pair[0],pair[1]);
			} else {
				nonDiagonalTotal+=getSubtotal(pair[0],pair[1]);
			}
		}

		finalTotal = diagonalTotal*2+nonDiagonalTotal;

		return finalTotal;
	}
}
