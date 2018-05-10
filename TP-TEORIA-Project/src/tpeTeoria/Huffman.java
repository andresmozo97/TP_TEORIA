package tpeTeoria;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

public class Huffman {

	private Hashtable<Integer,String> hash;
	
	public Huffman() {
		hash=new Hashtable<Integer,String>();
	}
	
	public void setHash(Imagen img)
	{
		Double [] probas = img.getDistr();
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

