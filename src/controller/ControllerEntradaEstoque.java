package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.ConexionOracle;
import exeption.WmsException;
import model.EntradaEstoque;
import model.Item;
import model.ItemEstoque;
import model.Localizacao;

public class ControllerEntradaEstoque {

	public void inserirEntrada(EntradaEstoque entradaEstoque, Localizacao localizacao) {

		if (!ControllerLocalizacao.verificarExistenciaLocalizacao(localizacao)) {
			throw new WmsException("\nA localização inserida não existe!");
		}

		if (!ControllerFornecedor.verificarExistenciaCnpj(entradaEstoque.getFornecedor_cnpj())) {
			throw new WmsException("\nO CNPJ não está cadastrado!");
		}

		if (!ControllerItem.verificarExistenciaCodigoItem(entradaEstoque.getCodigo_item())) {
			throw new WmsException("\nO Item não está cadastrado!");
		}

		Connection conexao = null;
		PreparedStatement preparedStatement = null;

		try {
			conexao = ConexionOracle.conectar();
			String sql = "INSERT INTO ENTRADAS_ESTOQUE (CODIGO_ITEM, NUMERO_LOTE, QUANTIDADE, DATA_ENTRADA, FORNECEDOR_CNPJ) VALUES (?, ?, ?, ?, ?)";
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setInt(1, entradaEstoque.getCodigo_item());
			preparedStatement.setString(2, entradaEstoque.getNumero_lote());
			preparedStatement.setInt(3, entradaEstoque.getQuantidade());

			Date dataEntrada = Date.valueOf(entradaEstoque.getData_entrada());
			preparedStatement.setDate(4, dataEntrada);

			preparedStatement.setString(5, entradaEstoque.getFornecedor_cnpj());
			preparedStatement.executeUpdate();

			Item item = ControllerItem.buscarItemPorCodigo(entradaEstoque.getCodigo_item());
			ItemEstoque itemEstoque = new ItemEstoque(localizacao, item, entradaEstoque.getQuantidade());
			ControllerItemEstoque.inserirEntrada(itemEstoque);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao inserir entrada no estoque.");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}

	}

	static public int contarRegistrosEntradaEstoque() {
		Connection conexao = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		int quantidadeTotalEstoque = 0;

		try {
			conexao = ConexionOracle.conectar();
			String sql = "SELECT COUNT(*) AS QUANTIDADE_TOTAL FROM ITEM_ESTOQUE";
			preparedStatement = conexao.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				quantidadeTotalEstoque = resultSet.getInt("QUANTIDADE_TOTAL");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WmsException("Erro ao obter quantidade total de itens no estoque.");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}

		return quantidadeTotalEstoque;
	}

}
