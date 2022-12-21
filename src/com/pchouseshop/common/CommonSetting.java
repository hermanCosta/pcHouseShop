package com.pchouseshop.common;

import com.pchouseshop.model.Company;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyVetoException;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class CommonSetting {

    Color defaultColor = new Color(21, 76, 121);
    Color clickedColor = new Color(118, 181, 197);
    public static int ID_COMPANY;
    public static Company COMPANY;
    public static JDesktopPane MAIN_MENU_DESKTOP_PANE;
    public static int DEFAULT_QTY = 1;

    public static void tableSettings(JTable jTable) {
        jTable.setRowHeight(20);
        jTable.getTableHeader().setFont(new Font("Lucida Grande", Font.BOLD, 12));
    }

    public static void defaultColorPanelAndLabel(JPanel jPanel, JLabel jLabel) {
        Color defaultColor = new Color(21, 76, 121);
        jPanel.setBackground(defaultColor);
        jLabel.setBackground(defaultColor);
    }

    public static void defaultColorLabel(JLabel jLabel) {
        Color defaultColor = new Color(21, 76, 121);
        jLabel.setBackground(defaultColor);
    }

    public static void scrollPaneSettings(JScrollPane jScrollPane) {
        jScrollPane.setOpaque(false);
        jScrollPane.getViewport().setOpaque(false);
    }

    public static void requestTxtFocus(JTextField jTextField) {
        SwingUtilities.invokeLater(() -> {
            jTextField.requestFocus();
        });
    }

    public static void removeInternalFrameBorder(JInternalFrame jInternalFrame) {
        jInternalFrame.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) jInternalFrame.getUI();
        ui.setNorthPane(null);
    }

    public static void setMaxInternalFrame(JInternalFrame jInternalFrame) {
        try {
            jInternalFrame.setMaximum(true);
        } catch (PropertyVetoException e) {
        }
    }

    public static PrinterJob printReport(JPanel jPanel, String pCategory) {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setJobName("Print Report " + pCategory);
        PageFormat format = printerJob.getPageFormat(null);

        printerJob.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }
                Graphics2D graphics2D = (Graphics2D) graphics;
                jPanel.paint(graphics2D);

                return Printable.PAGE_EXISTS;
            }
        }, format);

        return printerJob;
    }

    public static void fitContentJtable(JTable jTable) {
        jTable = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                int rendererWidth = component.getPreferredSize().width;
                TableColumn tableColumn = getColumnModel().getColumn(column);
                tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
                return component;
            }
        };
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }
    
    
}
