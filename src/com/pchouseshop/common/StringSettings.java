/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pchouseshop.common;

import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author herman
 */
public class StringSettings {
    
    //UIManager.getLookAndFeelDefaults().put("defaulFont", new Font("Arial", Font.BOLD, 14));
    public static void setFont(FontUIResource fontUIResource) {
        Font font = new Font("Arial", Font.BOLD, 16);
        UIManager.put("Label.font", fontUIResource);
    }
    
    //StringSettings.setFont(new FontUIResource(new Font("Cabin", Font.PLAIN, 10)));
}
