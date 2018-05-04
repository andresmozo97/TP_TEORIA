package tpeTeoria;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

public class Ejercicio1 {

	public static void main (String args [] )
	{
		Ejercicio1 e = new Ejercicio1();
		List<Imagen> l= e.OrdenarImagenes();
		for (int i=0;i<l.size();i++)
		{
			System.out.println(l.get(i).getNombre() + "  fc: " + l.get(i).getFc() + "  " + "desvio: " +l.get(i).getDesvio() + "  media: " + l.get(i).getMedia());
		}
		l.get(0).generarHistograma();
	}
	
	public Imagen abrirImagen(String nombre)
	{
	try {
			BufferedImage img = ImageIO.read(new File(nombre));
			String nombreImg= nombre.substring(9,15);
			Imagen i = new Imagen(img,nombreImg);
			return i;
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}
	return null;
	}
	
	public List<Imagen> OrdenarImagenes() //Ejercicio 1
	{
		Imagen original = abrirImagen("imagenes\\Will(Original).bmp");
		List <Imagen> imgs = new ArrayList<Imagen>();
		imgs.add(this.abrirImagen("imagenes\\Will_1.bmp")); //Preguntar si esta bien asi o hacemos un for
		imgs.add(this.abrirImagen("imagenes\\Will_2.bmp"));
		imgs.add(this.abrirImagen("imagenes\\Will_3.bmp"));
		imgs.add(this.abrirImagen("imagenes\\Will_4.bmp"));
		imgs.add(this.abrirImagen("imagenes\\Will_5.bmp"));
		imgs.add(this.abrirImagen("imagenes\\Will_6.bmp"));
		imgs.add(this.abrirImagen("imagenes\\Will_7.bmp"));
		
		
		for (Imagen i : imgs) {
			Double fc = this.CalcularFactorCorrelacion(original,i);
			i.setFc(fc);
		}
		Collections.sort(imgs);
		return imgs;
	}
	
	public void GenerarHistogramas()
	{
		
	}
	
	
	
	
	public Double CalcularFactorCorrelacion(Imagen original, Imagen imagenComp)
	{
		BufferedImage orig = original.getImg();
		BufferedImage img = imagenComp.getImg();
		
		Double A = 0.0; 
		Double B = 0.0;
		Double AB =0.0;
		Double A2 = 0.0; //A al cuadrado 
		Double B2 = 0.0; //B al cuadrado
		int n = orig.getWidth()*orig.getHeight();
		
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				//int rgbOrig = this.getValor(orig.getRGB(x, y));
				int rgbOrig = original.getValor(x,y);
				//int rgbImg = this.getValor(img.getRGB(x, y));
				int rgbImg = imagenComp.getValor(x,y);
				
				A=A+rgbOrig;
				B=B+rgbImg;
				AB= AB+rgbOrig*rgbImg;
				A2=A2+rgbOrig*rgbOrig;
				B2=B2+rgbImg*rgbImg;
			}
		}
		Double mediaA = A/n; 
		Double mediaB = B/n; 
		Double mediaA2 = A2/n;
		Double mediaB2 = B2/n;
		Double mediaAB = AB/n;
		Double mediaAcuadrado=mediaA*mediaA;
		Double mediaBcuadrado=mediaB*mediaB;
		Double desvioA= Math.sqrt(mediaA2-mediaAcuadrado);
		Double desvioB= Math.sqrt(mediaB2-mediaBcuadrado);
		Double correlacion=(Double)(mediaAB-mediaA*mediaB)/(desvioA*desvioB);
		
		//Seteamos los valores calculados de la media y el desvio a cada una de las imgs
		original.setDesvio(desvioA);
		original.setMedia(mediaA);
		imagenComp.setDesvio(desvioB);
		imagenComp.setMedia(mediaB);
		return correlacion;
	}
	
	private int getValor(int rgb)
	{
		Color c= new Color(rgb,true);
		return c.getBlue();
	}
	
	
}

