package com.example.yashodeepmahapatra.ambulancerouting;

public class RegisteredUser {
    String Email;
    String Name;
    String Phone;
    int Age;
    int userclass;
    public RegisteredUser(String email, String name, String phone, int age,int user_class) {
        Email = email;
        Name = name;
        Phone = phone;
        Age = age;
        userclass = user_class;
    }
}
