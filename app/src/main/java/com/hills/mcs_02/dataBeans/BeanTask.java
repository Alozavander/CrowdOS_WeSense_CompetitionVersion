package com.hills.mcs_02.dataBeans;

import java.util.Arrays;
import java.util.Date;

//当前Bean主要直接对接应用的直接使用
public class BeanTask {
    private Integer taskId;               //任务ID
    private String taskName;              //任务名称
    private Date postTime;                //发布日期
    private Date deadLine;                //截止日期
    private Integer userId;               //任务发布者ID
    private String userName;              //任务发布者的名字
    private Float coin;                   //激励金
    private String describe_task;         //任务描述
    private Integer totalNum;             //该任务的执行总人数
    private Integer taskStatus;           //该任务的执行状态
    private Integer sensorTypes[];        //任务需要的感知器

    public BeanTask() {
        super();
    }

    public BeanTask(Integer taskId, String taskName, Date postTime, Date deadline, Integer userId, String username, Float coin, String describeTask, Integer totalNum, Integer taskStatus) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.postTime = postTime;
        this.deadLine = deadline;
        this.userId = userId;
        this.userName = username;
        this.coin = coin;
        this.describe_task = describeTask;
        this.totalNum = totalNum;
        this.taskStatus = taskStatus;
        this.sensorTypes = new Integer[]{-1}; //-1=GPS
    }

    public BeanTask(Integer taskId, String taskName, Date postTime, Date deadline, Integer userId, String username, Float coin, String describeTask, Integer totalNum, Integer taskStatus,Integer[] sensorTypes) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.postTime = postTime;
        this.deadLine = deadline;
        this.userId = userId;
        this.userName = username;
        this.coin = coin;
        this.describe_task = describeTask;
        this.totalNum = totalNum;
        this.taskStatus = taskStatus;
        this.sensorTypes = sensorTypes;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public Date getPostTime() {
        return postTime;
    }


    public Date getDeadLine() {
        return deadLine;
    }


    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Float getCoin() {
        return coin;
    }

    public String getDescribe_task() {
        return describe_task;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public void setDeadLine(Date deadline) {
        this.deadLine = deadline;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public void setCoin(Float coin) {
        this.coin = coin;
    }

    public void setDescribe_task(String describeTask) {
        this.describe_task = describeTask;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer[] getSensorTypes() {
        return sensorTypes;
    }

    public void setSensorTypes(Integer[] pSensorTypes) {
        sensorTypes = pSensorTypes;
    }

    @Override
    public String toString() {
        return "Bean_Task{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", postTime=" + postTime +
                ", deadLine=" + deadLine +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", coin=" + coin +
                ", describe_task='" + describe_task + '\'' +
                ", totalNum=" + totalNum +
                ", taskStatus=" + taskStatus +
                ", sensorTypes=" + Arrays.toString(sensorTypes) +
                '}';
    }
}
