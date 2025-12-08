package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Pecas;
import model.PecasPedidos;

public class PecasPedidosDAO {
    
    private final Connection conn;
    
    public PecasPedidosDAO(Connection conn) {
        this.conn = conn;
    }
    
    public void salvar(PecasPedidos pecasPedidos) {
        String sql = "insert into pecas_pedidos (id_peca, id_pedido, quantidade, subtotal)"
                + "values (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pecasPedidos.getPeca().getId());
            stmt.setInt(2, pecasPedidos.getPedido().getId());
            stmt.setInt(3, pecasPedidos.getQuantidade());
            stmt.setDouble(4, pecasPedidos.getSubtotal());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar os itens do pedido!");
        }
    }
    
    public List<PecasPedidos> buscarItensPorPedido(int idPedido) {
        List<PecasPedidos> lista = new ArrayList<>();
        String sql = "select * from pecas_pedidos where id_pedido = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                PecasPedidos item = new PecasPedidos();
                Pecas peca = new Pecas();
                
                peca.setId(rs.getInt("id_peca"));
                item.setPeca(peca);
                
                item.setQuantidade(rs.getInt("quantidade"));
                item.setSubtotal(rs.getDouble("subtotal"));
                
                lista.add(item);
            }            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar itens do pedido." + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }
    
}
