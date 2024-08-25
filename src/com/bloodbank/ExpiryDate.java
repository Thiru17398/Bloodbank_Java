package com.bloodbank;


import java.util.*;
import java.text.*;
public class ExpiryDate {

    public int getCurrYear(int year) {
        return 1900+year;
    }
    public int getCurrMonth(int month) {
        return month+1;
    }
    public boolean checkForExpiry(String lastdonated) {
        Date d = new Date();
        int year = Integer.parseInt(lastdonated.substring(0,4));
        int month = Integer.parseInt(lastdonated.substring(5,7));
        int date = Integer.parseInt(lastdonated.substring(8,10));
        int currDate = d.getDate();
        int currMonth = getCurrMonth(d.getMonth());
        int currYear = getCurrYear(d.getYear());
        int daysCount = 0;
        boolean expired = false;
        for(;daysCount<42;date++,daysCount++) {
            switch(month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if(date>31) {
                        date = 1;
                        if(month==12) {
                            month=1;
                            year+=1;
                        }
                        else {
                            month+=1;
                        }
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    if(date>30) {
                        date = 1;
                        month+=1;
                    }
                    break;
                case 2:
                    if(date>29) {
                        date = 1;
                        month+=1;
                    }

                    break;
            }
        }
        if(year < currYear || (year == currYear && month < currMonth) || (month == currMonth && date < currDate))
                expired = true;

        return expired;
    }
    public boolean checkLastDonated(String lastdonated) {
        Date d = new Date();
        int year = Integer.parseInt(lastdonated.substring(0,4));
        int month = Integer.parseInt(lastdonated.substring(5,7));
        int date = Integer.parseInt(lastdonated.substring(8,10));
        int currDate = d.getDate();
        int currMonth = getCurrMonth(d.getMonth());
        int currYear = getCurrYear(d.getYear());
        int daysCount = 0,c=0;
        for(;daysCount<120;date++,daysCount++) {
            switch(month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if(date>31) {
                        date = 1;
                        if(month==12) {
                            month=1;
                            year+=1;
                        }
                        else {
                            month+=1;
                        }
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    if(date>30) {
                        date = 1;
                        month+=1;
                    }
                    break;
                case 2:
                    if(date>29) {
                        date = 1;
                        month+=1;
                    }

                    break;
            }
        }
        if(year<currYear) {
            c=1;
        }
        else {
            if(month<currMonth) {
                c=1;
            }
            else if(date<currDate && month==currMonth) {
                c=1;
            }
        }
        return c==1;
    }
}
