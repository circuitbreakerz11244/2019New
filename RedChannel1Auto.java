package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "RED AUTO CHNL_1", group = "autonomous")
@Disabled
public class RedChannel1Auto extends CBAutonomousBase {

    String inputColor = "red";

    @Override
    public void runOpMode() {

        initialization();
        waitForStart();

        encoderDrive("MB", 30, 0.5, false);
        sleep(1000);
        util.updateStatus(">", "Searching for Skystone...");

        encoderDrive("MR", 36, 0.2, true);
        util.updateStatus(">", "Skystone found! navigating...");

        sleep(1000);

        if (vcb.isTargetVisible()) {
            vuforiaNavigate(0.0);
        }
        sleep(2000);

        //TBD
        // pick up skystone

        util.updateStatus(">", "Skystone Delivered!");

        encoderDrive("MF", 24, 0.5, false);
        encoderDrive("ML", 24, 0.5, false, inputColor);
        encoderDrive("ML", 24, 0.5, false);
        encoderDrive("MR", 40, 0.5, false, inputColor);

        //TBD
        //drop the skystone
        // go sideways to park
//        strafe(1, 0.8, 0);

    }
}
