package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionOracle;
import exeption.WmsException;
import model.Fornecedor;

public class ControllerFornecedor {

	static public boolean verificarExistenciaCnpj(String cnpjFornecedor) {
		Connection conexao = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			conexao = ConexionOracle.conectar();
			String sql = "SELECT 1 FROM fornecedores WHERE CNPJ = ?";
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setString(1, cnpjFornecedor);
			resultSet = preparedStatement.executeQuery();

			return resultSet.next();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.printf("\nErro ao verificar existência do CNPJ.\n\n");
			return false;
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}
	}

	public void inserirFornecedor(Fornecedor fornecedor) {

		if (verificarExistenciaCnpj(fornecedor.getCnpj())) {
			throw new WmsException("\nO CNPJ inserido já está sendo utilizado.");
		}

		Connection conexao = null;
		PreparedStatement preparedStatement = null;

		try {

			conexao = ConexionOracle.conectar();
			String sql = "INSERT INTO FORNECEDORES (CNPJ, NOME_FANTASIA, RAZAO_SOCIAL, ENDEERECO_FORNECEDOR, RAMO_ATIVIDADE) VALUES (?, ?, ?, ?, ?)";
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setString(1, fornecedor.getCnpj());
			preparedStatement.setString(2, fornecedor.getNome_fantasia());
			preparedStatement.setString(3, fornecedor.getRazao_social());
			preparedStatement.setString(4, fornecedor.getEndereco());
			preparedStatement.setString(5, fornecedor.getRamo_atividade());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.printf("\nErro ao inserir fornecedor.\n\n");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}
	}

	public List<Fornecedor> buscarFornecedores() {

		Connection conexao = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Fornecedor> fornecedores = new ArrayList<>();

		try {
			conexao = ConexionOracle.conectar();
			String sql = "SELECT CNPJ, NOME_FANTASIA, RAZAO_SOCIAL, ENDEERECO_FORNECEDOR, RAMO_ATIVIDADE FROM FORNECEDORES";
			preparedStatement = conexao.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setCnpj(resultSet.getString("CNPJ"));
				fornecedor.setNome_fantasia(resultSet.getString("NOME_FANTASIA"));
				fornecedor.setRazao_social(resultSet.getString("RAZAO_SOCIAL"));
				fornecedor.setEndereco(resultSet.getString("ENDEERECO_FORNECEDOR"));
				fornecedor.setRamo_atividade(resultSet.getString("RAMO_ATIVIDADE"));
				fornecedores.add(fornecedor);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.printf("\nErro ao buscar fornecedores.\n");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}

		return fornecedores;
	}

	public boolean verificarReferenciasFornecedor(String cnpjFornecedor) {
		Connection conexao = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			conexao = ConexionOracle.conectar();

			String sqlOutraTabela = "SELECT 1 FROM ENTRADAS_ESTOQUE WHERE FORNECEDOR_CNPJ = ?";
			preparedStatement = conexao.prepareStatement(sqlOutraTabela);
			preparedStatement.setString(1, cnpjFornecedor);
			resultSet = preparedStatement.executeQuery();

			return resultSet.next();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.printf("\nErro ao verificar referências do fornecedor.\n");
			return false;
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}
	}

	public void removerFornecedor(String cnpjFornecedor) {
		if (verificarReferenciasFornecedor(cnpjFornecedor)) {
			System.out.printf("\nNão é possível excluir o item. Existem dependencias relacionadas a ele.\n\n");
			return;
		}

		if (!verificarExistenciaCnpj(cnpjFornecedor)) {
			throw new WmsException("\nO CNPJ inserido não está cadastrado.\n");
		}

		Connection conexao = null;
		PreparedStatement preparedStatement = null;

		try {
			conexao = ConexionOracle.conectar();
			String sql = "DELETE FROM FORNECEDORES WHERE CNPJ = ?";
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setString(1, cnpjFornecedor);
			preparedStatement.executeUpdate();

			System.out.printf("\nFornecedor removido com sucesso.\n\n");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.printf("\nErro ao remover fornecedor.\n");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}
	}

	public void atualizarFornecedor(Fornecedor fornecedor) {
		String cnpj = fornecedor.getCnpj();
		if (!verificarExistenciaCnpj(cnpj)) {
			throw new WmsException("\nO CNPJ inserido não está cadastrado.\n");
		}

		Connection conexao = null;
		PreparedStatement preparedStatement = null;

		try {
			conexao = ConexionOracle.conectar();
			String sql = "UPDATE FORNECEDORES SET NOME_FANTASIA = ?, RAZAO_SOCIAL = ?, ENDEERECO_FORNECEDOR = ?, RAMO_ATIVIDADE = ? WHERE CNPJ = ?";
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setString(1, fornecedor.getNome_fantasia());
			preparedStatement.setString(2, fornecedor.getRazao_social());
			preparedStatement.setString(3, fornecedor.getEndereco());
			preparedStatement.setString(4, fornecedor.getRamo_atividade());
			preparedStatement.setString(5, cnpj);
			preparedStatement.executeUpdate();

			System.out.printf("\nFornecedor atualizado com sucesso.\n");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.printf("\nErro ao editar fornecedor.\n");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}
	}

	static public int contarRegistrosFornecedores() {
		Connection conexao = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		int quantidadeTotalFornecedores = 0;

		try {
			conexao = ConexionOracle.conectar();
			String sql = "SELECT COUNT(*) AS QUANTIDADE_TOTAL FROM FORNECEDORES";
			preparedStatement = conexao.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				quantidadeTotalFornecedores = resultSet.getInt("QUANTIDADE_TOTAL");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WmsException("Erro ao obter quantidade total de fornecedores.");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}

		return quantidadeTotalFornecedores;
	}

}
