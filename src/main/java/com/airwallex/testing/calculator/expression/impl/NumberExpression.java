package com.airwallex.testing.calculator.expression.impl;

import com.airwallex.testing.calculator.expression.Expression;
import org.omg.CORBA.INTERNAL;

// NumberExpression
public class NumberExpression extends Expression {

	private double number;
	
	public NumberExpression(double number) {
		this.number = number;
	}
	
	public NumberExpression(String s) {
		this.number = Integer.parseInt(s);
	}
	
	@Override
	public double interpret() {
		return number;
	}

	@Override
	public String toString() {
		int n1 = (int)number;
		if(n1 == number) return String.valueOf(n1);
		return String.valueOf(number);
	}

}
