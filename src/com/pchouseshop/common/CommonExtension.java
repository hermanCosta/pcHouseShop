package com.pchouseshop.common;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Locale;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class CommonExtension {

    public static int setIdExtension(JTextField jTextField) {
        if (jTextField.getText().trim().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(jTextField.getText());
        }
    }
    
    public static long setLongIdExtension(Object object) {
        if (object == null) {
            return 0;
        } else {
            return (long) object;
        }
    }

    public static String joinCustomFields(JTextField jLabelField, JTextField jTextField) {
        String strCustom = "";
        if (!jLabelField.getText().trim().isEmpty() || !jTextField.getText().trim().isEmpty()) {
            strCustom = jLabelField.getText() + "##" + jTextField.getText().toUpperCase();
            return strCustom;
        } else {
            return strCustom;
        }
    }

    public static String joinCustomRefurbFields(JTextField jLabelField, JTextField jTextField) {
        String fullCustomField = null;

        if ((jLabelField.getText().toUpperCase().equals("CUSTOMSPEC") && jTextField.getText().trim().isEmpty())
                || (jLabelField.getText().trim().isEmpty() && jTextField.getText().trim().isEmpty())) {
            fullCustomField = "";
        } else {
            fullCustomField = jLabelField.getText() + "##" + jTextField.getText().toUpperCase();
        }
        return fullCustomField;
    }

    public static String[] splitCustomRefurbString(String strCustom) {
        String[] arrayStr = {"", ""};

        if (strCustom == null || strCustom.trim().isEmpty()) {
            return arrayStr;
        } else {
            arrayStr = strCustom.split("##");
            return arrayStr;
        }
    }

    public static void showHideCustomField(JTextField jLabelField, JTextField jTextField) {
        if (jLabelField.getText().trim().isEmpty() && jTextField.getText().trim().isEmpty()) {
            jLabelField.setText("");
            jTextField.setText("");
            jLabelField.setVisible(false);
            jTextField.setVisible(false);
        } else {
            jLabelField.setVisible(true);
            jTextField.setVisible(true);
        }
    }

    public static String[] splitString(String str, String delimit) {
        String[] arrayString = new String[2];
        if (!str.trim().isEmpty()) {
            arrayString = str.split(delimit);
        }
        return arrayString;
    }

    public static String getEuroFormat(double value) {
        Locale ireland = new Locale("en", "IE");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(ireland);

        return currencyFormatter.format(value);
    }

    public static String getPriceFormat(double price) {
        int intPrice = (int) price;

        if ((price - intPrice) > 0) {
            return String.valueOf(price);
        } else {
            return String.valueOf(intPrice);
        }
    }

    public static void checkEmailFormat(JTextField jTextField) {
        jTextField.setInputVerifier(new InputVerifier() {

            Border originalBorder;
            String emailFormat = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

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

    public static String encryptPassword(JPasswordField jPasswordField) {
        String encodePassword = null;

        Encoder encoder = Base64.getEncoder();
        encodePassword = encoder.encodeToString(String.valueOf(jPasswordField.getPassword()).getBytes());

        return encodePassword;
    }

    public static String requestUserPassword() {
        PasswordPanel passwordPanel = new PasswordPanel();
        String encodePassword = "";
        JOptionPane op = new JOptionPane(passwordPanel, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        JDialog dlg = op.createDialog("User Authentication");

        // Wire up FocusListener to ensure JPasswordField is able to request focus when the dialog is first shown.
        dlg.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                passwordPanel.gainedFocus();
            }
        });

        dlg.setVisible(true);
        if (op.getValue() != null && op.getValue().equals(JOptionPane.OK_OPTION)) {
            String passStr = new String(passwordPanel.getPassword());

            //Encrypt password
            Encoder encoder = Base64.getEncoder();
            encodePassword = encoder.encodeToString(passStr.getBytes());
        }
        return encodePassword;
    }

    public static java.util.Date parseTimestamp(String timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return dateFormat.parse(timestamp);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void autoCompleteTextField(ArrayList<String> list, String text, JTextField field) {
        String complete = "";
        int start = text.length();
        int last = text.length();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).startsWith(text)) {
                complete = list.get(i);
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
}
