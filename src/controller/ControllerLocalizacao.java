package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionOracle;
import exeption.WmsException;
import model.Localizacao;

public class ControllerLocalizacao {

	static public boolean verificarExistenciaLocalizacao(Localizacao localizacao) {

		Connection conexao = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			conexao = ConexionOracle.conectar();
			String sql = "SELECT * FROM Localizacoes WHERE Setor = ? AND Corredor = ? AND Prateleira = ? AND Nivel = ?";
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setInt(1, localizacao.getSetor());
			preparedStatement.setInt(2, localizacao.getCorredor());
			preparedStatement.setInt(3, localizacao.getPrateleira());
			preparedStatement.setInt(4, localizacao.getNivel());

			resultSet = preparedStatement.executeQuery();

			return resultSet.next();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.printf("\nErro ao verificar a existência da posição da prateleira\n");
			return false;
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}
	}

	public void inserirLocalizacao(Localizacao localizacao) {

		if (verificarExistenciaLocalizacao(localizacao)) {
			throw new WmsException("\nA localização inserida já existe.");
		}

		Connection conexao = null;
		PreparedStatement preparedStatement = null;

		try {

			conexao = ConexionOracle.conectar();
			String sql = "INSERT INTO localizacoes (SETOR, CORREDOR, PRATELEIRA, NIVEL) VALUES (?, ?, ?, ?)";
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setInt(1, localizacao.getSetor());
			preparedStatement.setInt(2, localizacao.getCorredor());
			preparedStatement.setInt(3, localizacao.getPrateleira());
			preparedStatement.setInt(4, localizacao.getNivel());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.printf("\nErro ao sinserir localização.\n");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}
	}

	static public int obterIdLocalizacao(Localizacao localizacao) {

		Connection conexao = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			conexao = ConexionOracle.conectar();
			String sql = "SELECT LOCALIZACAO_ID FROM LOCALIZACOES WHERE SETOR = ? AND CORREDOR = ? AND PRATELEIRA = ? AND NIVEL = ?";
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setInt(1, localizacao.getSetor());
			preparedStatement.setInt(2, localizacao.getCorredor());
			preparedStatement.setInt(3, localizacao.getPrateleira());
			preparedStatement.setInt(4, localizacao.getNivel());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return resultSet.getInt("LOCALIZACAO_ID");
			} else {
				return -1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.printf("\nErro ao obter ID da localização.\n");
			return -1;
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}
	}

	public List<Localizacao> buscarLocalizacoes() {
		Connection conexao = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Localizacao> localizacoes = new ArrayList<>();

		try {
			conexao = ConexionOracle.conectar();
			String sql = "SELECT LOCALIZACAO_ID, SETOR, CORREDOR, PRATELEIRA, NIVEL FROM LOCALIZACOES";
			preparedStatement = conexao.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Localizacao localizacao = new Localizacao();
				localizacao.setSetor(resultSet.getInt("SETOR"));
				localizacao.setCorredor(resultSet.getInt("CORREDOR"));
				localizacao.setPrateleira(resultSet.getInt("PRATELEIRA"));
				localizacao.setNivel(resultSet.getInt("NIVEL"));
				localizacoes.add(localizacao);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.printf("\nErro ao buscar localizações.\n");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}

		return localizacoes;
	}

	public static Localizacao obterLocalizacaoPorId(int localizacaoId) {
		Connection conexao = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			conexao = ConexionOracle.conectar();
			String sql = "SELECT SETOR, CORREDOR, PRATELEIRA, NIVEL FROM LOCALIZACOES WHERE LOCALIZACAO_ID = ?";
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setInt(1, localizacaoId);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int setor = resultSet.getInt("SETOR");
				int corredor = resultSet.getInt("CORREDOR");
				int prateleira = resultSet.getInt("PRATELEIRA");
				int nivel = resultSet.getInt("NIVEL");

				return new Localizacao(setor, corredor, prateleira, nivel);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.printf("\nErro ao buscar localização no banco de dados.\n");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}

		return null;
	}

	public boolean verificarReferenciasLocalizacao(int localizacaoId) {
		Connection conexao = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			conexao = ConexionOracle.conectar();

			String sqlOutraTabela = "SELECT 1 FROM ITEM_ESTOQUE WHERE LOCALIZACAO_ID = ?";
			preparedStatement = conexao.prepareStatement(sqlOutraTabela);
			preparedStatement.setInt(1, localizacaoId);
			resultSet = preparedStatement.executeQuery();

			return resultSet.next();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.printf("\nErro ao verificar referências da localização.\n");
			return false;
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}
	}

	public void removerLocalizacao(int localizacaoId) {

		if (verificarReferenciasLocalizacao(localizacaoId)) {
			System.out.printf("\nNão é possível excluir o item. Existem dependencias relacionadas a ele.\n");
			return;
		}

		if (!verificarExistenciaLocalizacao(obterLocalizacaoPorId(localizacaoId))) {
			throw new WmsException("\nA localização inserida não está cadastrada.\n");
		}

		Connection conexao = null;
		PreparedStatement preparedStatement = null;

		try {
			conexao = ConexionOracle.conectar();
			String sql = "DELETE FROM LOCALIZACOES WHERE LOCALIZACAO_ID = ?";
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setInt(1, localizacaoId);
			preparedStatement.executeUpdate();

			System.out.printf("\nLocalização removida com sucesso.\n\n");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.printf("\nErro ao remover localização.\n\n");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}
	}

	public void atualizarLocalizacao(Localizacao localizacao, int idLocalizacao) {

		if (!verificarExistenciaLocalizacao(obterLocalizacaoPorId(idLocalizacao))) {
			throw new WmsException("\nA localização inserida não está cadastrada.");
		}

		if (verificarExistenciaLocalizacao(localizacao)) {
			throw new WmsException("\nJá existe uma localização igual a que foi inserida.");
		}

		Connection conexao = null;
		PreparedStatement preparedStatement = null;

		try {
			conexao = ConexionOracle.conectar();
			String sql = "UPDATE LOCALIZACOES SET SETOR = ?, CORREDOR = ?, PRATELEIRA = ?, NIVEL = ? WHERE LOCALIZACAO_ID = ?";
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setInt(1, localizacao.getSetor());
			preparedStatement.setInt(2, localizacao.getCorredor());
			preparedStatement.setInt(3, localizacao.getPrateleira());
			preparedStatement.setInt(4, localizacao.getNivel());
			preparedStatement.setInt(5, idLocalizacao);
			preparedStatement.executeUpdate();

			System.out.printf("\nLocalização atualizada com sucesso.\n");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao editar localização.");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}
	}

	static public int contarRegistrosLocalizacoes() {
		Connection conexao = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int quantidadeRegistros = 0;

		try {
			conexao = ConexionOracle.conectar();
			String sql = "SELECT COUNT(*) AS TOTAL_REGISTROS FROM LOCALIZACOES";
			preparedStatement = conexao.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				quantidadeRegistros = resultSet.getInt("TOTAL_REGISTROS");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao contar registros de localizações.");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}

		return quantidadeRegistros;
	}

}
