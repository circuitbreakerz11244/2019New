package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.Range;

import java.text.DecimalFormat;
import java.lang.reflect.Field;

public class AppUtil {

    public static double[] getVectorArrays(double leftX, double leftY, double dblTurn) {

        double r = Math.hypot(leftX, leftY);
        double robotAngle = Math.atan2(leftY, leftX) - Math.PI / 4;

        System.out.println( " r is " + r + " robotAngle is " + robotAngle);

        double v1 = r * Math.cos(robotAngle) + dblTurn;
        double v2 = r * Math.sin(robotAngle) - dblTurn;
        double v3 = r * Math.sin(robotAngle) + dblTurn;
        double v4 = r * Math.cos(robotAngle) - dblTurn;

        double[] dbArray = {v1, v2, v3, v4};

        return dbArray;
    }

    public static void main(String args[]) throws Exception {
        System.out.println("AppUtil Initialized...");

        //**************************
        //Unit Test values. Range for x,y,z is -1 to 1
        double x = 0;
        double y = 0;
        double z = -1;
        //**************************

        double leftX  = 0.0;
        double leftY  = 0.0;
        double rightX = 0.0;

        DecimalFormat df = new DecimalFormat("0.00");
        if(args.length > 0) {
            //Get the values as argument if needed
            leftX  = Double.parseDouble(args[0]);
            leftY  = Double.parseDouble(args[1]);
            rightX = Double.parseDouble(args[2]);
        } else {
            //Set the desired test values
            leftX  = x;
            leftY  = y;
            rightX = z;
        }
        //Call the method used by Robot to get the desired power settings for the 4 motor used by mecanum wheels
        double[] tempArray = getVectorArrays(leftX, leftY, rightX);

        //Display the output values to console to view it
        System.out.println("x=" + leftX + " y=" + leftY + " z=" + rightX);
        for(int i=0; i<tempArray.length; i++) {
            if ((i % 2) == 0) {
                System.out.println("v" + (i+1) + " = " + df.format(-tempArray[i]));
            } else {
                System.out.println("v" + (i+1) + " = " + df.format(tempArray[i]));
            }
        }

        Field[] fields = AppUtil.class.getDeclaredFields();
        int i = 1;
        for (Field field : fields) {
            //names of the fields
            //System.out.println(i + "."+ field.getName());
            i++;
        }

        java.util.ArrayList nameList = new java.util.ArrayList();
        nameList.add("Arun");
        nameList.add("Babu");
        nameList.add("Charlis");
        nameList.add("Don");

        for(int j=0; j<nameList.size(); j++) {
                //System.out.println(j + " = " + nameList.get(j));
        }

        double dd = Math.asin(0.5);
        //System.out.println("dd = " + dd);

        double leftY2 = -0.5;
                leftY2 = Range.clip(leftY2, -0.1, 0.1);
                System.out.println(leftY2);

        double globalRobotPos = 377.11;
        double moveSideDistance = 0;
        double returnSideDistance = 0;
        String stoneNbr = "";
        if(globalRobotPos < 200) {
            //1.
            moveSideDistance = 5;
            returnSideDistance = 40;
            stoneNbr = "1";
        } else if(globalRobotPos > 200 && globalRobotPos < 300) {
            //2.
            moveSideDistance = 10;
            returnSideDistance = 48;
            stoneNbr = "2";
        } else if(globalRobotPos > 300 && globalRobotPos < 400) {
            //3.
            moveSideDistance = 11.5;
            returnSideDistance = 56;
            stoneNbr = "3";
        } else if(globalRobotPos > 400 && globalRobotPos < 550) {
            //4.
            moveSideDistance = 10.5;
            returnSideDistance = 64;
            stoneNbr = "4";
        } else if(globalRobotPos > 550 && globalRobotPos < 650) {
            //5
            moveSideDistance = 10.5;
            returnSideDistance = 72;
            stoneNbr = "5";
        } else {
            //6
            moveSideDistance = 0;
            returnSideDistance = 72;
            stoneNbr = "6";
        }

        System.out.println("Robot Pos>>  RP " + globalRobotPos + " S>>" + stoneNbr + " MSD " + moveSideDistance + " RSD " + returnSideDistance);


    }
}
