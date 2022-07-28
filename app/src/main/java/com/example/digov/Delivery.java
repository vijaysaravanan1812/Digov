package com.example.digov;

public class Delivery {
    String Name;
    String Phone;
    String Bill;
    String Total;

    @Override
    public String toString() {
        return
                "Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Bill='" + Bill + '\'' +
                ", Total='" + Total + '\'';
    }

    public Delivery(String name, String phone, String bill, String total) {
        Name = name;
        Phone = phone;
        Bill = bill;
        Total = total;
    }
}
