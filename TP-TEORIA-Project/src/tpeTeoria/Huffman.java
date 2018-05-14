package tpeTeoria;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class Huffman {

	private Hashtable<Integer,String> hash;
	
	public Huffman() {
		hash=new Hashtable<Integer,String>();
	}
	
	public void impHash() {
		Set<Integer> claves = hash.keySet();
		for (Integer i : claves) {
			System.out.println(i + " " + hash.get(i));
		}
	}
	
	public void setHash(Imagen img)
	{
		Double [] probas = img.getProbabilidades();
		Vector<Nodo> nodos= new Vector<Nodo>();
		for (int i=0;i<probas.length;i++)
		{
			if (probas[i]!=0) {
				Nodo nuevo= new Nodo(probas[i],0,null,null,i);
				nodos.add(nuevo);
			}
		}
		Nodo raiz = this.CrearArbol(nodos).elementAt(0);
		this.SetCodification(raiz,"");
		
	}
	
	public void SetCodification(Nodo raiz,String codif)
	{
		if (raiz!=null)
		{
			SetCodification(raiz.getHijo1(),codif +"0");
			raiz.setCodif(codif);
			if (raiz.getValorRGB()>=0) {
				hash.put(raiz.getValorRGB(),codif);
				
			}
			SetCodification(raiz.getHijo2(),codif+"1");
		}
	}
	
	public Vector<Nodo> CrearArbol(Vector<Nodo> nodosIniciales)
	{
		Vector<Nodo> nodosAux = new Vector<>(nodosIniciales);
		Collections.sort(nodosAux);
		if (nodosAux.size()>1)
		{
			Nodo nodo1= nodosAux.elementAt(nodosAux.size()-1);
			Nodo nodo2= nodosAux.elementAt(nodosAux.size()-2);
			Nodo nuevo= new Nodo(nodo1.getProb()+nodo2.getProb(),0,nodo1,nodo2,-1);
			nuevo.incrementarCantRamas();
			nodosAux.removeElementAt(nodosAux.size()-1);
			nodosAux.removeElementAt(nodosAux.size()-1);
			nodosAux.addElement(nuevo);
			nodosAux=CrearArbol(nodosAux);
		}
		return nodosAux; //RETORNAMOS LA RAIZ DEL ARBOL
		
	}
	
	public String generarCodificacion (Imagen img) { //probar con static
		String resultado = "";
		char buffer =0;
		int cant_dig = 0;
		char[] codigo = null;
		for (int i = 0; i<img.getAlto(); i++) {
			for (int j = 0; j<img.getAncho(); j++) {
				 codigo = (this.hash.get(img.getValor(j,i))).toCharArray();
				 if (i == 69) {
					 System.out.println("antes de codificar en la col " + j + " habia un "+(img.getValor(j,i)));
				 }
				 for (char b: codigo) {
					 buffer = (char) (buffer<<1);
					 if (b == '1')
						buffer = (char) (buffer|1);
					cant_dig++;
					if (cant_dig ==8) {
						resultado =resultado +buffer;
						buffer=0;
						cant_dig = 0;
					}
				}
			}
		}	
		if ((cant_dig <8)&& (cant_dig!=0))
		{
			buffer = (char) (buffer<<(8-cant_dig));
			resultado = resultado + buffer;
		}

		//System.out.println(resultado);
		
		
		return resultado;  //guardar el resultado en un archivo
 	}
	
	public String  generarArchivoComprimido(Imagen img) {
		this.setHash(img); 
		String codif= this.generarCodificacion(img);
		try {
			
			File archivo = new File("archivos\\codificacion_de_"+img.getNombre() + ".txt");
			FileWriter escribir = new FileWriter(archivo, false);		

			String mn= img.getAlto()+ ","+img.getAncho()+System.getProperty("line.separator");
			String frec = img.getStringFrecuencias()+System.getProperty("line.separator");
			
			
			FileReader lector= new FileReader(archivo);
			BufferedReader buff = new BufferedReader(lector);
			escribir.write(mn);
			escribir.write(frec);
			//VER DE GUARDAR DE FORMA TAL QUE GUARDE TODO EL STRING PORQUE ESTA GUARDANDO SOLO UNA PARTE 
			escribir.write(codif);
			escribir.close();
			
			String l = buff.readLine();
			l = buff.readLine();
			l ="";
			String aux ="";
			while ((aux = buff.readLine())!=null) {
				l =l +aux;
			}
			return codif;
		}
		catch (Exception e) {
			System.out.println("Error al escribir");
		}
		return null;
	}
	
	public Integer getValorDeCodif(String cod) {
		if (hash.containsValue((String)cod)){
			Set<Integer> claves = hash.keySet();
			for (Integer i : claves ) {
				if (hash.get(i)== cod)
					return i;
			}
			return null;
		}
		return null;
		
	}

	
}

 