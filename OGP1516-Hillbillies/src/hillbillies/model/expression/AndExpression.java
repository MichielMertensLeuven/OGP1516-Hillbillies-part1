package hillbillies.model.expression;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class AndExpression extends BinaryExpression<Boolean, Boolean, Boolean>{

	public AndExpression(Expression<Boolean> leftExpression, Expression<Boolean> rightExpression, SourceLocation loc) {
		super(leftExpression, rightExpression, loc);
	}

	@Override
	public Boolean getResult(Unit unit) {
		boolean leftResult = (boolean) super.getLeftExpression().getResult(unit);
		boolean rightResult = (boolean) super.getRightExpression().getResult(unit);
		return (leftResult && rightResult);
	}

	@Override
	public String toString() {
		return "( " + super.getLeftExpression().toString() + " and " +
				super.getRightExpression().toString() + " )";
	}

}