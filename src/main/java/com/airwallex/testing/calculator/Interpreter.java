package com.airwallex.testing.calculator;

import com.airwallex.testing.calculator.expression.Expression;
import com.airwallex.testing.calculator.expression.ExpressionException;
import com.airwallex.testing.calculator.expression.impl.*;

import java.util.Stack;
import java.util.regex.Pattern;

/**
 * calculating process engine
 * Created by on 19-1-27
 */
class Interpreter {

	private Stack<Expression> stack = new Stack<>();

	private String checkError;

	/**
	 * Expression can be evaluated using prefix, infix or postfix notations
	 * This sample uses postfix, where operator comes after the operands.
	 *
	 * @param tokenString for example: 4 3 2 - 1 + *
	 * @return resule if true, tokenString format right, else error, stop process.
	 */
	public InterpreterStatus process(String tokenString) {
		if(tokenString == null || "".equals(tokenString.trim())) return InterpreterStatus.SUCCESS;
		String[] tokenList = tokenString.split(" ");
		int position = 0;
		try {

			for (String s : tokenList) {
				//check if stack include 2 number element
				checkExpression(s);
				position++;
				if (isClear(s)) {
					stack.clear();
				} else if (isUndo(s)) {
					if (stack.size() > 0) {
						stack.pop();
					}
				} else if (isOperator(s)) {
					Expression operator = getOperatorInstance(s);
					double result = operator.interpret();
					NumberExpression resultExpression = new NumberExpression(result);
					stack.push(resultExpression);
				} else {
					Expression i = new NumberExpression(s);
					stack.push(i);
				}
			}
		} catch (ExpressionException ee) {
			String remainElement = "";
			for (int i = position; i < tokenList.length; i++) {
				remainElement += tokenList[i] + " ";
			}

			checkError = String.format("operator %s (position: %d): insucient parameters\n" +
					"stack: %s\n" +
					"(the %s were not pushed on to the stack\n" +
					"due to the previous error)", ee.getMessage(), position, toStringOfStack(), remainElement.trim());
			return InterpreterStatus.EXPRESS_ERROR;
		} catch (Throwable throwable) {
			checkError = String.format("unknow error, (position: %d)\n" +
					"error type: %s", position, throwable.getMessage());
			return InterpreterStatus.UNKNOWN_ERROR;
		}

		return InterpreterStatus.SUCCESS;
	}

	public String getErrorMessage() {
		return checkError;
	}

	private boolean isOperator(String s) {
		return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")
				|| s.equals("sqrt");
	}

	private boolean isUndo(String s) {
		return s.equals("undo");
	}

	private boolean isClear(String s) {
		return s.equals("clear");
	}

	/**
	 * ouput stack string
	 *
	 * @return
	 */
	public String getStackContent() {
		return "stack:" + toStringOfStack();
	}

	private String toStringOfStack() {
		return stack.toString()
				.replace("[", "")
				.replace("]", "")
				.replace("," , "");
	}

	/**
	 * Get expression for string
	 */
	private Expression getOperatorInstance(String s) throws Exception {
		switch (s) {
			case "+":
				return new PlusExpression(stack.pop(), stack.pop());
			case "-":
				return new MinusExpression(stack.pop(), stack.pop());
			case "*":
				return new MultiplyExpression(stack.pop(), stack.pop());
			case "/":
				return new DivideExpression(stack.pop(), stack.pop());
			case "sqrt":
				return new SqrtExpression(stack.pop());
			default:
				throw new Exception(String.format("Operator %s can not be parsed", s));
		}
	}

	/**
	 * check input s right or not
	 */

	private void checkExpression(String s) throws Exception {
		if (s.equals("+") || s.equals("*") || s.equals("/")) {
			if (stack.size() < 2) throw new ExpressionException(s);
		} else if (s.equals("-") || s.equals("sqrt")) {
			if (stack.size() < 1) throw new ExpressionException(s);
		} else if (!s.equals("undo") && !s.equals("clear")) {
			// if real number or not
			if (!Pattern.matches("^[1-9]\\d*|0$", s)) throw new ExpressionException(s);
		}
	}

	/**
	 * Interpreter process result
	 */
	enum InterpreterStatus {
		SUCCESS,
		EXPRESS_ERROR,
		UNKNOWN_ERROR;
	}
}
