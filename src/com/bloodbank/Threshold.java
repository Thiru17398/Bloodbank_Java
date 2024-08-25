package com.bloodbank;


import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Threshold{
    Scanner sc = new Scanner(System.in);
    public void setThreshold() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con  = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","Thirumalai_14");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from threshold");
        while(rs.next()) {
            String currBloodGroup = rs.getString("bloodgroup");
            System.out.println("Enter Threshold for "+currBloodGroup+"(in Litres)");
            double quantity = sc.nextDouble();
            String sql = "UPDATE threshold SET quantity="+quantity+" WHERE bloodgroup='"+currBloodGroup+"'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
        }

        System.out.print("Threshold Set Successfully !!!");
    }

    public void showConfiguration() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con  = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","Thirumalai_14");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from threshold");
        System.out.printf("%-20s %-20s\n","Blood Type","Threshold Quantity( in Litres )");
        while(rs.next()) {
            System.out.printf("%-20s %-20s\n",rs.getString("bloodgroup"),rs.getDouble("quantity")+" L");
        }
    }
}

