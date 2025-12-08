package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Cargos;

public class CargosDAO {

    private final Connection conn;

    // construtor que inicializa a conexão com o banco de dados
    public CargosDAO(Connection conn) {
        this.conn = conn;
    }

    public void salvar(Cargos cargo) {
        // define o comando sql
        String sql = "insert into cargos (nome) values (?)";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cargo.getNome());
            // executa o comando sql
            stmt.executeUpdate();
            // exibe a mensagem em caso de sucesso 
            JOptionPane.showMessageDialog(null, "Cargo salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao salvar o cargo. " + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Cargos> listar() {
        List<Cargos> lista = new ArrayList<>();
        // define o comando sql
        String sql = "select * from cargos";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // executa a consulta e armazena o resultado
            ResultSet rs = stmt.executeQuery();
            // itera sobre o resultado e preenche a lista com os cargos encontrados
            while (rs.next()) {
                Cargos cargo = new Cargos();
                cargo.setId(rs.getInt("id"));
                cargo.setNome(rs.getString("nome"));
                // adiciona o cargo na lista
                lista.add(cargo);
            }

        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao criar a lista de cargos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

    public void editar(Cargos cargo) {
        // define o comando sql
        String sql = "update cargos set nome = ? where id = ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // atribui os valores para cada parâmetro do comando sql
            stmt.setString(1, cargo.getNome());
            stmt.setInt(2, cargo.getId());
            // executa o comando sql
            stmt.executeUpdate();
            // exibe a mensagem em caso de sucesso            
            JOptionPane.showMessageDialog(null, "Cargo editado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao editar o cargo.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void excluir(Cargos cargo) {
        // define o comando sql
        String sql = "delete from cargos where id = ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cargo.getId());
            // executa o comando sql
            stmt.executeUpdate();
            // exibe a mensagem em caso de sucesso 
            JOptionPane.showMessageDialog(null, "Cargo excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao excluir o cargo. " + e);
        }
    }

    public Cargos buscarIdCargo(int id) {
        // define o comando sql
        String sql = "select * from cargos where id = ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            // executa a consulta e armazena o resultado
            ResultSet rs = stmt.executeQuery();
            // verifica se encontrou algum cargo
            if (rs.next()) {
                Cargos cargo = new Cargos();
                cargo.setId(rs.getInt("id"));
                cargo.setNome(rs.getString("nome"));
                return cargo;
            }
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao buscar cargo por ID. " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    public boolean cargoExiste(String nome) {
        // define o comando sql
        String sql = "select count(*) from cargos where nome = ?";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            // executa a consulta
            ResultSet rs = stmt.executeQuery();
            // verifica se já existe um cargo com o nome
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
        }
        return false;
    }

}
