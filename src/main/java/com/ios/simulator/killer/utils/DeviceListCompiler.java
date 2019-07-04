package com.ios.simulator.killer.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * @author Irfan Mauludin, 2019-06-27
 */
public class DeviceListCompiler {
    private JSONParser parser = new JSONParser();
    private String homeDir = System.getProperty("user.home");
    private Runtime runtime = Runtime.getRuntime();

    public void printDeviceListInJsonFile(){
        File devicesJson = new File(homeDir+"/devices.json");

        try{
            devicesJson.delete();
            Thread.sleep(2000);
            Process process = runtime.exec("xcrun simctl list devices -j");
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null){
                output.append(line + "\n");
            }
            int exitVal = process.waitFor();
            if (exitVal == 0){
                File file = new File(homeDir+"/devices.json");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(output.toString());
                }
                System.out.println("The devices.json file successfully created!");
            }
            else{
                System.out.println("Failure, devices.json file can't be created!");
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getDevicesList(){
        try{
            Object obj = parser.parse(new FileReader(homeDir + "/devices.json"));
            JSONObject device = (JSONObject) obj;
            return (JSONObject) device.get("devices");
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray getDeviceListByOSVersion(String osVersion){
        osVersion = osVersion.replace(".","-");
        return (JSONArray) getDevicesList().get("com.apple.CoreSimulator.SimRuntime.iOS-"+osVersion);
    }

    public String getDeviceUdid(String osVersion, String deviceName){
        String udid = null;
        JSONArray deviceArray = getDeviceListByOSVersion(osVersion);
        for (Object o : deviceArray) {
            JSONObject deviceObj = (JSONObject) o;
            if (deviceObj != null && deviceName.equals(deviceObj.get("name")) && deviceObj.get("state").equals("Booted")) {
                udid = (String) deviceObj.get("udid");
                break;
            }
        }
        System.out.println("Device udid         : "+udid);
        return udid;
    }

}
