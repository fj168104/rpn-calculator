package com.airwallex.testing.calculator.expression.impl;

import com.airwallex.testing.calculator.expression.Expression;

// PlusExpression
public class PlusExpression extends Expression {

	private Expression leftExpression;
	private Expression rightExpression;

	public PlusExpression(
			Expression leftExpression, 
			Expression rightExpression) {
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
	}
	
	@Override
	public double interpret() {
		return rightExpression.interpret() + leftExpression.interpret() ;
	}

	@Override
	public String toString() {
		return "+";
	}

}
