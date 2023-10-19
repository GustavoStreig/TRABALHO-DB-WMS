package model;

public class ItemEstoque {
	
	private Localizacao localizacao;
	private Item item;
	
	private int estoque_id;
	private int quantidade_estoque;
	
	public ItemEstoque(Localizacao localizacao, Item item, int quantidade_estoque) {
		this.localizacao = localizacao;
		this.item = item;
		this.quantidade_estoque = quantidade_estoque;
	}
	public Localizacao getLocalizacao() {
		return localizacao;
	}
	public void setLocalizacao(Localizacao localizacao) {
		this.localizacao = localizacao;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public int getEstoque_id() {
		return estoque_id;
	}
	public void setEstoque_id(int estoque_id) {
		this.estoque_id = estoque_id;
	}
	public int getQuantidade_estoque() {
		return quantidade_estoque;
	}
	public void setQuantidade_estoque(int quantidade_estoque) {
		this.quantidade_estoque = quantidade_estoque;
	}
	@Override
	public String toString() {
		return "" + localizacao.imprimirLocalizacao() + ", " + item.imprimirCodigoNome()
				+ ", quantidade no endereço:" + quantidade_estoque;
	}
	
	
}
