package com.pchouseshop.common;

import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JTextField;

public class CommonExtension {

    public static int setIdExtension(JTextField jTextField) {
        if (jTextField.getText().trim().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(jTextField.getText());
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
}
