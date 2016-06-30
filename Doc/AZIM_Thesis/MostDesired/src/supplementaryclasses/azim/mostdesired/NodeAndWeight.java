package supplementaryclasses.azim.mostdesired;

public class NodeAndWeight {

	
	private Double adjacentVertex;
	private Double weight;
	
	public NodeAndWeight(double adVertex, double w){
		this.adjacentVertex = adVertex;
		this.weight = w;
	}

	public double getAdjacentVertex() {
		return adjacentVertex;
	}

	public void setAdjacentVertex(int adjacentVertex) {
		this.adjacentVertex = (double) adjacentVertex;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	/**
	 * We need both 'hashCode()' and 'equals()' methods to be overridden because
	 * otherwise we will not be able to use the method 'contains()' or any other
	 * useful method which uses equality check, for our lists.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((adjacentVertex == null) ? 0 : adjacentVertex.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeAndWeight other = (NodeAndWeight) obj;
		if (adjacentVertex == null) {
			if (other.adjacentVertex != null)
				return false;
		} else if (!adjacentVertex.equals(other.adjacentVertex))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}
	
}
