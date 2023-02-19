package com.pchouseshop.view;

import com.pchouseshop.controllers.EmployeeController;
import com.pchouseshop.controllers.OrderController;
import com.pchouseshop.controllers.OrderNoteController;
import com.pchouseshop.model.Customer;
import com.pchouseshop.model.Employee;
import com.pchouseshop.model.OrderModel;
import com.pchouseshop.model.OrderNote;
import com.pchouseshop.model.Person;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class NoteView extends javax.swing.JDialog {
    
    /* For invoking this JDialog in a JInternalFrame
     NoteView noteView = new NoteView(new MainMenuView(CommonSetting.COMPANY), true);
        noteView.setVisible(true);
     */
    
    private  final OrderController _orderController;
    private final OrderNoteController _orderNoteController;
    private final EmployeeController _employeeController;
    NewOrderView _NewOrderView;

    public NoteView(NewOrderView newOrderView, java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this._orderController = new OrderController();
        this._orderNoteController = new OrderNoteController();
        this._employeeController = new EmployeeController();
        this._NewOrderView = newOrderView;
    }

    private OrderNote getOrderNote(OrderModel order) {
        OrderNote orderNote = null;
        Employee employee = this._employeeController.getItemEmployeeController(4);

        if (this.txt_note_description.getText().trim().isEmpty()) {
            return orderNote;
        } else {
            orderNote = new OrderNote(order, employee, this.txt_note_description.getText(), new Date());
        }
        return orderNote;
    }

    private void clearFields() {
        this.txt_search_note.setText("");
        this.txt_note_description.setText("");

        this.txt_search_note.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_notes = new javax.swing.JPanel();
        lbl_search_icon = new javax.swing.JLabel();
        txt_search_note = new javax.swing.JTextField();
        scroll_pane_notes = new javax.swing.JScrollPane();
        table_view_notes = new javax.swing.JTable();
        panel_note_input = new javax.swing.JPanel();
        hdn_txt_note_id = new javax.swing.JTextField();
        lbl_note_star = new javax.swing.JLabel();
        lbl_note = new javax.swing.JLabel();
        txt_note_description = new javax.swing.JTextField();
        panel_fault_buttons = new javax.swing.JPanel();
        btn_clear_fields = new javax.swing.JButton();
        btn_add = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Note View");
        setModal(true);

        panel_notes.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbl_search_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_search_black.png"))); // NOI18N

        txt_search_note.setPreferredSize(new java.awt.Dimension(12, 30));
        txt_search_note.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_search_noteActionPerformed(evt);
            }
        });

        table_view_notes.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        table_view_notes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Note", "User"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_notes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_notesMouseClicked(evt);
            }
        });
        scroll_pane_notes.setViewportView(table_view_notes);
        if (table_view_notes.getColumnModel().getColumnCount() > 0) {
            table_view_notes.getColumnModel().getColumn(0).setPreferredWidth(150);
            table_view_notes.getColumnModel().getColumn(0).setMaxWidth(200);
            table_view_notes.getColumnModel().getColumn(2).setPreferredWidth(100);
            table_view_notes.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        panel_note_input.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        hdn_txt_note_id.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        hdn_txt_note_id.setEnabled(false);
        hdn_txt_note_id.setMinimumSize(new java.awt.Dimension(80, 32));
        hdn_txt_note_id.setPreferredSize(new java.awt.Dimension(0, 0));

        lbl_note_star.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_note_star.setForeground(java.awt.Color.red);
        lbl_note_star.setText("*");

        lbl_note.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_note.setText("new Note");

        txt_note_description.setMinimumSize(new java.awt.Dimension(80, 32));
        txt_note_description.setPreferredSize(new java.awt.Dimension(600, 25));

        javax.swing.GroupLayout panel_note_inputLayout = new javax.swing.GroupLayout(panel_note_input);
        panel_note_input.setLayout(panel_note_inputLayout);
        panel_note_inputLayout.setHorizontalGroup(
            panel_note_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_note_inputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_note_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hdn_txt_note_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_note_star))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_note)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_note_description, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel_note_inputLayout.setVerticalGroup(
            panel_note_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_note_inputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_note_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_note_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_note)
                        .addComponent(lbl_note_star, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_note_description, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(hdn_txt_note_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_fault_buttons.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_clear_fields.setBackground(new java.awt.Color(21, 76, 121));
        btn_clear_fields.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        btn_clear_fields.setForeground(new java.awt.Color(255, 255, 255));
        btn_clear_fields.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_clear.png"))); // NOI18N
        btn_clear_fields.setText("Clear");
        btn_clear_fields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clear_fieldsActionPerformed(evt);
            }
        });

        btn_add.setBackground(new java.awt.Color(21, 76, 121));
        btn_add.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        btn_add.setForeground(new java.awt.Color(255, 255, 255));
        btn_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_add.png"))); // NOI18N
        btn_add.setText("Add");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        btn_delete.setBackground(new java.awt.Color(21, 76, 121));
        btn_delete.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cancel.png"))); // NOI18N
        btn_delete.setText("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_fault_buttonsLayout = new javax.swing.GroupLayout(panel_fault_buttons);
        panel_fault_buttons.setLayout(panel_fault_buttonsLayout);
        panel_fault_buttonsLayout.setHorizontalGroup(
            panel_fault_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_fault_buttonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_add)
                .addGap(18, 18, 18)
                .addComponent(btn_delete)
                .addGap(18, 18, 18)
                .addComponent(btn_clear_fields)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_fault_buttonsLayout.setVerticalGroup(
            panel_fault_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_fault_buttonsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_fault_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_clear_fields, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panel_fault_buttonsLayout.createSequentialGroup()
                        .addGroup(panel_fault_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_notesLayout = new javax.swing.GroupLayout(panel_notes);
        panel_notes.setLayout(panel_notesLayout);
        panel_notesLayout.setHorizontalGroup(
            panel_notesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_notesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_notesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll_pane_notes, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                    .addGroup(panel_notesLayout.createSequentialGroup()
                        .addComponent(lbl_search_icon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_search_note, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panel_note_input, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_fault_buttons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_notesLayout.setVerticalGroup(
            panel_notesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_notesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_notesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_search_note, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_search_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_pane_notes, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(panel_note_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panel_fault_buttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_notes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_notes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void table_view_notesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_notesMouseClicked
        if (evt.getClickCount() == 2) {
            DefaultTableModel dtm = (DefaultTableModel) table_view_notes.getModel();

            int selectedRow = table_view_notes.getSelectedRow();
            String selectedNote = dtm.getValueAt(selectedRow, 1).toString();
            String selectedUser = dtm.getValueAt(selectedRow, 2).toString();

            if (selectedUser.equals("System")) {
                JOptionPane.showMessageDialog(this, "Notes from System are not allowed to delete !", "Order Notes", JOptionPane.ERROR_MESSAGE);
            } else {
                int confirmDeletion = JOptionPane.showConfirmDialog(null, "Do you want to Delete note " + selectedNote + " ?", "Order Notes", JOptionPane.YES_NO_OPTION);
                if (confirmDeletion == 0) {
                }
            }
        }
    }//GEN-LAST:event_table_view_notesMouseClicked

    private void txt_search_noteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_search_noteActionPerformed

//        Date date = new Date();
//        Timestamp currentDate = new Timestamp(date.getTime());
//        noteDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentDate);
//        note = txt_search_note.getText();
//
//        int confirmInsertion = JOptionPane.showConfirmDialog(this, "Do you want to add note: " + note + " to the Database ?", "Order Notes", JOptionPane.YES_OPTION);
//        if (confirmInsertion == 0) {
//
//            txt_search_note.setText("");
//            loadTableNotes();
//        }
    }//GEN-LAST:event_txt_search_noteActionPerformed

    private void btn_clear_fieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clear_fieldsActionPerformed
        //clearFields();
        //loadFaultListTable();
        
        Person person = new Person();
        person.setFirstName("Herman");
        person.setLastName("Costa");
        person.setContactNo("(11) 95423-1558");
        person.setEmail("hermanhgc@gmail.com");
        Customer customer = new Customer();
        customer.setPerson(person);
        this.dispose();
        
        _NewOrderView.setCustomerFields(customer);
    }//GEN-LAST:event_btn_clear_fieldsActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed

        OrderModel order = _orderController.getItemOrderController(7);
        OrderNote addOrderNote = getOrderNote(order);
        if (addOrderNote != null) {

            int idNoteAdded = _orderNoteController.addOrderNoteController(addOrderNote);
            if (idNoteAdded > 0) {
                JOptionPane.showMessageDialog(this, "Note created successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Notes could not be saved!", null, JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

//        Note addFault = this.getFaultFields();
//
//        if (addFault != null) {
//            int idFault = this._faultController.addFaultController(addFault);
//
//            if (idFault > 0) {
//                JOptionPane.showMessageDialog(this, addFault.getDescription() + " added successfully!");
//                loadFaultListTable();
//
//                this.txt_search_fault.setText("");
//            } else {
//                JOptionPane.showMessageDialog(this, addFault.getDescription() + "could not be saved!", null, JOptionPane.ERROR_MESSAGE);
//            }
//        }
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        int selectedRow = this.table_view_notes.getSelectedRow();

        if (selectedRow >= 0) {

            OrderNote deleteNote = new OrderNote();

//            deleteNote.setNote((Integer) _dtmFault.getValueAt(selectedRow, 0));
//            deleteNote.setDescription(_dtmFault.getValueAt(selectedRow, 1).toString());
//
//            int confirmDeletion = JOptionPane.showConfirmDialog(this, "Do you really want to delete '"
//                    + deleteNote.getDescription(), "Delete Fault", JOptionPane.YES_NO_OPTION);
//
//            if (confirmDeletion == 0) {
//                boolean isDeleted = this._faultController.deleteFaultController(deleteNote);
//
//                if (isDeleted) {
//                    loadFaultListTable();
//                } else {
//                    JOptionPane.showMessageDialog(this, deleteNote.getDescription() + "could not be deleted!", null, JOptionPane.ERROR_MESSAGE);
//                }
//            }
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_clear_fields;
    private javax.swing.JButton btn_delete;
    private javax.swing.JTextField hdn_txt_note_id;
    private javax.swing.JLabel lbl_note;
    private javax.swing.JLabel lbl_note_star;
    private javax.swing.JLabel lbl_search_icon;
    private javax.swing.JPanel panel_fault_buttons;
    private javax.swing.JPanel panel_note_input;
    private javax.swing.JPanel panel_notes;
    private javax.swing.JScrollPane scroll_pane_notes;
    private javax.swing.JTable table_view_notes;
    private javax.swing.JTextField txt_note_description;
    private javax.swing.JTextField txt_search_note;
    // End of variables declaration//GEN-END:variables
}
