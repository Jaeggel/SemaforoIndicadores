/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    public static DefaultTableModel modelo=null;
    ImageIcon icon;
    String cabecera[]={"N°","Objetivo Estratégico","Meta","Precaución","Nivel Actual","Semáforo"};
    public Principal() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);  
        insertImage(panelMain,"/Img/img.jpg",816,490);
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Img/logo.png"));
        setIconImage(icon);
        this.setTitle("Evaluador de Objetivos v1.0");
        String datos[][]={};
        modelo=new DefaultTableModel(datos,cabecera);
        tablaIndicadores.setModel(modelo); 
        llenarTabla();
        cambio();
        genSemaforo();
        editarCabecera();
        
    }
    public void llenarTabla()
    {
        String[][] data={ {"1", "Incrementar las ventas", "100","60","200",null}, 
                        {"2","Aprovechamiento de los recursos", "10","6","12",null}, 
                        {"3","Calidez en la atención", "10,1","6,2","7,5",null}, 
                        {"4","Rapidez en la atención", "6","3","0",null}, 
                        {"5","Ofrecer más variedad al menú", "5","2","0",null}, 
                        {"6","Ofrecer precios razonables", "100","20","0",null}, 
                        {"7","Calidad del producto/plato", "10,5","6","20",null}, 
                        {"8","Mejorar el servicio al cliente", "100","60","59,8",null}, 
                        {"9","Mejorar el proceso de entrega o despacho de platos", "10","5","8",null}, 
                        {"10","Mejorar el proceso de preparación de platos", "9","8","8,5",null}, 
                        {"11","Retención del talento humano", "3","2","2,5",null}};
        
        modelo=new DefaultTableModel(data,cabecera);
        tablaIndicadores.setModel(modelo);
        TableColumnModel colMdl = tablaIndicadores.getColumnModel();
        colMdl.getColumn(0).setPreferredWidth(10);
        colMdl.getColumn(1).setPreferredWidth(275);
        colMdl.getColumn(2).setPreferredWidth(65);
        colMdl.getColumn(3).setPreferredWidth(65);
        colMdl.getColumn(4).setPreferredWidth(65);
        colMdl.getColumn(5).setPreferredWidth(30);
        
    }
    public void cambio()
    {
        try
        {
            InputMap im = tablaIndicadores.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap am = tablaIndicadores.getActionMap();
        KeyStroke enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

        im.put(enterKey, "Action.enter");
        am.put("Action.enter", new AbstractAction() {
        public void actionPerformed(ActionEvent evt) {
                double vE=Double.parseDouble(modelo.getValueAt(tablaIndicadores.getSelectedRow(),2).toString().replace(",","."));
                double vM=Double.parseDouble(modelo.getValueAt(tablaIndicadores.getSelectedRow(),3).toString().replace(",","."));
                tablaIndicadores.changeSelection(tablaIndicadores.getSelectedRow(), 4, false, false);
                if(Double.parseDouble(modelo.getValueAt(tablaIndicadores.getSelectedRow(),4).toString().replace(",","."))<vM)
                {
                    //MALO
                    ImageIcon icon = new ImageIcon(getClass().getResource("/Img/red.png"));
                    genImage(tablaIndicadores.getSelectedRow(), icon);
                    JOptionPane.showMessageDialog(null, "El Nivel Actual del Objetivo se ha Actualizado...\nAhora su Nivel es Malo");
                }else if(Double.parseDouble(modelo.getValueAt(tablaIndicadores.getSelectedRow(),4).toString().replace(",","."))>=vM && Double.parseDouble(modelo.getValueAt(tablaIndicadores.getSelectedRow(),4).toString().replace(",","."))<vE)
                {
                    //REGULAR
                    ImageIcon icon = new ImageIcon(getClass().getResource("/Img/yellow.png"));
                    genImage(tablaIndicadores.getSelectedRow(), icon);
                    JOptionPane.showMessageDialog(null, "El Nivel Actual del Objetivo se ha Actualizado...\nAhora su Nivel es Regular");
                }else
                {
                    //BUENO
                    ImageIcon icon = new ImageIcon(getClass().getResource("/Img/green.png"));
                    genImage(tablaIndicadores.getSelectedRow(), icon);
                    JOptionPane.showMessageDialog(null, "El Nivel Actual del Objetivo se ha Actualizado...\nAhora su Nivel es Excelente");
                }
            }
        });
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "ERROR: Procure ingresar correctamente los valores...","ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    public void genSemaforo()
    {
        for (int i = 0; i < modelo.getRowCount(); i++) 
        {
            double vE=Double.parseDouble(modelo.getValueAt(i,2).toString().replace(",","."));
            double vM=Double.parseDouble(modelo.getValueAt(i,3).toString().replace(",","."));
            if(Double.parseDouble(modelo.getValueAt(i,4).toString().replace(",","."))<vM)
            {
                //MALO
                ImageIcon icon = new ImageIcon(getClass().getResource("/Img/red.png"));
                genImage(i, icon);
            }else if(Double.parseDouble(modelo.getValueAt(i,4).toString().replace(",","."))>=vM && Double.parseDouble(modelo.getValueAt(i,4).toString().replace(",","."))<vE)
            {
                //REGULAR
                ImageIcon icon = new ImageIcon(getClass().getResource("/Img/yellow.png"));
                genImage(i, icon);
            }else
            {
                //BUENO
                ImageIcon icon = new ImageIcon(getClass().getResource("/Img/green.png"));
                genImage(i, icon);
            }
        }
        
    }
    public void genImage(int fila,ImageIcon icon)
    {
        tablaIndicadores.getColumnModel().getColumn(5).setCellRenderer(new ImageRenderer());
        DefaultTableModel Tmodel=(DefaultTableModel) tablaIndicadores.getModel();
        tablaIndicadores.setRowHeight(80);
        Tmodel.setValueAt(icon, fila, 5);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMain = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaIndicadores = new javax.swing.JTable();
        txtObj = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtValPre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtValMet = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtValAct = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        jLabel1.setText("Evaluador de Objetivos");

        jLabel2.setFont(new java.awt.Font("OCR A Extended", 0, 13)); // NOI18N
        jLabel2.setText("Objetivo Estratégico: ");

        tablaIndicadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaIndicadores);

        txtObj.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N

        jLabel3.setFont(new java.awt.Font("OCR A Extended", 0, 13)); // NOI18N
        jLabel3.setText("Valor de Precaución: ");

        txtValPre.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        txtValPre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValPreFocusLost(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("OCR A Extended", 0, 13)); // NOI18N
        jLabel4.setText("Valor Meta: ");

        txtValMet.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        txtValMet.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValMetFocusLost(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/add.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/logo.png"))); // NOI18N

        jLabel6.setBackground(new java.awt.Color(102, 153, 255));
        jLabel6.setFont(new java.awt.Font("OCR A Extended", 0, 13)); // NOI18N
        jLabel6.setText("Valor Actual:");

        txtValAct.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("OCR A Extended", 0, 11)); // NOI18N
        jButton2.setText("Evaluar Todo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelMainLayout = new javax.swing.GroupLayout(panelMain);
        panelMain.setLayout(panelMainLayout);
        panelMainLayout.setHorizontalGroup(
            panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMainLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(panelMainLayout.createSequentialGroup()
                        .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelMainLayout.createSequentialGroup()
                                    .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtObj))
                                .addGroup(panelMainLayout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(82, 82, 82)
                                    .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtValAct, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                                        .addComponent(txtValPre)
                                        .addComponent(txtValMet))
                                    .addGap(14, 14, 14)
                                    .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(4, 4, 4)))
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );
        panelMainLayout.setVerticalGroup(
            panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMainLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMainLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtObj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelMainLayout.createSequentialGroup()
                                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtValPre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtValMet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtValAct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(jButton2)))
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMainLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        modelo.addRow(new Object[]{tablaIndicadores.getRowCount()+1,txtObj.getText(),txtValMet.getText(),txtValPre.getText(),txtValAct.getText(),null});
        genSemaforo();
        txtObj.setText("");
        txtValAct.setText("");
        txtValMet.setText("");
        txtValPre.setText("");
        txtObj.requestFocus();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtValMetFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValMetFocusLost
        if(Double.parseDouble(txtValMet.getText())<=Double.parseDouble(txtValPre.getText()))
        {
            JOptionPane.showMessageDialog(null, "ERROR: El valor de meta no puede ser menor al valor de precaución...","ERROR", JOptionPane.ERROR_MESSAGE);
            txtValMet.setText("");
            txtValMet.requestFocus();
        }
    }//GEN-LAST:event_txtValMetFocusLost

    private void txtValPreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValPreFocusLost
        
    }//GEN-LAST:event_txtValPreFocusLost

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        genSemaforo();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public void editarCabecera()
    {
        JTableHeader th; 
        th = tablaIndicadores.getTableHeader(); 
        Font fuente = new Font("Consolas", Font.BOLD, 11); 
        th.setFont(fuente); 
        tablaIndicadores.setFont(new java.awt.Font("Consolas", 0, 11)); 
    }
    public void insertImage(JPanel panel,String ruta,int x, int y)
    {
        ImagePanel Imagen = new ImagePanel(ruta,x,y);
        panel.add(Imagen);
        panel.repaint();
    }
    public class ImagePanel extends JPanel 
    {
        private final String ruta;
        private final int x;
        private final int y;
        public ImagePanel(String ruta,int x,int y)
        {
            //Se crea un método cuyo parámetro debe ser un objeto Graphics
            this.ruta=ruta;
            this.x=x;
            this.y=y;
            this.setSize(x,y);
        } 
        @Override
        public void paint(Graphics grafico)
        {
            Dimension height = getSize();
            //Se selecciona la imagen que tenemos en el paquete de la //ruta del programa
            ImageIcon Img = new ImageIcon(getClass().getResource(ruta)); 
            //se dibuja la imagen que tenemos en el paquete Images //dentro de un panel
            grafico.drawImage(Img.getImage(), 0, 0, height.width, height.height, null);
            setOpaque(false);
            super.paintComponent(grafico);
        }
    }
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel panelMain;
    private javax.swing.JTable tablaIndicadores;
    private javax.swing.JTextField txtObj;
    private javax.swing.JTextField txtValAct;
    private javax.swing.JTextField txtValMet;
    private javax.swing.JTextField txtValPre;
    // End of variables declaration//GEN-END:variables
class ImageRenderer extends DefaultTableCellRenderer {
      JLabel lbl = new JLabel();
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        lbl.setIcon((ImageIcon)value);
        return lbl;
      }
    }

}