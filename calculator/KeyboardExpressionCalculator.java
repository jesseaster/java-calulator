import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	//JCheckBox checkBox= new JCheckBox("Drop.00");
	//JTextArea logTextArea = new JTextArea(20,40);
	//JScrollPane logScrollPane = new JScrollPane(logTextArea);
	//String newLine = System.lineSeparator();
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
		//totalTextField.setFont(new Font("Times Roman", Font.BOLD, 20));
		clearButton.addActionListener(this);
		amountTextField.addActionListener(this);
		window.getContentPane().add(errorTextField, "South");
		//window.getContentPane().add(logScrollPane, "Center");
		errorTextField.setEditable(false);
		//logTextArea.setEditable(false);
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
			if(ae.getSource()== amountTextField)
			{
				try{
					String enteredAmount = amountTextField.getText();
					String newTotal = accumulate(enteredAmount);
					//if(newTotal.endsWith("00")||checkBox.isSelected())
					newTotal=newTotal.substring(0, newTotal.length()-3);
					totalTextField.setText(newTotal);
					errorTextField.setText("");
					errorTextField.setBackground(Color.white);
					/*logTextArea.append(newLine+newTotal);
					  logTextArea.setCaretPosition(logTextArea.getDocument().getLength());*/
					amountTextField.setText("");
				}
				catch(IllegalArgumentException iae)
				{
					errorTextField.setText(iae.getMessage());
					errorTextField.setBackground(Color.pink);
				}
			}
		}
		if(expressionMode.isSelected() == true)
		{
			amountTextField.setEditable(false);
			System.out.println("Enter a simple expression (single operator + - * /)");
			while (true)
			{
				String expression = null;
				try {
					expression = br.readLine().trim();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (expression.equalsIgnoreCase("EXIT")
						|| expression.equalsIgnoreCase("STOP")) return;
				// Scan for operator
				for (i = 0; i<expression.length(); i++)
					if((expression.charAt(i) == '+')
							||(expression.charAt(i) == '-')
							||(expression.charAt(i) == '*')
							||(expression.charAt(i) == '/'))
					{
						operator = expression.charAt(i);
						break;
					}
				if (i == expression.length())
				{
					System.out.println("Expression does not contain an operator + - * or /");
					continue;
				}
				leftOperand = expression.substring(0,i).trim();
				rightOperand= expression.substring(i+1).trim();
				try {
					leftValue = Double.parseDouble(leftOperand);
					rightValue= Double.parseDouble(rightOperand);
				}
				catch(NumberFormatException nfe)
				{
					System.out.println("Left or right operand is not numeric.");
					continue;
				}
				switch(operator)
				{
					case '+': result = leftValue + rightValue; break;
					case '-': result = leftValue - rightValue; break;
					case '*': result = leftValue * rightValue; break;
					case '/': result = leftValue / rightValue; break;
				}
				System.out.println(" = " + result);
			}
		}
	}

	//added by Kiki
	public void clear() {

		amountTextField.setText("");
		totalTextField.setText("");
		amountTextField.requestFocus();

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
