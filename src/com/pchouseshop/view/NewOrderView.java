package com.pchouseshop.view;

//import Forms.DepositPayment;
//import Forms.Login;
//import Forms.PrintOrder;
//import Forms.MainMenu;
//import Model.CompletedOrder;
//import Model.Customer;
//import Model.Order;
//import Model.ProductService;
import com.pchouseshop.common.CommonSetting;
import com.pchouseshop.controllers.OrderController;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class NewOrderView extends javax.swing.JInternalFrame {

    ArrayList firstNames = new ArrayList();
    ArrayList lastNames = new ArrayList();
    ArrayList faults = new ArrayList();
    ArrayList brands = new ArrayList();
    ArrayList models = new ArrayList();
    ArrayList serialNumbers = new ArrayList();

    Vector vecFaults = new Vector();
    Vector vecProducts = new Vector();
    Vector vecQty = new Vector();
    Vector vecUnitPrice = new Vector();
    Vector vecPriceTotal = new Vector();

    Connection con;
    PreparedStatement ps;
    Statement stmt;
//    Customer customer;
//    ProductService productService;
//    Order order;
    ResultSet rs;
    ResultSetMetaData rsmd;
//    CompletedOrder completedOrder;
    Timestamp currentDate;

    String orderNo, firstName, lastName, contactNo, email, deviceBrand,
            deviceModel, serialNumber, importantNotes, stringFaults,
            stringProducts, stringPriceTotal, stringQty, stringUnitPrice, issueDate, status,
            finishDate = "", pickDate = "", refundDate = "";

    double total, deposit, cashDeposit = 0, cardDeposit = 0, due;
    boolean isOrderDetails = false;
    
    OrderController _orderController = new OrderController();

    public NewOrderView() {
        initComponents();
       
         //avoid auto old value by focus loosing
        txt_contact.setFocusLostBehavior(JFormattedTextField.PERSIST);
        
        CommonSetting.requestTxtFocus(txt_first_name);
        CommonSetting.tableSettings(table_view_faults);
        CommonSetting.tableSettings(table_view_products);

        this._orderController = new OrderController();
        autoID();
        
        checkEmailFormat();
        accessDbColumn(firstNames, "SELECT * FROM customers", "firstName");
        accessDbColumn(lastNames, "SELECT * FROM customers", "lastName");
        accessDbColumn(brands, "SELECT deviceBrand FROM orderDetails", "deviceBrand");
        accessDbColumn(models, "SELECT deviceModel FROM orderDetails", "deviceModel");
        accessDbColumn(serialNumbers, "SELECT serialNumber FROM orderDetails", "serialNumber");
        accessDbColumn(faults, "SELECT faultName FROM faults", "faultName");
        listProductService();
    }

    public void dbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pcHouse", "root", "hellmans");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(NewOrderView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex, "DB Connection", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void autoID() {
        int orderId = _orderController.getLastOrderIdController();
        lbl_auto_order_no.setText(String.format("%06d", orderId++));
    }

    public void autoCompleteFromDb(ArrayList list, String text, JTextField field) {
        String complete = "";
        int start = text.length();
        int last = text.length();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().startsWith(text)) {
                complete = list.get(i).toString();
                last = complete.length();
                break;
            }
        }

        if (last > start) {
            field.setText(complete);
            field.setCaretPosition(last);
            field.moveCaretPosition(start);
        }
    }

    public void listProductService() {
        AutoCompleteDecorator.decorate(combo_box_product_service);

        try {
            dbConnection();

            String query = "SELECT productService FROM products";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                combo_box_product_service.addItem(rs.getString("productService"));
            }
            rs.close();
            con.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(NewOrderView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getPriceSum() {
        double sum = 0;
        for (int i = 0; i < table_view_products.getRowCount(); i++) {
            sum = sum + Double.parseDouble(table_view_products.getValueAt(i, 3).toString());
        }

        txt_total.setText(Double.toString(sum));
        txt_due.setText(txt_total.getText()); //set total to the due field
    }

    public final void accessDbColumn(ArrayList list, String query, String columnName) {
        try {
            dbConnection();

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString(columnName));
            }
            
            rs.close();
            ps.close();
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(NewOrderView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cleanAllFields(JTable table) {
        //Clean all Fields 
        lbl_auto_order_no.setText("");
        txt_first_name.setText("");
        txt_last_name.setText("");
        txt_contact.setText("");
        txt_email.setText("");
        txt_brand.setText("");
        txt_model.setText("");
        txt_serial_number.setText("");
        txt_fault.setText("");
        editor_pane_notes.setText("");
        combo_box_product_service.setSelectedIndex(-1);
        txt_total.setText("");
        txt_deposit.setText(Double.toString(0));
        txt_due.setText("");
        txt_first_name.requestFocus();

        //Clean table Fields
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();

        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
    }

    public final void checkEmailFormat() {
        txt_email.setInputVerifier(new InputVerifier() {

            Border originalBorder;
            String emailFormat = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            String email = txt_email.getText();

            @Override
            public boolean verify(JComponent input) {
                JTextField comp = (JTextField) input;
                //return !comp.getText().trim().isEmpty();
                return comp.getText().matches(emailFormat) | comp.getText().trim().isEmpty();
            }

            @Override
            public boolean shouldYieldFocus(JComponent input) {
                boolean isValid = verify(input);

                if (!isValid) {
                    originalBorder = originalBorder == null ? input.getBorder() : originalBorder;
                    //input.setBorder(BorderFactory.createLineBorder(Color.red, 2));
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

    public void loadOrderList() {
        if (txt_first_name.getText().trim().isEmpty() | txt_last_name.getText().trim().isEmpty()
                | txt_contact.getText().trim().isEmpty() | txt_brand.getText().trim().isEmpty()
                | txt_model.getText().trim().isEmpty() | txt_serial_number.getText().trim().isEmpty()
                | table_view_products.getRowCount() == 0 | table_view_faults.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Please, check Empty fields", "New Order", JOptionPane.ERROR_MESSAGE);

        } else {
            orderNo = lbl_auto_order_no.getText();
            firstName = txt_first_name.getText().toUpperCase();
            lastName = txt_last_name.getText().toUpperCase();
            contactNo = txt_contact.getText().replace("(","").replace(")", "").replace("-", "").replace(" ", "");
            email = txt_email.getText();
            deviceBrand = txt_brand.getText().toUpperCase();
            deviceModel = txt_model.getText().toUpperCase();
            serialNumber = txt_serial_number.getText().toUpperCase();
            importantNotes = editor_pane_notes.getText();

            if (txt_deposit.getText() == null || txt_deposit.getText().trim().isEmpty()) {
                deposit = 0;

            } else {
                deposit = Double.parseDouble(txt_deposit.getText());
            }

            due = Double.parseDouble(txt_due.getText());
            total = Double.parseDouble(txt_total.getText());
            status = "In Progress";

            Date date = new java.util.Date();
            currentDate = new Timestamp(date.getTime());
            issueDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentDate);

            //Empty vector before looping to avoid duplicate values on tableView
            vecFaults.clear();
            vecProducts.clear();
            vecQty.clear();
            vecUnitPrice.clear();
            vecPriceTotal.clear();
            //pass table items from faults and products table to vector 
            for (int i = 0; i < table_view_faults.getRowCount(); i++) {
                vecFaults.add(table_view_faults.getValueAt(i, 0));
            }

            for (int j = 0; j < table_view_products.getRowCount(); j++) {
                vecProducts.add(table_view_products.getValueAt(j, 0));
                vecQty.add(table_view_products.getValueAt(j, 1));
                vecUnitPrice.add(table_view_products.getValueAt(j, 2));
                vecPriceTotal.add(table_view_products.getValueAt(j, 3));
            }

            // pass vector elemnets to a String splitted by a comma,
            // in order to save into DB
            stringFaults = vecFaults.toString().replace("[", " ").replace("]", "");
            stringProducts = vecProducts.toString().replace("[", " ").replace("]", "");
            stringQty = vecQty.toString().replace("[", " ").replace("]", "");
            stringUnitPrice = vecUnitPrice.toString().replace("[", " ").replace("]", "");
            stringPriceTotal = vecPriceTotal.toString().replace("[", " ").replace("]", "");

//            order = new Order(orderNo, firstName, lastName, contactNo, email, deviceBrand,
//                    deviceModel, serialNumber, importantNotes, stringFaults, stringProducts,
//                    stringQty, stringUnitPrice, stringPriceTotal, total, deposit, cashDeposit,
//                    cardDeposit, due, status, issueDate, finishDate, pickDate, refundDate, Login.fullName);
        }
    }
    
    public boolean saveCustomerIntoDb() {
         boolean isContactNo = false;
         contactNo = txt_contact.getText().replace("(","").replace(")", "").replace("-", "").replace(" ", "");
         try {
            dbConnection();
            String query = "SELECT contactNo, firstName, lastName FROM customers WHERE contactNo = ? ";
            ps = con.prepareStatement(query);
            ps.setString(1, contactNo);
            rs = ps.executeQuery();
            
            if (!rs.isBeforeFirst()) {
//                customer = new Customer(txt_first_name.getText().toUpperCase(), txt_last_name.getText().toUpperCase(), contactNo, txt_email.getText());
//                    
//                String queryInsert = "INSERT INTO customers (firstName, lastName, contactNo, email) VALUES(?, ?, ?, ?)";
//                ps = con.prepareStatement(queryInsert);
//                ps.setString(1, customer.getFirstName());
//                ps.setString(2, customer.getLastName());
//                ps.setString(3, customer.getContactNo());
//                ps.setString(4, customer.getEmail());
                ps.executeUpdate();
            }
            
            else  {
                while (rs.next()) {
                    firstName = rs.getString("firstName");
                    lastName = rs.getString("lastName");
                }
            
                if (!firstName.equals(txt_first_name.getText())
                    && !lastName.equals(txt_last_name.getText())) {
                    JOptionPane.showMessageDialog(this, "There is another Customer associated with ContactNo " + txt_contact.getText(), "New Customer", JOptionPane.ERROR_MESSAGE);
                    isContactNo = true;
                }
            }
         } catch (SQLException ex) {
            Logger.getLogger(NewOrderView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return isContactNo;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_order_details = new javax.swing.JPanel();
        lbl_order_no = new javax.swing.JLabel();
        lbl_first_name = new javax.swing.JLabel();
        lbl_last_name = new javax.swing.JLabel();
        lbl_contact = new javax.swing.JLabel();
        lbl_email = new javax.swing.JLabel();
        lbl_brand = new javax.swing.JLabel();
        lbl_model = new javax.swing.JLabel();
        lbl_sn = new javax.swing.JLabel();
        lbl_fault = new javax.swing.JLabel();
        lbl_auto_order_no = new javax.swing.JLabel();
        lbl_service_product = new javax.swing.JLabel();
        lbl_price = new javax.swing.JLabel();
        lbl_deposit = new javax.swing.JLabel();
        lbl_due = new javax.swing.JLabel();
        txt_first_name = new javax.swing.JTextField();
        txt_last_name = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        txt_contact = new javax.swing.JFormattedTextField();
        txt_brand = new javax.swing.JTextField();
        txt_model = new javax.swing.JTextField();
        txt_serial_number = new javax.swing.JTextField();
        txt_deposit = new javax.swing.JTextField();
        btn_save_order = new javax.swing.JButton();
        btn_cancel = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_view_products = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_view_faults = new javax.swing.JTable();
        txt_due = new javax.swing.JTextField();
        jScrollPane_notes = new javax.swing.JScrollPane();
        editor_pane_notes = new javax.swing.JEditorPane();
        txt_fault = new javax.swing.JTextField();
        combo_box_product_service = new javax.swing.JComboBox<>();
        btn_international_number = new javax.swing.JButton();
        btn_copy = new javax.swing.JButton();
        btn_add_product_service = new javax.swing.JButton();
        txt_total = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("New Order");
        setMaximumSize(new java.awt.Dimension(1049, 700));
        setPreferredSize(new java.awt.Dimension(1050, 650));
        setSize(new java.awt.Dimension(1050, 650));

        panel_order_details.setPreferredSize(new java.awt.Dimension(1050, 700));

        lbl_order_no.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_order_no.setText("Order No.");

        lbl_first_name.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_first_name.setText("First Name");

        lbl_last_name.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_last_name.setText("Last Name");

        lbl_contact.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_contact.setText("Contact No.");

        lbl_email.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_email.setText("Email");

        lbl_brand.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_brand.setText("Device Brand");

        lbl_model.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_model.setText("Device Model");

        lbl_sn.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_sn.setText("Serial Number");

        lbl_fault.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_fault.setText("Faults");

        lbl_auto_order_no.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        lbl_auto_order_no.setText("autoGen");

        lbl_service_product.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_service_product.setText("Service | Product");

        lbl_price.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_price.setText("Total €");

        lbl_deposit.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_deposit.setText("Deposit €");

        lbl_due.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lbl_due.setText("Due €");

        txt_first_name.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_first_name.setFocusCycleRoot(true);
        txt_first_name.setNextFocusableComponent(txt_last_name);
        txt_first_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_first_nameKeyPressed(evt);
            }
        });

        txt_last_name.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_last_name.setNextFocusableComponent(txt_contact);
        txt_last_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_last_nameKeyPressed(evt);
            }
        });

        txt_email.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_email.setNextFocusableComponent(txt_brand);
        txt_email.setPreferredSize(new java.awt.Dimension(63, 20));

        try {
            txt_contact.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(0##) ###-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_contact.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_contact.setNextFocusableComponent(txt_email);
        txt_contact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_contactActionPerformed(evt);
            }
        });

        txt_brand.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_brand.setMinimumSize(new java.awt.Dimension(63, 20));
        txt_brand.setNextFocusableComponent(txt_model);
        txt_brand.setPreferredSize(new java.awt.Dimension(63, 20));
        txt_brand.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_brandKeyPressed(evt);
            }
        });

        txt_model.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_model.setNextFocusableComponent(txt_serial_number);
        txt_model.setPreferredSize(new java.awt.Dimension(63, 20));
        txt_model.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_modelKeyPressed(evt);
            }
        });

        txt_serial_number.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        txt_serial_number.setNextFocusableComponent(editor_pane_notes);
        txt_serial_number.setPreferredSize(new java.awt.Dimension(63, 20));
        txt_serial_number.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_serial_numberKeyPressed(evt);
            }
        });

        txt_deposit.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        txt_deposit.setForeground(new java.awt.Color(51, 51, 255));
        txt_deposit.setNextFocusableComponent(btn_save_order);
        txt_deposit.setPreferredSize(new java.awt.Dimension(63, 20));
        txt_deposit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_depositKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_depositKeyReleased(evt);
            }
        });

        btn_save_order.setBackground(new java.awt.Color(21, 76, 121));
        btn_save_order.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btn_save_order.setForeground(new java.awt.Color(255, 255, 255));
        btn_save_order.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_save.png"))); // NOI18N
        btn_save_order.setText("Save");
        btn_save_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_save_orderActionPerformed(evt);
            }
        });

        btn_cancel.setBackground(new java.awt.Color(21, 76, 121));
        btn_cancel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btn_cancel.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cancel.png"))); // NOI18N
        btn_cancel.setText("Cancel");
        btn_cancel.setNextFocusableComponent(txt_first_name);
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });

        table_view_products.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        table_view_products.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product | Service", "Qty", "Unit €", "Total €"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_products.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_productsMouseClicked(evt);
            }
        });
        table_view_products.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                table_view_productsKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(table_view_products);
        if (table_view_products.getColumnModel().getColumnCount() > 0) {
            table_view_products.getColumnModel().getColumn(1).setPreferredWidth(40);
            table_view_products.getColumnModel().getColumn(1).setMaxWidth(60);
            table_view_products.getColumnModel().getColumn(2).setPreferredWidth(80);
            table_view_products.getColumnModel().getColumn(2).setMaxWidth(120);
            table_view_products.getColumnModel().getColumn(3).setPreferredWidth(80);
            table_view_products.getColumnModel().getColumn(3).setMaxWidth(120);
        }

        table_view_faults.setFont(new java.awt.Font("Lucida Grande", 0, 17)); // NOI18N
        table_view_faults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fault Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_view_faults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_faultsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(table_view_faults);

        txt_due.setEditable(false);
        txt_due.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        txt_due.setForeground(new java.awt.Color(255, 0, 51));

        jScrollPane_notes.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane_notes.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane_notes.setVerifyInputWhenFocusTarget(false);

        editor_pane_notes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Important Notes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 16))); // NOI18N
        editor_pane_notes.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        editor_pane_notes.setFocusCycleRoot(false);
        editor_pane_notes.setNextFocusableComponent(txt_fault);
        editor_pane_notes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                editor_pane_notesKeyPressed(evt);
            }
        });
        jScrollPane_notes.setViewportView(editor_pane_notes);

        txt_fault.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        txt_fault.setNextFocusableComponent(combo_box_product_service);
        txt_fault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_faultActionPerformed(evt);
            }
        });
        txt_fault.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_faultKeyReleased(evt);
            }
        });

        combo_box_product_service.setEditable(true);
        combo_box_product_service.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        combo_box_product_service.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select or Type" }));
        combo_box_product_service.setNextFocusableComponent(txt_deposit);

        btn_international_number.setBackground(new java.awt.Color(0, 0, 0));
        btn_international_number.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_international_number.png"))); // NOI18N
        btn_international_number.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_international_numberActionPerformed(evt);
            }
        });

        btn_copy.setBackground(new java.awt.Color(0, 0, 0));
        btn_copy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_copy.png"))); // NOI18N
        btn_copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_copyActionPerformed(evt);
            }
        });

        btn_add_product_service.setBackground(new java.awt.Color(0, 0, 0));
        btn_add_product_service.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_add_new.png"))); // NOI18N
        btn_add_product_service.setNextFocusableComponent(txt_deposit);
        btn_add_product_service.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_product_serviceActionPerformed(evt);
            }
        });

        txt_total.setEditable(false);
        txt_total.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N

        javax.swing.GroupLayout panel_order_detailsLayout = new javax.swing.GroupLayout(panel_order_details);
        panel_order_details.setLayout(panel_order_detailsLayout);
        panel_order_detailsLayout.setHorizontalGroup(
            panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(lbl_order_no)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_auto_order_no))
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(lbl_email)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(lbl_brand)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_brand, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_order_detailsLayout.createSequentialGroup()
                            .addComponent(lbl_model)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_model, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_order_detailsLayout.createSequentialGroup()
                            .addComponent(lbl_sn)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_serial_number, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)))
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(lbl_contact)
                        .addGap(11, 11, 11)
                        .addComponent(txt_contact, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_international_number, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_copy, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_order_detailsLayout.createSequentialGroup()
                            .addComponent(lbl_first_name)
                            .addGap(18, 18, 18)
                            .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_order_detailsLayout.createSequentialGroup()
                            .addComponent(lbl_last_name)
                            .addGap(20, 20, 20)
                            .addComponent(txt_last_name, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panel_order_detailsLayout.createSequentialGroup()
                            .addComponent(btn_save_order, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(23, 23, 23)
                            .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 26, Short.MAX_VALUE)
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(lbl_due)
                        .addGap(0, 0, 0)
                        .addComponent(txt_due, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(lbl_deposit)
                        .addGap(0, 0, 0)
                        .addComponent(txt_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_price)
                        .addGap(0, 0, 0)
                        .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(lbl_fault)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_fault))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(lbl_service_product)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combo_box_product_service, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_add_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        panel_order_detailsLayout.setVerticalGroup(
            panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_auto_order_no, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_order_no)
                    .addComponent(lbl_fault)
                    .addComponent(txt_fault, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_first_name))
                        .addGap(15, 15, 15)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_last_name)
                            .addComponent(txt_last_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbl_contact)
                                .addComponent(txt_contact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn_international_number, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_copy, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_email)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_brand, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_brand))
                        .addGap(15, 15, 15)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_model, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_model))
                        .addGap(15, 15, 15)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_serial_number, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_sn))
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(combo_box_product_service, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_service_product))
                            .addComponent(btn_add_product_service))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_save_order, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_cancel))
                    .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_deposit)
                            .addComponent(txt_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_price)
                            .addComponent(txt_due, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panel_order_detailsLayout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(lbl_due))))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_order_details, javax.swing.GroupLayout.DEFAULT_SIZE, 1038, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_order_details, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_save_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_save_orderActionPerformed
        // TODO add your handling code here:
        loadOrderList();
        String formatContactNo = txt_contact.getText();
        
        if (!saveCustomerIntoDb()) {
             
            if (deposit == 0) {
                int confirmNewOrder = 0; //JOptionPane.showConfirmDialog(this, "Do you want to save this new Order " + order.getOrderNo() + " ?");
                if (confirmNewOrder == 0) {


                        try {
                            dbConnection();
                            String query = "INSERT INTO orderDetails(orderNo, firstName, lastName, contactNo, "
                                    + "email, deviceBrand, deviceModel, serialNumber, importantNotes, fault, "
                                    + "productService, qty, unitPrice, priceTotal, total, deposit, cashDeposit, cardDeposit, "
                                    + "due, status, issueDate, createdBy) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                            ps = con.prepareStatement(query);
//                            ps.setString(1, order.getOrderNo());
//                            ps.setString(2, order.getFirstName());
//                            ps.setString(3, order.getLastName());
//                            ps.setString(4, order.getContactNo());
//                            ps.setString(5, order.getEmail());
//                            ps.setString(6, order.getBrand());
//                            ps.setString(7, order.getModel());
//                            ps.setString(8, order.getSerialNumber());
//                            ps.setString(9, order.getImportantNotes());
//                            ps.setString(10, order.getStringFaults());
//                            ps.setString(11, order.getStringProducts());
//                            ps.setString(12, order.getStringQty());
//                            ps.setString(13, order.getUnitPrice());
//                            ps.setString(14, order.getPriceTotal());
//                            ps.setDouble(15, order.getTotal());
//                            ps.setDouble(16, order.getDeposit());
//                            ps.setDouble(17, order.getCashDeposit());
//                            ps.setDouble(18, order.getCardDeposit());
//                            ps.setDouble(19, order.getDue());
//                            ps.setString(20, order.getStatus());
//                            ps.setString(21, order.getIssueDate());
//                            ps.setString(22, order.getUsername());
                            ps.executeUpdate();

                            String removeSpace = "UPDATE orderDetails SET fault = REPLACE(fault, '  ', ' '), "
                                    + "productService = REPLACE(productService, '  ', ' '), "
                                    + "qty = REPLACE(qty, '  ', ' '), "
                                    + "unitPrice = REPLACE(unitPrice, '  ', ' '), "
                                    + "total = REPLACE(total, '  ', ' ')";
                            ps = con.prepareStatement(removeSpace);
                            ps.executeUpdate();

                            //JOptionPane.showMessageDialog(this, order.getOrderNo() + " Created successfully!");

                            cleanAllFields(table_view_faults);
                            cleanAllFields(table_view_products);
                            //Generate new OrderNo.
                            autoID();

                            String issueDateFormat = new SimpleDateFormat("dd/MM/yyyy").format(currentDate);
                            //order.setIssueDate(issueDateFormat);

                            //PrintOrder printOrder = new PrintOrder(order, completedOrder, isOrderDetails, formatContactNo);
                            //printOrder.setVisible(true);

                            ps.close();
                            con.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(NewOrderView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    //DepositPayment depositPayment = new DepositPayment(order, 0, completedOrder, isOrderDetails, formatContactNo);
                    //depositPayment.setVisible(true);
            }
        }
    }//GEN-LAST:event_btn_save_orderActionPerformed

    private void txt_depositKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_depositKeyReleased
        //Calculate deposit paid and display due value
        if (txt_deposit.getText() == null || txt_deposit.getText().trim().isEmpty()) {
            txt_due.setText(txt_total.getText());
            deposit = 0;
        } else {
            double priceTotal = Double.parseDouble(txt_total.getText());
            deposit = Double.parseDouble(txt_deposit.getText());
            total = priceTotal - deposit;
            txt_due.setText(String.valueOf(total));
        }
    }//GEN-LAST:event_txt_depositKeyReleased

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        int confirmCancelling = JOptionPane.showConfirmDialog(null, "Do you want to cancel this ?", "New Order",
                JOptionPane.YES_NO_OPTION);
        if (confirmCancelling == 0) {
            //new MainMenu().setVisible(true);
        }
    }//GEN-LAST:event_btn_cancelActionPerformed

    private void txt_first_nameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_first_nameKeyPressed
        //Sugest autoComplete firstNames from Database
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:
                break;

            case KeyEvent.VK_ENTER:
                txt_first_name.setText(txt_first_name.getText());
                break;
            default:
                EventQueue.invokeLater(() -> {
                    String text = txt_first_name.getText();
                    autoCompleteFromDb(firstNames, text, txt_first_name);
                });
        }
    }//GEN-LAST:event_txt_first_nameKeyPressed

    private void txt_serial_numberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_serial_numberKeyPressed
        //Sugest autoComplete serialNumber from Database
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:
                break;

            case KeyEvent.VK_ENTER:
                txt_serial_number.setText(txt_serial_number.getText());
                break;
            default:
                EventQueue.invokeLater(() -> {
                    String text = txt_serial_number.getText();
                    autoCompleteFromDb(serialNumbers, text, txt_serial_number);
                });
        }
    }//GEN-LAST:event_txt_serial_numberKeyPressed

    private void txt_depositKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_depositKeyPressed
        //Accepts number characters only
        if (Character.isLetter(evt.getKeyChar()))
            txt_deposit.setEditable(false);
        else
            txt_deposit.setEditable(true);
    }//GEN-LAST:event_txt_depositKeyPressed

    private void table_view_productsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_productsMouseClicked
        //Delete products|Service item of the selected row (Function is called with 2 clicks) 
        DefaultTableModel dtm = (DefaultTableModel) table_view_products.getModel();

        if (evt.getClickCount() == 2) {
            int confirmDeletion = JOptionPane.showConfirmDialog(null, "Remove This Item ?", "Remove Product|Service", JOptionPane.YES_NO_OPTION);
            if (confirmDeletion == 0) {
                dtm.removeRow(table_view_products.getSelectedRow());
                // Sum price column and set into total textField
                getPriceSum();
            }
        }
    }//GEN-LAST:event_table_view_productsMouseClicked

    private void table_view_faultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_faultsMouseClicked
        //Delete fault item of the selected row (Function is called with 2 clicks) 
        DefaultTableModel dtm = (DefaultTableModel) table_view_faults.getModel();

        if (evt.getClickCount() == 2) {
            int confirmDeletion = JOptionPane.showConfirmDialog(null, "Remove This Item ?", "Remove Faults", JOptionPane.YES_NO_OPTION);
            if (confirmDeletion == 0) {
                dtm.removeRow(table_view_faults.getSelectedRow());
            }
        }
    }//GEN-LAST:event_table_view_faultsMouseClicked

    private void txt_last_nameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_last_nameKeyPressed
         //Sugest autoComplete firstNames from Database
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:
                break;

            case KeyEvent.VK_ENTER:
                txt_last_name.setText(txt_last_name.getText());
                break;
            default:
                EventQueue.invokeLater(() -> {
                    String text = txt_last_name.getText();
                    autoCompleteFromDb(lastNames, text, txt_last_name);
                });
        }
    }//GEN-LAST:event_txt_last_nameKeyPressed

    private void txt_brandKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_brandKeyPressed
        //Suggest autoComplete brand from Database
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:
                break;

            case KeyEvent.VK_ENTER:
                txt_brand.setText(txt_brand.getText());
                break;
            default:
                EventQueue.invokeLater(() -> {
                    String text = txt_brand.getText();
                    autoCompleteFromDb(brands, text, txt_brand);
                });
        }
    }//GEN-LAST:event_txt_brandKeyPressed

    private void txt_modelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_modelKeyPressed
         //Suggest autoComplete model from Database
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:
                break;

            case KeyEvent.VK_ENTER:
                txt_model.setText(txt_model.getText());
                break;
            default:
                EventQueue.invokeLater(() -> {
                    String text = txt_model.getText();
                    autoCompleteFromDb(models, text, txt_model);
                });
        }
    }//GEN-LAST:event_txt_modelKeyPressed

    private void txt_faultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_faultActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) table_view_faults.getModel();
        ArrayList<String> tableFaultsList = new ArrayList<>();
        String faultText = txt_fault.getText().replace(" ", "");
        String tableFault = "";
        Vector faultsVector = new Vector();

        for (int i = 0; i < dtm.getRowCount(); i++) {
            tableFault = dtm.getValueAt(i, 0).toString().replace(" ", "");
            tableFaultsList.add(tableFault);
        }
        if (faultText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add a Fault!", "Faults", JOptionPane.ERROR_MESSAGE);
        } else if (tableFaultsList.contains(faultText)) {
            JOptionPane.showMessageDialog(this, "Item '" + faultText + "' already added !", "Faults", JOptionPane.ERROR_MESSAGE);
            txt_fault.setText("");
        } else {
            try {
                dbConnection();
                faultText = txt_fault.getText();

                String queryCheck = "SELECT * FROM faults WHERE faultName = ?";
                ps = con.prepareStatement(queryCheck);
                ps.setString(1, faultText);
                rs = ps.executeQuery();

                if (!rs.isBeforeFirst()) {
                    int confirmInsertion = JOptionPane.showConfirmDialog(null, "Do you want to add a new fault ?", "Add New Fault", JOptionPane.YES_NO_OPTION);
                    if (confirmInsertion == 0) {
                        String query = "INSERT INTO faults (faultName) VALUES(?)";
                        ps = con.prepareStatement(query);
                        ps.setString(1, faultText);
                        ps.executeUpdate();

                        faultsVector.add(faultText);
                        dtm.addRow(faultsVector);
                        txt_fault.setText("");
                    } else {
                        txt_fault.setText("");
                    }
                } else {
                    faultsVector.add(faultText);
                    dtm.addRow(faultsVector);
                    txt_fault.setText("");
                }

                ps.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(NewOrderView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txt_faultActionPerformed

    private void editor_pane_notesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editor_pane_notesKeyPressed
        if (evt.isShiftDown() && evt.getKeyCode() == KeyEvent.VK_TAB)
            txt_serial_number.requestFocus();
        else if (evt.getKeyCode() == KeyEvent.VK_TAB)
            txt_fault.requestFocus();
        
    }//GEN-LAST:event_editor_pane_notesKeyPressed

    private void txt_faultKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_faultKeyReleased
         //Sugest autoComplete firstNames from Database
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:
                break;

            case KeyEvent.VK_ENTER:
                txt_fault.setText(txt_fault.getText());
                break;
            default:
                EventQueue.invokeLater(() -> {
                    String text = txt_fault.getText();
                    autoCompleteFromDb(faults, text, txt_fault);
                });
        }
    }//GEN-LAST:event_txt_faultKeyReleased

    private void btn_international_numberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_international_numberActionPerformed
        txt_contact.setFormatterFactory(null);
        txt_contact.setText("");
        txt_contact.requestFocus();
    }//GEN-LAST:event_btn_international_numberActionPerformed

    private void btn_copyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_copyActionPerformed
        StringSelection stringSelection = new StringSelection(txt_contact.getText().replace("(","").replace(")", "").replace("-", "").replace(" ", ""));
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection , null);
        JOptionPane.showMessageDialog(this, txt_contact.getText() + " Copied to Clipboard");
    }//GEN-LAST:event_btn_copyActionPerformed

    private void btn_add_product_serviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_product_serviceActionPerformed
        // Add selected items to the products table
        ArrayList<String> productsList = new ArrayList<>();
        DefaultTableModel dtm = (DefaultTableModel) table_view_products.getModel();
        String selectedItem = combo_box_product_service.getSelectedItem().toString();
        String newProdAdd = "", category = "";
        int qty = 0;
        double unitPrice = 0;
        double totalPrice = 0;
        Vector vector = new Vector();
        
        productsList.clear();
        for (int i = 0; i < dtm.getRowCount(); i++) {
            productsList.add(dtm.getValueAt(i, 0).toString());
        }

        if (selectedItem.isEmpty() || selectedItem.matches("Select or Type"))
            JOptionPane.showMessageDialog(this, "Please select a Product | Service!", "Service | Product", JOptionPane.ERROR_MESSAGE);
        else if (productsList.contains(selectedItem))
            JOptionPane.showMessageDialog(this, "Item '" + selectedItem + "' already added !", "Add Computer", JOptionPane.ERROR_MESSAGE);
        else {
            try {
                dbConnection();

                String query = "SELECT * FROM products WHERE productService = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, selectedItem);
                rs = ps.executeQuery();

                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(this, "Item not Found!", "Service | Product", JOptionPane.ERROR_MESSAGE);
                } else {
                    while (rs.next()) {
                        newProdAdd = rs.getString("productService");
                        unitPrice = rs.getDouble("price");
                        category = rs.getString("category");
                    }

                    if (category.equals("Product")) {
                        boolean valid = false;
                        while (!valid) {
                            try {
                                qty = Integer.parseInt(JOptionPane.showInputDialog("Enter '" + selectedItem + "' Qty:"));
                                if (qty > 0) {
                                    valid = true;
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(this, "Qty must be an Integer!", "Product | Service", JOptionPane.ERROR_MESSAGE);
                            }

                            totalPrice = unitPrice * qty;
                            vector.add(newProdAdd);
                            vector.add(qty);
                            vector.add(unitPrice);
                            vector.add(totalPrice);
                            dtm.addRow(vector);
                        }

                    } else {
                            qty = 1;
                            totalPrice = unitPrice * qty;

                            vector.add(newProdAdd);
                            vector.add(qty);
                            vector.add(unitPrice);
                            vector.add(totalPrice);
                            dtm.addRow(vector);
                    }

                    combo_box_product_service.setSelectedIndex(-1);
                    // Sum price column and set into total textField
                    getPriceSum();
                    ps.close();
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(NewOrderView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_add_product_serviceActionPerformed

    private void txt_contactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_contactActionPerformed
        try {
            dbConnection();

            String query = "SELECT * FROM customers WHERE contactNo = ? ";
            ps = con.prepareStatement(query);
            ps.setString(1, contactNo = txt_contact.getText().replace("(","").replace(")", "").replace("-", "").replace(" ", ""));
            rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "Customer not found in the Database", "New Customer", JOptionPane.ERROR_MESSAGE);
            }
            else {
                while (rs.next()) {
                    txt_first_name.setText(rs.getString("firstName"));
                    txt_last_name.setText(rs.getString("lastName"));
                    txt_contact.setText(rs.getString("contactNo"));
                    txt_email.setText(rs.getString("email"));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(NewOrderView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txt_contactActionPerformed

    private void table_view_productsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_view_productsKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            double sum = 0;
            for (int i = 0; i < table_view_products.getRowCount(); i++) {
                double unitPrice = Double.parseDouble(table_view_products.getValueAt(i, 2).toString());
                int qty = Integer.parseInt(table_view_products.getValueAt(i, 1).toString());
                
                table_view_products.setValueAt(unitPrice, i, 2);
                double priceTotal = unitPrice * qty;
                table_view_products.setValueAt(priceTotal, i, 3);
                sum += priceTotal;
            }

            txt_total.setText(String.valueOf(sum));
            txt_due.setText(String.valueOf(txt_total.getText()));
        }
    }//GEN-LAST:event_table_view_productsKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_product_service;
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_copy;
    private javax.swing.JButton btn_international_number;
    private javax.swing.JButton btn_save_order;
    private javax.swing.JComboBox<String> combo_box_product_service;
    private javax.swing.JEditorPane editor_pane_notes;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane_notes;
    private javax.swing.JLabel lbl_auto_order_no;
    private javax.swing.JLabel lbl_brand;
    private javax.swing.JLabel lbl_contact;
    private javax.swing.JLabel lbl_deposit;
    private javax.swing.JLabel lbl_due;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_fault;
    private javax.swing.JLabel lbl_first_name;
    private javax.swing.JLabel lbl_last_name;
    private javax.swing.JLabel lbl_model;
    private javax.swing.JLabel lbl_order_no;
    private javax.swing.JLabel lbl_price;
    private javax.swing.JLabel lbl_service_product;
    private javax.swing.JLabel lbl_sn;
    private javax.swing.JPanel panel_order_details;
    private javax.swing.JTable table_view_faults;
    private javax.swing.JTable table_view_products;
    private javax.swing.JTextField txt_brand;
    private javax.swing.JFormattedTextField txt_contact;
    private javax.swing.JTextField txt_deposit;
    private javax.swing.JTextField txt_due;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_fault;
    private javax.swing.JTextField txt_first_name;
    private javax.swing.JTextField txt_last_name;
    private javax.swing.JTextField txt_model;
    private javax.swing.JTextField txt_serial_number;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
