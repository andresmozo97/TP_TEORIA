package tpeTeoria;

import java.awt.Color;
import java.awt.image.BufferedImage;



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

public class Imagen implements Comparable<Imagen>{

	private BufferedImage img;
	private Double fc;
	private Double desvio;
	private Double media;

	private Double [] distr;
	
	public Imagen() {
	}
	
	public Imagen(BufferedImage img, String nombre) {
		super();
		this.img = img;
		this.fc = 0.0;
		this.media = 0.0;
		this.desvio = 0.0;
		this.nombre=nombre;
		
	}

	
	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}
	
	public Double[] getDistr() {
		return distr;
	}

	public void setDistr(Double[] distr) {
		this.distr = distr;
	}


	public double getFc() {
		return fc;
	}

	public void setFc(double fc) {
		this.fc = fc;
	}

	public Double getDesvio() {
		return desvio;
	}

	public void setDesvio(Double desvio) {
		this.desvio = desvio;
	}

	public Double getMedia() {
		return media;
	}

	public void setMedia(Double media) {
		this.media = media;
	}

	public void setFc(Double fc) {
		this.fc = fc;
	}
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private String nombre;


	@Override
	public int compareTo(Imagen i) {
		return (this.fc).compareTo(i.getFc());
	}
	
	public int getValor(int x, int y)
	{
		Color c= new Color(img.getRGB(x, y),true);
		return c.getBlue();
	}
	public int getAncho() {
		return img.getWidth();
	}
	
	public int getAlto() { 
		return img.getHeight();
	}
	public String getStringDistr()
	{
		String out="";
		for (int i=0;i<distr.length;i++)
		{
			out=out+i+": "+distr[i].toString()+System.getProperty("line.separator");
		}
		return out;
	}
	
	public void generarHistograma()
	{
		double v [] = new double [img.getWidth()* img.getHeight()]; 
		int indice=0;
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++)
			{
				v[indice]=this.getValor(x, y);
				indice++;
			}
		}
		Histograma histo = new Histograma("Histograma "+this.nombre,v);
	}
	
	
	
}