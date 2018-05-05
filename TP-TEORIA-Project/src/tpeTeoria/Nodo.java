package tpeTeoria;

public class Nodo implements Comparable<Nodo> {

	private Double prob;
	private int cantRamas;
	private Nodo hijoArio;
	private Nodo hijo;

	private String codif;
	
	public Nodo(Double prob, int cantRamas, Nodo hijoArio, Nodo hijo) {
		super();
		this.prob = prob;
		this.cantRamas = cantRamas;
		this.hijoArio = hijoArio;
		System.out.println("HIJO LLAMA");
		this.hijo = hijo;
	}
	
	public Double getProb() {
		return prob;
	}
	public void setProb(Double prob) {
		this.prob = prob;
	}
	public int getCantRamas() {
		return cantRamas;
	}
	public void setCantRamas(int cantRamas) {
		this.cantRamas = cantRamas;
	}
	public Nodo getHijoArio() {
		return hijoArio;
	}
	public void setHijoArio(Nodo hijoArio) {
		this.hijoArio = hijoArio;
	}
	public Nodo getHijo() {
		return hijo;
	}
	public void setHijo(Nodo hijo) {
		this.hijo = hijo;
	}
	public String getCodif() {
		return codif;
	}

	public void setCodif(String codif) {
		this.codif = codif;
	}

	public void incrementarCantRamas()
	{
		this.cantRamas++;
		if (hijo!=null && hijoArio!=null)
		{
			hijo.incrementarCantRamas();
			hijoArio.incrementarCantRamas();
		}
	}
	
	@Override
	public int compareTo(Nodo n) {
		return (this.prob).compareTo(n.getProb());
	}
	
	
}
