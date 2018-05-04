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
import org.jfree.ui.RefineryUtilities;

public class Histograma extends ApplicationFrame {
	
	private String nombre; 
	
	public Histograma(String title, double v []) {
		super(title);
		nombre=title;
		JPanel chartPanel = crearPanel(v);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 475));
		setContentPane(chartPanel);
	}

	private static IntervalXYDataset crearDataset(double v []) {
		HistogramDataset dataset = new HistogramDataset();
		// vecto almacena los ingresos quincenales de 45 personas
		
		//double vector[] = { 63, 89, 36, 49, 56, 64, 59, 35, 78, 43, 53, 70, 57, 62, 43, 68, 62, 26, 64, 72, 52, 51, 62,
		//		60, 71, 61, 55, 59, 60, 67, 57, 67, 61, 67, 51, 81, 53, 64, 76, 44, 73, 56, 62, 63, 60 };
		// En el ejercicio nos piden construir una distribución de frecuencias de 8
		// intervalos
		// Por eso ponemos 8 en el tercer parámetro del addSeries
		dataset.addSeries("Frecuencias de los ingresos", v,50);
		
		return dataset;
	}

	private static JFreeChart crearChart(IntervalXYDataset dataset) {
		JFreeChart chart = ChartFactory.createHistogram("Histograma", null, null, dataset, PlotOrientation.VERTICAL,
				true, true, false);
		XYPlot plot = (XYPlot) chart.getPlot();
		XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		try {
			
			ChartUtilities.saveChartAsJPEG(new File("imagenes\\histograma1.jpg"), chart, 500, 475);//cambiar la direcc
		} catch (IOException e) {
			System.out.println("Error al abrir el archivo");
		}
		return chart;
	}

	public static JPanel crearPanel(double v []) {
		JFreeChart chart = crearChart(crearDataset(v));
		return new ChartPanel(chart);
	}

	public static void main(String[] args) throws IOException {
		//Histograma histo = new Histograma("Histograma");
		//histo.pack();
		//RefineryUtilities.centerFrameOnScreen(histo);
		//histo.setVisible(true);
	}
}

