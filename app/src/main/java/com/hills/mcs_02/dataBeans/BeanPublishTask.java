package com.hills.mcs_02.dataBeans;

class BeanPublishTask {
    private String postTime;
    private String deadLine;
    private String postID;
    private String coin;
    private String Text;

    public BeanPublishTask(String postTime, String deadline, String postId, String coin, String text) {
        this.postTime = postTime;
        this.deadLine = deadline;
        this.postID = postId;
        this.coin = coin;
        Text = text;
    }

    public String getPostTime() {
        return postTime;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public String getPostID() {
        return postID;
    }

    public String getCoin() {
        return coin;
    }

    public String getText() {
        return Text;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public void setDeadLine(String deadline) {
        this.deadLine = deadline;
    }

    public void setPostID(String postId) {
        this.postID = postId;
    }

    public void setText(String text) {
        Text = text;
    }

    @Override
    public String toString() {
        return "Bean_publish_Task{" +
                "postTime='" + postTime + '\'' +
                ", deadLine='" + deadLine + '\'' +
                ", postID='" + postID + '\'' +
                ", coin='" + coin + '\'' +
                ", Text='" + Text + '\'' +
                '}';
    }
}
