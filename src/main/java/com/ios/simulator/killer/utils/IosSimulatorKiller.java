package com.ios.simulator.killer.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Irfan Mauludin, 2019-06-27
 */
public class IosSimulatorKiller {
    ProcessBuilder processBuilder = new ProcessBuilder();

    public void killSimulator(String simUdid) {
        processBuilder.command("xcrun", "simctl", "shutdown", simUdid);

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!! iOS simulator with UDID "+simUdid+" has been terminated.");
                System.out.println(output);
            } else {
                System.out.println("Failure!! iOS simulator with UDID "+simUdid+" can't be terminated.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
