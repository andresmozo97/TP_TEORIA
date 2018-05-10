package tpeTeoria;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Vector;

public class Huffman {

	private Hashtable<Integer,String> hash;
	
	public Huffman() {
		hash=new Hashtable<Integer,String>();
	}
	
	public void setHash(Imagen img)
	{
		Double [] probas = img.getDistr();
		Vector<Nodo> noditos= new Vector<Nodo>();
		for (int i=0;i<probas.length;i++)
		{
			if (probas[i]!=0) {
				Nodo nuevo= new Nodo(probas[i],0,null,null,i);
				noditos.add(nuevo);
			}
		}
		Nodo raiz = this.CrearArbol(noditos);
		for (int i=0;i<noditos.size();i++)
		{
			System.out.println("la cantidad de ramas en " + i + " es " + noditos.elementAt(i).getCantRamas());
		}
		this.SetCodification(raiz,"");
		
		System.out.println("tiwanaku2");
		for (int i=0;i<hash.size();i++)
		{
			System.out.println("la codif de el numero " + i + " es " + hash.get(i));
		}
	}
	
	public void SetCodification(Nodo raiz,String codif)
	{
		if (raiz!=null)
		{
			if (raiz.getHijo()!=null)
			{
				SetCodification(raiz.getHijo(),codif +"0");
			}
			if (raiz.getHijoArio()!=null)
			{
				SetCodification(raiz.getHijoArio(),codif+"1");
			}
			else
			{
				raiz.setCodif(codif);
				hash.put(raiz.getValorRGB(),codif);
			}
		}
	}
	
	public Nodo CrearArbol(Vector<Nodo> noditos1)
	{
		Vector<Nodo> noditos = new Vector<>(noditos1);
		Collections.sort(noditos);
		
		if (noditos.size()>1)
		{
			Nodo nodito1= noditos.elementAt(noditos.size()-1);
			Nodo nodito2= noditos.elementAt(noditos.size()-2);
			int maximoRama = Math.max(nodito1.getCantRamas(), nodito2.getCantRamas());
			Nodo nuevo= new Nodo(nodito1.getProb()+nodito2.getProb(),maximoRama,nodito1,nodito2,-1);
			nuevo.incrementarCantRamas();
			noditos.removeElementAt(noditos.size()-1);
			noditos.removeElementAt(noditos.size()-1);
			noditos.addElement(nuevo);
			CrearArbol(noditos);
			System.out.println("NO LLEGUESSSSSSSSSS POR FAVOR");
			return null;
		}
		else
		{
			return noditos.elementAt(0); //RETORNAMOS LA RAIZ DEL ARBOL
		}
	}
	
	public String generarCodificacion (Imagen img) { //probar con static
		int cantbits = 0;
		String resultado = "";
		char buffer =0;
		int cant_dig = 0;
		char[] codigo = null;
		for (int i = 0; i<img.getAncho(); i++) {
			for (int j = 0; j<img.getAlto(); j++) {
				 codigo = this.hash.get(img.getValor(i, j)).toCharArray();
				 for (char bit: codigo) {
						buffer = (char) (buffer<<1);
						if (bit == '1')
							buffer = (char) (buffer|1);
						cantbits++;  //VER A QUIEN SE LE PASA
						cant_dig++;
						if (cant_dig ==16) {
							resultado +=buffer;
							buffer=0;
							cant_dig = 0;
						}
					}
				}
			}	
		if ((cant_dig <16)&& (cant_dig!=0))
		{
			buffer = (char) (buffer<<(16-cant_dig));
			resultado += buffer;
		}
		return resultado;  //guardar el resultado en un archivo
 	}
	
	
	
	
}

