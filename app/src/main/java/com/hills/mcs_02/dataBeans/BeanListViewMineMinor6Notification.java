package com.hills.mcs_02.dataBeans;

public class BeanListViewMineMinor6Notification {
    private int icon;
    private String ID;
    private String time;
    private String content;

    public BeanListViewMineMinor6Notification(int icon, String Id, String time, String content) {
        this.icon = icon;
        this.ID = Id;
        this.time = time;
        this.content = content;
    }

    public int getIcon() {
        return icon;
    }

    public String getID() {
        return ID;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public void setID(String id) {
        this.ID = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
