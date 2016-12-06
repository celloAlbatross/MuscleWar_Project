package com.mygdx.game;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class Serial {
	public static void main(String[] args) throws Exception
    {

        String dev = "/dev/ttyUSB0";

        System.out.println("Monitoring serial stream on " + dev);
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(dev);
        if (portIdentifier.isCurrentlyOwned())
        {
            System.out.println( "Error: Port is currently in use" );
        }
        else
        {
            int timeout = 2000;
            CommPort commPort = portIdentifier.open("serial",timeout);

            if (commPort instanceof SerialPort)
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE );

                //InputStream in = serialPort.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
                while (true)
                {
//                    int data = in.read();
//                    if (data < 0) break;
//                    System.out.print((char)data);
                	String line = reader.readLine();
                	double value = Double.parseDouble(line.split(" ")[1]);
                	System.out.println(value);
                }
            }
        }
    }
}
