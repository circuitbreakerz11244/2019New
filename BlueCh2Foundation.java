package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue Channel 2 Foundation", group = "autonomous")
public class BlueCh2Foundation extends CBAutonomousBase {

    String inputColor = "blue";

    @Override
    public void runOpMode() {

        blueChannelFoundationPick();
        encoderDrive("MR", 34, 0.5);
        encoderDrive("MF", 21, 0.3);
        encoderDrive("MR", 18, 0.2, inputColor);

    }
}
