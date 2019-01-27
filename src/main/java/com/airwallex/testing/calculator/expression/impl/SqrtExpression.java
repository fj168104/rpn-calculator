package com.airwallex.testing.calculator.expression.impl;


import com.airwallex.testing.calculator.expression.Expression;

// MinusExpression
public class SqrtExpression extends Expression {

	private Expression expression;

	public SqrtExpression(Expression expression) {
		this.expression = expression;
	}
	
	@Override
	public double interpret() {
		return Math.sqrt(expression.interpret());
	}

	@Override
	public String toString() {
		return "/";
	}

}
