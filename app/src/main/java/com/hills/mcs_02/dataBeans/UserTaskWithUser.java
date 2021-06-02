package com.hills.mcs_02.dataBeans;

public class UserTaskWithUser {
    private User u;
    private UserTask ut;

    public UserTaskWithUser(User user, UserTask userTask) {
        this.u = user;
        this.ut = userTask;
    }

    public User getU() {
        return u;
    }

    public UserTask getUt() {
        return ut;
    }

    public void setU(User user) {
        this.u = user;
    }

    @Override
    public String toString() {
        return "Combine_u_ut{" +
                "u=" + u +
                ", ut=" + ut +
                '}';
    }
}
