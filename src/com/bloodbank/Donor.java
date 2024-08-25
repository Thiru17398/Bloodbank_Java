package com.bloodbank;

import java.math.RoundingMode;
import java.sql.Connection;
import java.text.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.*;

public class Donor{
    Scanner sc = new Scanner(System.in);
    private String donorName;
    private String address;
    private String area;
    private String district;
    private String state;
    private String pincode;
    private long phoneNumber;
    private String bloodGroup;
    private String lastDonated;
    private double  bloodQuantity;
    int count = 0;
    String[] bloodGroups = {"A+","A-","B+","B-","AB+","AB-","O+","O-"};
    String[][] correspondingBloodGroup = {{"A+","A-","O+","O-"},{"A-","O-"},{"B+","B-","O+","O-"},{"B-","O-"},{"AB+","AB-","A+","A-","B+","B-","O+","O-"},{"AB-","A-","B-","O-"},{"O+","O-"},{"O-"}};
    public void getDetails() {
        System.out.print("Enter your name : ");
        this.donorName = sc.nextLine();
        getAddress();
        System.out.println("Choose blood group from the given blood groups ");
        this.bloodGroup = displayAndGet();
        System.out.print("Enter phone number : ");
        this.phoneNumber = sc.nextLong();
        sc.nextLine();
        this.lastDonated = getLastDonated();
        System.out.print("Enter the quantity of blood (in Litres): ");
        bloodQuantity = sc.nextDouble();
    }
    public String displayAndGet(){
        String bg ="";
        while(bg.length()<=0)
        {
            System.out.print("1.A +ve"+"\n"+"2.A -ve"+"\n");
            System.out.print("3.B +ve"+"\n"+"4.B -ve"+"\n");
            System.out.print("5.AB +ve"+"\n"+"6.AB -ve"+"\n");
            System.out.print("7.O +ve"+"\n"+"8.O -ve"+"\n");
            System.out.print("Enter choice of blood group : ");
            int bloodGroupChoice = sc.nextInt();
            sc.nextLine();
            switch(bloodGroupChoice) {
                case 1:
                    bg = "A+";
                    break;
                case 2:
                    bg = "A-";
                    break;
                case 3:
                    bg =  "B+";
                    break;
                case 4:
                    bg = "B-";
                    break;
                case 5:
                    bg = "AB+";
                    break;
                case 6:
                    bg = "AB-";
                    break;
                case 7:
                    bg = "O+";
                    break;
                case 8:
                    bg = "O-";
                    break;
                default:
                    System.out.println("Enter Valid Choice ! ! !");
                    break;
            }
        }
        return bg;
    }
    public String getLastDonated() {
        System.out.print("Enter the date of last donated : ");
        String date = sc.nextLine();
        System.out.print("Enter the month of last donated (Enter only number Eg: 1 - for January , 6 - for June ) : ");
        String month = sc.nextLine();
        System.out.print("Enter the year of last donated : ");
        String year = sc.nextLine();
        return year+"-"+month+"-"+date;
    }
    public void getAddress(){
        System.out.print("Enter door number and street name : ");
        this.address = sc.nextLine();
        System.out.print("Enter area : ");
        this.area = sc.nextLine();
        System.out.print("Enter district : ");
        this.district = sc.nextLine();
        System.out.print("Enter state : ");
        this.state = sc.nextLine();
        System.out.print("Enter area pincode : ");
        this.pincode = sc.nextLine();
    }
    public void insertData() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","Thirumalai_14");
        Statement st=con.createStatement();
        ResultSet rs = st.executeQuery("select * from bloodbank");
        rs.next();

        ResultSetMetaData rsmd = rs.getMetaData();
        String query="insert into bloodbank(";
        String start = rsmd.getColumnName(2);
        query = query+start;
        int len = rsmd.getColumnCount(),i=3;
        while(i<=len) {
            String currCol = rsmd.getColumnName(i);
            query=query+","+currCol;
            i++;
        }
        query=query+") values('"+this.donorName+"','"+this.address+"','"+this.area+"','"+this.bloodGroup+"','";
        query=query+this.district+"','"+this.state+"','"+this.pincode+"','"+this.phoneNumber+"','"+this.lastDonated+"',"+this.bloodQuantity+")";
        PreparedStatement ps = con.prepareStatement(query);
        ps.execute();
        System.out.println("Registered Successfully");
        con.close();
    }
    public void displayData() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","Thirumalai_14");
        Statement st=con.createStatement();
        String reqBloodGroup = displayAndGet();
        int len = bloodGroups.length,i=0,j=0,bg=-1;
        while(i<len) {
            if(bloodGroups[i].equals(reqBloodGroup)) {
                bg=i;
                break;
            }
            i++;
        }
        System.out.printf("%-50s %-20s %-20s %-150s","NAME","BLOODGROUP","PHONENUMBER","ADDRESS");
        System.out.println();
        int correspondingBloodGroupLen = correspondingBloodGroup[bg].length;
        while(j<correspondingBloodGroupLen) {
            displayRelatedBloodGroups(correspondingBloodGroup[bg][j]);
            j++;
        }
        if(count==0) {
            System.out.printf("\n\n%70s","No Donors Available");
        }
    }
    public void displayRelatedBloodGroups(String bloodGroup) throws Exception {
        ExpiryDate ed = new ExpiryDate();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","Thirumalai_14");
        Statement st=con.createStatement();
        ResultSet rs = st.executeQuery("select * from bloodbank where bloodgroup='"+bloodGroup+"'");
        while(rs.next()) {
            String lastDonated = rs.getDate("lastdonated").toString();
            if(ed.checkLastDonated(lastDonated)) {
                System.out.printf("%-50s ",rs.getNString("name"));
                System.out.printf("%-20s ",rs.getNString("bloodgroup"));
                System.out.printf("%-20s ",rs.getLong("phonenumber"));
                System.out.print(rs.getNString("address")+",");
                System.out.print(rs.getNString("area")+",");
                System.out.print(rs.getNString("district")+"-");
                System.out.print(rs.getLong("pincode")+",");
                System.out.print(rs.getNString("state")+".");
                System.out.println();
                count++;
            }
        }
    }

    public void displayAvailability() throws Exception{
        ExpiryDate ed = new ExpiryDate();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","Thirumalai_14");
        Statement st=con.createStatement();
        Statement st1 = con.createStatement();
        ResultSet rs1 = st1.executeQuery("select * from threshold");
        DecimalFormat df = new DecimalFormat("0.000");
        df.setRoundingMode(RoundingMode.DOWN);
        int i=0;
        System.out.printf("%-20s %-50s %-50s %-50s \n","Blood Group","Available Blood in Litres","Available Blood in Units","Quantity Status");
        while(rs1.next()) {
            String message="Fine";
            double quantity = 0,units=0;
            int count = 0;
            ResultSet rs = st.executeQuery("select * from bloodbank where bloodgroup='"+bloodGroups[i]+"'");
            while(rs.next()) {
                String currDate = rs.getDate("lastdonated").toString();
                if(!ed.checkForExpiry(currDate)) {
                    quantity += rs.getDouble("quantity");
                }
            }
            if(quantity<=rs1.getDouble("quantity")) {
                message = "Alert !!!";
            }
            units = getUnits(quantity);
            String quantityString = df.format(quantity);
            String unitsString = df.format(units);
            String q = String.valueOf(quantityString);
            String u = String.valueOf(unitsString);
            System.out.printf("%-20s %-50s %-50s %-50s \n",bloodGroups[i],q,u,message);
            i++;
        }
    }
    public double getUnits(double quantity) {
        return quantity/0.5;
    }
}

