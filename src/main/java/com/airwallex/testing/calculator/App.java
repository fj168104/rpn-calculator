package com.airwallex.testing.calculator;

import java.util.Scanner;

/**
 * App class receive input from users
 * call Interpretor process
 */
public class App extends Thread {

	private Interpreter interpreter = new Interpreter();

	public void run(){
		Scanner input = new Scanner(System.in);
		while (true){
			Interpreter.InterpreterStatus status = interpreter.process(input.nextLine());
			if(status == Interpreter.InterpreterStatus.SUCCESS){
				System.out.println(interpreter.getStackContent());
			}else if(status == Interpreter.InterpreterStatus.EXPRESS_ERROR){
				System.out.println(interpreter.getErrorMessage());
			}else {
				System.err.println(interpreter.getErrorMessage());
				break;
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		App app = new App();
		app.start();
		app.join();
	}

}
