package com.hills.mcs_02.dataBeans;

public class BeanUserAccount {
    private String state;                      /**  Indicates the validation status. 0 is incorrect and 1 is correct */
    private String id;
    private String name;
    private String pwd;
    private String phone;
    private String email;
    private String sex;

    public BeanUserAccount(){

    }
    
    public String getState() {
        return state;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getSex() {
        return sex;
    }

    /*
    public String getSign() {
        return sign;
    }*/

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
