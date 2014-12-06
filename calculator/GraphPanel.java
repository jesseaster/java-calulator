import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.Arrays;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

public class GraphPanel extends JPanel implements MouseListener{
	public void mousePressed(MouseEvent me)
	{
		Point clickPoint = me.getPoint();
		//System.out.println(clickPoint.x);
		//System.out.println(max);
		xClick = (clickPoint.x - padding -labelPadding)/xScale + getMinValue(xValues);
		System.out.println(xClick);
	}
	public void mouseReleased(MouseEvent me){}
	public void mouseClicked(MouseEvent me){}
	public void mouseEntered(MouseEvent me){}
	public void mouseExited(MouseEvent  me){}
	//private static KeyboardExpressionCalculator calc;
	private int width = 800;
	private int heigth = 400;
	private int padding = 25;
	private int labelPadding = 25;
	private Color lineColor = new Color(44, 102, 230, 180);
	private Color pointColor = new Color(100, 100, 100, 180);
	private Color gridColor = new Color(200, 200, 200, 200);
	private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
	private int pointWidth = 4;
	private int numberYDivisions = 10;
	private String expression;
	private double[] xValues;
	private double[] yValues;
	private double min;
	private double max;
	private double range;
	private int exponent;
	private double yrange;
	private double ymin;
	private double ymax;
	private double count;
	private double xClick;
	private double xScale;

	public GraphPanel(String expression, double[] xValues, double[] yValues) throws IllegalArgumentException {
		this.expression = expression;
		this.xValues = xValues;
		this.yValues = yValues;
		addMouseListener(this);
	}

	@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			min = getMinValue(yValues);
			max = getMaxValue(yValues);
			range = max - min;
			exponent = (int)Math.ceil(Math.log10(range));
			yrange = (Math.pow(10, exponent));
			ymin = ((int)Math.floor(min/Math.pow(10, (exponent-1))))*(Math.pow(10, (exponent-1)));
			ymax = yrange + ymin;
			count = Math.pow(10, (exponent-1));
			System.out.println("exponent " + exponent);
			System.out.println("yrange " + yrange);
			System.out.println("ymin " + ymin);
			System.out.println("ymax " + ymax);
			System.out.println("count " + count);

			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (getMaxValue(xValues) - getMinValue(xValues));
			double yScale = ((double) getHeight() - (2 * padding) - labelPadding) / (yrange);

			List<Point> graphPoints = new ArrayList<>();
			for (int i = 0; i < yValues.length; i++) {
				int x1 = (int) ((xValues[i] - getMinValue(xValues)) * xScale + padding + labelPadding);
				int y1 = (int) ((ymax - yValues[i]) * yScale + padding);
				graphPoints.add(new Point(x1, y1));
				System.out.println(y1);
			}

			// draw white background
			g2.setColor(Color.WHITE);
			g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
			g2.setColor(Color.BLACK);

			// create hatch marks and grid lines for y axis.
			for (int i = 0; i < numberYDivisions + 1; i++) {
				int x0 = padding + labelPadding;
				int x1 = pointWidth + padding + labelPadding;
				int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
				int y1 = y0;
				if (yValues.length > 0) {
					g2.setColor(gridColor);
					g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
					g2.setColor(Color.BLACK);
					String yLabel = i*count + ymin + "";
					FontMetrics metrics = g2.getFontMetrics();
					int labelWidth = metrics.stringWidth(yLabel);
					g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
				}
				g2.drawLine(x0, y0, x1, y1);
			}

			// and for x axis
			for (int i = 0; i < yValues.length; i++) {
				if (yValues.length > 1) {
					int x0 = i * (getWidth() - padding * 2 - labelPadding) / (yValues.length - 1) + padding + labelPadding;
					int x1 = x0;
					int y0 = getHeight() - padding - labelPadding;
					int y1 = y0 - pointWidth;
					if ((i % ((int) ((yValues.length / 20.0)) + 1)) == 0) {
						g2.setColor(gridColor);
						g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
						g2.setColor(Color.BLACK);
						String xLabel = ((int)(xValues[i]* 100))/ 100.0 + "";
						FontMetrics metrics = g2.getFontMetrics();
						int labelWidth = metrics.stringWidth(xLabel);
						g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
					}
					g2.drawLine(x0, y0, x1, y1);
				}
			}

			// create x and y axes 
			g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
			g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

			Stroke oldStroke = g2.getStroke();
			g2.setColor(lineColor);
			g2.setStroke(GRAPH_STROKE);
			for (int i = 0; i < graphPoints.size() - 1; i++) {
				int x1 = graphPoints.get(i).x;
				int y1 = graphPoints.get(i).y;
				int x2 = graphPoints.get(i + 1).x;
				int y2 = graphPoints.get(i + 1).y;
				g2.drawLine(x1, y1, x2, y2);
			}

			g2.setStroke(oldStroke);
			g2.setColor(pointColor);
			for (int i = 0; i < graphPoints.size(); i++) {
				int x = graphPoints.get(i).x - pointWidth / 2;
				int y = graphPoints.get(i).y - pointWidth / 2;
				int ovalW = pointWidth;
				int ovalH = pointWidth;
				g2.fillOval(x, y, ovalW, ovalH);
			}
		}

	private double getMinValue(double array[]) {
		double min = array[0];
		for (int i = 0; i  < array.length; i++) {
			if(array[i] < min){
				min = array[i];
			}
		}
		return min;
	}

	private double getMaxValue(double array[]) {
		double max = array[0];
		for (int i = 0; i  < array.length; i++) {
			if(array[i] > max){
				max = array[i];
			}
		}
		return max;
	}

	//private static void createAndShowGui() {
	//	double[] xValues;
	//	double[] yValues;
	//	xValues = new double[10];
	//	yValues = new double[10];
	//	String xStart = "0";
	//	String increment = "1";
	//	String expression = "x";
	//	double dxStart = Double.parseDouble(xStart);
	//	double dIncrement = Double.parseDouble(increment);
	//	double xLocation;
	//	for(int i = 0; i<10; i++){
	//		xLocation = dxStart + dIncrement*i;
	//		xValues[i] = xLocation;
	//		yValues[i] = i;//Double.parseDouble(calculate(expression, Double.toString(xLocation)));
	//	}
	//	GraphPanel mainPanel = new GraphPanel(expression, xValues, yValues);
	//	mainPanel.setPreferredSize(new Dimension(800, 600));
	//	JFrame frame = new JFrame(expression);
	//	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	frame.getContentPane().add(mainPanel);
	//	frame.pack();
	//	frame.setLocationRelativeTo(null);
	//	frame.setVisible(true);
	//}

	//public static void main(String[] args) {
	//	SwingUtilities.invokeLater(new Runnable() {
	//			public void run() {
	//			createAndShowGui();
	//			}
	//			});
	//}
}
