package lab5.problems;

import java.util.LinkedList;
import java.util.List;

import lab5.util.dataStructures.LinkedStack;
import lab5.util.interfaces.Stack;

public class FullyParensProblem {

	/********************************************************
	 * PLACE YOUR RESULTS AND INVALID EXPRESSIONS *
	 * AS YOU RECEIVE THEM IN YOUR PROGRAM IN THESE *
	 * 2 LINKED LISTS THAT ARE DECALRED FOR YOU. *
	 * *
	 * TESTS WILL FAIL IF YOU DO NOT STORE THE RESULTS *
	 * AND INVALID EXPRESSIONS AS YOU RECEIVE THEM OR *
	 * IF YOU DON'T USE THEM AND JUST PRINT OUT THE RESULTS *
	 * TO THE CONSOLE *
	 ********************************************************/

	private List<String> invalidExpressions;
	private List<String> results;
	private List<String> expressions;

	/***********************************************************
	 * DO NOT REMOVE THESE, YOU MUST USE THEM IN YOUR SOLUTION *
	 ***********************************************************/
	private Stack<Character> operators;
	private Stack<Integer> operands;
	private Stack<Character> parenthesis;

	/***************************************************
	 * ADD ANY PRIVATE FIELDS YOU MAY NEED TO USE HERE *
	 ***************************************************/

	public FullyParensProblem(List<String> expressions) {
		invalidExpressions = new LinkedList<>();
		results = new LinkedList<>();
		this.expressions = expressions;

		operands = new LinkedStack<>();
		operators = new LinkedStack<>();
		parenthesis = new LinkedStack<>();

		/*************************************************************
		 * ADD ANY ADDITIONAL FIELD INTIALIZATIONS YOU MAY NEED HERE *
		 *************************************************************/

	}

	public List<String> getInvalidExpressions() {
		return invalidExpressions;
	}

	public List<String> getResults() {
		return results;
	}

	public List<String> getExpressions() {
		return expressions;
	}

	/**
	 * TODO EXERCISE 3:
	 * 
	 * Write a method named fullyParens() that uses stacks to evaluate fully
	 * parenthesized expressions with variables.
	 * You should use 3 stacks: one for the operators, one for the parenthesis (or
	 * brackets or braces), and one for the operands.
	 * For this exercise, every time you see the word “parenthesis”, keep in mind
	 * that it may well be a bracket (“[“) or a brace (“{“).
	 * 
	 * There will only be two types of expressions:
	 * 
	 * 1) variableName=integerConstant
	 * 2) variableName:expression
	 * 
	 * You may assume that:
	 * 
	 * 1) Variable names will consist of a capital letter, so you’ll have at most 26
	 * variables.
	 * 2) We will be using only integer values.
	 * 3) There will be no spaces in the expressions.
	 * 
	 * WARNING: YOU MUST USE THREE STACKS, IMPLEMENTATIONS THAT DO NOT USE STACKS OR
	 * THAT ARE FORCED OUTPUTS WILL RECEIVE ZERO CREDIT
	 */
	public void fullyParens() {
		/* TODO implement */
		for (String s : expressions) {
			for (Character c : s.toCharArray()) {
				if (c == '(' || c == '[' || c == '{') {
					parenthesis.push(c);
				} else if (c == '+' || c == '-' || c == '*' || c == '/') {
					operators.push(c);
				} else if (c == ')' || c == ']' || c == '}') {
					parenthesis.pop();
					evaluate();
				} else {
					operands.push(c - '0');
				}
			}
		}
	}

	/***********************************************
	 * ADD ANY AUXILIARY METHODS YOU MAY NEED HERE *
	 ***********************************************/

	private void evaluate() {
		int r = operands.pop();
		int l = operands.pop();
		char op = operators.pop();
		int res = 0;

		switch (op) {
			case '+':
				res = l + r;
				break;
			case '-':
				res = l - r;
				break;
			case '*':
				res = l * r;
				break;
			case '/':
				res = l / r;
				break;
		}
		operands.push(res);
	}
}