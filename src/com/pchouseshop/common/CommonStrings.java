package com.pchouseshop.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonStrings {

    public static String formatDateToString(Date date) {
        String stringDate = null;
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            stringDate = dateFormat.format(date);
        }
        return stringDate;
    }
}
