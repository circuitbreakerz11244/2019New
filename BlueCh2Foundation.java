package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue Channel 2 Foundation", group = "autonomous")
public class BlueCh2Foundation extends CBAutonomousBase {

    String inputColor = "blue";

    @Override
    public void runOpMode() {

        initialization();
        waitForStart();
        encoderDrive("ML", 10, 0.6);
        encoderDrive("MF", 34, 0.6);
        //pull foundation
        util.robot.roboArmClaw.pullServoClose();
        sleep(200);

        encoderDrive("MB", 33.5, 0.6);
        util.robot.roboArmClaw.pullServoOpen();
        sleep(200);

        encoderDrive("MR", 34, 0.6);
        encoderDrive("MF", 21, 0.4);
        encoderDrive("MR", 18, 0.25,inputColor);

    }
}
