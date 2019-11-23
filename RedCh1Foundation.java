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
        encoderDrive("MR", 10, 0.6);
        encoderDrive("MF", 34, 0.6);
        //pull foundation

        util.robot.roboArmClaw.pullServoClose();
        sleep(200);

        encoderDrive("MB", 33.5, 0.6);
        util.robot.roboArmClaw.pullServoOpen();
        sleep(200);

        encoderDrive("ML", 50, 0.25, inputColor);

    }
}
