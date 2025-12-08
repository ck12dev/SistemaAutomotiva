package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Fornecedores;
import model.Pedidos;
import model.Pedidos.Status;

public class PedidosDAO {

    private final Connection conn;

    // construtor que inicializa a conexão com o banco de dados
    public PedidosDAO(Connection conn) {
        this.conn = conn;
    }

    // salva o pedido no banco de dados
    public void salvar(Pedidos pedido) {
        String sql = "insert into pedidos (id_fornecedor, data, valor_total, status)"
                + "values (?, ?, ?, ?)";
        // prepara a declaração sql
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // atribui os valores do pedido para cada parâmetro do sql
            stmt.setInt(1, pedido.getFornecedor().getId());
            stmt.setObject(2, pedido.getData());
            stmt.setDouble(3, pedido.getValorTotal());
            stmt.setString(4, pedido.getStatus().name());
            // executa o comando sql
            stmt.executeUpdate();
            // exibe a mensagem em caso de sucesso
            JOptionPane.showMessageDialog(null, "Pedido efetuado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // exibe a mensagem em caso de erro
            JOptionPane.showMessageDialog(null, "Erro ao fazer o pedido!" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public List<Pedidos> listarPendentes() {
        List<Pedidos> lista = new ArrayList<>();
        String sql = "select p.*, f.nome as nome_fornecedor from pedidos p "
                + "inner join fornecedores f on p.id_fornecedor = f.id where p.status = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Pedidos.Status.PENDENTE.name());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Pedidos pedido = new Pedidos();
                pedido.setId(rs.getInt("id"));
                
                Fornecedores fornecedor = new Fornecedores();
                fornecedor.setRazaoSocial(rs.getString("nome_fornecedor"));
                pedido.setFornecedor(fornecedor);
                
                pedido.setData(rs.getTimestamp("data"));
                pedido.setValorTotal(rs.getDouble("valor_total"));
                pedido.setStatus(Pedidos.Status.valueOf(rs.getString("status")));
                
                lista.add(pedido);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar a lista de pedidos pendentes.\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }
    
    public List<Pedidos> listarConcluidos() {
        List<Pedidos> lista = new ArrayList<>();
        String sql = "select p.*, f.nome as nome_fornecedor from pedidos p "
                + "inner join fornecedores f on p.id_fornecedor = f.id where p.status = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Pedidos.Status.CONCLUIDO.name());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Pedidos pedido = new Pedidos();
                pedido.setId(rs.getInt("id"));
                
                Fornecedores fornecedor = new Fornecedores();
                fornecedor.setRazaoSocial(rs.getString("nome_fornecedor"));
                pedido.setFornecedor(fornecedor);
                
                pedido.setData(rs.getTimestamp("data"));
                pedido.setValorTotal(rs.getDouble("valor_total"));
                pedido.setStatus(Pedidos.Status.valueOf(rs.getString("status")));
                
                lista.add(pedido);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar a lista de pedidos concluídos. \n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }   
    
    public List<Pedidos> filtrarPedidosPendentesFornecedor(String nome) {
        List<Pedidos> listaPedidosPendentes = new ArrayList<>();
        String sql = "select p.*, f.nome as nome_fornecedor from pedidos p inner "
                + "join fornecedores f on (p.id_fornecedor = f.id) where f.nome like ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Pedidos pedido = new Pedidos();
                pedido.setId(rs.getInt("id"));
                
                int idFornecedor = rs.getInt("id_fornecedor");
                FornecedoresDAO fornecedoresDao = new FornecedoresDAO(conn);
                Fornecedores fornecedor = fornecedoresDao.buscarIdFornecedor(idFornecedor);
                pedido.setFornecedor(fornecedor);
                
                pedido.setData(rs.getDate("data"));
                
                String status = rs.getString("status");
                pedido.setStatus(Status.valueOf(status));
                pedido.setValorTotal(rs.getDouble("valor_total"));
                
                listaPedidosPendentes.add(pedido);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao filtrar pedidos por fornecedor.\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return listaPedidosPendentes;
    }

    public int retornaUltimoIdVenda() {
        int ultimoId = 0;
        String sql = "select max(id) id from pedidos";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ultimoId = rs.getInt("id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o último id do pedido!" + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return ultimoId;
    }
    
    public boolean setConcluido(int idPedido, Pedidos.Status novoStatus) {
        String sql = "update pedidos set status = ? where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoStatus.name());
            stmt.setInt(2, idPedido);
            int linhasAlteradas = stmt.executeUpdate();
            return linhasAlteradas > 0;            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o status do pedido." + e, "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
