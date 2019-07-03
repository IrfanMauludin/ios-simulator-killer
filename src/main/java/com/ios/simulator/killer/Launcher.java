package com.ios.simulator.killer;

import com.ios.simulator.killer.utils.DeviceListCompiler;
import com.ios.simulator.killer.utils.IosSimulatorKiller;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Irfan Mauludin, 2019-06-27
 */
public class Launcher {
    public static void main(String[] args){
        DeviceListCompiler deviceListCompiler = new DeviceListCompiler();
        IosSimulatorKiller iosSimulatorKiller = new IosSimulatorKiller();
        String osVersion;
        String deviceName;
        String deviceUdid;
        ArrayList<String> params = new ArrayList<>();

        if(args.length == 0){
            System.out.println("Please enter iOS version and device name.");
            System.exit(0);
        }else if(args.length > 0){
            params.addAll(Arrays.asList(args));
        }
        osVersion = params.get(0);
        deviceName = params.get(1);

        System.out.println("Device OS Version   : " + osVersion);
        System.out.println("Device name         : " + deviceName);

        deviceListCompiler.printDeviceListInJsonFile();
        deviceUdid = deviceListCompiler.getDeviceUdid(osVersion,deviceName);
        iosSimulatorKiller.killSimulator(deviceUdid);

    }

}