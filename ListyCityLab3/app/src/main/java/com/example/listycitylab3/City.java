package com.example.listycitylab3;

public class  City {
    private  String name;
    private String province; //final is like a const (can't change)


    public City(String name, String province) {
        this.name = name;
        this.province = province;
    }


    public String getName() {
        return name;
    }

    public String getProvince() {
        return province;
    }
}
