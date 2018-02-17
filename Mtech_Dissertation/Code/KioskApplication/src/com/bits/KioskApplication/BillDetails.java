/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.KioskApplication;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Krishna
 */
public class BillDetails extends javax.swing.JPanel implements Printable {

    DefaultTableModel tableModel;
    public static DecimalFormat df;
    static String IP;
    static String printer;
    Properties prop;
    InputStream input = null;

      //  static String  IP = "mycafeteria.elasticbeanstalk.com";
    /**
     * Creates new form BillDetails
     */
    public BillDetails() {
        try {
            initComponents();
            prop = new Properties();
            df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            df.setMinimumFractionDigits(2);
            input = new FileInputStream(System.getProperty("user.dir") + "/lib/config.properties");

            // load a properties file
            prop.load(input);
            IP = prop.getProperty("IP");
            printer = prop.getProperty("PRINTER");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BillDetails.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BillDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        itemsScrollPane = new javax.swing.JScrollPane();
        tableModel = new DefaultTableModel(new Object[]{"ItemName","Count","Price","Amount"},0);
        tbl_itemstable = new javax.swing.JTable();
        lbl_Orderid = new javax.swing.JLabel();
        lbl_orderidval = new javax.swing.JLabel();
        lbl_vendorName = new javax.swing.JLabel();
        lbl_vendorNameValue = new javax.swing.JLabel();
        lbl_userName = new javax.swing.JLabel();
        lbl_userNameValue = new javax.swing.JLabel();
        lbl_Status = new javax.swing.JLabel();
        lbl_StatusValue = new javax.swing.JLabel();
        lbl_TotalAmount = new javax.swing.JLabel();
        lbl_totalamountVal = new javax.swing.JLabel();
        lbl_TimeStamp = new javax.swing.JLabel();
        lbl_timestampValue = new javax.swing.JLabel();

        tbl_itemstable.setModel(tableModel);
        itemsScrollPane.setViewportView(tbl_itemstable);

        lbl_Orderid.setText("Order ID:");

        lbl_vendorName.setText("VendorName");

        lbl_userName.setText("UserName");

        lbl_Status.setText("Status");

        lbl_TotalAmount.setText("Total Amount");

        lbl_TimeStamp.setText("Date and Time");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(itemsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lbl_Orderid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_userName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_Status)
                                .addComponent(lbl_vendorName, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                            .addComponent(lbl_TotalAmount)
                            .addComponent(lbl_TimeStamp))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_orderidval, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_vendorNameValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_userNameValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_StatusValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_totalamountVal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_timestampValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Orderid)
                    .addComponent(lbl_orderidval))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_vendorName)
                    .addComponent(lbl_vendorNameValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_userName)
                    .addComponent(lbl_userNameValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Status)
                    .addComponent(lbl_StatusValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_TotalAmount)
                    .addComponent(lbl_totalamountVal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_TimeStamp)
                    .addComponent(lbl_timestampValue))
                .addGap(18, 18, 18)
                .addComponent(itemsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane itemsScrollPane;
    private javax.swing.JLabel lbl_Orderid;
    private javax.swing.JLabel lbl_Status;
    private javax.swing.JLabel lbl_StatusValue;
    private javax.swing.JLabel lbl_TimeStamp;
    private javax.swing.JLabel lbl_TotalAmount;
    private javax.swing.JLabel lbl_orderidval;
    private static javax.swing.JLabel lbl_timestampValue;
    private javax.swing.JLabel lbl_totalamountVal;
    private javax.swing.JLabel lbl_userName;
    private javax.swing.JLabel lbl_userNameValue;
    private javax.swing.JLabel lbl_vendorName;
    private javax.swing.JLabel lbl_vendorNameValue;
    private javax.swing.JTable tbl_itemstable;
    // End of variables declaration//GEN-END:variables

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (pageIndex > 0) {
            return Printable.NO_SUCH_PAGE;
        }

        Graphics2D g2 = (Graphics2D) graphics;
        g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        this.printAll(g2);
        return Printable.PAGE_EXISTS;
    }

    public void sendGET(String Code, int type) throws IOException {
        int rowCount = tableModel.getRowCount();
//Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
        if (type == 1) {
            URL obj = new URL("http://" + IP + "/webapi/order/bill/secretcode/" + Code);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            //con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                orderparsing(response.toString());
            } else {
                System.out.println("GET request not worked");
            }
        } else if (type == 2) {

            lbl_orderidval.setText("");
            lbl_vendorNameValue.setText("");
            lbl_userNameValue.setText("");
            lbl_StatusValue.setText("");
            lbl_totalamountVal.setText("");
            lbl_timestampValue.setText("");
        } else {
            System.out.println("Invalid option");

        }

    }

    public void orderparsing(String result) {

        try {

            JSONObject jsonRootObject = new JSONObject(result);
            String Status = jsonRootObject.optString("orderStatus");
            if (Status.equals("paid")) {
                lbl_orderidval.setText(jsonRootObject.optString("orderId"));
                lbl_vendorNameValue.setText(jsonRootObject.optString("vendorName"));
                lbl_userNameValue.setText(jsonRootObject.optString("userName"));
                lbl_StatusValue.setText(Status);
                // jLabel10.setText(jsonRootObject.optString("secretCode"));
                lbl_totalamountVal.setText(df.format(jsonRootObject.optDouble("amount")));
                lbl_timestampValue.setText(jsonRootObject.optString("timeStamp"));

                jsonRootObject.getJSONArray("itemList").toString();
                JSONArray itemlist = new JSONArray(jsonRootObject.getJSONArray("itemList").toString());

                for (int i = 0; i < itemlist.length(); i++) {
                    JSONObject item = itemlist.getJSONObject(i);
                    String itemname = item.optString("itemName");
                    Integer itemcount = item.optInt("itemCount");
                    Double valueitem = item.optDouble("price") * itemcount;
                    tableModel.addRow(new Object[]{itemname, itemcount, df.format(item.optDouble("price")), df.format(valueitem)});
                }

            } else {
                JOptionPane.showMessageDialog(this, "Invalid Code", "Alert",
                        JOptionPane.PLAIN_MESSAGE);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Invalid Code", "Alert",
                    JOptionPane.PLAIN_MESSAGE);
        }

    }

}