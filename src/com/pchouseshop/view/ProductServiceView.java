package com.pchouseshop.view;

import com.pchouseshop.common.CommonExtension;
import com.pchouseshop.common.CommonSetting;
import com.pchouseshop.controllers.ProductServiceController;
import com.pchouseshop.model.ProductService;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProductServiceView extends javax.swing.JInternalFrame {

    DefaultTableModel _dtmProdServ;
    List<ProductService> _listProducts;
    ProductServiceController _productServiceController;

    public ProductServiceView() {
        initComponents();

        CommonSetting.requestTxtFocus(this.txt_search_prodServ);
        CommonSetting.tableSettings(this.table_view_products_list);

        this._dtmProdServ = (DefaultTableModel) this.table_view_products_list.getModel();
        this._productServiceController = new ProductServiceController();

        loadProdServListTable();
    }

    private void loadProdServListTable() {
        this._listProducts = _productServiceController.getAlLProductController(CommonSetting.ID_COMPANY);

        _dtmProdServ.setRowCount(0);
        CommonSetting.fitContentJtable(this.table_view_products_list);

        if (_listProducts != null) {
            for (ProductService product : _listProducts) {
                _dtmProdServ.addRow(
                        new Object[]{
                            product.getIdProductService(),
                            product.getProdServName(),
                            product.getPrice(),
                            product.getQty(),
                            product.getNote(),
                            product.getCategory()
                        }
                );
            }
        }
    }

    private void searchProductService() {
        if (!this.txt_search_prodServ.getText().trim().isEmpty()) {
            _listProducts = _productServiceController.searchProdServController(this.txt_search_prodServ.getText());
            if (_listProducts != null) {
                _dtmProdServ.setRowCount(0);

                _listProducts.forEach((product) -> {
                    _dtmProdServ.addRow(
                            new Object[]{
                                product.getIdProductService(),
                                product.getProdServName(),
                                product.getPrice(),
                                product.getQty(),
                                product.getNote(),
                                product.getCategory()
                            }
                    );
                });
            } else {
                loadProdServListTable();
            }
        }
    }

    private ProductService getProdServFields() {
        ProductService getProdServ = null;
        if (txt_prod_serv_name.getText().trim().isEmpty() || txt_prod_serv_price.getText().trim().isEmpty()
                || combo_box_prod_serv_categ.getSelectedItem().equals("SELECT")) {
            JOptionPane.showMessageDialog(this, "Please check empty fields !", "Product Service", JOptionPane.ERROR_MESSAGE);
            return getProdServ;

        } else {
            getProdServ = new ProductService(
                    txt_prod_serv_name.getText().toUpperCase(),
                    (this.txt_prod_serv_qty.getText().trim().isEmpty()) ? 1 : Integer.parseInt(this.txt_prod_serv_qty.getText()),
                    Double.parseDouble(txt_prod_serv_price.getText()),
                    combo_box_prod_serv_categ.getSelectedItem().toString(),
                    txt_prod_serv_notes.getText().toUpperCase(),
                    CommonSetting.ID_COMPANY
            );

            int idProdServ = CommonExtension.setIdExtension(this.txt_prod_serv_id);
            getProdServ.setIdProductService(idProdServ);
            return getProdServ;
        }
    }

    private void setProdServFields(ProductService pProdServ) {
        this.txt_prod_serv_id.setText(String.valueOf(pProdServ.getIdProductService()));
        this.txt_prod_serv_name.setText(pProdServ.getProdServName());
        this.txt_prod_serv_qty.setText(String.valueOf(pProdServ.getQty()));
        this.txt_prod_serv_price.setText(String.valueOf(pProdServ.getPrice()));
        this.combo_box_prod_serv_categ.setSelectedItem(pProdServ.getCategory());
        this.txt_prod_serv_notes.setText(pProdServ.getNote());
    }

    private void cleanFields() {
        this.txt_prod_serv_id.setText("");
        this.txt_prod_serv_name.setText("");
        this.txt_prod_serv_price.setText("");
        this.txt_prod_serv_qty.setText("");
        this.txt_prod_serv_notes.setText("");
        this.combo_box_prod_serv_categ.setSelectedIndex(0);
        loadProdServListTable();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_product_list = new javax.swing.JPanel();
        panel_prodServ_buttons = new javax.swing.JPanel();
        btn_update_product_service = new javax.swing.JButton();
        btn_add_product_service = new javax.swing.JButton();
        txt_delete_product_service = new javax.swing.JButton();
        btn_clear_fields = new javax.swing.JButton();
        panel_new_product = new javax.swing.JPanel();
        txt_prod_serv_name = new javax.swing.JTextField();
        txt_prod_serv_price = new javax.swing.JTextField();
        txt_prod_serv_qty = new javax.swing.JTextField();
        txt_prod_serv_notes = new javax.swing.JTextField();
        combo_box_prod_serv_categ = new javax.swing.JComboBox<>();
        lbl_prod_serv_id = new javax.swing.JLabel();
        txt_prod_serv_id = new javax.swing.JTextField();
        lbl_prod_serv_qty = new javax.swing.JLabel();
        lbl_prod_serv_name1 = new javax.swing.JLabel();
        lbl_prod_serv_price = new javax.swing.JLabel();
        lbl_prod_serv_notes = new javax.swing.JLabel();
        lbl_prod_serv_categ = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_view_products_list = new javax.swing.JTable();
        txt_search_prodServ = new javax.swing.JTextField();
        lbl_search_icon = new javax.swing.JLabel();

        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Product | Service");
        setMaximumSize(new java.awt.Dimension(1049, 700));
        setPreferredSize(new java.awt.Dimension(1050, 650));
        setSize(new java.awt.Dimension(0, 0));

        panel_product_list.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        panel_prodServ_buttons.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_update_product_service.setBackground(new java.awt.Color(21, 76, 121));
        btn_update_product_service.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        btn_update_product_service.setForeground(new java.awt.Color(255, 255, 255));
        btn_update_product_service.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_save_changes.png"))); // NOI18N
        btn_update_product_service.setText("Update");
        btn_update_product_service.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update_product_serviceActionPerformed(evt);
            }
        });

        btn_add_product_service.setBackground(new java.awt.Color(21, 76, 121));
        btn_add_product_service.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        btn_add_product_service.setForeground(new java.awt.Color(255, 255, 255));
        btn_add_product_service.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_add.png"))); // NOI18N
        btn_add_product_service.setText("Add");
        btn_add_product_service.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_product_serviceActionPerformed(evt);
            }
        });

        txt_delete_product_service.setBackground(new java.awt.Color(21, 76, 121));
        txt_delete_product_service.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        txt_delete_product_service.setForeground(new java.awt.Color(255, 255, 255));
        txt_delete_product_service.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cancel.png"))); // NOI18N
        txt_delete_product_service.setText("Delete");
        txt_delete_product_service.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_delete_product_serviceActionPerformed(evt);
            }
        });

        btn_clear_fields.setBackground(new java.awt.Color(21, 76, 121));
        btn_clear_fields.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        btn_clear_fields.setForeground(new java.awt.Color(255, 255, 255));
        btn_clear_fields.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_clear.png"))); // NOI18N
        btn_clear_fields.setText("Clear Fields");
        btn_clear_fields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clear_fieldsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_prodServ_buttonsLayout = new javax.swing.GroupLayout(panel_prodServ_buttons);
        panel_prodServ_buttons.setLayout(panel_prodServ_buttonsLayout);
        panel_prodServ_buttonsLayout.setHorizontalGroup(
            panel_prodServ_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_prodServ_buttonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_add_product_service)
                .addGap(18, 18, 18)
                .addComponent(btn_update_product_service)
                .addGap(18, 18, 18)
                .addComponent(txt_delete_product_service)
                .addGap(18, 18, 18)
                .addComponent(btn_clear_fields)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_prodServ_buttonsLayout.setVerticalGroup(
            panel_prodServ_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_prodServ_buttonsLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(panel_prodServ_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_update_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_add_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_delete_product_service)
                    .addComponent(btn_clear_fields))
                .addGap(15, 15, 15))
        );

        panel_new_product.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txt_prod_serv_name.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        txt_prod_serv_name.setMinimumSize(new java.awt.Dimension(80, 32));
        txt_prod_serv_name.setPreferredSize(new java.awt.Dimension(320, 32));

        txt_prod_serv_price.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        txt_prod_serv_price.setMinimumSize(new java.awt.Dimension(80, 32));
        txt_prod_serv_price.setPreferredSize(new java.awt.Dimension(80, 32));
        txt_prod_serv_price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_prod_serv_priceKeyPressed(evt);
            }
        });

        txt_prod_serv_qty.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        txt_prod_serv_qty.setMinimumSize(new java.awt.Dimension(80, 32));
        txt_prod_serv_qty.setPreferredSize(new java.awt.Dimension(80, 32));
        txt_prod_serv_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_prod_serv_qtyKeyPressed(evt);
            }
        });

        txt_prod_serv_notes.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        txt_prod_serv_notes.setMinimumSize(new java.awt.Dimension(80, 32));
        txt_prod_serv_notes.setPreferredSize(new java.awt.Dimension(442, 32));

        combo_box_prod_serv_categ.setFont(new java.awt.Font("Lucida Grande", 0, 13)); // NOI18N
        combo_box_prod_serv_categ.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT", "PRODUCT", "SERVICE", "OTHER" }));
        combo_box_prod_serv_categ.setMinimumSize(new java.awt.Dimension(80, 32));
        combo_box_prod_serv_categ.setPreferredSize(new java.awt.Dimension(180, 32));

        lbl_prod_serv_id.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_prod_serv_id.setText("ID");

        txt_prod_serv_id.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        txt_prod_serv_id.setEnabled(false);
        txt_prod_serv_id.setMinimumSize(new java.awt.Dimension(80, 32));
        txt_prod_serv_id.setPreferredSize(new java.awt.Dimension(80, 32));

        lbl_prod_serv_qty.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_prod_serv_qty.setText("Qty");

        lbl_prod_serv_name1.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_prod_serv_name1.setText("Name");

        lbl_prod_serv_price.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_prod_serv_price.setText("Price");

        lbl_prod_serv_notes.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_prod_serv_notes.setText("Notes");

        lbl_prod_serv_categ.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_prod_serv_categ.setText("Category");

        javax.swing.GroupLayout panel_new_productLayout = new javax.swing.GroupLayout(panel_new_product);
        panel_new_product.setLayout(panel_new_productLayout);
        panel_new_productLayout.setHorizontalGroup(
            panel_new_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_new_productLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_new_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_new_productLayout.createSequentialGroup()
                        .addComponent(lbl_prod_serv_notes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_prod_serv_notes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_new_productLayout.createSequentialGroup()
                        .addComponent(lbl_prod_serv_id)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_prod_serv_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_prod_serv_name1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_prod_serv_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(panel_new_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_new_productLayout.createSequentialGroup()
                        .addComponent(lbl_prod_serv_qty)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_prod_serv_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_prod_serv_categ))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_new_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_new_productLayout.createSequentialGroup()
                        .addComponent(lbl_prod_serv_price)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_prod_serv_price, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(combo_box_prod_serv_categ, 0, 1, Short.MAX_VALUE))
                .addGap(234, 234, 234))
        );
        panel_new_productLayout.setVerticalGroup(
            panel_new_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_new_productLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panel_new_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_prod_serv_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_prod_serv_id)
                    .addComponent(lbl_prod_serv_qty)
                    .addComponent(txt_prod_serv_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_prod_serv_name1)
                    .addComponent(txt_prod_serv_qty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_prod_serv_price)
                    .addComponent(txt_prod_serv_price, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel_new_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_new_productLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(panel_new_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(combo_box_prod_serv_categ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_prod_serv_categ)))
                    .addGroup(panel_new_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_prod_serv_notes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_prod_serv_notes)))
                .addGap(15, 15, 15))
        );

        table_view_products_list.setAutoCreateRowSorter(true);
        table_view_products_list.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        table_view_products_list.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product | Service", "Price €", "Qty", "Notes", "Category"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_products_list.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        table_view_products_list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_products_listMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_view_products_list);
        if (table_view_products_list.getColumnModel().getColumnCount() > 0) {
            table_view_products_list.getColumnModel().getColumn(0).setMinWidth(0);
            table_view_products_list.getColumnModel().getColumn(0).setPreferredWidth(0);
            table_view_products_list.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        txt_search_prodServ.setFont(new java.awt.Font("sansserif", 0, 16)); // NOI18N
        txt_search_prodServ.setMinimumSize(new java.awt.Dimension(80, 32));
        txt_search_prodServ.setPreferredSize(new java.awt.Dimension(500, 32));
        txt_search_prodServ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_search_prodServKeyReleased(evt);
            }
        });

        lbl_search_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_search_black.png"))); // NOI18N

        javax.swing.GroupLayout panel_product_listLayout = new javax.swing.GroupLayout(panel_product_list);
        panel_product_list.setLayout(panel_product_listLayout);
        panel_product_listLayout.setHorizontalGroup(
            panel_product_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_product_listLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_product_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(panel_product_listLayout.createSequentialGroup()
                        .addGroup(panel_product_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(panel_new_product, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panel_prodServ_buttons, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panel_product_listLayout.createSequentialGroup()
                        .addGap(236, 236, 236)
                        .addComponent(lbl_search_icon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_search_prodServ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(236, 236, 236)))
                .addContainerGap())
        );
        panel_product_listLayout.setVerticalGroup(
            panel_product_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_product_listLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_product_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_search_icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_search_prodServ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(panel_new_product, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(panel_prodServ_buttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_product_list, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_product_list, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setBounds(0, 0, 1050, 650);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_update_product_serviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_update_product_serviceActionPerformed
        ProductService updateProdServ = this.getProdServFields();
        if (updateProdServ != null) {
            int confirmEditing = JOptionPane.showConfirmDialog(null, "Confirm Editing " + updateProdServ.getProdServName() + " ?",
                    "Edit Product|Service", JOptionPane.YES_NO_OPTION);

            if (confirmEditing == 0) {
                boolean isUpdated = _productServiceController.updateProductService(updateProdServ);

                if (isUpdated) {

                    loadProdServListTable();
                    cleanFields();
                } else {
                    JOptionPane.showMessageDialog(this, updateProdServ.getProdServName() + "could not be updated!", null, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btn_update_product_serviceActionPerformed

    private void btn_add_product_serviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_product_serviceActionPerformed
        ProductService addProdServ = this.getProdServFields();

        if (addProdServ != null) {
            int idProdServAdded = _productServiceController.addProductServiceController(addProdServ);

            if (idProdServAdded > 0) {
                JOptionPane.showMessageDialog(this, addProdServ.getProdServName() + " added successfully!");
                loadProdServListTable();
                cleanFields();
            } else {
                JOptionPane.showMessageDialog(this, addProdServ.getProdServName() + "could not be saved!", null, JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_add_product_serviceActionPerformed

    private void txt_delete_product_serviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_delete_product_serviceActionPerformed
        int selectedRow = this.table_view_products_list.getSelectedRow();

        if (selectedRow >= 0) {

            ProductService deleteProdServ = new ProductService();

            deleteProdServ.setIdProductService((Integer) _dtmProdServ.getValueAt(selectedRow, 0));
            deleteProdServ.setProdServName(_dtmProdServ.getValueAt(selectedRow, 1).toString());

            int confirmDeletion = JOptionPane.showConfirmDialog(this, "Do you really want to delete '"
                    + deleteProdServ.getProdServName(), "Delete Product|Service", JOptionPane.YES_NO_OPTION);

            if (confirmDeletion == 0) {
                boolean isDeleted = _productServiceController.deleteProductService(deleteProdServ);

                if (isDeleted) {
                    loadProdServListTable();
                } else {
                    JOptionPane.showMessageDialog(this, deleteProdServ.getProdServName() + "could not be deleted!", null, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_txt_delete_product_serviceActionPerformed

    private void btn_clear_fieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clear_fieldsActionPerformed
        cleanFields();
    }//GEN-LAST:event_btn_clear_fieldsActionPerformed

    private void table_view_products_listMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_products_listMouseClicked
        if (evt.getClickCount() == 2) {
            int selectedRow = table_view_products_list.getSelectedRow();

            ProductService updateProdServ = new ProductService(
                    _dtmProdServ.getValueAt(selectedRow, 1).toString(),
                    (Integer) _dtmProdServ.getValueAt(selectedRow, 3),
                    (Double) _dtmProdServ.getValueAt(selectedRow, 2),
                    _dtmProdServ.getValueAt(selectedRow, 5).toString(),
                    _dtmProdServ.getValueAt(selectedRow, 4).toString(),
                    CommonSetting.ID_COMPANY);

            updateProdServ.setIdProductService((Integer) _dtmProdServ.getValueAt(selectedRow, 0));
            setProdServFields(updateProdServ);
        }
    }//GEN-LAST:event_table_view_products_listMouseClicked

    private void txt_prod_serv_priceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_prod_serv_priceKeyPressed
        if (Character.isLetter(evt.getKeyChar())) {
            txt_prod_serv_price.setEditable(false);
        } else {
            txt_prod_serv_price.setEditable(true);
        }
    }//GEN-LAST:event_txt_prod_serv_priceKeyPressed

    private void txt_prod_serv_qtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_prod_serv_qtyKeyPressed
        if (Character.isLetter(evt.getKeyChar())) {
            txt_prod_serv_qty.setEditable(false);
        } else {
            txt_prod_serv_qty.setEditable(true);
        }
    }//GEN-LAST:event_txt_prod_serv_qtyKeyPressed

    private void txt_search_prodServKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_prodServKeyReleased
        searchProductService();
    }//GEN-LAST:event_txt_search_prodServKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_product_service;
    private javax.swing.JButton btn_clear_fields;
    private javax.swing.JButton btn_update_product_service;
    private javax.swing.JComboBox<String> combo_box_prod_serv_categ;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_prod_serv_categ;
    private javax.swing.JLabel lbl_prod_serv_id;
    private javax.swing.JLabel lbl_prod_serv_name1;
    private javax.swing.JLabel lbl_prod_serv_notes;
    private javax.swing.JLabel lbl_prod_serv_price;
    private javax.swing.JLabel lbl_prod_serv_qty;
    private javax.swing.JLabel lbl_search_icon;
    private javax.swing.JPanel panel_new_product;
    private javax.swing.JPanel panel_prodServ_buttons;
    private javax.swing.JPanel panel_product_list;
    private javax.swing.JTable table_view_products_list;
    private javax.swing.JButton txt_delete_product_service;
    private javax.swing.JTextField txt_prod_serv_id;
    private javax.swing.JTextField txt_prod_serv_name;
    private javax.swing.JTextField txt_prod_serv_notes;
    private javax.swing.JTextField txt_prod_serv_price;
    private javax.swing.JTextField txt_prod_serv_qty;
    private javax.swing.JTextField txt_search_prodServ;
    // End of variables declaration//GEN-END:variables
}
