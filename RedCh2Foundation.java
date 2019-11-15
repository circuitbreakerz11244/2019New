package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.CameraDevice;

@Autonomous(name = "Red Channel 2 Foundation", group = "autonomous")
public class RedCh2Foundation extends CBAutonomousBase {

    String inputColor = "red";

    @Override
    public void runOpMode() {

        initialization();
        waitForStart();
        encoderDrive("MR", 10, 0.6, false);
        encoderDrive("MF", 32, 0.6, false);
        //pull foundation
        util.robot.roboArmClaw.pullServoClose();

        sleep(200);
        util.updateStatus(">", "Searching for Skystone...");
        encoderDrive("MB", 32, 0.6, false);
        util.robot.roboArmClaw.pullServoOpen();
        sleep(200);
        encoderDrive("ML", 34, 0.6, false);
        encoderDrive("MF", 21, 0.4, false);
        encoderDrive("ML", 18, 0.25, false,inputColor);

    }
}
