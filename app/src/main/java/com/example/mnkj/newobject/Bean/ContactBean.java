package com.example.mnkj.newobject.Bean;

import java.io.Serializable;

public class ContactBean implements Serializable {

    private String name;
    private String phone;
    private String letters;//显示拼音的首字母

    @Override
    public String toString() {
        return name +"         "+ phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }
}
