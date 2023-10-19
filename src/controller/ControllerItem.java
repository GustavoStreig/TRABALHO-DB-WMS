package controller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionOracle;
import exeption.WmsException;
import model.Item;

public class ControllerItem {
	
	
	static public boolean verificarExistenciaCodigoItem(int codigoItem) {
		Connection conexao = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			conexao = ConexionOracle.conectar();
			String sql = "SELECT 1 FROM itens WHERE CODIGO_ITEM = ?";
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setInt(1, codigoItem);
			resultSet = preparedStatement.executeQuery();

			return resultSet.next();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.printf("\nErro ao verificar existência do código do item.\n");
			return false;
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}
	}
	

	public void inserirItem(Item item) {

		if (verificarExistenciaCodigoItem(item.getCodigo())) {
			throw new WmsException("\nO código inserido já está sendo utilizado.");
		}

		Connection conexao = null;
		PreparedStatement preparedStatement = null;

		try {

			conexao = ConexionOracle.conectar();
			String sql = "INSERT INTO itens (CODIGO_ITEM, NOME_ITEM, TIPO_ITEM, DESCRICAO_ITEM, VOLUME) VALUES (?, ?, ?, ?, ?)";
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setInt(1, item.getCodigo());
			preparedStatement.setString(2, item.getNome());
			preparedStatement.setString(3, item.getTipo());
			preparedStatement.setString(4, item.getDescricao());
			preparedStatement.setDouble(5, item.getVolume());
			preparedStatement.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.printf("\nErro ao inserir item.\n");
		} finally {
			ConexionOracle.fecharConexao(conexao);
		}
	}
	

	public List<Item> buscarItens() {
	        Connection conexao = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;
	        List<Item> itens = new ArrayList<>();

	        try {
	            conexao = ConexionOracle.conectar();
	            String sql = "SELECT CODIGO_ITEM, NOME_ITEM, TIPO_ITEM, DESCRICAO_ITEM, VOLUME FROM ITENS";
	            preparedStatement = conexao.prepareStatement(sql);
	            resultSet = preparedStatement.executeQuery();

	            while (resultSet.next()) {
	                Item item = new Item();
	                item.setCodigo(resultSet.getInt("CODIGO_ITEM"));
	                item.setNome(resultSet.getString("NOME_ITEM"));
	                item.setTipo(resultSet.getString("TIPO_ITEM"));
	                item.setDescricao(resultSet.getString("DESCRICAO_ITEM"));
	                item.setVolume(resultSet.getDouble("VOLUME"));
	                itens.add(item);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("Erro ao buscar itens.");
	        } finally {
	            ConexionOracle.fecharConexao(conexao);
	        }

	        return itens;
	    }

	public List<Item> buscarItensPorFornecedor(String cnpjFornecedor) {
		
		if (!ControllerFornecedor.verificarExistenciaCnpj(cnpjFornecedor)) {
			throw new WmsException("\nO CNPJ inserido não está cadatrado.\n\n");
		}

	    Connection conexao = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    List<Item> itens = new ArrayList<>();

	    try {
	        conexao = ConexionOracle.conectar();
	        String sql = "SELECT DISTINCT I.CODIGO_ITEM, I.NOME_ITEM, I.TIPO_ITEM, I.DESCRICAO_ITEM, I.VOLUME " +
	                     "FROM ITENS I " +
	                     "JOIN ENTRADAS_ESTOQUE E ON I.CODIGO_ITEM = E.CODIGO_ITEM " +
	                     "JOIN FORNECEDORES F ON E.FORNECEDOR_CNPJ = F.CNPJ " +
	                     "WHERE F.CNPJ = ?";
	        preparedStatement = conexao.prepareStatement(sql);
	        preparedStatement.setString(1, cnpjFornecedor);
	        resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            Item item = new Item();
	            item.setCodigo(resultSet.getInt("CODIGO_ITEM"));
	            item.setNome(resultSet.getString("NOME_ITEM"));
	            item.setTipo(resultSet.getString("TIPO_ITEM"));
	            item.setDescricao(resultSet.getString("DESCRICAO_ITEM"));
	            item.setVolume(resultSet.getDouble("VOLUME"));
	            itens.add(item);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.printf("\nErro ao buscar itens do fornecedor.\n\n");
	    } finally {
	        ConexionOracle.fecharConexao(conexao);
	    }

	    return itens;
	}
    
    static public Item buscarItemPorCodigo(int codigoItem) {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Item item = null;

        try {
            conexao = ConexionOracle.conectar();
            String sql = "SELECT CODIGO_ITEM, NOME_ITEM, TIPO_ITEM, DESCRICAO_ITEM, VOLUME FROM ITENS WHERE CODIGO_ITEM = ?";
            preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setInt(1, codigoItem);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                item = new Item();
                item.setCodigo(resultSet.getInt("CODIGO_ITEM"));
                item.setNome(resultSet.getString("NOME_ITEM"));
                item.setTipo(resultSet.getString("TIPO_ITEM"));
                item.setDescricao(resultSet.getString("DESCRICAO_ITEM"));
                item.setVolume(resultSet.getDouble("VOLUME"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.printf("\nErro ao buscar item por código.\n\n");
        } finally {
            ConexionOracle.fecharConexao(conexao);
        }

        return item;
    }
    
    public boolean verificarReferenciasItem(int codigoItem) {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conexao = ConexionOracle.conectar();

            String sqlOutraTabela = "SELECT 1 FROM ENTRADAS_ESTOQUE WHERE CODIGO_ITEM = ?";
            preparedStatement = conexao.prepareStatement(sqlOutraTabela);
            preparedStatement.setInt(1, codigoItem);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.printf("\nErro ao verificar referências do item.\n\n");
            return false;
        } finally {
            ConexionOracle.fecharConexao(conexao);
        }
    }

    public void removerItem(int codigoItem) {
    
    	if (!verificarExistenciaCodigoItem(codigoItem)) {
			throw new WmsException("\nO código inserido não está cadastrado.\n");
		}
    	
        if (verificarReferenciasItem(codigoItem)) {
            throw new WmsException("\nNão é possível excluir o item. Existem dependencias relacionadas a ele.");
        }

        Connection conexao = null;
        PreparedStatement preparedStatement = null;

        try {
            conexao = ConexionOracle.conectar();
            String sql = "DELETE FROM ITENS WHERE CODIGO_ITEM = ?";
            preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setInt(1, codigoItem);
            preparedStatement.executeUpdate();

            System.out.printf("\nItem removido com sucesso.\n\n");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.printf("\nErro ao remover item.\n\n");
        } finally {
            ConexionOracle.fecharConexao(conexao);
        }
    }
    
    public void atualizarItem(Item item) {
 
        Connection conexao = null;
        PreparedStatement preparedStatement = null;

        try {
            conexao = ConexionOracle.conectar();
            String sql = "UPDATE ITENS SET NOME_ITEM = ?, TIPO_ITEM = ?, DESCRICAO_ITEM = ?, VOLUME = ? WHERE CODIGO_ITEM = ?";
            preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, item.getNome());
            preparedStatement.setString(2, item.getTipo());
            preparedStatement.setString(3, item.getDescricao());
            preparedStatement.setDouble(4, item.getVolume());
            preparedStatement.setInt(5, item.getCodigo());
            preparedStatement.executeUpdate();

            System.out.printf("\nItem atualizado com sucesso.\n\n");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao editar item.");
        } finally {
            ConexionOracle.fecharConexao(conexao);
        }
    }
    
    static public int contarRegistroslItens() {
        Connection conexao = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int quantidadeTotalItens = 0;

        try {
            conexao = ConexionOracle.conectar();
            String sql = "SELECT COUNT(*) AS QUANTIDADE_TOTAL FROM ITENS";
            preparedStatement = conexao.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                quantidadeTotalItens = resultSet.getInt("QUANTIDADE_TOTAL");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WmsException("Erro ao obter quantidade total de itens.");
		} finally {
			ConexionOracle.fecharConexao(conexao);
        }

        return quantidadeTotalItens;
    }
    
}