package model;

import java.time.LocalDate;

public class EntradaEstoque {

	private int codigo;
	private int codigo_item;
	private String numero_lote;
	private int quantidade;
	private LocalDate data_entrada;
	private String fornecedor_cnpj;
	
	
	public EntradaEstoque() {
	}
	
	public EntradaEstoque(int codigo_item, String numero_lote, int quantidade, LocalDate data_entrada,
			String fornecedor_cnpj) {
		this.codigo_item 
		= codigo_item;
		this.numero_lote = numero_lote;
		this.quantidade = quantidade;
		this.data_entrada = data_entrada;
		this.fornecedor_cnpj = fornecedor_cnpj;
	
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigo_item() {
		return codigo_item;
	}

	public void setCodigo_item(int codigo_item) {
		this.codigo_item = codigo_item;
	}

	public String getNumero_lote() {
		return numero_lote;
	}

	public void setNumero_lote(String numero_lote) {
		this.numero_lote = numero_lote;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public LocalDate getData_entrada() {
		return data_entrada;
	}

	public void setData_entrada(LocalDate data_entrada) {
		this.data_entrada = data_entrada;
	}

	public String getFornecedor_cnpj() {
		return fornecedor_cnpj;
	}

	public void setFornecedor_cnpj(String fornecedor_cnpj) {
		this.fornecedor_cnpj = fornecedor_cnpj;
	}




	@Override
	public String toString() {
		return "EntradaEstoque [codigo_item=" + codigo_item + ", numero_lote=" + numero_lote + ", quantidade="
				+ quantidade + ", data_entrada=" + data_entrada + ", fornecedor_cnpj=" + fornecedor_cnpj
				+ ", localizacao=";
	}
	
	

}
