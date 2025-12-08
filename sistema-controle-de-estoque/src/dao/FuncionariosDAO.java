package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Cargos;
import model.Funcionarios;

public class FuncionariosDAO {

    private final Connection conn;    

    // construtor que inicializa a conexão com o banco de dados
    public FuncionariosDAO(Connection conn) {
        this.conn = conn;
    }

    public void salvar(Funcionarios funcionario) {
        // define o comando sql
        String sql = "insert into funcionarios (id_cargo, nome, data_nascimento, "
                + "email, cpf, telefone, cep, endereco, numero, complemento, bairro, "
                + "cidade, estado, senha, nivel_acesso) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {   
            // atribui os valores do funcionário para cada parâmetro do sql
            stmt.setInt(1, funcionario.getCargo().getId());
            stmt.setString(2, funcionario.getNome());
            stmt.setObject(3, funcionario.getDataNascimento());
            stmt.setString(4, funcionario.getEmail());
            stmt.setString(5, funcionario.getCpf());
            stmt.setString(6, funcionario.getTelefone());
            stmt.setString(7, funcionario.getCep());
            stmt.setString(8, funcionario.getEndereco());
            stmt.setShort(9, funcionario.getNumero());
            stmt.setString(10, funcionario.getComplemento());
            stmt.setString(11, funcionario.getBairro());
            stmt.setString(12, funcionario.getCidade());
            stmt.setString(13, funcionario.getEstado());
            stmt.setString(14, funcionario.getSenha());
            stmt.setString(15, funcionario.getNivelAcesso().name());
            // executa o comando sql
            stmt.executeUpdate();
            // exibe a mensagem em caso de sucesso
            JOptionPane.showMessageDialog(null, "Funcionário salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao salvar o funcionário! " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Funcionarios buscarFuncionario(String cpf) {
        // define o comando sql
        String sql = "select * from funcionarios where cpf = ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            // executa a consulta e armazena o resultado
            ResultSet rs = stmt.executeQuery();
            Funcionarios funcionario = new Funcionarios();
            // verifica se encontrou algum funcionário
            if (rs.next()) {
                funcionario.setId(rs.getInt("id"));
                // busca o nome do cargo correspondente ao id
                int idCargo = rs.getInt("id_cargo");
                CargosDAO cargosDao = new CargosDAO(conn);
                Cargos cargo = cargosDao.buscarIdCargo(idCargo);
                funcionario.setCargo(cargo);
                
                funcionario.setNome(rs.getString("nome"));
                funcionario.setDataNascimento(rs.getDate("data_nascimento"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setCpf(rs.getString("cpf"));
                funcionario.setTelefone(rs.getString("telefone"));
                funcionario.setCep(rs.getString("cep"));
                funcionario.setEndereco(rs.getString("endereco"));
                funcionario.setNumero(rs.getShort("numero"));
                funcionario.setComplemento(rs.getString("complemento"));
                funcionario.setBairro(rs.getString("bairro"));
                funcionario.setCidade(rs.getString("cidade"));
                funcionario.setEstado(rs.getString("estado"));
                funcionario.setSenha(rs.getString("senha"));

                String nivelAcesso = rs.getString("nivel_acesso");
                try {
                    funcionario.setNivelAcesso(Funcionarios.NivelAcesso.valueOf(nivelAcesso));
                } catch (IllegalArgumentException e) {
                    funcionario.setNivelAcesso(Funcionarios.NivelAcesso.COMUM);
                }
            }
            return funcionario;

        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Funcionário não cadastrado! " + e.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        return null;
    }

    public List<Funcionarios> listar() {
        List<Funcionarios> lista = new ArrayList<>();
        // define o comando sql
        String sql = "select f.*, c.nome as cargo_nome from funcionarios f inner join cargos c on f.id_cargo = c.id";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // executa a consulta e armazena o resultado
            ResultSet rs = stmt.executeQuery();
            // itera sobre o resultado e preenche a lista com o funcionarios
            while (rs.next()) {
                Funcionarios funcionario = new Funcionarios();
                funcionario.setId(rs.getInt("id"));
                // instancia um cargo com o nome retornado da consulta
                Cargos cargo = new Cargos();
                cargo.setNome(rs.getString("cargo_nome"));
                funcionario.setCargo(cargo);

                funcionario.setNome(rs.getString("nome"));
                funcionario.setDataNascimento(rs.getDate("data_nascimento"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setCpf(rs.getString("cpf"));
                funcionario.setTelefone(rs.getString("telefone"));
                funcionario.setCep(rs.getString("cep"));
                funcionario.setEndereco(rs.getString("endereco"));
                funcionario.setNumero(rs.getShort("numero"));
                funcionario.setComplemento(rs.getString("complemento"));
                funcionario.setBairro(rs.getString("bairro"));
                funcionario.setCidade(rs.getString("cidade"));
                funcionario.setEstado(rs.getString("estado"));
                funcionario.setSenha(rs.getString("senha"));
                String nivelAcesso = rs.getString("nivel_acesso");

                try {
                    funcionario.setNivelAcesso(Funcionarios.NivelAcesso.valueOf(nivelAcesso));
                } catch (IllegalArgumentException e) {
                    funcionario.setNivelAcesso(Funcionarios.NivelAcesso.COMUM);
                }
                // adiciona o funcionario na lista
                lista.add(funcionario);
            }

        } catch (Exception e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao criar a lista de funcionários.\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }
    
    public List<Funcionarios> filtrar(String nome) {
        List<Funcionarios> lista = new ArrayList<>();
        // define o comando sql
        String sql = "select * from funcionarios where nome like ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            // executa a consulta e armazena o resultado
            ResultSet rs = stmt.executeQuery();
            // itera sobre o resultado e preenche a lista com o funcionarios
            while (rs.next()) {
                Funcionarios funcionario = new Funcionarios();
                funcionario.setId(rs.getInt("id"));
                // busca o nome do cargo correspondente ao id
                int idCargo = rs.getInt("id_cargo");
                CargosDAO cargosDao = new CargosDAO(conn);
                Cargos cargo = cargosDao.buscarIdCargo(idCargo);
                funcionario.setCargo(cargo);
                
                funcionario.setNome(rs.getString("nome"));
                funcionario.setDataNascimento(rs.getDate("data_nascimento"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setCpf(rs.getString("cpf"));
                funcionario.setTelefone(rs.getString("telefone"));
                funcionario.setCep(rs.getString("cep"));
                funcionario.setEndereco(rs.getString("endereco"));
                funcionario.setNumero(rs.getShort("numero"));
                funcionario.setComplemento(rs.getString("complemento"));
                funcionario.setBairro(rs.getString("bairro"));
                funcionario.setCidade(rs.getString("cidade"));
                funcionario.setEstado(rs.getString("estado"));
                funcionario.setSenha(rs.getString("senha"));
                String nivelAcesso = rs.getString("nivel_acesso");
                
                try {
                    funcionario.setNivelAcesso(Funcionarios.NivelAcesso.valueOf(nivelAcesso));
                } catch (IllegalArgumentException e) {
                    funcionario.setNivelAcesso(Funcionarios.NivelAcesso.COMUM);
                }
                // adiciona o funcionario na lista
                lista.add(funcionario);
            }
            
        } catch (Exception e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao encontrar o cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }
    
    public void editar(Funcionarios funcionario) {
        // define o comando sql
        String sql = "update funcionarios set id_cargo = ?, nome = ?, data_nascimento = ?, email = ?,"
                + "cpf = ?, telefone = ?, cep = ?, endereco = ?, numero = ?, complemento = ?,"
                + "bairro = ?, cidade = ?, estado = ?, senha = ?, nivel_acesso = ? where id = ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // atribui os valores para cada parâmetro do comando sql
            stmt.setInt(1, funcionario.getCargo().getId());
            stmt.setString(2, funcionario.getNome());
            stmt.setObject(3, funcionario.getDataNascimento());
            stmt.setString(4, funcionario.getEmail());
            stmt.setString(5, funcionario.getCpf());
            stmt.setString(6, funcionario.getTelefone());
            stmt.setString(7, funcionario.getCep());
            stmt.setString(8, funcionario.getEndereco());
            stmt.setShort(9, funcionario.getNumero());
            stmt.setString(10, funcionario.getComplemento());
            stmt.setString(11, funcionario.getBairro());
            stmt.setString(12, funcionario.getCidade());
            stmt.setString(13, funcionario.getEstado());
            stmt.setString(14, funcionario.getSenha());
            stmt.setString(15, funcionario.getNivelAcesso().name());
            stmt.setInt(16, funcionario.getId());
            // executa o comando sql
            stmt.executeUpdate();
            // exibe a mensagem em caso de sucesso
            JOptionPane.showMessageDialog(null, "Funcionário editado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);            
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao editar o funcionário!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void excluir(Funcionarios funcionario) {
        // define o comando sql
        String sql = "delete from funcionarios where id = ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, funcionario.getId());
            //executa o comando sql
            stmt.executeUpdate();
            // exibe a mensagem em caso de sucesso
            JOptionPane.showMessageDialog(null, "Funcionário excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao excluir o funcionário!", "Erro", JOptionPane.ERROR_MESSAGE);
        }        
    }
    
    public boolean login(String email, String senha) {
        // define o comando sql
        String sql = "select * from funcionarios where email = ? and senha = ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {            
            stmt.setString(1, email);
            stmt.setString(2, senha);
            // executa a consulta e armazena o resultado
            ResultSet rs = stmt.executeQuery();
            // verifica se a conta existe
            if (rs.next()) {
                return true;
           }            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
