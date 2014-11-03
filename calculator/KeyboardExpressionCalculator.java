import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
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
	 JFrame  window      = new JFrame("Complex Expression Calculator");
	 JButton clearButton = new JButton("CLEAR"); 
	 JLabel amountLabel = new JLabel("Enter amount", SwingConstants.RIGHT);
	 JLabel totalLabel = new JLabel("Total", SwingConstants.RIGHT);
		JTextField leftTextField = new JTextField(8);
		JTextField rightTextField = new JTextField(8);
		JTextField totalTextField = new JTextField(8);
		JPanel panel = new JPanel();
		JTextField errorTextField = new JTextField(32);
		JCheckBox checkBox= new JCheckBox("Drop.00");
		JTextArea logTextArea = new JTextArea(20,40);
		JScrollPane logScrollPane = new JScrollPane(logTextArea);
		String newLine = System.lineSeparator();
		JTextField xValue = new JTextField(8);
		static JRadioButton accumulatorMode = new JRadioButton();
		static JRadioButton expressionMode = new JRadioButton();
		static JRadioButton graphMode = new JRadioButton();
		ButtonGroup buttonGroup = new ButtonGroup();
		
		
public static void main(String[] args) throws Exception
 {
 BufferedReader br = new BufferedReader(
                     new InputStreamReader(System.in));
 char operator = ' ';
 String leftOperand = null;
 double leftValue   = 0;
 String rightOperand= null;
 double rightValue  = 0;
 double result      = 0;
 int    i           = 0;
 
 if(expressionMode.isSelected() == true )
 {
 System.out.println("Enter a simple expression (single operator + - * /)");
 while (true)
   {
   String expression = br.readLine().trim();
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
public KeyboardExpressionCalculator()
{
	panel.setLayout(new GridLayout(1,5));
	panel.add(clearButton);
	panel.add(amountLabel);
	panel.add(leftTextField);
	panel.add(rightTextField);
	panel.add(totalLabel);
	panel.add(totalTextField);
	panel.add(checkBox);
	buttonGroup.add(accumulatorMode);
	buttonGroup.add(expressionMode);
	buttonGroup.add(graphMode);
	panel.add(accumulatorMode);
	panel.add(expressionMode);
	panel.add(graphMode);
	window.getContentPane().add(panel,"North");
	window.setSize(600, 300);
	window.setLocation(300,200);
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.setVisible(true);
	totalTextField.setEditable(false);
	//totalTextField.setFont(new Font("Times Roman", Font.BOLD, 20));
	clearButton.addActionListener(this);
	leftTextField.addActionListener(this);
	rightTextField.addActionListener(this);
	window.getContentPane().add(errorTextField, "South");
	window.getContentPane().add(logScrollPane, "Center");
	errorTextField.setEditable(false);
	logTextArea.setEditable(false);
	//logTextArea.setFont(new Font("Times Roman", Font.BOLD, 20));
	leftTextField.requestFocus();
}


public void actionPerformed(ActionEvent ae) {
	if(ae.getSource()== clearButton)
		{
		clear();
		return;
		}
	if(ae.getSource()== leftTextField)
	{
	try{
		String enteredAmount = leftTextField.getText();
		String newTotal = accumulate(enteredAmount);
		//if(newTotal.endsWith("00")||checkBox.isSelected())
			newTotal=newTotal.substring(0, newTotal.length()-3);
		totalTextField.setText(newTotal);
		errorTextField.setText("");
		errorTextField.setBackground(Color.white);
		/*logTextArea.append(newLine+newTotal);
		logTextArea.setCaretPosition(logTextArea.getDocument().getLength());*/
		leftTextField.setText("");
		}
	catch(IllegalArgumentException iae)
		{
		errorTextField.setText(iae.getMessage());
		errorTextField.setBackground(Color.pink);
		}
	}
	// buttons and text fields call here!	
	}

//added by Kiki
public void clear() {
	
	leftTextField.setText("");
	rightTextField.setText("");
	totalTextField.setText("");
	logTextArea.setText("");
	leftTextField.requestFocus();

}
//added by Kiki
 public void actionListener(ActionEvent ae){
	 
 }

@Override
public String accumulate(String amount) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	return null;
}
 
}
