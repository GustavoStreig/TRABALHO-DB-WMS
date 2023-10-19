package model;

public class Item {

	private int codigo;
	private String nome;
	private String tipo;
	private String descricao;
	private Double volume;

	public Item() {
	}
	
	public Item(int codigo, String nome, String tipo, String descricao, Double volume) {
		this.codigo = codigo;
		this.nome = nome;
		this.tipo = tipo;
		this.descricao = descricao;
		this.volume = volume;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	@Override
	public String toString() {
		return "codigo: " + codigo + ", nome: " + nome + ", tipo: " + tipo + ", descricao: " + descricao + ", volume: "
				+ volume;
	}

	public String imprimirCodigoNome() {
		return "Codigo do item:" + codigo + ", nome=" + nome;
	}

	
	
	
	
}
