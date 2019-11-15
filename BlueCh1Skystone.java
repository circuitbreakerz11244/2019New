package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.CameraDevice;

@Autonomous(name = "Blue Channel 1 Skystone", group = "autonomous")
public class BlueCh1Skystone extends CBAutonomousBase {

    String inputColor = "blue";

    @Override
    public void runOpMode() {

        initialization();
        waitForStart();
        boolean skystonePicked=false;
        int offset=0;

        encoderDrive("MB", 24, 0.6, false);

        //sleep(1000);
        util.updateStatus(">", "Searching for Skystone...");

        CameraDevice.getInstance().setFlashTorchMode(true);

        encoderDrive("ML", 41, 0.2, true);

       // sleep(1000);
        if (vcb.isTargetVisible()) {
            util.updateStatus(">", "Skystone found:"+vcb.stoneTarget.getName()+"Navigating...");
            sleep(500);

            vuforiaNavigate(inputColor);
            skystonePicked=true;
        }else{
            util.updateStatus(">", "Skystone NOT found");
            skystonePicked=false;
            offset=10;
        }
        CameraDevice.getInstance().setFlashTorchMode(false);
        // pick up skystone
        util.skystoneServoClose();
        sleep(200);
        util.updateStatus(">", "pick up skystone");
        encoderDrive("MF", 34-offset, 0.5);

        // move till you detect color sensor
        encoderDrive("MR", 65, 0.25, false, inputColor);
        //   sleep(1000);
        if(skystonePicked) {
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
