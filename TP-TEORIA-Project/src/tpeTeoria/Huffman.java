package tpeTeoria;

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
				Nodo nuevo= new Nodo(probas[i],0,null,null);
				noditos.add(nuevo);
			}
		}
		Nodo raiz = this.CrearArbol(noditos);
		for (int i=0;i<noditos.size();i++)
		{
			System.out.println("la cantidad de llamas en " + i + " es " + noditos.elementAt(i).getCantRamas());
		}
		this.SetCodification(raiz,"");
		
		for (int i=0;i<noditos.size();i++)
		{
			hash.put((Integer) i,noditos.elementAt(i).getCodif());
		}
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
			Nodo nuevo= new Nodo(nodito1.getProb()+nodito2.getProb(),maximoRama,nodito1,nodito2);
			nuevo.incrementarCantRamas();
			noditos.removeElementAt(noditos.size()-1);
			noditos.removeElementAt(noditos.size()-1);
			noditos.addElement(nuevo);
			CrearArbol(noditos);
			System.out.println("NO LLEGUESSSSSSSSSS OR FAVOR");
			return null;
		}
		else
		{
			return noditos.elementAt(0); //RETORNAMOS LA RAIZ DEL ARBOL
		}
	}
}
