package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.CameraDevice;

@Autonomous(name = "Red Channel 2 Skystone", group = "autonomous")
public class RedCh2Skystone extends CBRedChannelSkyStone {

    String inputColor = "red";

    @Override
    public void runOpMode() {

        initialization();
        waitForStart();
        autonomousStartTime = System.currentTimeMillis();

        //Move infront of Stone 1, just 5 inches before the Sky Stone
        encoderDrive("MB", CBRoboConstants.SKYSTONE_INIT_DRIVE_DIST
                , CBRoboConstants.SKYSTONE_DRIVE_MIN_PWR
                , CBRoboConstants.SKYSTONE_DRIVE_MAX_PWR);

        //Track total FWD/BACK Movement distance
        totalMBDistance = totalMBDistance + CBRoboConstants.SKYSTONE_INIT_DRIVE_DIST;

        //Time to search skystone START
        flashTorchOn();
        searchSkyStone();
        flashTorchOff();
        //Time to search skystone END
//TBD Fine tune MB Distance

        double mbDistance = CBRoboConstants.SKYSTONE_AP_CH2_DRIVE_DIST;
        if(!skystonePicked) {
            mbDistance = 0;
        }


        encoderDrive("MF", mbDistance-2
                , CBRoboConstants.SKYSTONE_AFTERPICK_MIN_PWR
                , CBRoboConstants.SKYSTONE_AFTERPICK_MAX_PWR);

        double moveDistanceStoneDelivery = 40 + iStoneNbr * CBRoboConstants.SKYSTONE_LENGTH;
        // move till you detect color sensor
        encoderDrive("ML", moveDistanceStoneDelivery, CBRoboConstants.SKYSTONE_AP_SD_MIN_PWR
                , CBRoboConstants.SKYSTONE_AP_SD_MAX_PWR, inputColor);

        if(skystonePicked && colorLineFound) {
            // enter the foundation zone area
            encoderDrive("ML", 15, 0.5);
            util.updateStatus(">", "skystone delivered.");
            // drop skystone
            util.skystoneServoOpen();
            sleep(200);
            //move towards the RED bridge and stop on the red line
            encoderDrive("MR", 15, 0.25, inputColor);
            //  sleep(2000);
        } else if(skystonePicked) {
            util.skystoneServoOpen();
            //move towards the RED bridge and stop on the red line
            encoderDrive("MR", 12, 0.25, inputColor);
        } else {
            util.skystoneServoOpen();
            encoderDrive("MR", 9, 0.25, inputColor);
        }
        //Chnl 2 Final Adj
        encoderDrive("MB",2,0.16);

    }
}
