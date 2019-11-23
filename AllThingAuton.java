package org.firstinspires.ftc.teamcode;

public class AllThingAuton extends CBAutonomousBase {

    String inputColor = "red";
    boolean skystoneFound  = false;
    boolean skystonePicked = false;

    double totalMBDistance = 0;
    double totalMSDistance = 0;

    @Override
    public void runOpMode() {
    }

    public void searchSkyStone() {

        moveToNextStone("MR");

        vcb.startTracking();
        skystoneFound = vcb.getPose(1000);
        sleep(200);
        if (skystoneFound) {
            vcb.stopTracking();

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
            encoderDrive("MB", Math.abs(vcb.getX()), 0.2, 0.3);
            totalMBDistance = totalMBDistance + Math.abs(vcb.getX());
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
