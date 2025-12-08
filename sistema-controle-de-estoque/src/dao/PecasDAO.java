package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Fornecedores;
import model.Pecas;

public class PecasDAO {

    private final Connection conn;
    
    // construtor que inicializa a conexão com o banco de dados
    public PecasDAO(Connection conn) {
        this.conn = conn;
    }

    // salva a peça no banco de dados
    public void salvar(Pecas peca) {
        // define o comando sql
        String sql = "insert into pecas (id_fornecedor, nome, descricao, "
                + "quantidade, valor_unidade_fornecedor, valor_unidade_cliente)"
                + " values (?, ?, ?, ?, ?, ?)";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // atribui os valores da peça para cada parâmetro do sql
            stmt.setInt(1, peca.getFornecedor().getId());
            stmt.setString(2, peca.getNome());
            stmt.setString(3, peca.getDescricao());
            stmt.setShort(4, peca.getQuantidade());
            stmt.setDouble(5, peca.getValorUnidadeFornecedor());
            stmt.setDouble(6, peca.getValorUnidadeCliente());
            // executa o comando sql
            stmt.executeUpdate();
            // exibe a mensagem em caso de sucesso
            JOptionPane.showMessageDialog(null, "Peça salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao salvar a peça! " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void editar(Pecas peca) {
        // define o comando sql
        String sql = "update pecas set id_fornecedor = ?, nome = ?, descricao = ?, "
                + "quantidade = ?, valor_unidade_fornecedor = ?, "
                + "valor_unidade_cliente = ? where id = ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // atribui os valores para cada parâmetro do comando sql
            stmt.setInt(1, peca.getFornecedor().getId());
            stmt.setString(2, peca.getNome());
            stmt.setString(3, peca.getDescricao());
            stmt.setShort(4, peca.getQuantidade());
            stmt.setDouble(5, peca.getValorUnidadeFornecedor());
            stmt.setDouble(6, peca.getValorUnidadeCliente());
            stmt.setInt(7, peca.getId());
            // executa o comando sql
            stmt.executeUpdate();
            // exibe a mensagem em caso de sucesso
            JOptionPane.showMessageDialog(null, "Peça editada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao editar a peça!" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void excluir(Pecas peca) {
        // define o comando sql
        String sql = "delete from pecas where id = ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, peca.getId());
            // executa o comando sql
            stmt.executeUpdate();
            // exibe a mensagem em caso de sucesso
            JOptionPane.showMessageDialog(null, "Peça excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao excluir a peça!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public List<Pecas> listar() {
        List<Pecas> lista = new ArrayList<>();
        // define o comando sql
        String sql = "select p.*, f.nome as nome_fornecedor from pecas p inner join fornecedores f on p.id_fornecedor = f.id";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // executa a consulta e armazena o resultado
            ResultSet rs = stmt.executeQuery();
            // itera sobre o resultado e preenche a lista com as peças
            while (rs.next()) {
                Pecas peca = new Pecas();
                peca.setId(rs.getInt("id"));
                // instancia um fornecedor com o nome retornado da consulta
                Fornecedores fornecedor = new Fornecedores();
                fornecedor.setRazaoSocial(rs.getString("nome_fornecedor"));
                peca.setFornecedor(fornecedor);

                peca.setNome(rs.getString("nome"));
                peca.setDescricao(rs.getString("descricao"));
                peca.setQuantidade(rs.getShort("quantidade"));
                peca.setValorUnidadeCliente(rs.getDouble("valor_unidade_cliente"));
                peca.setValorUnidadeFornecedor(rs.getDouble("valor_unidade_fornecedor"));
                // adiciona a peça na lista
                lista.add(peca);
            }

        } catch (SQLException e) {
            // exibe a mensagem caso ocorra um erro na consulta ao banco
            JOptionPane.showMessageDialog(null, "Erro ao criar a lista de peças.\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

    public Pecas buscarPeca(String nome) {
        // define o comando sql
        String sql = "select * from pecas where nome = ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            // executa a consulta e armazena o resultado
            ResultSet rs = stmt.executeQuery();
            Pecas peca = new Pecas();
            // verifica se encontrou alguma peça
            if (rs.next()) {
                peca.setId(rs.getInt("id"));
                // busca o nome do fornecedor correspondente ao id
                int idFornecedor = rs.getInt("id_fornecedor");
                FornecedoresDAO fornecedoresDao = new FornecedoresDAO(conn);
                Fornecedores fornecedor = fornecedoresDao.buscarIdFornecedor(idFornecedor);
                peca.setFornecedor(fornecedor);

                peca.setNome(rs.getString("nome"));
                peca.setDescricao(rs.getString("descricao"));
                peca.setQuantidade(rs.getShort("quantidade"));
                peca.setValorUnidadeFornecedor(rs.getDouble("valor_unidade_fornecedor"));
                peca.setValorUnidadeCliente(rs.getDouble("valor_unidade_cliente"));
                
                return peca;
            }
        } catch (SQLException e) {
            // exibe a mensagem caso ocorra um erro na consulta ao banco
            JOptionPane.showMessageDialog(null, "Peça não cadastrada." + e.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        return null;
    }

    public List<Pecas> filtrar(String nome) {
        List<Pecas> lista = new ArrayList<>();
        // define o comando sql
        String sql = "select * from pecas where nome like ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            // executa a consulta e armazena o resultado
            ResultSet rs = stmt.executeQuery();
            // itera sobre o resultado e preenche a lista com as peças encontradas
            while (rs.next()) {
                Pecas peca = new Pecas();
                peca.setId(rs.getInt("id"));
                // busca o nome do fornecedor correspondente ao id
                int idFornecedor = rs.getInt("id_fornecedor");
                FornecedoresDAO fornecedorDao = new FornecedoresDAO(conn);
                Fornecedores fornecedor = fornecedorDao.buscarIdFornecedor(idFornecedor);
                peca.setFornecedor(fornecedor);

                peca.setNome(rs.getString("nome"));
                peca.setDescricao(rs.getString("descricao"));
                peca.setQuantidade(rs.getShort("quantidade"));
                peca.setValorUnidadeFornecedor(rs.getDouble("valor_unidade_fornecedor"));
                peca.setValorUnidadeCliente(rs.getDouble("valor_unidade_cliente"));
                // adiciona a peça na lista
                lista.add(peca);
            }

        } catch (SQLException e) {
            // exibe a mensagem caso ocorra um erro na consulta ao banco
            JOptionPane.showMessageDialog(null, "Erro ao criar a lista de peças." + e.getMessage());
        }
        return lista;
    }

    public void alterarEstoque(int id, int qtdNova) {
        // define o comando sql
        String sql = "update pecas set quantidade = ? where id = ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, qtdNova);
            stmt.setInt(2, id);
            // executa o comando sql
            stmt.executeUpdate();
            // exibe a mensagem em caso de sucesso
            JOptionPane.showMessageDialog(null, "Quantidade alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao alterar o estoque!\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public int retornaQuantidadeEstoque(int id) {
        int quantidadeAtual = 0;
        String sql = "select quantidade from pecas where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                quantidadeAtual = rs.getInt("quantidade");
            }
            return quantidadeAtual;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao retornar a quantidade atual do estoque." + e);
        }
    }

    public List<Pecas> filtrarPecasFornecedor(String nome) {
        List<Pecas> lista = new ArrayList<>();
        // define a declaração sql
        String sql = "select p.*, f.nome as nome_fornecedor from pecas p inner join "
                + "fornecedores f on (p.id_fornecedor = f.id) where f.nome like ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            // executa a consulta e armazena o resultado
            ResultSet rs = stmt.executeQuery();
            // itera sobre  o resultado e preenche a lista com as peças encontradas
            while (rs.next()) {
                Pecas peca = new Pecas();
                peca.setId(rs.getInt("id"));
                // busca o nome do fornecedor correspondente ao id
                int idFornecedor = rs.getInt("id_fornecedor");
                FornecedoresDAO fornecedorDao = new FornecedoresDAO(conn);
                Fornecedores fornecedor = fornecedorDao.buscarIdFornecedor(idFornecedor);
                peca.setFornecedor(fornecedor);

                peca.setNome(rs.getString("nome"));
                peca.setDescricao(rs.getString("descricao"));
                peca.setQuantidade(rs.getShort("quantidade"));
                peca.setValorUnidadeFornecedor(rs.getDouble("valor_unidade_fornecedor"));
                peca.setValorUnidadeCliente(rs.getDouble("valor_unidade_cliente"));
                // adiciona a peça na lista
                lista.add(peca);
            }

        } catch (SQLException e) {
            // exibe mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao filtrar peças por fornecedor.\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

}
