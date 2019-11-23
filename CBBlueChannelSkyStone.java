package org.firstinspires.ftc.teamcode;

public class CBBlueChannelSkyStone extends CBAutonomousBase {

    String inputColor = "blue";
    boolean skystoneFound  = false;
    boolean skystonePicked = false;

    double totalMBDistance = 0;
    double totalMSDistance = 0;

    @Override
    public void runOpMode() {
    }

    public void searchSkyStone() {

        if(iStoneNbr > 1) {
            moveToNextStone("ML");
        }
        long SCANNING_TIME = CBRoboConstants.SKYSTONE_SEARCH_TIME_MS;
        if(iStoneNbr == 1){
            SCANNING_TIME = 2* CBRoboConstants.SKYSTONE_SEARCH_TIME_MS;
        }

        skystoneFound = vcb.getPose(SCANNING_TIME);
        //sleep(200);
        if (skystoneFound) {
            vcb.stopTracking();
            util.updateStatus("Skystone X Y"," x=" + vcb.getX() + " y=" + vcb.getY());

            double moveSideDistance = CBRoboConstants.PHONE_PULLSERVO_DIST;
            if (vcb.getY() < 0) {
                moveSideDistance = moveSideDistance + Math.abs(vcb.getY());
            } else if (vcb.getY() > 0) {
                moveSideDistance = moveSideDistance - Math.abs(vcb.getY());
            }
            totalMSDistance = totalMSDistance + moveSideDistance;
            moveSide("MR", moveSideDistance);

            //PULL SERVO ARM IS STRAIGHT to middle of the stone
            //MOVE FWD to pick it
            encoderDrive("MB", Math.abs(vcb.getX())+0.75, 0.15, 0.2);
            totalMBDistance = totalMBDistance + Math.abs(vcb.getX())+0.75;
            util.skystoneServoClose();
            skystonePicked = true;
            sleep(200);
        } else {
            iStoneNbr++;
            //Move to next stone
            if(iStoneNbr <= 5) {
                searchSkyStone();
            }
        }
    }
}
