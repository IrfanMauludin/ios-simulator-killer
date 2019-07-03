package com.ios.simulator.killer.data;

/**
 * @author Irfan Mauludin, 2019-06-27
 */
public class Device {

    private String name;
    private String osVersion;
    private String udid;
    private String state;

    public void setName(String name){
        this.name = name;
    }

    public void setOsVersion(String osVersion){
        this.osVersion = osVersion;
    }

    public void setUdid(String udid){
        this.udid = osVersion;
    }

    public void setState(String state){
        this.state = state;
    }

    public String getName(){
        return name;
    }

    public String getOsVersion(){
        return osVersion;
    }

    public String getUdid(){
        return udid;
    }

    public String getState(){
        return state;
    }
}
