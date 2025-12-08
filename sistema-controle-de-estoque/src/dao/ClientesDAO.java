package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Clientes;

public class ClientesDAO {

    private final Connection conn;

    // construtor que inicializa a conexão com o banco de dados
    public ClientesDAO(Connection conn) {
        this.conn = conn;
    }

    public void salvar(Clientes cliente) {
        // define o comando sql
        String sql = "insert into clientes (nome, data_nascimento, email, cpf,"
                + " telefone, cep, endereco, numero, complemento, bairro, cidade, estado)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // prepara a declaração sql com os parametros do cliente
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // atribui os valores do cliente para cada parâmetro do sql
            stmt.setString(1, cliente.getNome());
            stmt.setObject(2, cliente.getDataNascimento());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getCpf());
            stmt.setString(5, cliente.getTelefone());
            stmt.setString(6, cliente.getCep());
            stmt.setString(7, cliente.getEndereco());
            stmt.setShort(8, cliente.getNumero());
            stmt.setString(9, cliente.getComplemento());
            stmt.setString(10, cliente.getBairro());
            stmt.setString(11, cliente.getCidade());
            stmt.setString(12, cliente.getEstado());
            // executa o comando sql
            stmt.executeUpdate();
            // exibe a mensagem em caso de sucesso
            JOptionPane.showMessageDialog(null, "Cliente salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException erro) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao salvar o cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Clientes buscarCpfCliente(String cpf) {
        // define o comando sql
        String sql = "select * from clientes where cpf = ?";
        // prepara a declaração sql com o cpf como parâmetro
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, cpf);
            // executa a consulta sql e armazena o resultado
            ResultSet rs = stmt.executeQuery();
            // verifica se encontrou algum cliente
            if (rs.next()) {
                // cria e preenche um objeto Cliente com os dados retornados
                Clientes cliente = new Clientes();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setDataNascimento(rs.getDate("data_nascimento"));
                cliente.setEmail(rs.getString("email"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setCep(rs.getString("cep"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setNumero(rs.getShort("numero"));
                cliente.setComplemento(rs.getString("complemento"));
                cliente.setBairro(rs.getString("bairro"));
                cliente.setCidade(rs.getString("cidade"));
                cliente.setEstado(rs.getString("estado"));
                
                return cliente;
            }
        } catch (SQLException e) {
            // exibe a mensagem caso ocorra um erro na consulta ao banco
            JOptionPane.showMessageDialog(null, "Cliente não cadastrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        return null;
    }
    
    public Clientes buscarNomeCliente(String nome) {
        String sql = "select * from clientes where nome = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                Clientes cliente = new Clientes();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setDataNascimento(rs.getDate("data_nascimento"));
                cliente.setEmail(rs.getString("email"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setCep(rs.getString("cep"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setNumero(rs.getShort("numero"));
                cliente.setComplemento(rs.getString("complemento"));
                cliente.setBairro(rs.getString("bairro"));
                cliente.setCidade(rs.getString("cidade"));
                cliente.setEstado(rs.getString("estado"));
                
                return cliente;
            }
        } catch (SQLException e) {            
        }
        return null;
    }

    public List<Clientes> listar() {
        List<Clientes> lista = new ArrayList<>();
        // define o comando sql
        String sql = "select * from clientes";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            // executa a consulta e armazena o resultado 
            ResultSet rs = stmt.executeQuery();
            // itera sobre o resultado e preencha a lista com os clientes
            while (rs.next()) {
                Clientes cliente = new Clientes();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setDataNascimento(rs.getDate("data_nascimento"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setCep(rs.getString("cep"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setNumero(rs.getShort("numero"));
                cliente.setComplemento(rs.getString("complemento"));
                cliente.setBairro(rs.getString("bairro"));
                cliente.setCidade(rs.getString("cidade"));
                cliente.setEstado(rs.getString("estado"));
                // adiciona o cliente na lista
                lista.add(cliente);
            }

        } catch (SQLException e) {
            // exibe a mensagem caso ocorra um erro na consulta ao banco
            JOptionPane.showMessageDialog(null, "Erro ao criar a lista de clientes." + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

    public List<Clientes> filtrar(String nome) {
        List<Clientes> lista = new ArrayList<>();
        // define o comando sql
        String sql = "select * from clientes where nome like ?";
        // prepara a declaração sql pegando o nome como parâmetro
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, nome);
            // executa a consulta e armazena o resultado
            ResultSet rs = stmt.executeQuery();
            // itera sobre o resultado e preenche a lista com os clientes encontrados
            while (rs.next()) {
                Clientes cliente = new Clientes();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setDataNascimento(rs.getDate("data_nascimento"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setCep(rs.getString("cep"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setNumero(rs.getShort("numero"));
                cliente.setComplemento(rs.getString("complemento"));
                cliente.setBairro(rs.getString("bairro"));
                cliente.setCidade(rs.getString("cidade"));
                cliente.setEstado(rs.getString("estado"));
                // adiciona o cliente na lista
                lista.add(cliente);
            }

        } catch (SQLException e) {
            // exibe a mensagem caso ocorra um erro na consulta ao banco
            JOptionPane.showMessageDialog(null, "Erro ao criar a lista de clientes.");
        }
        return lista;
    }

    public void editar(Clientes cliente) {
        // define o comando sql
        String sql = "update clientes set nome = ?, data_nascimento = ?, email = ?, "
                + "cpf = ?, telefone = ?, cep = ?, endereco = ?, numero = ?, "
                + "complemento = ?, bairro = ?, cidade = ?, estado = ? where id = ?";
        // prepara a declarção sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // atribui os valores para cada parâmetro do comando sql
            stmt.setString(1, cliente.getNome());
            stmt.setObject(2, cliente.getDataNascimento());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getCpf());
            stmt.setString(5, cliente.getTelefone());
            stmt.setString(6, cliente.getCep());
            stmt.setString(7, cliente.getEndereco());
            stmt.setShort(8, cliente.getNumero());
            stmt.setString(9, cliente.getComplemento());
            stmt.setString(10, cliente.getBairro());
            stmt.setString(11, cliente.getCidade());
            stmt.setString(12, cliente.getEstado());
            stmt.setInt(13, cliente.getId());
            // executa o comando sql
            stmt.executeUpdate();
            // exibe mensagem em caso de sucesso
            JOptionPane.showMessageDialog(null, "Cliente editado com sucesso!");
        } catch (SQLException erro) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao editar o cliente!");
        }
    }

    public void excluir(Clientes cliente) {
        // define o comando sql
        String sql = "delete from clientes where id = ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getId());
            // executa o comando sql
            stmt.executeUpdate();
            // exibe a mensagem em caso de sucesso
            JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao excluir o cliente!");
        }
    }
    
}
