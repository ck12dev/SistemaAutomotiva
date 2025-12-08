package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Fornecedores;

public class FornecedoresDAO {

    private final Connection conn;

    // construtor que inicializa a conexão com o banco de dados
    public FornecedoresDAO(Connection conn) {
        this.conn = conn;
    }

    public void salvar(Fornecedores fornecedor) {
        // define o comando sql
        String sql = "insert into fornecedores (nome, email, cnpj, telefone, cep, "
                + "endereco, numero, complemento, bairro, cidade, estado)"
                + " values(?,?,?,?,?,?,?,?,?,?,?)";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // atribui os valores do fornecedor para cada parâmetro do sql
            stmt.setString(1, fornecedor.getRazaoSocial());
            stmt.setString(2, fornecedor.getEmail());
            stmt.setString(3, fornecedor.getCnpj());
            stmt.setString(4, fornecedor.getTelefone());
            stmt.setString(5, fornecedor.getCep());
            stmt.setString(6, fornecedor.getEndereco());
            stmt.setShort(7, fornecedor.getNumero());
            stmt.setString(8, fornecedor.getComplemento());
            stmt.setString(9, fornecedor.getBairro());
            stmt.setString(10, fornecedor.getCidade());
            stmt.setString(11, fornecedor.getEstado());
            // executa o comando sql
            stmt.executeUpdate();
            // exibe a mensagem em caso de sucesso
            JOptionPane.showMessageDialog(null, "Fornecedor salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao salvar o fornecedor! " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public Fornecedores buscarCnpjFornecedor(String cnpj) {
        // define o comando sql
        String sql = "select * from fornecedores where cnpj = ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            // executa a consulta e armazena o resultado
            ResultSet rs = stmt.executeQuery();
            // verifica se encontrou algum fornecedor
            if (rs.next()) {
                // cria e preenche um objeto fornecedor com os dados encontrados
                Fornecedores fornecedor = new Fornecedores();
                fornecedor.setId(rs.getInt("id"));
                fornecedor.setRazaoSocial(rs.getString("nome"));
                fornecedor.setEmail(rs.getString("email"));
                fornecedor.setCnpj(rs.getString("cnpj"));
                fornecedor.setTelefone(rs.getString("telefone"));
                fornecedor.setCep(rs.getString("cep"));
                fornecedor.setEndereco(rs.getString("endereco"));
                fornecedor.setNumero(rs.getShort("numero"));
                fornecedor.setComplemento(rs.getString("complemento"));
                fornecedor.setBairro(rs.getString("bairro"));
                fornecedor.setEstado(rs.getString("estado"));
                fornecedor.setCidade(rs.getString("cidade"));
                return fornecedor;
            }
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao encontrar o fornecedor. " + e.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        return null;

    }

    public List<Fornecedores> listar() {
        List<Fornecedores> lista = new ArrayList<>();
        // define o comando sql
        String sql = "select * from fornecedores";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // executa a consulta e armazena o resultado
            ResultSet rs = stmt.executeQuery();
            // itera sobre o resultado e preenche a lista com os fornecedores
            while (rs.next()) {
                Fornecedores fornecedor = new Fornecedores();
                fornecedor.setId(rs.getInt("id"));
                fornecedor.setRazaoSocial(rs.getString("nome"));
                fornecedor.setEmail(rs.getString("email"));
                fornecedor.setCnpj(rs.getString("cnpj"));
                fornecedor.setTelefone(rs.getString("telefone"));
                fornecedor.setCep(rs.getString("cep"));
                fornecedor.setEndereco(rs.getString("endereco"));
                fornecedor.setNumero(rs.getShort("numero"));
                fornecedor.setComplemento(rs.getString("complemento"));
                fornecedor.setBairro(rs.getString("bairro"));
                fornecedor.setCidade(rs.getString("cidade"));
                fornecedor.setEstado(rs.getString("estado"));
                // adiciona o fornecedor a lista
                lista.add(fornecedor);
            }
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao criar a lista! " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

    public List<Fornecedores> filtrar(String nome) {
        List<Fornecedores> lista = new ArrayList<>();
        // define o comando sql
        String sql = "select * from fornecedores where nome like ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            // executa a consulta e armazena o resultado
            ResultSet rs = stmt.executeQuery();
            // itera sobre o resultado e preenche a lista com os fornecedores
            while (rs.next()) {
                Fornecedores fornecedor = new Fornecedores();
                fornecedor.setId(rs.getInt("id"));
                fornecedor.setRazaoSocial(rs.getString("nome"));
                fornecedor.setEmail(rs.getString("email"));
                fornecedor.setCnpj(rs.getString("cnpj"));
                fornecedor.setTelefone(rs.getString("telefone"));
                fornecedor.setCep(rs.getString("cep"));
                fornecedor.setEndereco(rs.getString("endereco"));
                fornecedor.setNumero(rs.getShort("numero"));
                fornecedor.setComplemento(rs.getString("complemento"));
                fornecedor.setBairro(rs.getString("bairro"));
                fornecedor.setCidade(rs.getString("cidade"));
                fornecedor.setEstado(rs.getString("estado"));
                // adiciona o fornecedor a lista
                lista.add(fornecedor);
            }
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao criar a lista de fornecedores! " + e.getMessage());
        }
        return lista;
    }

    public void editar(Fornecedores fornecedor) {
        // define o comando sql
        String sql = "update fornecedores set nome = ?, email = ?, cnpj = ?, telefone = ?,"
                + "cep = ?, endereco = ?, numero = ?, complemento = ?, bairro = ?,"
                + "cidade = ?, estado = ? where id = ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // atribui os valores para cada parâmetro do comando sql
            stmt.setString(1, fornecedor.getRazaoSocial());
            stmt.setString(2, fornecedor.getEmail());
            stmt.setString(3, fornecedor.getCnpj());
            stmt.setString(4, fornecedor.getTelefone());
            stmt.setString(5, fornecedor.getCep());
            stmt.setString(6, fornecedor.getEndereco());
            stmt.setShort(7, fornecedor.getNumero());
            stmt.setString(8, fornecedor.getComplemento());
            stmt.setString(9, fornecedor.getBairro());
            stmt.setString(10, fornecedor.getCidade());
            stmt.setString(11, fornecedor.getEstado());
            stmt.setInt(12, fornecedor.getId());
            // executa o comando sql
            stmt.executeUpdate();
            // exibe a mensagem em caso de sucesso
            JOptionPane.showMessageDialog(null, "Fornecedor editado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao editar o fornecedor! " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void excluir(Fornecedores fornecedor) {
        // define o comando sql
        String sql = "delete from fornecedores where id = ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, fornecedor.getId());
            // executa o comando sql
            stmt.executeUpdate();
            // exibe a mensagem em caso de sucesso
            JOptionPane.showMessageDialog(null, "Fornecedor excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao excluir o fornecedor! " + e.getMessage());
        }

    }

    public Fornecedores buscarIdFornecedor(int id) {
        Fornecedores fornecedor = null;
        // define o comando sql
        String sql = "select * from fornecedores where id = ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            // executa a consulta e armazena o resultado
            ResultSet rs = stmt.executeQuery();
            // verifica se encontrou algum fornecedor
            if (rs.next()) {
                fornecedor = new Fornecedores();
                fornecedor.setId(rs.getInt("id"));
                fornecedor.setRazaoSocial(rs.getString("nome"));
            }
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao buscar fornecedor por ID. " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return fornecedor;
    }

    public Fornecedores buscarNomeFornecedor(String nome) {
        // define o comando sql
        String sql = "select * from fornecedores where nome = ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            // executa a consulta e armazena o resultado
            ResultSet rs = stmt.executeQuery();
            // verifica se encontrou algum fornecedor
            if (rs.next()) {
                Fornecedores fornecedor = new Fornecedores();
                fornecedor.setId(rs.getInt("id"));
                fornecedor.setRazaoSocial(rs.getString("nome"));
                fornecedor.setEmail(rs.getString("email"));
                fornecedor.setCnpj(rs.getString("cnpj"));
                fornecedor.setTelefone(rs.getString("telefone"));
                fornecedor.setCep(rs.getString("cep"));
                fornecedor.setEndereco(rs.getString("endereco"));
                fornecedor.setNumero(rs.getShort("numero"));
                fornecedor.setComplemento(rs.getString("complemento"));
                fornecedor.setBairro(rs.getString("bairro"));
                fornecedor.setEstado(rs.getString("estado"));
                fornecedor.setCidade(rs.getString("cidade"));
                return fornecedor;
            }
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao encontrar o fornecedor. " + e.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        return null;

    }

}
