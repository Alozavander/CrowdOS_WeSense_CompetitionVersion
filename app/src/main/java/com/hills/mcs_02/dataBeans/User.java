package com.hills.mcs_02.dataBeans;

public class User {
    private Integer userId;
    private String userName;
    private String passWord;
    private String realName;
    private int coin;

    public User() {
        super();
    }

    public User(Integer userId, String username, String password, String realName, int coin) {
        this.userId = userId;
        this.userName = username;
        this.passWord = password;
        this.realName = realName;
        this.coin = coin;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String password) {
        this.passWord = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getCoin() {
        return coin;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", realName='" + realName + '\'' +
                ", coins=" + coin +
                '}';
    }
}
