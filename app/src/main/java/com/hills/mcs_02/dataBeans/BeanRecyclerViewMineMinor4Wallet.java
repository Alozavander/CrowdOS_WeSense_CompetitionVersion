package com.hills.mcs_02.dataBeans;

public class BeanRecyclerViewMineMinor4Wallet {

    private int userId;
    private String userName;
    private int userIcon;
    private int userCoins;

    public BeanRecyclerViewMineMinor4Wallet(){
        super();
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String username){
        this.userName = username;
    }

    public int getUserIcon(){
        return userIcon;
    }

    public void setUserIcon(int userIcon){
        this.userIcon = userIcon;
    }

    public int getUserCoins(){
        return userCoins;
    }

    public void setUserCoins(int userCoin){
        this.userCoins = userCoin;
    }

    @Override
    public String toString(){
        return "userId:" + userId + ","
                + "userName:" + userName + ","
                + "userCoins:" + userCoins;
    }

}
