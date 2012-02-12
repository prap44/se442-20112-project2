package hms.nursingstation.gui;

import java.util.Collection;

import javax.swing.JPanel;

public class VitalPlot extends JPanel {
	private double min_x, max_x;
	private double min_y, max_y;
	private Collection<Point> data;
	
	public class Point {
		private double x, y;
		
		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
		
		public void setX(double x) {
			this.x = x;
		}
		
		public double getX() {
			return this.x;
		}
		
		public void setY(double y) {
			this.y = y;
		}
		
		public double getY() {
			return this.y;
		}
	}
	
	public VitalPlot(double min_x, double max_x, double min_y, double max_y, Collection<Point> data) {
		this.min_x = min_x;
		this.max_x = max_x;
		this.min_y = min_y;
		this.max_y = max_y;
		this.data = data;
		
		this.initialize();
	}
	
	private void initialize() {
		
	}
}
