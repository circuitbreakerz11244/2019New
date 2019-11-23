package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.CameraDevice;

@Autonomous(name = "Red Channel 2 Foundation", group = "autonomous")
public class RedCh2Foundation extends CBAutonomousBase {

    String inputColor = "red";

    @Override
    public void runOpMode() {
        redChannelFoundationPick();
        encoderDrive("ML", 34, 0.5);
        encoderDrive("MF", 21, 0.3);
        encoderDrive("ML", 18, 0.2, inputColor);

    }
}
