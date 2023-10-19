package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.ConexionOracle;
import exeption.WmsException;
import model.SaidaEstoque;

public class ControllerSaidaEstoque {

	public void inserirSaidaEstoque(SaidaEstoque saidaEstoque) {
		Connection conexao = null;
		PreparedStatement preparedStatement = null;

		if (saidaEstoque.getQuantidade() <= 0) {
			throw new WmsException("\nQuantidade de saída inválida.");
		}

		if (!ControllerItem.verificarExistenciaCodigoItem(saidaEstoque.getCodigoItem())) {
			throw new WmsException("\nO código do item inserido não está cadastrado.");
		}

		try {
			conexao = ConexionOracle.conectar();
			String verificarSql = "SELECT QUANTIDADE_EM_ESTOQUE FROM ITEM_ESTOQUE WHERE CODIGO_ITEM = ? AND LOCALIZACAO_ID = ?";
			preparedStatement = conexao.prepareStatement(verificarSql);
			preparedStatement.setInt(1, saidaEstoque.getCodigoItem());
			preparedStatement.setInt(2, saidaEstoque.getOrigemLocalizacao());
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int quantidadeAtual = resultSet.getInt("QUANTIDADE_EM_ESTOQUE");
				int quantidadeSaida = saidaEstoque.getQuantidade();

				if (quantidadeSaida <= quantidadeAtual) {
					int novaQuantidade = quantidadeAtual - quantidadeSaida;

					if (novaQuantidade == 0) {
						String deleteSql = "DELETE FROM ITEM_ESTOQUE WHERE CODIGO_ITEM = ? AND LOCALIZACAO_ID = ?";
						preparedStatement = conexao.prepareStatement(deleteSql);
						preparedStatement.setInt(1, saidaEstoque.getCodigoItem());
						preparedStatement.setInt(2, saidaEstoque.getOrigemLocalizacao());
						preparedStatement.executeUpdate();
					} else {
						String updateSql = "UPDATE ITEM_ESTOQUE SET QUANTIDADE_EM_ESTOQUE = ? WHERE CODIGO_ITEM = ? AND LOCALIZACAO_ID = ?";
						preparedStatement = conexao.prepareStatement(updateSql);
						preparedStatement.setInt(1, novaQuantidade);
						preparedStatement.setInt(2, saidaEstoque.getCodigoItem());
						preparedStatement.setInt(3, saidaEstoque.getOrigemLocalizacao());
						preparedStatement.executeUpdate();
					}

					String insertSql = "INSERT INTO SAIDAS_ESTOQUE (CODIGO_ITEM, QUANTIDADE, DATA_SAIDA, ORIGEM_LOCALIZACAO, DESTINO) VALUES (?, ?, ?, ?, ?)";
					preparedStatement = conexao.prepareStatement(insertSql);
					preparedStatement.setInt(1, saidaEstoque.getCodigoItem());
					preparedStatement.setInt(2, quantidadeSaida);
					preparedStatement.setDate(3, java.sql.Date.valueOf(saidaEstoque.getDataSaida()));
					preparedStatement.setInt(4, saidaEstoque.getOrigemLocalizacao());
					preparedStatement.setString(5, saidaEstoque.getDestino());
					preparedStatement.executeUpdate();

				} else {
					throw new WmsException("\nQuantidade a ser removida é maior do que a quantidade em estoque.");
				}
			} else {
				throw new WmsException("\nItem não encontrado na localização de origem.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new WmsException("Erro ao inserir saída de estoque.");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}
	}

	static public int contarRegistrosSaidasEstoque() {
		Connection conexao = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int quantidadeRegistros = 0;

		try {
			conexao = ConexionOracle.conectar();
			String sql = "SELECT COUNT(*) AS TOTAL_REGISTROS FROM SAIDAS_ESTOQUE";
			preparedStatement = conexao.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				quantidadeRegistros = resultSet.getInt("TOTAL_REGISTROS");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao contar registros de saídas de estoque.");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}

		return quantidadeRegistros;
	}

}
