package model;

public class Fornecedor {

	private String cnpj;
	private String nome_fantasia;
	private String razao_social;
	private String endereco;
	private String ramo_atividade;
	
	public Fornecedor() {
		
	}
	
	public Fornecedor(String cnpj, String nome_fantasia, String razao_social, String endereco, String ramo_atividade) {
		this.cnpj = cnpj;
		this.nome_fantasia = nome_fantasia;
		this.razao_social = razao_social;
		this.endereco = endereco;
		this.ramo_atividade = ramo_atividade;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNome_fantasia() {
		return nome_fantasia;
	}

	public void setNome_fantasia(String nome_fantasia) {
		this.nome_fantasia = nome_fantasia;
	}

	public String getRazao_social() {
		return razao_social;
	}

	public void setRazao_social(String razao_social) {
		this.razao_social = razao_social;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getRamo_atividade() {
		return ramo_atividade;
	}

	public void setRamo_atividade(String ramo_atividade) {
		this.ramo_atividade = ramo_atividade;
	}

	@Override
	public String toString() {
		return "CNPJ: " + cnpj + ", Razão social: " + razao_social + ", Ramo de atividade: " + ramo_atividade;
	}
	
	public String imprimirCnpjRazaoSocial() {
		return "CNPJ: " + cnpj + ", Razão social: " + razao_social + ", Ramo de atividade: " + ramo_atividade;
	}
}
