package tpeTeoria;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
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
		Imagen original = e.abrirImagen("imagenes\\Will(Original).bmp");
		List <Imagen> imgs = new ArrayList<Imagen>();
		imgs.add(e.abrirImagen("imagenes\\Will_1.bmp")); //Preguntar si esta bien asi o hacemos un for
		imgs.add(e.abrirImagen("imagenes\\Will_2.bmp"));
		imgs.add(e.abrirImagen("imagenes\\Will_3.bmp"));
		imgs.add(e.abrirImagen("imagenes\\Will_4.bmp"));
		imgs.add(e.abrirImagen("imagenes\\Will_5.bmp"));
		imgs.add(e.abrirImagen("imagenes\\Will_6.bmp"));
		imgs.add(e.abrirImagen("imagenes\\Will_7.bmp"));
		
		List<Imagen> l= e.OrdenarImagenes(original,imgs);
		e.GenerarHistogramas(l);
		e.GenerarTxtDistribuciones(l.get(0));
		e.GenerarTxtDistribuciones(l.get(l.size()-1));
		e.GenerarTxtDistribuciones(original);
		Huffman h = new Huffman();
		h.setHash(original);
	}
	
	public Imagen abrirImagen(String nombreArchivo)
	{
	try {
			BufferedImage img = ImageIO.read(new File(nombreArchivo));
			String nombreImg="";
			if (nombreArchivo=="imagenes\\Will(Original).bmp")
				nombreImg= "Will Original";
			else
				nombreImg= nombreArchivo.substring(9,15);
			Imagen i = new Imagen(img,nombreImg);
			return i;
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}
	return null;
	}
	
	public List<Imagen> OrdenarImagenes(Imagen original,List <Imagen> imgs ) //Ejercicio 1
	{
		for (Imagen i : imgs) {
			Double fc = this.CalcularFactorCorrelacionYDistribuciones(original,i);
			i.setFc(fc);
		}
		Collections.sort(imgs);
		return imgs;
	}
	
	public void GenerarHistogramas(List <Imagen> l)
	{
		for (int i=0;i<l.size();i++)
		{
			l.get(i).generarHistograma();
			//System.out.println(l.get(i).getNombre() + "  fc: " + l.get(i).getFc() + "  " + "desvio: " +l.get(i).getDesvio() + "  media: " + l.get(i).getMedia());
		
		}
	}
	
	public void GenerarTxtDistribuciones(Imagen img) {
		try {
			File archivo = new File("distribucion_de_"+img.getNombre() + ".txt");
			FileWriter escribir = new FileWriter(archivo, true);			
			String s="La distribucion de la foto "+img.getNombre()+" es: ";
			s =s+ System.getProperty("line.separator") + img.getStringDistr();
			//System.out.println(s); //BORRAR DSP
			escribir.write(s);
			escribir.close();
		}
		catch (Exception e) {
			System.out.println("Error al escribir");
		}
	}
	
	
	
	public Double CalcularFactorCorrelacionYDistribuciones(Imagen original, Imagen imagenComp)
	{
		BufferedImage orig = original.getImg();
		BufferedImage img = imagenComp.getImg();
		
		Double A = 0.0; 
		Double B = 0.0;
		Double AB =0.0;
		Double A2 = 0.0; //A al cuadrado 
		Double B2 = 0.0; //B al cuadrado
		int n = orig.getWidth()*orig.getHeight();
		Double [] distrA = new Double[256];
		Double [] distrB = new Double[256];
		for (int i=0;i<distrA.length;i++)
		{
			distrA[i]=0.0;
			distrB[i]=0.0;
		}
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int rgbOrig = original.getValor(x,y);
				int rgbImg = imagenComp.getValor(x,y);
				distrA[rgbOrig]++;
				distrB[rgbImg]++;
				A=A+rgbOrig;
				B=B+rgbImg;
				AB= AB+rgbOrig*rgbImg;
				A2=A2+rgbOrig*rgbOrig;
				B2=B2+rgbImg*rgbImg;
			}
		}
		
		for (int i=0;i<256;i++)
		{
			distrA[i]= distrA[i]/n;
			distrB[i]= distrB[i]/n;
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
		Double factorCorrelacion=(Double)(mediaAB-mediaA*mediaB)/(desvioA*desvioB);
		
		//Seteamos los valores calculados de la media y el desvio a cada una de las imgs
		original.setDesvio(desvioA);
		original.setDistr(distrA);
		
		original.setMedia(mediaA);
		imagenComp.setDesvio(desvioB);
		imagenComp.setMedia(mediaB);
		imagenComp.setDistr(distrB);

		return factorCorrelacion;
	}
	
	
	
	
}
