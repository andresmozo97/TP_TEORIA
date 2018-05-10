package tpeTeoria;

import java.io.File;

import java.io.IOException;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;

public class Histograma extends ApplicationFrame {
	
	private String nombre; 
	
	public Histograma(String title, double v []) {
		super(title);
		nombre=title;
		JPanel chartPanel = crearPanel(v,this.nombre);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 475));
		setContentPane(chartPanel);
	}

	private static IntervalXYDataset crearDataset(double v []) {
		HistogramDataset dataset = new HistogramDataset();
		dataset.addSeries("Frecuencias de los ingresos", v,50);
		return dataset;
	}

	private static JFreeChart crearChart(IntervalXYDataset dataset, String nombre) {
		JFreeChart chart = ChartFactory.createHistogram("Histograma", null, null, dataset, PlotOrientation.VERTICAL,
				true, true, false);
		XYPlot plot = (XYPlot) chart.getPlot();
		XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		try {
			
			ChartUtilities.saveChartAsJPEG(new File("imagenes"+"\\"+nombre+ ".jpg"), chart, 500, 475);//cambiar la direcc

		} catch (IOException e) {
			System.out.println("Error al abrir el archivo");
		}
		return chart;
	}

	public static JPanel crearPanel(double v [], String nombre) {
		JFreeChart chart = crearChart(crearDataset(v),nombre);
		return new ChartPanel(chart);
	}

	public static void main(String[] args) throws IOException {
		//Histograma histo = new Histograma("Histograma");
		//histo.pack();
		//RefineryUtilities.centerFrameOnScreen(histo);
		//histo.setVisible(true);
	}
}

