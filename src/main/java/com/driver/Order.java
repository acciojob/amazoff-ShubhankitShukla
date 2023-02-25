package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public void setId(String id) {
        this.id = id;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id=id;
        String[]temp=deliveryTime.split(":");
        int hours=Integer.valueOf(temp[0]);
        int min=Integer.valueOf(temp[1]);
        this.deliveryTime=(hours*60)+min;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
