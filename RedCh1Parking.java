package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.CameraDevice;

@Autonomous(name = "Red Channel 1 parking", group = "autonomous")
public class RedCh1Parking extends CBAutonomousBase {

    String inputColor = "red";

    @Override
    public void runOpMode() {

        initialization();
        waitForStart();


        // move till you detect color sensor
            encoderDrive("MR", 10, 0.25, inputColor);
        //   sleep(1000);




    }
}
