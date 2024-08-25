package com.bloodbank;


import java.sql.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","Thirumalai_14");
            Statement st=con.createStatement();
            Donor donor = new Donor();
            Threshold threshold = new Threshold();
            int choice=0;
            while(choice!=6) {
                System.out.println("                                 --------------------------------------------------------------------------                                 ");
                System.out.println("1.Donor Registration"+"\n"+"2.Search for Donor"+"\n"+"3.Blood Availability"+"\n"+"4.Set Threshold for Blood Groups"+"\n"+"5.Show Threshold Configuration\n6.Exit\n");
                System.out.print("Enter Your Choice : ");
                choice = sc.nextInt();
                System.out.println();
                switch(choice) {
                    case 1:
                        donor.getDetails();
                        donor.insertData();
                        break;
                    case 2:
                        donor.displayData();
                        break;
                    case 3:
                        donor.displayAvailability();
                        break;
                    case 4:
                        threshold.setThreshold();
                        break;
                    case 5:
                        threshold.showConfiguration();
                        break;
                    case 6:
                        System.out.println("Succesfully Exited !!!");
                        continue;
                    default:
                        System.out.println("Enter Valid Choice!!!");
                }
                System.out.println();
            }
        }
        catch(Exception e){
            System.out.println("Exception Caught: "+e.getMessage());
        }
    }
}
