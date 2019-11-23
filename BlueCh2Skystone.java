package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.CameraDevice;

@Autonomous(name = "Blue Channel 2 Skystone", group = "autonomous")
public class BlueCh2Skystone extends CBAutonomousBase {

    String inputColor = "blue";

    @Override
    public void runOpMode() {

        initialization();
        waitForStart();
        boolean skystonePicked=false;
        encoderDrive("MB", 24, 0.6);
    }


}
