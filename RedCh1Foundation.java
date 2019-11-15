package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.CameraDevice;

@Autonomous(name = "Red Channel 1 Foundation", group = "autonomous")
public class RedCh1Foundation extends CBAutonomousBase {

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
        encoderDrive("ML", 50, 0.25, false, inputColor);

    }
}
