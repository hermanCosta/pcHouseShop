package com.pchouseshop.common;

import javax.swing.JTextField;

public class CommonExtension {

    public static int setIdExtension(JTextField jTextField) {
        if (jTextField.getText().trim().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(jTextField.getText());
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

    public static String[] splitString(String str, String delimit) {
        String[] arrayString = null;
        if (!str.trim().isEmpty()) {
            arrayString = str.split(delimit);
        }
        return arrayString;
    }
}
