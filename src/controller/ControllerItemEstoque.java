package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionOracle;
import model.Item;
import model.ItemEstoque;
import model.Localizacao;

public class ControllerItemEstoque {

	public static void inserirEntrada(ItemEstoque itemEstoque) {

		Connection conexao = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			conexao = ConexionOracle.conectar();

			String verificarSql = "SELECT QUANTIDADE_EM_ESTOQUE FROM ITEM_ESTOQUE WHERE CODIGO_ITEM = ? AND LOCALIZACAO_ID = ?";
			preparedStatement = conexao.prepareStatement(verificarSql);
			preparedStatement.setInt(1, itemEstoque.getItem().getCodigo());
			preparedStatement.setInt(2, ControllerLocalizacao.obterIdLocalizacao(itemEstoque.getLocalizacao()));
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int quantidadeAtual = resultSet.getInt("QUANTIDADE_EM_ESTOQUE");
				int novaQuantidade = quantidadeAtual + itemEstoque.getQuantidade_estoque();

				String updateSql = "UPDATE ITEM_ESTOQUE SET QUANTIDADE_EM_ESTOQUE = ? WHERE CODIGO_ITEM = ? AND LOCALIZACAO_ID = ?";
				preparedStatement = conexao.prepareStatement(updateSql);
				preparedStatement.setInt(1, novaQuantidade);
				preparedStatement.setInt(2, itemEstoque.getItem().getCodigo());
				preparedStatement.setInt(3, ControllerLocalizacao.obterIdLocalizacao(itemEstoque.getLocalizacao()));
				preparedStatement.executeUpdate();
			} else {
				String insertSql = "INSERT INTO ITEM_ESTOQUE (CODIGO_ITEM, LOCALIZACAO_ID, QUANTIDADE_EM_ESTOQUE) VALUES (?, ?, ?)";
				preparedStatement = conexao.prepareStatement(insertSql);
				preparedStatement.setInt(1, itemEstoque.getItem().getCodigo());
				preparedStatement.setInt(2, ControllerLocalizacao.obterIdLocalizacao(itemEstoque.getLocalizacao()));
				preparedStatement.setInt(3, itemEstoque.getQuantidade_estoque());
				preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao inserir entrada no estoque.");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}
	}

	public List<ItemEstoque> buscarItensEstoque() {
		Connection conexao = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<ItemEstoque> itensEstoque = new ArrayList<>();

		try {
			conexao = ConexionOracle.conectar();
			String sql = "SELECT E.CODIGO_ITEM, E.LOCALIZACAO_ID, I.NOME_ITEM, I.TIPO_ITEM, I.DESCRICAO_ITEM, I.VOLUME, E.QUANTIDADE_EM_ESTOQUE "
					+ "FROM ITEM_ESTOQUE E " + "JOIN ITENS I ON E.CODIGO_ITEM = I.CODIGO_ITEM";
			preparedStatement = conexao.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int codigoItem = resultSet.getInt("CODIGO_ITEM");
				int localizacaoId = resultSet.getInt("LOCALIZACAO_ID");
				String nomeItem = resultSet.getString("NOME_ITEM");
				String tipoItem = resultSet.getString("TIPO_ITEM");
				String descricaoItem = resultSet.getString("DESCRICAO_ITEM");
				double volume = resultSet.getDouble("VOLUME");
				int quantidadeEstoque = resultSet.getInt("QUANTIDADE_EM_ESTOQUE");

				Item item = new Item(codigoItem, nomeItem, tipoItem, descricaoItem, volume);
				Localizacao localizacao = ControllerLocalizacao.obterLocalizacaoPorId(localizacaoId);

				ItemEstoque itemEstoque = new ItemEstoque(localizacao, item, quantidadeEstoque);
				itensEstoque.add(itemEstoque);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao buscar itens no estoque.");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}

		return itensEstoque;
	}

	static public int contarRegistrosEstoque() {
		Connection conexao = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int quantidadeRegistros = 0;

		try {
			conexao = ConexionOracle.conectar();
			String sql = "SELECT COUNT(*) AS TOTAL_REGISTROS FROM ITEM_ESTOQUE";
			preparedStatement = conexao.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				quantidadeRegistros = resultSet.getInt("TOTAL_REGISTROS");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao contar registros no estoque.");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}

		return quantidadeRegistros;
	}

}
