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
    private ProcessBuilder processBuilder = new ProcessBuilder();
    private JSONParser parser = new JSONParser();

    public void printDeviceListInJsonFile(){
        processBuilder.command("sh","-c",Constants.MAIN_RESOURCES + "devices.sh");
        try{
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null){
                output.append(line + "\n");
            }
            int exitVal = process.waitFor();
            if (exitVal == 0){
                System.out.println("The devices.json file successfully created!");
                System.out.println(output);
            }
            else{
                System.out.println("Failure, device.json file can't be created!");
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public JSONObject getDevicesList(){
        try{
            Object obj = parser.parse(new FileReader(Constants.MAIN_RESOURCES + "devices.json"));
            JSONObject device = (JSONObject) obj;
            JSONObject deviceObject = (JSONObject) device.get("devices");

//            System.out.println("devices json object is: \n");
//            System.out.println(deviceObject.toString()+"\n\n");
            return deviceObject;
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
        JSONArray deviceArray = (JSONArray) getDevicesList().get("iOS "+osVersion);
        return deviceArray;
    }

    public String getDeviceUdid(String osVersion, String deviceName){
        String udid = null;
        JSONArray deviceArray = getDeviceListByOSVersion(osVersion);
        for(int i=0;i<deviceArray.size();i++){
            JSONObject deviceObj = (JSONObject)  deviceArray.get(i);
            if(deviceObj!=null && deviceName.equals(deviceObj.get("name"))){
                udid = (String) deviceObj.get("udid");
            }
        }
        System.out.println("Device udid         : "+udid);
        return udid;
    }

}
