package com.example.jarry.projectpsm;

/**
 * Created by Jarry on 18/8/2015.
 */
public class BankAccount {
    private String AccountName,AccountNo;
    private int BankID;

    public BankAccount(String accountName, String accountNo, int bankID) {
        super();
        AccountName = accountName;
        AccountNo = accountNo;
        BankID = bankID;

    }

    public String getAccountName() {
        return AccountName;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public int getBankID() {
        return BankID;
    }
}
