package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.CameraDevice;

@Autonomous(name = "Red Channel 1 Foundation", group = "autonomous")
public class RedCh1Foundation extends CBAutonomousBase {

    String inputColor = "red";

    @Override
    public void runOpMode() {

        redChannelFoundationPick();
        encoderDrive("ML", CBRoboConstants.FOUNDATION_CHNL_1_RET_DIST, 0.25, inputColor);

    }
}
