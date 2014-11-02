import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Math;

public class KeyboardExpressionCalculator
{
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
		||(expression.charAt(i) == '^')
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
     case '^': result = Math.pow(leftValue, rightValue);
     }
   System.out.println(" = " + result);
   }
 }
}
