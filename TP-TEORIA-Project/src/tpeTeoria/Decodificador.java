package tpeTeoria;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Decodificador extends Huffman {
	
	private int posBits;
	
	public Decodificador() {
		super();
		posBits=0;
	}
	public void decodificar(String nombreArchivo, String cod)
	{
		try {
			posBits=0;
			File archivo = new File("archivos\\codificacion_de_"+nombreArchivo + ".txt");
			FileReader lector= new FileReader(archivo);
			BufferedReader buff = new BufferedReader(lector);
			String linea = buff.readLine();
			Integer alto = 0 , ancho = 0;
			alto = this.getAlto(linea);
			ancho = this.getAncho(linea);
			linea=buff.readLine();
			Vector<Nodo> nodos =this.getDistribucion(linea,ancho, alto);		
			Nodo raiz = this.CrearArbol(nodos).elementAt(0);
			this.SetCodification(raiz,"");	
			/*String codificacion ="";
			while((linea = buff.readLine())!=null) {
		          codificacion = codificacion + linea;
		      }
			System.out.println("Leyendolo con un while acumulandolo en un string, el nuevo string tiene " + codificacion.length());
			char[] bits = this.generarTodosLosBits(codificacion); */
			char [] bits = this.generarTodosLosBits(cod);
			Imagen salida = this.obtenerImagen(bits, nombreArchivo, ancho, alto, raiz);	
			salida.generarBmp();
			buff.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected int getAlto(String linea) {
		String aux = "";
		int i=0;
		while (linea.charAt(i)!=',') {
			aux= aux+linea.charAt(i);
			i++;
		}
		return Integer.parseInt(aux);
	}
	
	private int getAncho (String linea) {
		String aux= "";
		int i =0;
		while (linea.charAt(i)!=',')
			i++;
		i++;
		while (i<linea.length())
		{
			aux= aux+linea.charAt(i);	
			i++;
		}
		return Integer.parseInt(aux);
	}
	
	public void ideaPaula (Nodo raiz)
	{
		if (raiz!=null)
		{
			if (raiz.esHoja())
			{
				System.out.println("llego a una hoja y su valor rgb " + raiz.getValorRGB() + " prob " + raiz.getProb() + " codif "+ raiz.getCodif() );
			}
			else
			{
				ideaPaula(raiz.getHijo1());
				ideaPaula(raiz.getHijo2());
			}
		}
		
	}
	private Vector<Nodo> getDistribucion(String linea, int ancho, int largo)
	{
		Vector<Nodo> out = new Vector<>();
		int total = ancho * largo;
		int i=0;
		String aux="";
		while (i < linea.length())
		{
			while (linea.charAt(i)!=' ')
			{
				aux= aux + linea.charAt(i);
				i++;
			}
			Integer valorRGB= Integer.parseInt(aux);
			i++;
			aux ="";
			while (linea.charAt(i)!=';')
			{
				aux= aux + linea.charAt(i);
				i++;
			}
			Double valorFrecuencia= Double.parseDouble(aux);
			aux = "";
			Nodo nuevo = new Nodo ((valorFrecuencia/total), 0, null, null, valorRGB);
			out.add(nuevo);
			i++;			
		}
		return out;
	}
	private Imagen obtenerImagen (char[] bits, String nombre, int ancho, int alto, Nodo raiz) {
		BufferedImage img = new BufferedImage (ancho, alto, BufferedImage.TYPE_INT_RGB );
		int cantSimbolos= 0;
		int f =0;
		int c = 0;
		int total = ancho*alto;
		int valorRGB=0;
		posBits=0;
		System.out.println("la longitud del arreglo de char es "+bits.length);
		while (cantSimbolos <total && posBits<bits.length && f<alto) {
			valorRGB= this.getValorRGB(bits, raiz);
			Color color = new Color(valorRGB, valorRGB, valorRGB);
			Integer rgb = color.getRGB();
			cantSimbolos++;
			if (f==69) 
				System.out.println("Ahora en la fila " + f +" colummna " + c + " rgb "   + rgb + " valorRGB " + valorRGB);
			img.setRGB(c, f, rgb);
			c++;
			if (c == ancho) {
				f= f+1;
				c=0;
			}
		}
		Imagen salida = new Imagen(img,nombre);	
		return salida;
	}
	
	private Integer getValorRGB(char[] bits) {
		boolean encontro = false;
		String c="";
		Integer result = -1;
		while (posBits<bits.length && !encontro) {
			c=c+bits[posBits];
			result = this.getValorDeCodif(c);
			if (result!= null) {
				return result;
			}
			posBits++;
		}
		return -1;
	}
	
	private Integer getValorRGB(char[] bits, Nodo raiz) {
		Nodo recorre = raiz;
		String c="";
		while ((recorre.getValorRGB()<0) && posBits<bits.length) {
			if (bits[posBits]=='0') {
				c=c+"0";
				recorre = recorre.getHijo1();
			}
			if (bits [posBits]=='1'){
				c=c+"1";
				recorre = recorre.getHijo2();
			}
			posBits++;
		}
		if (recorre.esHoja()) {
			return recorre.getValorRGB();
		}
		else {
			return -1;
		}
	}

	public char[] generarTodosLosBits(String linea) {
		String salida ="";
		char [] l = linea.toCharArray();
		for (int i = 0; i<l.length; i++) {
			salida = salida + generarBits(l[i]);
		}
		return salida.toCharArray();
	}
	
	private String generarBits(char c) {
		String salida = "";
		char mascara = 1<<7; //16 U 8????
		for (int i =0; i<8;i++) {
			if ((c & mascara )==128) {
				salida = salida + "1";
			}
			else 
				salida = salida + "0";
			c = (char) (c<<1);
		}
		return salida;
	}
	
}
