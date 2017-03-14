package PhysicalOperators;

import java.util.Map;

import Support.Mule;
import TableElement.Tuple;

public class CartesianOperator extends Operator {

	@Override
	public Tuple getNextTuple() {
		return null;
	}

	@Override
	public void reset() {
		
	}

	@Override
	public Map<String, Mule> getSchema() {
		return null;
	}

}
