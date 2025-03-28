package com.pchouseshop.view;

import com.pchouseshop.common.CommonExtension;
import com.pchouseshop.common.CommonSetting;
import com.pchouseshop.controllers.CustomerController;
import com.pchouseshop.controllers.PersonController;
import com.pchouseshop.model.Customer;
import com.pchouseshop.model.Person;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

public class CustomerView extends javax.swing.JInternalFrame {

    private final DefaultTableModel _dtmCustomer;
    private final CustomerController _customerController;
    private List<Customer> _listCustomer;

    public CustomerView() {
        initComponents();
        //avoid auto old value by focus loosing
        this.txt_contact.setFocusLostBehavior(JFormattedTextField.PERSIST);

        CommonSetting.tableSettings(table_view_customers);

        this._customerController = new CustomerController();
        this._dtmCustomer = (DefaultTableModel) this.table_view_customers.getModel();

        //checkEmailFormat();
        loadCustomerListTable();
    }

    private void loadCustomerListTable() {
        this._listCustomer = this._customerController.getAllCustomerController(CommonSetting.COMPANY);

        this._dtmCustomer.setRowCount(0);

        if (this._listCustomer != null) {
            this._listCustomer.forEach((custItem) -> {
                this._dtmCustomer.addRow(
                        new Object[]{
                            custItem.getIdCustomer(),
                            custItem.getPerson().getFirstName(),
                            custItem.getPerson().getLastName(),
                            formatContactNo(custItem.getPerson().getContactNo()),
                            custItem.getPerson().getEmail(),
                            custItem.getPerson().getIdPerson()
                        }
                );
            });
        }
    }

    private Customer getCustomerFields() {
        Customer getCustomer = null;

        if (this.txt_first_name.getText().trim().isEmpty() || this.txt_last_name.getText().trim().isEmpty()
                || this.txt_contact.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please, check Empty fields", "New Costumer", JOptionPane.ERROR_MESSAGE);

            return getCustomer;
        } else {

            Person person = new Person(
                    this.txt_first_name.getText().toUpperCase(),
                    this.txt_last_name.getText().toUpperCase(),
                    this.txt_contact.getText().replace("(", "").replace(")", "").replace("-", "").replace(" ", ""),
                    this.txt_email.getText().toLowerCase());

            getCustomer = new Customer(person, CommonSetting.COMPANY);

            int idCustomer = CommonExtension.setIdExtension(this.txt_customer_id);
            getCustomer.setIdCustomer(idCustomer);

            return getCustomer;
        }
    }

    private void setCustomerFields(Customer pCustomer) {
        this.txt_contact.setFormatterFactory(null);
        this.txt_customer_id.setText(String.valueOf(pCustomer.getIdCustomer()));
        this.txt_first_name.setText(pCustomer.getPerson().getFirstName());
        this.txt_last_name.setText(pCustomer.getPerson().getLastName());
        this.txt_contact.setText(pCustomer.getPerson().getContactNo());
        this.txt_email.setText(pCustomer.getPerson().getEmail());
    }

    private void searchCustomer() {
        PersonController personController = new PersonController();
        Customer searchCustomer = null;

        if (!this.txt_search_customer.getText().trim().isEmpty()) {
            List<Person> _listPerson = personController.searchPerson(this.txt_search_customer.getText());

            if (_listPerson != null) {
                _dtmCustomer.setRowCount(0);

                for (Person person : _listPerson) {
                    searchCustomer = _customerController.getCustomerController(person);

                    if (searchCustomer != null) {
                        _dtmCustomer.addRow(
                                new Object[]{
                                    searchCustomer.getIdCustomer(),
                                    searchCustomer.getPerson().getFirstName(),
                                    searchCustomer.getPerson().getLastName(),
                                    formatContactNo(searchCustomer.getPerson().getContactNo()),
                                    searchCustomer.getPerson().getEmail(),
                                    searchCustomer.getPerson().getIdPerson()
                                }
                        );
                    }
                }
            }
        } else {
            loadCustomerListTable();
        }
    }

    private void getItemCustomer(int idCustomer) {
        if (idCustomer != 0) {
            Customer customerItem = _customerController.getItemCustomerController(idCustomer);

            this._dtmCustomer.setRowCount(0);
            _dtmCustomer.addRow(
                    new Object[]{
                        customerItem.getIdCustomer(),
                        customerItem.getPerson().getFirstName(),
                        customerItem.getPerson().getLastName(),
                        formatContactNo(customerItem.getPerson().getContactNo()),
                        customerItem.getPerson().getEmail(),
                        customerItem.getPerson().getIdPerson()
                    }
            );
        } else {
            loadCustomerListTable();
        }
    }

    private void cleanFields() {
        this.txt_customer_id.setText("");
        this.txt_first_name.setText("");
        this.txt_last_name.setText("");
        this.txt_contact.setText("");
        this.txt_email.setText("");
        this.txt_search_customer.setText("");
        this.txt_first_name.requestFocus();
    }

    private final void checkEmailFormat() {
        this.txt_email.setInputVerifier(new InputVerifier() {

            Border originalBorder;
            String emailFormat = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            String email = txt_email.getText();

            @Override
            public boolean verify(JComponent input) {
                JTextField comp = (JTextField) input;

                return comp.getText().matches(emailFormat) | comp.getText().trim().isEmpty();
            }

            @Override
            public boolean shouldYieldFocus(JComponent input) {
                boolean isValid = verify(input);

                if (!isValid) {
                    originalBorder = originalBorder == null ? input.getBorder() : originalBorder;

                    input.setBorder(new LineBorder(Color.RED));
                } else {
                    if (originalBorder != null) {
                        input.setBorder(originalBorder);
                        originalBorder = null;
                    }
                }
                return isValid;
            }
        });
    }

    private String formatContactNo(String pNumber) {
        String format = "";
        if (!pNumber.startsWith("+") && !pNumber.startsWith("00")) {
            if (pNumber.length() == 9 && pNumber.startsWith("0")) {
                format = pNumber.replaceFirst("(\\d{2})(\\d{3})(\\d+)", "($1) $2-$3");
            } else if (pNumber.length() == 10 && pNumber.startsWith("0")) {
                format = pNumber.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
            }
        } else {
            format = pNumber;
        }

        return format;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_customers = new javax.swing.JPanel();
        lbl_search_icon = new javax.swing.JLabel();
        txt_search_customer = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_view_customers = new javax.swing.JTable();
        panel_customer_input = new javax.swing.JPanel();
        btn_international_number = new javax.swing.JButton();
        lbl_first_name = new javax.swing.JLabel();
        txt_contact = new javax.swing.JFormattedTextField();
        txt_last_name = new javax.swing.JTextField();
        lbl_last_name = new javax.swing.JLabel();
        lbl_customer_id = new javax.swing.JLabel();
        lbl_contact = new javax.swing.JLabel();
        txt_customer_id = new javax.swing.JTextField();
        txt_first_name = new javax.swing.JTextField();
        btn_copy = new javax.swing.JButton();
        lbl_email = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        panel_customer_buttons = new javax.swing.JPanel();
        btn_add = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_clear_fields = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setMaximumSize(new java.awt.Dimension(0, 0));
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(1050, 650));
        setSize(new java.awt.Dimension(0, 0));
        setVisible(true);

        panel_customers.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_customers.setPreferredSize(new java.awt.Dimension(0, 0));

        lbl_search_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_search_black.png"))); // NOI18N

        txt_search_customer.setFont(new java.awt.Font("sansserif", 0, 16)); // NOI18N
        txt_search_customer.setFocusTraversalKeysEnabled(false);
        txt_search_customer.setMinimumSize(new java.awt.Dimension(80, 32));
        txt_search_customer.setNextFocusableComponent(txt_first_name);
        txt_search_customer.setPreferredSize(new java.awt.Dimension(500, 32));
        txt_search_customer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_search_customerKeyReleased(evt);
            }
        });

        table_view_customers.setAutoCreateRowSorter(true);
        table_view_customers.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        table_view_customers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IDCustomer", "First Name", "Last Name", "Contact", "Email", "IDPerson"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_customers.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        table_view_customers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_customersMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_view_customers);
        if (table_view_customers.getColumnModel().getColumnCount() > 0) {
            table_view_customers.getColumnModel().getColumn(0).setMinWidth(0);
            table_view_customers.getColumnModel().getColumn(0).setPreferredWidth(0);
            table_view_customers.getColumnModel().getColumn(0).setMaxWidth(0);
            table_view_customers.getColumnModel().getColumn(1).setPreferredWidth(250);
            table_view_customers.getColumnModel().getColumn(1).setMaxWidth(250);
            table_view_customers.getColumnModel().getColumn(2).setPreferredWidth(250);
            table_view_customers.getColumnModel().getColumn(2).setMaxWidth(250);
            table_view_customers.getColumnModel().getColumn(3).setPreferredWidth(200);
            table_view_customers.getColumnModel().getColumn(3).setMaxWidth(200);
            table_view_customers.getColumnModel().getColumn(5).setMinWidth(0);
            table_view_customers.getColumnModel().getColumn(5).setPreferredWidth(0);
            table_view_customers.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        panel_customer_input.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_international_number.setBackground(new java.awt.Color(0, 0, 0));
        btn_international_number.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_international_number.png"))); // NOI18N
        btn_international_number.setPreferredSize(new java.awt.Dimension(40, 35));
        btn_international_number.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_international_numberActionPerformed(evt);
            }
        });

        lbl_first_name.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_first_name.setText("First Name");

        try {
            txt_contact.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(0##) ###-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_contact.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_contact.setMinimumSize(new java.awt.Dimension(80, 32));
        txt_contact.setNextFocusableComponent(txt_email);
        txt_contact.setPreferredSize(new java.awt.Dimension(300, 32));

        txt_last_name.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        txt_last_name.setMinimumSize(new java.awt.Dimension(80, 32));
        txt_last_name.setNextFocusableComponent(txt_contact);
        txt_last_name.setPreferredSize(new java.awt.Dimension(340, 32));

        lbl_last_name.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_last_name.setText("Last Name");

        lbl_customer_id.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_customer_id.setText("ID");

        lbl_contact.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_contact.setText("Contact No.");

        txt_customer_id.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        txt_customer_id.setEnabled(false);
        txt_customer_id.setMinimumSize(new java.awt.Dimension(80, 32));
        txt_customer_id.setPreferredSize(new java.awt.Dimension(80, 32));

        txt_first_name.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        txt_first_name.setMinimumSize(new java.awt.Dimension(80, 32));
        txt_first_name.setNextFocusableComponent(txt_last_name);
        txt_first_name.setPreferredSize(new java.awt.Dimension(320, 32));

        btn_copy.setBackground(new java.awt.Color(0, 0, 0));
        btn_copy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_copy.png"))); // NOI18N
        btn_copy.setToolTipText("Copy to clipboard");
        btn_copy.setPreferredSize(new java.awt.Dimension(40, 35));
        btn_copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_copyActionPerformed(evt);
            }
        });

        lbl_email.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_email.setText("Email");

        txt_email.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        txt_email.setMinimumSize(new java.awt.Dimension(80, 32));
        txt_email.setPreferredSize(new java.awt.Dimension(380, 32));

        javax.swing.GroupLayout panel_customer_inputLayout = new javax.swing.GroupLayout(panel_customer_input);
        panel_customer_input.setLayout(panel_customer_inputLayout);
        panel_customer_inputLayout.setHorizontalGroup(
            panel_customer_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_customer_inputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_customer_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_contact)
                    .addGroup(panel_customer_inputLayout.createSequentialGroup()
                        .addComponent(lbl_customer_id)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_customer_id, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_customer_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_customer_inputLayout.createSequentialGroup()
                        .addComponent(txt_contact, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                        .addGap(43, 43, 43)
                        .addComponent(btn_international_number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_copy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_customer_inputLayout.createSequentialGroup()
                        .addComponent(lbl_first_name)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_first_name, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)))
                .addGap(36, 36, 36)
                .addGroup(panel_customer_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_customer_inputLayout.createSequentialGroup()
                        .addComponent(lbl_email)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_customer_inputLayout.createSequentialGroup()
                        .addComponent(lbl_last_name)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_last_name, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_customer_inputLayout.setVerticalGroup(
            panel_customer_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_customer_inputLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panel_customer_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_first_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_last_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_customer_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_first_name)
                    .addComponent(lbl_customer_id)
                    .addComponent(lbl_last_name))
                .addGroup(panel_customer_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_customer_inputLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(panel_customer_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_customer_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbl_contact)
                                .addComponent(txt_contact, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_customer_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbl_email)
                                .addComponent(txt_email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_customer_inputLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panel_customer_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_copy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_international_number, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(14, 14, 14))
        );

        panel_customer_buttons.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        btn_update.setBackground(new java.awt.Color(21, 76, 121));
        btn_update.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        btn_update.setForeground(new java.awt.Color(255, 255, 255));
        btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_save_changes.png"))); // NOI18N
        btn_update.setText("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
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

        javax.swing.GroupLayout panel_customer_buttonsLayout = new javax.swing.GroupLayout(panel_customer_buttons);
        panel_customer_buttons.setLayout(panel_customer_buttonsLayout);
        panel_customer_buttonsLayout.setHorizontalGroup(
            panel_customer_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_customer_buttonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_add)
                .addGap(18, 18, 18)
                .addComponent(btn_update)
                .addGap(18, 18, 18)
                .addComponent(btn_delete)
                .addGap(18, 18, 18)
                .addComponent(btn_clear_fields)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_customer_buttonsLayout.setVerticalGroup(
            panel_customer_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_customer_buttonsLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panel_customer_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_add, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_clear_fields, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout panel_customersLayout = new javax.swing.GroupLayout(panel_customers);
        panel_customers.setLayout(panel_customersLayout);
        panel_customersLayout.setHorizontalGroup(
            panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_customersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_customersLayout.createSequentialGroup()
                        .addComponent(lbl_search_icon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_search_customer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)
                    .addComponent(panel_customer_input, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_customer_buttons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_customersLayout.setVerticalGroup(
            panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_customersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_customersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_search_icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_search_customer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(panel_customer_input, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(panel_customer_buttons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_customers, javax.swing.GroupLayout.DEFAULT_SIZE, 1026, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_customers, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE))
        );

        setBounds(0, 0, 1050, 650);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_copyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_copyActionPerformed
        StringSelection stringSelection = new StringSelection(txt_contact.getText().replace("(", "").replace(")", "").replace("-", "").replace(" ", ""));
        if (!this.txt_contact.getText().trim().isEmpty()) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        }
    }//GEN-LAST:event_btn_copyActionPerformed

    private void btn_international_numberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_international_numberActionPerformed
        if (this.txt_contact.getFormatterFactory() != null) {
            this.txt_contact.setFormatterFactory(null);
            this.txt_contact.setText("+");
            this.txt_contact.requestFocus();
        } else {
            this.txt_contact.setText("");
            try {
                this.txt_contact.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("(0##) ###-####")));
            } catch (java.text.ParseException ex) {
            }
            this.txt_contact.requestFocus();
        }
    }//GEN-LAST:event_btn_international_numberActionPerformed

    private void btn_clear_fieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clear_fieldsActionPerformed
        cleanFields();
        loadCustomerListTable();
    }//GEN-LAST:event_btn_clear_fieldsActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        int selectedRow = this.table_view_customers.getSelectedRow();

        if (selectedRow >= 0) {

            Customer deleteCustomer = new Customer();

            deleteCustomer.setIdCustomer((Integer) this._dtmCustomer.getValueAt(selectedRow, 0));
            String firstName = this._dtmCustomer.getValueAt(selectedRow, 1).toString();

            int confirmDeletion = JOptionPane.showConfirmDialog(this, "Do you really want to delete '"
                    + firstName, "Delete Customer", JOptionPane.YES_NO_OPTION);

            if (confirmDeletion == 0) {
                boolean isDeleted = _customerController.deleteCustomerController(deleteCustomer.getIdCustomer());

                if (isDeleted) {
                    cleanFields();
                    loadCustomerListTable();
                } else {
                    JOptionPane.showMessageDialog(this, deleteCustomer.getPerson().getFirstName() + "could not be deleted!", null, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void txt_search_customerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_customerKeyReleased
        searchCustomer();
    }//GEN-LAST:event_txt_search_customerKeyReleased

    private void table_view_customersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_customersMouseClicked
        if (evt.getClickCount() == 2) {
            int selectedRow = this.table_view_customers.getSelectedRow();

            Person updatePerson = new Person(
                    this._dtmCustomer.getValueAt(selectedRow, 1).toString(),
                    this._dtmCustomer.getValueAt(selectedRow, 2).toString(),
                    this._dtmCustomer.getValueAt(selectedRow, 3).toString(),
                    this._dtmCustomer.getValueAt(selectedRow, 4).toString());

            updatePerson.setIdPerson((Integer) this._dtmCustomer.getValueAt(selectedRow, 5));

            Customer updateCustomer = new Customer(updatePerson, CommonSetting.COMPANY);
            updateCustomer.setIdCustomer((int) this._dtmCustomer.getValueAt(selectedRow, 0));

            setCustomerFields(updateCustomer);
        }
    }//GEN-LAST:event_table_view_customersMouseClicked

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        Customer updateCustomer = this.getCustomerFields();
        if (updateCustomer != null) {
            if (updateCustomer.getIdCustomer() > 0) {
                int confirmEditing = JOptionPane.showConfirmDialog(null, "Confirm Editing " + updateCustomer.getPerson().getFirstName() + " ?",
                        "Edit Customer", JOptionPane.YES_NO_OPTION);

                if (confirmEditing == 0) {
                    boolean isUpdated = this._customerController.updateCustomerController(updateCustomer);
                    if (isUpdated) {

                        getItemCustomer(updateCustomer.getIdCustomer());

                    } else {
                        JOptionPane.showMessageDialog(this, updateCustomer.getPerson().getFirstName() + "could not be updated!", null, JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        Customer addCustomer = this.getCustomerFields();

        if (addCustomer != null) {
            if (addCustomer.getIdCustomer() == 0) {
                int idCustomerAdded = this._customerController.addCustomerController(addCustomer);
                if (idCustomerAdded > 0) {
                    JOptionPane.showMessageDialog(this, addCustomer.getPerson().getFirstName() + " added successfully!");

                    loadCustomerListTable();
                    cleanFields();
                } else {
                    JOptionPane.showMessageDialog(this, addCustomer.getPerson().getFirstName() + "could not be saved!", null, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btn_addActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_clear_fields;
    private javax.swing.JButton btn_copy;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_international_number;
    private javax.swing.JButton btn_update;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_contact;
    private javax.swing.JLabel lbl_customer_id;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_first_name;
    private javax.swing.JLabel lbl_last_name;
    private javax.swing.JLabel lbl_search_icon;
    private javax.swing.JPanel panel_customer_buttons;
    private javax.swing.JPanel panel_customer_input;
    private javax.swing.JPanel panel_customers;
    private javax.swing.JTable table_view_customers;
    private javax.swing.JFormattedTextField txt_contact;
    private javax.swing.JTextField txt_customer_id;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_first_name;
    private javax.swing.JTextField txt_last_name;
    private javax.swing.JTextField txt_search_customer;
    // End of variables declaration//GEN-END:variables
}
