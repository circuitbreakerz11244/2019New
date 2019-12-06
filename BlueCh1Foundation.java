package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue Channel 1 Foundation", group = "autonomous")
public class BlueCh1Foundation extends CBAutonomousBase {

    String inputColor = "blue";

    @Override
    public void runOpMode() {

        blueChannelFoundationPick();
        //encoderDrive("MR", CBRoboConstants.FOUNDATION_CHNL_1_RET_DIST, 0.25, inputColor);

        encoderDrive("MR", 34, 0.5);
        encoderDrive("MF", 17, 0.5);
        encoderDrive("ML", 10, 0.5);
        encoderDrive("MB", 17, 0.5);
        encoderDrive("MR", 18+11, 0.3, inputColor);

    }
}
