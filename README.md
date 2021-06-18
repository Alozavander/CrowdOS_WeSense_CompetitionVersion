# Introduction

WeSense is a Crowdsensing application in Android, which allows all users to register accounts to become Participants to receive sensing tasks or become Publishers to publish sensing tasks. WeSense collects the sensor data of the smart device and upload them to CrowdOS Server to finish the sensing tasks, so as to solve the problem of idle sensing capabilities. The WeSense sensing task supports custom sensor requirements. The sensing data collection page is opened, allowing users to view or delete the sensing data produced by smart devices. WeSense currently supports both Chinese and English.



## Official website 

Welcome to visit CrowdOS website : https://www.crowdos.cn/



## Links to Other Open Source Websites of WeSense Project 

Mulan:https://toscode.gitee.com/Alozavander/CrowdOS_WeSense_CompetitionVersion

Trustie:https://forgeplus.trustie.net/projects/Alozavander/CrowdOS_WeSense_CompetitionVersion



## Open Source Document

[Document](./开源文档.docx)



## Sensing Data Upload Function

Each time the app is launched ten minutes later, the data collected in the previous hour will be automatically uploaded, and then the collected sensing data will be uploaded every hour 

You can view the auto-saved sensor files under the "/SensorDataStore" relative path of the data directory in your phone. The file name is formatted as "SensorType_Date_Time.txt". The sensorType is a constant value and defined by Android. You can check it in [传感器概览  | Android 开发者  | Android Developers (google.cn)](https://developer.android.google.cn/guide/topics/sensors/sensors_overview). "StringError1088X" means that the sensor is not predefined.

**ps**: If you want a csv file containing all of sensor data,  Click "Account" -> "Sensor Function"->"Output Data". The extracted file will be stored in the same path with the "senseData.csv" filename(It will be overwrote if you do the same operations). 