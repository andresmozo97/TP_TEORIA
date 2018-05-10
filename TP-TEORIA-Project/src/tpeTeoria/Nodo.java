package tpeTeoria;

public class Nodo implements Comparable<Nodo> {

	private Double prob;
	private int cantRamas;
	private Nodo hijo1;
	private Nodo hijo2;
	private Integer valorRGB;

	private String codif;
	
	public Nodo(Double prob, int cantRamas, Nodo hijo1, Nodo hijo2, Integer valorRGB) {
		super();
		this.prob = prob;
		this.cantRamas = cantRamas;
		this.hijo1 = hijo1;
		this.hijo2 = hijo2;
		this.valorRGB = valorRGB;
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
		return hijo1;
	}
	public void setHijoArio(Nodo hijoArio) {
		this.hijo1 = hijoArio;
	}
	public Nodo getHijo() {
		return hijo2;
	}
	public void setHijo(Nodo hijo) {
		this.hijo2 = hijo;
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
		if (hijo2!=null && hijo1!=null)
		{
			hijo2.incrementarCantRamas();
			hijo1.incrementarCantRamas();
		}
	}
	
	public Integer getValorRGB() {
		return valorRGB;
	}

	public void setValorRGB(Integer valorRGB) {
		this.valorRGB = valorRGB;
	}

	@Override
	public int compareTo(Nodo n) {
		return (this.prob).compareTo(n.getProb());
	}
	
	
}
