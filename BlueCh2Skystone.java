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
        encoderDrive("MB", 24, 0.6, false);

        //sleep(1000);
        util.updateStatus(">", "Searching for Skystone...");

        CameraDevice.getInstance().setFlashTorchMode(true);

        encoderDrive("ML", 41, 0.2, true);

        // sleep(1000);
        if (vcb.isTargetVisible()) {
            util.updateStatus(">", "Skystone found:" + vcb.stoneTarget.getName() + "Navigating...");
            sleep(500);

            vuforiaNavigate(inputColor);
            skystonePicked=true;
        } else {
            util.updateStatus(">", "Skystone NOT found");
            skystonePicked=false;
        }
        CameraDevice.getInstance().setFlashTorchMode(false);
        // pick up skystone
        util.skystoneServoClose();
        sleep(200);
        util.updateStatus(">", "pick up skystone");
        encoderDrive("MF", 12, 0.5);

        // move till you detect color sensor
        encoderDrive("MR", 75, 0.25, false, inputColor);
        //   sleep(1000);
        if(skystonePicked){
        // enter the foundation zone area
        encoderDrive("MR", 15, 0.5, false);
        util.updateStatus(">", "skystone delivered.");
        // drop skystone
        util.skystoneServoOpen();
        sleep(200);
        //move towards the RED bridge and stop on the red line
        encoderDrive("ML", 15, 0.25, false, inputColor);
        //  sleep(2000);
    }else{
            util.skystoneServoOpen();
        }

    }
}
