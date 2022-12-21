package com.pchouseshop.view;

//import Forms.DepositPayment;
//import Forms.Login;
//import Forms.PrintOrder;
//import Forms.MainMenu;
//import Model.CompletedOrder;
//import Model.Customer;
//import Model.Order;
//import Model.ProductService;
import com.pchouseshop.common.CommonExtension;
import com.pchouseshop.common.CommonSetting;
import com.pchouseshop.common.Java2sAutoComboBox;
import com.pchouseshop.common.Java2sAutoTextField;
import com.pchouseshop.controllers.FaultController;
import com.pchouseshop.controllers.OrderController;
import com.pchouseshop.controllers.ProductServiceController;
import com.pchouseshop.model.Fault;
import com.pchouseshop.model.ProductService;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class NewOrderViewTeste extends javax.swing.JInternalFrame {

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

    private final DefaultListModel _defaultListModelFault;
    private final DefaultListModel _defaultListModelProdServ;

    private final ListSelectionModel _listSelectionModel;
    private List<Fault> _listFault;
    private List<ProductService> _listProdServ;
    private final FaultController _faultController;
    private final ProductServiceController _productServiceController;

    private final DefaultTableModel _dtmProdServ;
    private final OrderController _orderController;

    public NewOrderViewTeste() {
        initComponents();

        //avoid auto old value by focus loosing
        this.txt_contact.setFocusLostBehavior(JFormattedTextField.PERSIST);
        //this.list_prod_serv_search.setVisible(false);

        CommonSetting.requestTxtFocus(txt_first_name);
        CommonSetting.tableSettings(table_view_faults);
        //CommonSetting.tableSettings(table_view_products);

        this._defaultListModelFault = new DefaultListModel();
        this._defaultListModelProdServ = new DefaultListModel();

        this._listSelectionModel = this.list_prod_serv_search.getSelectionModel();
        //this._listSelectionModel.addListSelectionListener(new LisSel);

        this.list_prod_serv_search.setModel(_defaultListModelProdServ);

        this._faultController = new FaultController();
        this._productServiceController = new ProductServiceController();
        this._dtmProdServ = (DefaultTableModel) this.table_view_products.getModel();

        this._orderController = new OrderController();

        checkEmailFormat();
        //loadProductServiceList();

        autoOrderId();
    }

    private void autoOrderId() {
        int orderId = _orderController.getLastOrderIdController();
        lbl_auto_order_no.setText(String.format("%06d", orderId++));
    }

    private void searchFault() {
        if (!this.txt_search_fault.getText().trim().isEmpty()) {
            this._listFault = _faultController.searchFault(this.txt_search_fault.getText());

            if (this._listFault != null) {
                this._defaultListModelFault.removeAllElements();
                this._listFault.forEach((faultItem) -> {
                    this._defaultListModelFault.addElement(faultItem);
                });
            }
        }
    }

    private void searchProdServ() {
        _defaultListModelProdServ.removeAllElements();
        if (!this.txt_search_prod_serv.getText().trim().isEmpty()) {
            this._listProdServ = _productServiceController.orderSearchProdServController(this.txt_search_prod_serv.getText());
            if (this._listProdServ != null) {
                _listProdServ.forEach((prodServ) -> {
                    _defaultListModelProdServ.addElement(prodServ);
                });
            }
        }

        //addEventMouse(this.list_prod_serv_search);
        //           else {
//            this.list_prod_serv_search.setVisible(false);
//        }
    }

    private void getPriceSum() {
        double sum = 0;
        for (int i = 0; i < table_view_products.getRowCount(); i++) {
            sum = sum + Double.parseDouble(table_view_products.getValueAt(i, 3).toString());
        }

        txt_total.setText(Double.toString(sum));
        txt_due.setText(txt_total.getText()); //set total to the due field
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

    public void clearFields(JTable table) {
        //Clean all Fields 
        lbl_auto_order_no.setText("");
        txt_first_name.setText("");
        txt_last_name.setText("");
        txt_contact.setText("");
        txt_email.setText("");
        txt_brand.setText("");
        txt_model.setText("");
        txt_serial_number.setText("");
        txt_search_fault.setText("");
        editor_pane_notes.setText("");
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
                | txt_model.getText().trim().isEmpty() | txt_serial_number.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please, check Empty fields", "New Order", JOptionPane.ERROR_MESSAGE);

        } else {
            orderNo = lbl_auto_order_no.getText();
            firstName = txt_first_name.getText().toUpperCase();
            lastName = txt_last_name.getText().toUpperCase();
            contactNo = txt_contact.getText().replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
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

//            for (int j = 0; j < table_view_products.getRowCount(); j++) {
//                vecProducts.add(table_view_products.getValueAt(j, 0));
//                vecQty.add(table_view_products.getValueAt(j, 1));
//                vecUnitPrice.add(table_view_products.getValueAt(j, 2));
//                vecPriceTotal.add(table_view_products.getValueAt(j, 3));
//            }
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
        contactNo = txt_contact.getText().replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
        try {
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
            } else {
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
            Logger.getLogger(NewOrderViewTeste.class.getName()).log(Level.SEVERE, null, ex);
        }

        return isContactNo;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_order_details = new javax.swing.JPanel();
        panel_input_detail = new javax.swing.JPanel();
        lbl_order_no = new javax.swing.JLabel();
        lbl_auto_order_no = new javax.swing.JLabel();
        lbl_first_name = new javax.swing.JLabel();
        txt_first_name = new javax.swing.JTextField();
        lbl_last_name = new javax.swing.JLabel();
        txt_last_name = new javax.swing.JTextField();
        lbl_contact = new javax.swing.JLabel();
        txt_contact = new javax.swing.JFormattedTextField();
        btn_international_number = new javax.swing.JButton();
        btn_copy = new javax.swing.JButton();
        lbl_email = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        lbl_brand = new javax.swing.JLabel();
        txt_brand = new javax.swing.JTextField();
        lbl_model = new javax.swing.JLabel();
        txt_model = new javax.swing.JTextField();
        lbl_sn = new javax.swing.JLabel();
        txt_serial_number = new javax.swing.JTextField();
        scroll_pane_notes = new javax.swing.JScrollPane();
        editor_pane_notes = new javax.swing.JEditorPane();
        lbl_first_name_star = new javax.swing.JLabel();
        lbl_last_name_star = new javax.swing.JLabel();
        lbl_contact_star = new javax.swing.JLabel();
        lbl_dev_model_star = new javax.swing.JLabel();
        lbl_serial_number_star = new javax.swing.JLabel();
        lbl_bad_sectors_star = new javax.swing.JLabel();
        lbl_bad_sectors = new javax.swing.JLabel();
        txt_bad_sectors = new javax.swing.JTextField();
        lbl_dev_brand_star = new javax.swing.JLabel();
        panel_order_buttons = new javax.swing.JPanel();
        btn_save_order = new javax.swing.JButton();
        btn_cancel = new javax.swing.JButton();
        panel_total_amount = new javax.swing.JPanel();
        lbl_total = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        lbl_deposit = new javax.swing.JLabel();
        txt_deposit = new javax.swing.JTextField();
        lbl_due = new javax.swing.JLabel();
        txt_due = new javax.swing.JTextField();
        panel_fault_prod_serv = new javax.swing.JPanel();
        panel_fault = new javax.swing.JPanel();
        txt_search_fault = new javax.swing.JTextField();
        list_fault_search = new javax.swing.JList<>();
        scroll_pane_faults = new javax.swing.JScrollPane();
        table_view_faults = new javax.swing.JTable();
        txt_search_prod_serv = new javax.swing.JTextField();
        layered_pane_list_prod_serv = new javax.swing.JLayeredPane();
        list_prod_serv_search = new javax.swing.JList<>();
        scroll_pane_products = new javax.swing.JScrollPane();
        table_view_products = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("New Order");
        setMaximumSize(new java.awt.Dimension(1049, 700));
        setPreferredSize(new java.awt.Dimension(1050, 650));
        setSize(new java.awt.Dimension(1050, 650));

        panel_order_details.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_order_details.setPreferredSize(new java.awt.Dimension(1026, 607));

        panel_input_detail.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbl_order_no.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        lbl_order_no.setText("Order :");

        lbl_auto_order_no.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_auto_order_no.setText("autoGen");

        lbl_first_name.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        lbl_first_name.setText("First Name");

        txt_first_name.setFocusCycleRoot(true);
        txt_first_name.setNextFocusableComponent(txt_last_name);
        txt_first_name.setPreferredSize(new java.awt.Dimension(339, 25));
        txt_first_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_first_nameKeyPressed(evt);
            }
        });

        lbl_last_name.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        lbl_last_name.setText("Last Name");

        txt_last_name.setNextFocusableComponent(txt_contact);
        txt_last_name.setPreferredSize(new java.awt.Dimension(342, 25));
        txt_last_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_last_nameKeyPressed(evt);
            }
        });

        lbl_contact.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        lbl_contact.setText("Contact No.");

        try {
            txt_contact.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(0##) ###-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_contact.setNextFocusableComponent(txt_email);
        txt_contact.setPreferredSize(new java.awt.Dimension(224, 25));
        txt_contact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_contactActionPerformed(evt);
            }
        });

        btn_international_number.setBackground(new java.awt.Color(0, 0, 0));
        btn_international_number.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_international_number.png"))); // NOI18N
        btn_international_number.setPreferredSize(new java.awt.Dimension(35, 25));
        btn_international_number.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_international_numberActionPerformed(evt);
            }
        });

        btn_copy.setBackground(new java.awt.Color(0, 0, 0));
        btn_copy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_copy.png"))); // NOI18N
        btn_copy.setPreferredSize(new java.awt.Dimension(35, 25));
        btn_copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_copyActionPerformed(evt);
            }
        });

        lbl_email.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        lbl_email.setText("Email");

        txt_email.setNextFocusableComponent(txt_brand);
        txt_email.setPreferredSize(new java.awt.Dimension(388, 25));

        lbl_brand.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        lbl_brand.setText("Device Brand");

        txt_brand.setMinimumSize(new java.awt.Dimension(63, 20));
        txt_brand.setNextFocusableComponent(txt_model);
        txt_brand.setPreferredSize(new java.awt.Dimension(322, 25));
        txt_brand.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_brandKeyPressed(evt);
            }
        });

        lbl_model.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        lbl_model.setText("Device Model");

        txt_model.setNextFocusableComponent(txt_serial_number);
        txt_model.setPreferredSize(new java.awt.Dimension(320, 25));
        txt_model.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_modelKeyPressed(evt);
            }
        });

        lbl_sn.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        lbl_sn.setText("Serial Number");

        txt_serial_number.setNextFocusableComponent(editor_pane_notes);
        txt_serial_number.setPreferredSize(new java.awt.Dimension(313, 25));

        scroll_pane_notes.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_pane_notes.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scroll_pane_notes.setVerifyInputWhenFocusTarget(false);

        editor_pane_notes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Important Notes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 14))); // NOI18N
        editor_pane_notes.setFocusCycleRoot(false);
        editor_pane_notes.setNextFocusableComponent(txt_search_fault);
        editor_pane_notes.setPreferredSize(new java.awt.Dimension(403, 58));
        editor_pane_notes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                editor_pane_notesKeyPressed(evt);
            }
        });
        scroll_pane_notes.setViewportView(editor_pane_notes);

        lbl_first_name_star.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_first_name_star.setForeground(java.awt.Color.red);
        lbl_first_name_star.setText("*");

        lbl_last_name_star.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_last_name_star.setForeground(java.awt.Color.red);
        lbl_last_name_star.setText("*");

        lbl_contact_star.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_contact_star.setForeground(java.awt.Color.red);
        lbl_contact_star.setText("*");

        lbl_dev_model_star.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_dev_model_star.setForeground(java.awt.Color.red);
        lbl_dev_model_star.setText("*");

        lbl_serial_number_star.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_serial_number_star.setForeground(java.awt.Color.red);
        lbl_serial_number_star.setText("*");

        lbl_bad_sectors_star.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_bad_sectors_star.setForeground(java.awt.Color.red);
        lbl_bad_sectors_star.setText("*");

        lbl_bad_sectors.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        lbl_bad_sectors.setText("Bad Sectors");

        txt_bad_sectors.setNextFocusableComponent(editor_pane_notes);
        txt_bad_sectors.setPreferredSize(new java.awt.Dimension(331, 25));

        lbl_dev_brand_star.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        lbl_dev_brand_star.setForeground(java.awt.Color.red);
        lbl_dev_brand_star.setText("*");

        javax.swing.GroupLayout panel_input_detailLayout = new javax.swing.GroupLayout(panel_input_detail);
        panel_input_detail.setLayout(panel_input_detailLayout);
        panel_input_detailLayout.setHorizontalGroup(
            panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_input_detailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_input_detailLayout.createSequentialGroup()
                        .addGroup(panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_first_name_star, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_last_name_star, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_contact_star, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_input_detailLayout.createSequentialGroup()
                                .addComponent(lbl_contact)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_contact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_international_number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_copy, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_input_detailLayout.createSequentialGroup()
                                .addComponent(lbl_last_name)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_last_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panel_input_detailLayout.createSequentialGroup()
                                .addComponent(lbl_first_name)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_first_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(scroll_pane_notes)
                    .addGroup(panel_input_detailLayout.createSequentialGroup()
                        .addGroup(panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lbl_bad_sectors_star, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_serial_number_star, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_dev_model_star, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lbl_dev_brand_star, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel_input_detailLayout.createSequentialGroup()
                                .addComponent(lbl_brand)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_brand, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_input_detailLayout.createSequentialGroup()
                                .addComponent(lbl_model)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_model, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_input_detailLayout.createSequentialGroup()
                                .addComponent(lbl_sn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_serial_number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_input_detailLayout.createSequentialGroup()
                                .addComponent(lbl_bad_sectors)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_bad_sectors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panel_input_detailLayout.createSequentialGroup()
                        .addComponent(lbl_order_no)
                        .addGap(7, 7, 7)
                        .addComponent(lbl_auto_order_no)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panel_input_detailLayout.createSequentialGroup()
                        .addComponent(lbl_email)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_input_detailLayout.setVerticalGroup(
            panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_input_detailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_auto_order_no)
                    .addComponent(lbl_order_no))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_first_name)
                    .addComponent(lbl_first_name_star, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_last_name)
                    .addComponent(txt_last_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_last_name_star, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_copy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_international_number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_contact)
                        .addComponent(txt_contact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_contact_star, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_email))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_brand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_brand)
                    .addComponent(lbl_dev_brand_star, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_model, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_model))
                    .addComponent(lbl_dev_model_star, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_serial_number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_sn)
                    .addComponent(lbl_serial_number_star))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_input_detailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_bad_sectors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_bad_sectors)
                    .addComponent(lbl_bad_sectors_star))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_pane_notes, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel_order_buttons.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_save_order.setBackground(new java.awt.Color(21, 76, 121));
        btn_save_order.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        btn_save_order.setForeground(new java.awt.Color(255, 255, 255));
        btn_save_order.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_save.png"))); // NOI18N
        btn_save_order.setText("Save");
        btn_save_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_save_orderActionPerformed(evt);
            }
        });

        btn_cancel.setBackground(new java.awt.Color(21, 76, 121));
        btn_cancel.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        btn_cancel.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icon_cancel.png"))); // NOI18N
        btn_cancel.setText("Cancel");
        btn_cancel.setNextFocusableComponent(txt_first_name);
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_order_buttonsLayout = new javax.swing.GroupLayout(panel_order_buttons);
        panel_order_buttons.setLayout(panel_order_buttonsLayout);
        panel_order_buttonsLayout.setHorizontalGroup(
            panel_order_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_order_buttonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_save_order)
                .addGap(18, 18, 18)
                .addComponent(btn_cancel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_order_buttonsLayout.setVerticalGroup(
            panel_order_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_order_buttonsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_order_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_save_order)
                    .addComponent(btn_cancel))
                .addContainerGap())
        );

        panel_total_amount.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbl_total.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_total.setText("Total €");
        lbl_total.setPreferredSize(null);

        txt_total.setEditable(false);
        txt_total.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        txt_total.setPreferredSize(new java.awt.Dimension(164, 25));

        lbl_deposit.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_deposit.setText("Deposit €");
        lbl_deposit.setPreferredSize(null);

        txt_deposit.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        txt_deposit.setForeground(new java.awt.Color(51, 51, 255));
        txt_deposit.setNextFocusableComponent(btn_save_order);
        txt_deposit.setPreferredSize(new java.awt.Dimension(146, 25));
        txt_deposit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_depositKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_depositKeyReleased(evt);
            }
        });

        lbl_due.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbl_due.setText("Due €");
        lbl_due.setPreferredSize(null);

        txt_due.setEditable(false);
        txt_due.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        txt_due.setForeground(new java.awt.Color(255, 0, 51));
        txt_due.setPreferredSize(new java.awt.Dimension(174, 25));

        javax.swing.GroupLayout panel_total_amountLayout = new javax.swing.GroupLayout(panel_total_amount);
        panel_total_amount.setLayout(panel_total_amountLayout);
        panel_total_amountLayout.setHorizontalGroup(
            panel_total_amountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_total_amountLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_total_amountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_total_amountLayout.createSequentialGroup()
                        .addComponent(lbl_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_total_amountLayout.createSequentialGroup()
                        .addComponent(lbl_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_deposit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_total_amountLayout.createSequentialGroup()
                        .addComponent(lbl_due, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_due, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_total_amountLayout.setVerticalGroup(
            panel_total_amountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_total_amountLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_total_amountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_total_amountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_total_amountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_due, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_due, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panel_fault_prod_serv.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txt_search_fault.setPreferredSize(new java.awt.Dimension(495, 25));
        txt_search_fault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_search_faultActionPerformed(evt);
            }
        });
        txt_search_fault.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_search_faultKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_search_faultKeyReleased(evt);
            }
        });

        list_fault_search.setBorder(null);
        list_fault_search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                list_fault_searchMouseClicked(evt);
            }
        });

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
        table_view_faults.setShowGrid(false);
        table_view_faults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_view_faultsMouseClicked(evt);
            }
        });
        scroll_pane_faults.setViewportView(table_view_faults);

        javax.swing.GroupLayout panel_faultLayout = new javax.swing.GroupLayout(panel_fault);
        panel_fault.setLayout(panel_faultLayout);
        panel_faultLayout.setHorizontalGroup(
            panel_faultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_faultLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(list_fault_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panel_faultLayout.createSequentialGroup()
                .addGroup(panel_faultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_faultLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txt_search_fault, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scroll_pane_faults, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_faultLayout.setVerticalGroup(
            panel_faultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_faultLayout.createSequentialGroup()
                .addGroup(panel_faultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_faultLayout.createSequentialGroup()
                        .addComponent(txt_search_fault, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(list_fault_search))
                    .addGroup(panel_faultLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(scroll_pane_faults, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_fault_prod_servLayout = new javax.swing.GroupLayout(panel_fault_prod_serv);
        panel_fault_prod_serv.setLayout(panel_fault_prod_servLayout);
        panel_fault_prod_servLayout.setHorizontalGroup(
            panel_fault_prod_servLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_fault_prod_servLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_fault, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel_fault_prod_servLayout.setVerticalGroup(
            panel_fault_prod_servLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_fault_prod_servLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_fault, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txt_search_prod_serv.setNextFocusableComponent(list_prod_serv_search);
        txt_search_prod_serv.setPreferredSize(new java.awt.Dimension(495, 25));
        txt_search_prod_serv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_search_prod_servFocusLost(evt);
            }
        });
        txt_search_prod_serv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_search_prod_servActionPerformed(evt);
            }
        });
        txt_search_prod_serv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_search_prod_servKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_search_prod_servKeyReleased(evt);
            }
        });

        layered_pane_list_prod_serv.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        list_prod_serv_search.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        list_prod_serv_search.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        list_prod_serv_search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                list_prod_serv_searchMousePressed(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                list_prod_serv_searchMouseEntered(evt);
            }
        });
        list_prod_serv_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                list_prod_serv_searchKeyReleased(evt);
            }
        });
        list_prod_serv_search.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                list_prod_serv_searchValueChanged(evt);
            }
        });
        layered_pane_list_prod_serv.add(list_prod_serv_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 530, -1));

        table_view_products.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product | Service", "Qty", "Unit €", "Total €"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
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
        scroll_pane_products.setViewportView(table_view_products);
        if (table_view_products.getColumnModel().getColumnCount() > 0) {
            table_view_products.getColumnModel().getColumn(0).setMinWidth(0);
            table_view_products.getColumnModel().getColumn(0).setPreferredWidth(0);
            table_view_products.getColumnModel().getColumn(0).setMaxWidth(0);
            table_view_products.getColumnModel().getColumn(2).setPreferredWidth(40);
            table_view_products.getColumnModel().getColumn(2).setMaxWidth(60);
            table_view_products.getColumnModel().getColumn(3).setPreferredWidth(80);
            table_view_products.getColumnModel().getColumn(3).setMaxWidth(120);
            table_view_products.getColumnModel().getColumn(4).setPreferredWidth(80);
            table_view_products.getColumnModel().getColumn(4).setMaxWidth(120);
        }

        layered_pane_list_prod_serv.add(scroll_pane_products, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 220));

        javax.swing.GroupLayout panel_order_detailsLayout = new javax.swing.GroupLayout(panel_order_details);
        panel_order_details.setLayout(panel_order_detailsLayout);
        panel_order_detailsLayout.setHorizontalGroup(
            panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_order_buttons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panel_input_detail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panel_total_amount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(layered_pane_list_prod_serv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(panel_fault_prod_serv, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(txt_search_prod_serv, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        panel_order_detailsLayout.setVerticalGroup(
            panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_order_detailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_order_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(panel_fault_prod_serv, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_search_prod_serv, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(layered_pane_list_prod_serv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_order_detailsLayout.createSequentialGroup()
                        .addComponent(panel_input_detail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panel_total_amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(panel_order_buttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel_order_details, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_order_details, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                        clearFields(table_view_faults);
                        //clearFields(table_view_products);
                        //Generate new OrderNo.
                        autoOrderId();

                        String issueDateFormat = new SimpleDateFormat("dd/MM/yyyy").format(currentDate);
                        //order.setIssueDate(issueDateFormat);

                        //PrintOrder printOrder = new PrintOrder(order, completedOrder, isOrderDetails, formatContactNo);
                        //printOrder.setVisible(true);
                        ps.close();
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(NewOrderViewTeste.class.getName()).log(Level.SEVERE, null, ex);
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

    private void txt_depositKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_depositKeyPressed
        //Accepts number characters only
        if (Character.isLetter(evt.getKeyChar())) {
            txt_deposit.setEditable(false);
        } else {
            txt_deposit.setEditable(true);
        }
    }//GEN-LAST:event_txt_depositKeyPressed

    private void table_view_faultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_faultsMouseClicked
        // TODO add your handling code here:
        //Delete fault item of the selected row (Function is called with 2 clicks)
        DefaultTableModel dtm = (DefaultTableModel) table_view_faults.getModel();

        if (evt.getClickCount() == 2) {
            int confirmDeletion = JOptionPane.showConfirmDialog(null, "Remove This Item ?", "Remove Faults", JOptionPane.YES_NO_OPTION);
            if (confirmDeletion == 0) {
                dtm.removeRow(table_view_faults.getSelectedRow());
            }
        }
    }//GEN-LAST:event_table_view_faultsMouseClicked

    private void editor_pane_notesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editor_pane_notesKeyPressed
        if (evt.isShiftDown() && evt.getKeyCode() == KeyEvent.VK_TAB) {
            txt_serial_number.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txt_search_fault.requestFocus();
        }
    }//GEN-LAST:event_editor_pane_notesKeyPressed

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

    private void btn_copyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_copyActionPerformed
        StringSelection stringSelection = new StringSelection(txt_contact.getText().replace("(", "").replace(")", "").replace("-", "").replace(" ", ""));
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        JOptionPane.showMessageDialog(this, txt_contact.getText() + " Copied to Clipboard");
    }//GEN-LAST:event_btn_copyActionPerformed

    private void btn_international_numberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_international_numberActionPerformed
        txt_contact.setFormatterFactory(null);
        txt_contact.setText("");
        txt_contact.requestFocus();
    }//GEN-LAST:event_btn_international_numberActionPerformed

    private void txt_contactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_contactActionPerformed
        try {
            String query = "SELECT * FROM customers WHERE contactNo = ? ";
            ps = con.prepareStatement(query);
            ps.setString(1, contactNo = txt_contact.getText().replace("(", "").replace(")", "").replace("-", "").replace(" ", ""));
            rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "Customer not found in the Database", "New Customer", JOptionPane.ERROR_MESSAGE);
            } else {
                while (rs.next()) {
                    txt_first_name.setText(rs.getString("firstName"));
                    txt_last_name.setText(rs.getString("lastName"));
                    txt_contact.setText(rs.getString("contactNo"));
                    txt_email.setText(rs.getString("email"));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(NewOrderViewTeste.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txt_contactActionPerformed

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

    private void txt_search_faultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_search_faultActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_search_faultActionPerformed

    private void txt_search_faultKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_faultKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_search_faultKeyTyped

    private void txt_search_faultKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_faultKeyReleased
        searchFault();
    }//GEN-LAST:event_txt_search_faultKeyReleased

    private void list_fault_searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_list_fault_searchMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_list_fault_searchMouseClicked

    private void txt_search_prod_servActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_search_prod_servActionPerformed
        //        DefaultTableModel dtm = (DefaultTableModel) table_view_faults.getModel();
        //        ArrayList<String> tableFaultsList = new ArrayList<>();
        //        String faultText = txt_fault.getText().replace(" ", "");
        //        String tableFault = "";
        //        Vector faultsVector = new Vector();
        //
        //        for (int i = 0; i < dtm.getRowCount(); i++) {
        //            tableFault = dtm.getValueAt(i, 0).toString().replace(" ", "");
        //            tableFaultsList.add(tableFault);
        //        }
        //        if (faultText.isEmpty()) {
        //            JOptionPane.showMessageDialog(this, "Please add a Fault!", "Faults", JOptionPane.ERROR_MESSAGE);
        //        } else if (tableFaultsList.contains(faultText)) {
        //            JOptionPane.showMessageDialog(this, "Item '" + faultText + "' already added !", "Faults", JOptionPane.ERROR_MESSAGE);
        //            txt_fault.setText("");
        //        } else {
        //            try {
        //                faultText = txt_fault.getText();
        //
        //                String queryCheck = "SELECT * FROM faults WHERE faultName = ?";
        //                ps = con.prepareStatement(queryCheck);
        //                ps.setString(1, faultText);
        //                rs = ps.executeQuery();
        //
        //                if (!rs.isBeforeFirst()) {
        //                    int confirmInsertion = JOptionPane.showConfirmDialog(null, "Do you want to add a new fault ?", "Add New Fault", JOptionPane.YES_NO_OPTION);
        //                    if (confirmInsertion == 0) {
        //                        String query = "INSERT INTO faults (faultName) VALUES(?)";
        //                        ps = con.prepareStatement(query);
        //                        ps.setString(1, faultText);
        //                        ps.executeUpdate();
        //
        //                        faultsVector.add(faultText);
        //                        dtm.addRow(faultsVector);
        //                        txt_fault.setText("");
        //                    } else {
        //                        txt_fault.setText("");
        //                    }
        //                } else {
        //                    faultsVector.add(faultText);
        //                    dtm.addRow(faultsVector);
        //                    txt_fault.setText("");
        //                }
        //
        //                ps.close();
        //                con.close();
        //            } catch (SQLException ex) {
        //                Logger.getLogger(NewOrderView.class.getName()).log(Level.SEVERE, null, ex);
        //            }
        //        }
    }//GEN-LAST:event_txt_search_prod_servActionPerformed

    private void txt_search_prod_servKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_prod_servKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_search_prod_servKeyTyped

    private void txt_search_prod_servKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_search_prod_servKeyReleased
        searchProdServ();
    }//GEN-LAST:event_txt_search_prod_servKeyReleased

    private void list_prod_serv_searchMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_list_prod_serv_searchMousePressed
        if (!this.list_prod_serv_search.isSelectionEmpty()) {
            ProductService prodServ = (ProductService) this._defaultListModelProdServ.getElementAt(this.list_prod_serv_search.getSelectedIndex());
            boolean isAdded = false;

            if (this._dtmProdServ.getRowCount() > 0) {
                for (int i = 0; i < _dtmProdServ.getRowCount(); i++) {
                    int idProdServ = Integer.valueOf(this._dtmProdServ.getValueAt(i, 0).toString());
                    if (idProdServ == prodServ.getIdProductService()) {
                        isAdded = true;
                        break;
                    }
                }
            }

            if (!isAdded) {
                this._dtmProdServ.addRow(
                        new Object[]{
                            prodServ.getIdProductService(),
                            prodServ.getProdServName(),
                            CommonSetting.DEFAULT_QTY,
                            CommonExtension.getPriceFormat(prodServ.getPrice()),
                            CommonExtension.getPriceFormat(prodServ.getPrice() * prodServ.getQty())
                        }
                );

                this.txt_search_prod_serv.setText("");
                this._defaultListModelProdServ.removeAllElements();
                getPriceSum();
            }
        }
    }//GEN-LAST:event_list_prod_serv_searchMousePressed

    private void table_view_productsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_view_productsMouseClicked
        //Delete products|Service item of the selected row (Function is called with 2 clicks)
        DefaultTableModel dtm = (DefaultTableModel) table_view_products.getModel();

        if (evt.getClickCount() == 2) {
            int confirmDeletion = JOptionPane.showConfirmDialog(null, "Remove This Item ?", "Remove Product|Service", JOptionPane.YES_NO_OPTION);
            if (confirmDeletion == 0) {
                dtm.removeRow(table_view_products.getSelectedRow());
                // Sum price column and set into total textField
                //getPriceSum();
            }
        }
    }//GEN-LAST:event_table_view_productsMouseClicked

    private void table_view_productsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_view_productsKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
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

    private void txt_search_prod_servFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_search_prod_servFocusLost
        this.txt_search_prod_serv.setText("");
        this._defaultListModelProdServ.removeAllElements();
    }//GEN-LAST:event_txt_search_prod_servFocusLost

    private void list_prod_serv_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_list_prod_serv_searchKeyReleased

    }//GEN-LAST:event_list_prod_serv_searchKeyReleased

    private void list_prod_serv_searchValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_list_prod_serv_searchValueChanged
    }//GEN-LAST:event_list_prod_serv_searchValueChanged

    private void list_prod_serv_searchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_list_prod_serv_searchMouseEntered
//        MouseListener mouseListener = new MouseAdapter() {
//                @Override
//                public void mouseEntered(MouseEvent mouseEvent) {
//                    JList theList = (JList) mouseEvent.getSource();
//                    //if (mouseEvent.getClickCount() == 2) {
//                        int index = theList.locationToIndex(mouseEvent.getPoint());
//                        
//                        if (index >= 0) {
//                            Color o = (Color) theList.getModel().getElementAt(index);
//                            setBackground(o);
//                            
//                            //theList.setBackground(Color.red);
//                            
//                            //System.out.println("EnteredPoint: " + o.toString());
//                            
//                        }
//                    //}
//                }
//            };
//            
//            this.list_prod_serv_search.addMouseListener(mouseListener);
    }//GEN-LAST:event_list_prod_serv_searchMouseEntered

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_copy;
    private javax.swing.JButton btn_international_number;
    private javax.swing.JButton btn_save_order;
    private javax.swing.JEditorPane editor_pane_notes;
    private javax.swing.JLayeredPane layered_pane_list_prod_serv;
    private javax.swing.JLabel lbl_auto_order_no;
    private javax.swing.JLabel lbl_bad_sectors;
    private javax.swing.JLabel lbl_bad_sectors_star;
    private javax.swing.JLabel lbl_brand;
    private javax.swing.JLabel lbl_contact;
    private javax.swing.JLabel lbl_contact_star;
    private javax.swing.JLabel lbl_deposit;
    private javax.swing.JLabel lbl_dev_brand_star;
    private javax.swing.JLabel lbl_dev_model_star;
    private javax.swing.JLabel lbl_due;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_first_name;
    private javax.swing.JLabel lbl_first_name_star;
    private javax.swing.JLabel lbl_last_name;
    private javax.swing.JLabel lbl_last_name_star;
    private javax.swing.JLabel lbl_model;
    private javax.swing.JLabel lbl_order_no;
    private javax.swing.JLabel lbl_serial_number_star;
    private javax.swing.JLabel lbl_sn;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JList<String> list_fault_search;
    private javax.swing.JList<String> list_prod_serv_search;
    private javax.swing.JPanel panel_fault;
    private javax.swing.JPanel panel_fault_prod_serv;
    private javax.swing.JPanel panel_input_detail;
    private javax.swing.JPanel panel_order_buttons;
    private javax.swing.JPanel panel_order_details;
    private javax.swing.JPanel panel_total_amount;
    private javax.swing.JScrollPane scroll_pane_faults;
    private javax.swing.JScrollPane scroll_pane_notes;
    private javax.swing.JScrollPane scroll_pane_products;
    private javax.swing.JTable table_view_faults;
    private javax.swing.JTable table_view_products;
    private javax.swing.JTextField txt_bad_sectors;
    private javax.swing.JTextField txt_brand;
    private javax.swing.JFormattedTextField txt_contact;
    private javax.swing.JTextField txt_deposit;
    private javax.swing.JTextField txt_due;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_first_name;
    private javax.swing.JTextField txt_last_name;
    private javax.swing.JTextField txt_model;
    private javax.swing.JTextField txt_search_fault;
    private javax.swing.JTextField txt_search_prod_serv;
    private javax.swing.JTextField txt_serial_number;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
