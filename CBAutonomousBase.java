package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

public class CBAutonomousBase extends LinearOpMode {

    RoboUtil util = null;
    VuforiaCB vcb = new VuforiaCB();
    ColorSensor colorSensor;

    // test to fine tune value
    final double COLOR_THRESH = 500;

    public void initialization() {

        vcb.initVuforia();

        String strVersion = "Nov 13 v1.1";
        util  = new RoboUtil("Manual", telemetry);
        boolean IsDriveReady = util.robot.initializeDrive(hardwareMap, true);
        boolean IsArmReady = util.robot.initializeArmClaw(hardwareMap,false,false);

        if(IsDriveReady && IsArmReady){
            util.addStatus("Initialized Circuit Breakerz. Ver " + strVersion);
            util.updateStatus(">>", " Press Start...");
        } else {
            util.updateStatus(">>", "Not All Hardware are Initialized. Ver " + strVersion);
        }

        colorSensor= hardwareMap.get(ColorSensor.class, "color");
        colorSensor.enableLed(true);

        util.robot.drive.resetDriveMotorsEncoder();
        util.skystoneServoOpen();
        util.robot.roboArmClaw.pullServoOpen();
    }

    @Override
    public void runOpMode() {
    }

    public void encoderDrive(String strDirection, double distance, double maxPwr) {
        encoderDrive(strDirection, distance, maxPwr, false, "");
    }

    public void encoderDrive(String strDirection, double distance, double maxPwr, boolean checkStone) {
        encoderDrive(strDirection, distance, maxPwr, checkStone, "");
    }

    public void encoderDrive(String strDirection, double distance, double maxPwr, boolean checkStone, String inputColor) {

        if(inputColor == null) {
            inputColor = "";
        }

        util.robot.drive.resetDriveMotorsEncoder();
        if(strDirection.equalsIgnoreCase("MF") || strDirection.equalsIgnoreCase("MB")) {
            distance = distance/2;
        }

        double initPosLF = util.robot.drive.leftFront.getCurrentPosition();
        double initPosRF = util.robot.drive.rightFront.getCurrentPosition();
        double initPosLR = util.robot.drive.leftRear.getCurrentPosition();
        double initPosRR = util.robot.drive.rightRear.getCurrentPosition();

        double targetPos = distance * CBRoboConstants.COUNTS_PER_INCH;

        double currentRobotPos = 0.;

        double power = maxPwr;

        // slopes for proportional speed increase/decrease
        double decSlope = (maxPwr - CBRoboConstants.DRIVE_MINIMUM_DRIVE_PWR) / (CBRoboConstants.DRIVE_DECELERATION_THRESHOLD);

        while (Math.abs(currentRobotPos) < Math.abs(targetPos)) {

            double curPosLF = util.robot.drive.leftFront.getCurrentPosition() - initPosLF;
            double curPosRF = util.robot.drive.rightFront.getCurrentPosition() - initPosRF;
            double curPosLR = util.robot.drive.rightFront.getCurrentPosition() - initPosLR;
            double curPosRR = util.robot.drive.rightFront.getCurrentPosition() - initPosRR;

            currentRobotPos = (curPosLF + curPosRF + curPosLR + curPosRR) / 4;

            // calculating points on trapezoidal profile graph
            power = maxPwr - decSlope * (Math.abs(currentRobotPos) / CBRoboConstants.COUNTS_PER_INCH);

            if (power < CBRoboConstants.DRIVE_MINIMUM_DRIVE_PWR)
                power = CBRoboConstants.DRIVE_MINIMUM_DRIVE_PWR;

            if(strDirection.equalsIgnoreCase("MF")) {
                util.moveForward(power);
            } else if(strDirection.equalsIgnoreCase("MB")) {
                util.moveBackward(power);
            } else if(strDirection.equalsIgnoreCase("ML")) {
                util.moveLeft(power);
            } else if(strDirection.equalsIgnoreCase("MR")) {
                util.moveRight(power);
            }

            if (vcb.isTargetVisible() && checkStone) {
                break;
            }
            util.updateStatus("inputColor:", inputColor);
            util.updateStatus("colorSensor.red():", colorSensor.red()+"");
            util.updateStatus("COLOR_THRESH:", COLOR_THRESH+"");

            // test to see whether to check red, blue or green
            if(inputColor!=null && ((inputColor.equalsIgnoreCase("red") && colorSensor.red() > COLOR_THRESH) ) ) {
                util.updateStatus("RED FOUND!!!!!!!!!!!!!!!!", "");
                break;
            }

            if(inputColor!=null && ((inputColor.equalsIgnoreCase("blue") && colorSensor.blue() > COLOR_THRESH) ) ) {
                util.updateStatus("BLUE FOUND!!!!!!!!!!!!!!!!", "");
                break;
            }

            util.updateStatus(">", " power = " + power);

        }
        util.robot.drive.stopDriveMotors();
    }
     public void vuforiaNavigate(String allianceColor) {

        vcb.getPose();

       // if(vcb.isTargetVisible()) {
            double x= vcb.getX();
            double z= vcb.getY();
            util.updateStatus(">", "TARGET VISIBLE!!! x="+x+", y:"+z);
            if(allianceColor!=null && allianceColor.equalsIgnoreCase("red")) {
                encoderDrive("MR",  Math.abs(vcb.getY()), 0.2);
                //sleep(200);
                encoderDrive("MR", 9 , 0.2);
                //sleep(200);
            }else if(allianceColor!=null && allianceColor.equalsIgnoreCase("blue")) {
                encoderDrive("ML",  Math.abs(vcb.getY()), 0.2);
              //  sleep(200);
                encoderDrive("ML", 1.5 , 0.2);
                // sleep(200);
            }
            encoderDrive("MB", Math.abs(vcb.getX())-2, 0.2);
            //sleep(200);
      //  }
    }
}
