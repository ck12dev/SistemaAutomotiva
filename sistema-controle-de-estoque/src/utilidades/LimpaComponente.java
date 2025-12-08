package utilidades;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class LimpaComponente {

    // limpa todos os campos de texto e combo boxes de um painel
    public void limparCampos(Container container) {
        Component componentes[] = container.getComponents();
        for (Component componente : componentes) {
            if (componente instanceof JTextField jTextField) {
                jTextField.setText("");
            }
            if (componente instanceof JComboBox jComboBox) {
                jComboBox.setSelectedIndex(0);
            }   
            if (componente instanceof JTextArea jTextArea) {
                jTextArea.setText("");
            }
            if (componente instanceof Container subContainer) {
                limparCampos(subContainer);
            }
        }
    }

    // remove todas as linhas de uma tabela, mantendo apenas as colunas
    public void limparTabela(JTable tabela) {
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setRowCount(0);
    }
}
