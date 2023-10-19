package model;

public class Localizacao {
	private int setor;
	private int corredor;
	private int prateleira;
	private int nivel;
	
	public Localizacao() {
	}
	
	public Localizacao(int setor, int corredor, int prateleira, int nivel) {
		this.setor = setor;
		this.corredor = corredor;
		this.prateleira = prateleira;
		this.nivel = nivel;
	}

	public int getSetor() {
		return setor;
	}

	public void setSetor(int setor) {
		this.setor = setor;
	}

	public int getCorredor() {
		return corredor;
	}

	public void setCorredor(int corredor) {
		this.corredor = corredor;
	}

	public int getPrateleira() {
		return prateleira;
	}

	public void setPrateleira(int prateleira) {
		this.prateleira = prateleira;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	@Override
	public String toString() {
		return "Localizacao setor: " + setor + ", corredor: " + corredor + ", prateleira: " + prateleira + ", nivel: "
				+ nivel;
	}
	
	public String imprimirLocalizacao() {
		return "Endereco: " + setor + corredor + prateleira + nivel;
	}
	

}