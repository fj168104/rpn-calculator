package com.airwallex.testing.calculator.expression.impl;


import com.airwallex.testing.calculator.expression.Expression;

// MinusExpression
public class DivideExpression extends Expression {

	private Expression leftExpression;
	private Expression rightExpression;

	public DivideExpression(
			Expression leftExpression,
			Expression rightExpression) {
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
	}
	
	@Override
	public double interpret() {
		return rightExpression.interpret() / leftExpression.interpret() ;
	}

	@Override
	public String toString() {
		return "/";
	}

}
