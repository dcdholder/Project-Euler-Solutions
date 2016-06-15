package dcdholder.projectEuler.p147;

class LimitedCombinationPairIterator {
	private int maxValueA;
	private int maxValueB;

	private int currentA;
	private int currentB;

	private int[] returnPair = new int[2];

	private boolean completedAnIteration = false;

	private boolean reachedMax() {
		if(maxValueA==currentA && maxValueB==currentB) {
			return true;
		} else {
			return false;
		}
	}

	public boolean completedAnIteration() {return completedAnIteration;}

	public void resetIterator() {
		currentA = 1;
		currentB = 1;
		completedAnIteration = false;
	}

	private void iteratePair() {
		if(reachedMax()) {
			resetIterator();
			completedAnIteration = true;
		} else {
			returnPair[0] = currentA;
			returnPair[1] = currentB;

			if(currentB==maxValueB) {
				currentA++;
				currentB=currentA;
			} else {
				currentB++;
			}
		}
	}

	public int[] getNextPair() {
		int[] pairToReturn = new int[2];
		
		pairToReturn[0] = returnPair[0];
		pairToReturn[1] = returnPair[1];
		iteratePair();

		return pairToReturn;
	}

	LimitedCombinationPairIterator(int maxValueA, int maxValueB) {
		if(maxValueA>maxValueB) {
			this.maxValueA = maxValueA;
			this.maxValueB = maxValueB;
		} else {
			this.maxValueA = maxValueB;
			this.maxValueB = maxValueA;
		}
	}
}
