import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Math;
import java.util.LinkedList;
import java.util.Stack;
import java.util.EmptyStackException;
import java.io.*;
import java.lang.String;
import java.lang.StringIndexOutOfBoundsException;
import java.lang.Math;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class KeyboardExpressionCalculator implements ActionListener, Accumulator
{

	//added by Kiki

	double total=0;
	BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in));
	char operator = ' ';
	String leftOperand = null;
	double leftValue   = 0;
	String rightOperand= null;
	double rightValue  = 0;
	double result      = 0;
	int    i           = 0;

	JFrame  window      = new JFrame("Complex Expression Calculator");
	JButton clearButton = new JButton("CLEAR"); 
	JLabel amountLabel = new JLabel("Enter amount", SwingConstants.RIGHT);
	JLabel totalLabel = new JLabel("Total", SwingConstants.RIGHT);
	JLabel expressionLabel = new JLabel("Enter expression", SwingConstants.RIGHT);
	JLabel xLabel = new JLabel("X value", SwingConstants.RIGHT);
	JTextField amountTextField = new JTextField(8);
	JTextField totalTextField = new JTextField(8);
	JTextField expressionField= new JTextField();
	JTextField xValue = new JTextField(8);
	JPanel panel = new JPanel();
	JTextField errorTextField = new JTextField(32);
	JCheckBox checkBox= new JCheckBox("Drop.00");
	JTextArea logTextArea = new JTextArea(20,40);
	JScrollPane logScrollPane = new JScrollPane(logTextArea);
	String newLine = System.lineSeparator();
	JRadioButton accumulatorMode = new JRadioButton("accumulator mode");
	JRadioButton expressionMode = new JRadioButton("expression mode");
	JRadioButton graphMode = new JRadioButton("graph mode");
	ButtonGroup buttonGroup = new ButtonGroup();

	//added by Kiki
	public KeyboardExpressionCalculator()
	{
		panel.setLayout(new GridLayout(2,3));
		panel.add(clearButton);
		buttonGroup.add(accumulatorMode);
		buttonGroup.add(expressionMode);
		buttonGroup.add(graphMode);
		panel.add(accumulatorMode);
		panel.add(expressionMode);
		panel.add(graphMode);
		panel.add(amountLabel);
		panel.add(amountTextField);
		panel.add(expressionLabel);
		panel.add(expressionField);
		panel.add(xLabel);
		panel.add(xValue);
		panel.add(totalLabel);
		panel.add(totalTextField);
		window.getContentPane().add(panel,"North");
		window.setSize(1000, 300);
		window.setLocation(300,200);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		totalTextField.setEditable(false);
		expressionMode.setSelected(true);
		expressionField.setEditable(true);	
		xValue.setEditable(true);
		amountTextField.setEditable(false);
		//totalTextField.setFont(new Font("Times Roman", Font.BOLD, 20));
		clearButton.addActionListener(this);
		expressionMode.addActionListener(this);
		accumulatorMode.addActionListener(this);
		graphMode.addActionListener(this);
		expressionField.addActionListener(this);
		amountTextField.addActionListener(this);
		window.getContentPane().add(errorTextField, "South");
		window.getContentPane().add(logScrollPane, "Center");
		errorTextField.setEditable(false);
		logTextArea.setEditable(false);
		//logTextArea.setFont(new Font("Times Roman", Font.BOLD, 20));
		amountTextField.requestFocus();
	}

	public static void main(String[] args) throws Exception
	{
		new KeyboardExpressionCalculator();
	}



	public void actionPerformed(ActionEvent ae) {

		if(ae.getSource()== clearButton)
		{
			clear();
			return;
		}


		if(accumulatorMode.isSelected() == true)
		{
			expressionField.setEditable(false);	
			xValue.setEditable(false);
			amountTextField.setEditable(true);
		}

		if(expressionMode.isSelected() == true)
		{
			expressionField.setEditable(true);	
			xValue.setEditable(true);
			amountTextField.setEditable(false);
		}


		if(ae.getSource()== amountTextField)
		{
			try{
				String enteredAmount = amountTextField.getText();
				String newTotal = accumulate(enteredAmount);
				if(newTotal.endsWith("00")||checkBox.isSelected())
					newTotal=newTotal.substring(0, newTotal.length()-3);
				totalTextField.setText(newTotal);//newTotal);
				errorTextField.setText("");
				errorTextField.setBackground(Color.white);
				logTextArea.append(newLine+newTotal);
				logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
				amountTextField.setText("");
			}
			catch(IllegalArgumentException iae)
			{
				errorTextField.setText(iae.getMessage());
				errorTextField.setBackground(Color.pink);
			}
		}

		if(ae.getSource() == expressionField)
		{

			String expression = null;
			String x_value = null;
			try {
				expression = expressionField.getText();
				x_value = xValue.getText();
				System.out.println(expression);
				String newTotal= Shunting(expression, x_value);
				totalTextField.setText(newTotal);
				errorTextField.setText("");
				errorTextField.setBackground(Color.white);
				logTextArea.append(newLine+newTotal);
				logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
				expressionField.setText("");
			} catch(IllegalArgumentException iae)
			{
				errorTextField.setText(iae.getMessage());
				errorTextField.setBackground(Color.pink);
			}


		}
	}

	public static String Shunting(String infix, String x_value){
		String Pi = Double.toString(Math.PI);
		String E = Double.toString(Math.E);
		String test = infix.replaceAll("\\^", " ^ ");
		test = test.replaceAll("x", x_value);
		test = test.replaceAll("r", " r ");
		test = test.replaceAll("R", " r ");
		test = test.replaceAll("\\+", " + ");
		test = test.replaceAll("\\*", " * ");
		test = test.replaceAll("\\(", " ( ");
		test = test.replaceAll("\\)", " ) ");
		test = test.replaceAll("/", " / ");
		test = test.replaceAll("-", " - ");
		test = test.replaceAll("pi", Pi);
		test = test.replaceAll("e", E); 
		test = test.replaceAll("^ +| +$|( )+", "$1");
		test = test.replaceAll("- -", "+");
		test = test.replaceAll("\\+ - (\\d)", "+ -$1");
		test = test.replaceAll("\\* - (\\d)", "* -$1");
		test = test.replaceAll("\\^ - (\\d)", "^ -$1");
		test = test.replaceAll("\\( - (\\d)", "( -$1");
		test = test.replaceAll("/ - (\\d)", "/ -$1");
		System.out.println(test);

		if(test.contains("( +")){
			System.out.println("operand missing before +");
			System.exit(1);
		}

		System.out.printf("infix:   %s%n", test);
		System.out.printf("postfix: %s%n", infixToPostfix(test));
		return evalRPN(infixToPostfix(test));
	}   

	static String infixToPostfix(String infix) {
		final String ops = "-+/*^r";
		StringBuilder sb = new StringBuilder();
		Stack<Integer> s = new Stack<>();

		for (String token : infix.split("\\s")) {
			char c = token.charAt(0);
			int idx = ops.indexOf(c);
			if (idx != -1 && token.length() == 1) {
				if (s.isEmpty())
					s.push(idx);
				else {
					while (!s.isEmpty()) {
						int prec2 = s.peek() / 2;
						int prec1 = idx / 2;
						if (prec2 > prec1 || (prec2 == prec1 && c != '^'))
							sb.append(ops.charAt(s.pop())).append(' ');
						else break;
					}
					s.push(idx);
				}
			} else if (c == '(') {
				s.push(-2);
			} else if (c == ')') {
				try{
					while (s.peek() != -2) 
						sb.append(ops.charAt(s.pop())).append(' ');
					s.pop();
				}
				catch (EmptyStackException ese){
					System.out.println("unbalanced parentheses");
					System.exit(1);
				}
			} else {
				sb.append(token).append(' ');
			}
		}
		try{
			while (!s.isEmpty())
				sb.append(ops.charAt(s.pop())).append(' ');
		}
		catch (StringIndexOutOfBoundsException ex){
			System.out.println("unbalanced parentheses");
			System.exit(1);
		}
		return sb.toString();
	}   

	public static String evalRPN(String expr){
		String cleanExpr = cleanExpr(expr);
		LinkedList<Double> stack = new LinkedList<Double>();
		System.out.println("Input\tOperation\tStack after");
		for(String token:cleanExpr.split("\\s")){
			System.out.print(token+"\t");
			Double tokenNum = null;
			try{
				tokenNum = Double.parseDouble(token);
			}catch(NumberFormatException e){}
			if(tokenNum != null){
				System.out.print("Push\t\t");
				stack.push(Double.parseDouble(token+""));
			}else if(token.equals("*")){
				System.out.print("Operate\t\t");
				double secondOperand = stack.pop();
				double firstOperand = stack.pop();
				stack.push(firstOperand * secondOperand);
			}else if(token.equals("/")){
				System.out.print("Operate\t\t");
				double secondOperand = stack.pop();
				double firstOperand = stack.pop();
				stack.push(firstOperand / secondOperand);
			}else if(token.equals("-")){
				System.out.print("Operate\t\t");
				double secondOperand = stack.pop();
				double firstOperand = stack.pop();
				stack.push(firstOperand - secondOperand);
			}else if(token.equals("+")){
				System.out.print("Operate\t\t");
				double secondOperand = stack.pop();
				double firstOperand = stack.pop();
				stack.push(firstOperand + secondOperand);
			}else if(token.equals("^")){
				System.out.print("Operate\t\t");
				double secondOperand = stack.pop();
				double firstOperand = stack.pop();
				stack.push(Math.pow(firstOperand, secondOperand));
			}else if(token.equals("r")){
				System.out.print("Operate\t\t");
				double secondOperand = stack.pop();
				double firstOperand = stack.pop();
				stack.push(Math.pow(firstOperand, 1.0 / secondOperand));
			}else{//just in case
				System.out.println("Error");
				return "Error";
			}
			System.out.println(stack);
		}
		return Double.toString(stack.pop());
	}

	private static String cleanExpr(String expr){
		//remove all non-operators, non-whitespace, and non digit chars
		return expr.replaceAll("[^.^r^\\^\\*\\+\\-\\d/\\s]", "");
	}

	//added by Kiki
	public void clear() {
		accumulatorMode.setSelected(false);
		expressionMode.setSelected(true);
		graphMode.setSelected(false);
		expressionField.setText("");
		amountTextField.setText("");
		totalTextField.setText("");
		xValue.setText("");
		amountTextField.setEditable(true);
		expressionField.setEditable(true);
		amountTextField.requestFocus();

	}

	public void enter() {

	}
	//added by Kiki
	public void actionListener(ActionEvent ae){

	}

	@Override
		public String accumulate(String amount) throws IllegalArgumentException {

			amount=amount.trim();
			double amount1;

			amount1 = Double.parseDouble(amount);


			total=total+amount1;
			String newTotal = String.valueOf(total);
			if(newTotal.contains("."))
			{
				int periodOffset= newTotal.indexOf(".");
				String decimalPortion= newTotal.substring(periodOffset+1);
				if(decimalPortion.length()==0)
					newTotal +="00";
				if(decimalPortion.length()==1)
					newTotal +="0";
				if(decimalPortion.length()>2)
				{
					total += .005;
					newTotal=String.valueOf(total);
					periodOffset=newTotal.indexOf(".");
					newTotal=newTotal.substring(0,periodOffset+3);
				}
			}
			return newTotal;
		}

}
