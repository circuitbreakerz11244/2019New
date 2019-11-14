package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.ColorSensor;
// RObot is using the arm located at its back to pick up the skystones
@Autonomous(name = "kavi color Test v8", group = "autonomous")
@Disabled
public class kaviTest extends LinearOpMode {

    String inputColor = "blue";
    ColorSensor kkk;

    @Override
    public void runOpMode() {


        kkk = hardwareMap.get(ColorSensor.class, "color");
        kkk.enableLed(true);
        waitForStart();


        while (true) {
            telemetry.addData("IN while loop", "");
            sleep(1000);
            telemetry.addData("Blue value ", kkk.blue());
            if (kkk.blue() > 35) {
                telemetry.addData("BLUE FOUND!!!!!!!!!!!!!!!!", "");
                sleep(2000);
               // break;
            } else if (kkk.red() > 35) {

                    telemetry.addData("RED FOUND!!!!!!!!!!!!!!!!", "");
                    sleep(2000);

            }
                // send the info back to driver station using telemetry function.
                telemetry.addData("Clear", kkk.alpha());
                telemetry.addData("Red  ", kkk.red());
                telemetry.addData("Green", kkk.green());
                telemetry.addData("Blue ", kkk.blue());
                telemetry.update();


        }
    }
}

