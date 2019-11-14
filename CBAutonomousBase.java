package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
/*
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
*/
public class CBAutonomousBase extends LinearOpMode {

    RoboUtil util = null;
    VuforiaCB vcb = new VuforiaCB();
    ColorSensor colorSensor;

   /* Orientation angles;

    final double GYRO_CORRECTION_FACTOR = 0.02;*/

    // test to fine tune value
    final double COLOR_THRESH = 500;

    public void initialization() {

        vcb.initVuforia();

        String strVersion = "Nov 13 v1.1";
        util  = new RoboUtil("Manual", telemetry);
        boolean IsDriveReady = util.robot.initializeDrive(hardwareMap, true);
        boolean IsArmReady = util.robot.initializeArmClaw(hardwareMap,false,false);

        // util.robot.initializeIMU(hardwareMap);
        //boolean bHWInitialized = util.robot.getRoboInitializationStatus();
       // if(util.robot.drive.getDriveInitializationStatus() && util.robot.imu.isCalibrated()) {
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
    }

    @Override
    public void runOpMode() {
    }
   /* public void encoderDrive(String strDirection, double distance, double maxPwr, double targetHeading) {
        encoderDrive(strDirection, distance, maxPwr, targetHeading, false, false);
    }*/
    public void encoderDrive(String strDirection, double distance, double maxPwr) {
        encoderDrive(strDirection, distance, maxPwr, false, "");
    }
    /*public void encoderDrive(String strDirection, double distance, double maxPwr, double targetHeading, boolean checkStone) {
        encoderDrive(strDirection, distance, maxPwr, targetHeading, checkStone, false);
    }*/
    public void encoderDrive(String strDirection, double distance, double maxPwr, boolean checkStone) {
        encoderDrive(strDirection, distance, maxPwr, checkStone, "");
    }
/*public void encoderDrive(String strDirection, double distance, double maxPwr, double targetHeading, boolean checkStone, boolean checkColor) {

        angles = util.robot.imu.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
*/
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

       /*
         angles = util.robot.imu.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            double currentHeading = angles.firstAngle;

            double gyroCorrectionPwr = GYRO_CORRECTION_FACTOR * computeGyroDriveCorrectionError(targetHeading, currentHeading);

            gyroCorrectionPwr = 0; // gyro correction needs fixing
       */

            double curPosLF = util.robot.drive.leftFront.getCurrentPosition() - initPosLF;
            double curPosRF = util.robot.drive.rightFront.getCurrentPosition() - initPosRF;
            double curPosLR = util.robot.drive.rightFront.getCurrentPosition() - initPosLR;
            double curPosRR = util.robot.drive.rightFront.getCurrentPosition() - initPosRR;

            currentRobotPos = (curPosLF + curPosRF + curPosLR + curPosRR) / 4;

            // calculating points on trapezoidal profile graph
            power = maxPwr - decSlope * (Math.abs(currentRobotPos) / CBRoboConstants.COUNTS_PER_INCH);

            if (power < CBRoboConstants.DRIVE_MINIMUM_DRIVE_PWR)
                power = CBRoboConstants.DRIVE_MINIMUM_DRIVE_PWR;
/*
double rightPwr = power + gyroCorrectionPwr;
            double leftPwr = power - gyroCorrectionPwr;

            if(strDirection.equalsIgnoreCase("MF")) {
                util.setPower(-leftPwr, rightPwr, -leftPwr, rightPwr);
            } else if(strDirection.equalsIgnoreCase("MB")) {
                util.setPower(leftPwr, -rightPwr, leftPwr, -rightPwr);
            } else if(strDirection.equalsIgnoreCase("ML")) {
                util.setPower(leftPwr, rightPwr, -leftPwr, -rightPwr);
            } else if(strDirection.equalsIgnoreCase("MR")) {
                util.setPower(-leftPwr, -rightPwr, leftPwr, rightPwr);
            }
 */
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


//            util.addStatus(">", " target position = " + targetPos);
//            util.addStatus(">", " current robot pos = " + currentRobotPos);
//            util.addStatus(">", " Direction = " + strDirection);
            util.updateStatus(">", " power = " + power);

        }
        util.robot.drive.stopDriveMotors();
    }
    //    public void encoderDrive(String strDirection, double distance, double maxPwr, boolean checkStone) {
//
//        //robot.leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        //robot.rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        util.robot.drive.resetDriveMotorsEncoder();
//        if(strDirection.equalsIgnoreCase("MF") || strDirection.equalsIgnoreCase("MB")) {
//            distance = distance/2;
//        }
//
//        double initPosLF = util.robot.drive.leftFront.getCurrentPosition();
//        double initPosRF = util.robot.drive.rightFront.getCurrentPosition();
//        double initPosLR = util.robot.drive.leftRear.getCurrentPosition();
//        double initPosRR = util.robot.drive.rightRear.getCurrentPosition();
//
//        double targetPos    = distance * CBRoboConstants.COUNTS_PER_INCH;
//
//        double currentRobotPos = 0.;
//
//        double power = maxPwr;
//
//        // slopes for proportional speed increase/decrease
//        double decSlope = (maxPwr - CBRoboConstants.DRIVE_MINIMUM_DRIVE_PWR) / (CBRoboConstants.DRIVE_DECELERATION_THRESHOLD);
//
//        while (Math.abs(currentRobotPos) < Math.abs(targetPos)){
//
//            double curPosLF = util.robot.drive.leftFront.getCurrentPosition() - initPosLF;
//            double curPosRF = util.robot.drive.rightFront.getCurrentPosition() - initPosRF;
//            double curPosLR = util.robot.drive.rightFront.getCurrentPosition() - initPosLR;
//            double curPosRR = util.robot.drive.rightFront.getCurrentPosition() - initPosRR;
//
//            currentRobotPos = (curPosLF + curPosRF + curPosLR + curPosRR) / 4;
//
//            // calculating points on trapezoidal profile graph
//            power = maxPwr - decSlope * (Math.abs(currentRobotPos) / CBRoboConstants.COUNTS_PER_INCH);
//
//            if (power < CBRoboConstants.DRIVE_MINIMUM_DRIVE_PWR)
//                power = CBRoboConstants.DRIVE_MINIMUM_DRIVE_PWR;
//
//            if(strDirection.equalsIgnoreCase("MF")) {
//                util.moveForward(power);
//            } else if(strDirection.equalsIgnoreCase("MB")) {
//                util.moveBackward(power);
//            } else if(strDirection.equalsIgnoreCase("ML")) {
//                util.moveLeft(power);
//            } else if(strDirection.equalsIgnoreCase("MR")) {
//                util.moveRight(power);
//            }
//
//            if (vcb.targetVisible() && checkStone) {
//                break;
//            }
//
//
//            util.addStatus(">", " target position = " + targetPos);
//            util.addStatus(">", " current robot pos = " + currentRobotPos);
//            util.addStatus(">", " Direction = " + strDirection);
//            util.updateStatus(">", " power = " + power);
//
//        }
//
//        util.robot.drive.stopDriveMotors();
//
//    }

    /*
    public void vuforiaNavigate(double y, String alliance) {
        vcb.getPose();

        String direction = "";

        if (alliance.equalsIgnoreCase("red"))
            direction = "ML";
        else
            direction = "MR";

        encoderDrive(direction, Math.abs(vcb.getY()), 0.3, 0);

        double x = vcb.getX();
        encoderDrive("MB", x, 0.4, 0);

        sleep(500);

        encoderDrive("MF", x, 0.4, 0);

    }*/
     public void vuforiaNavigate(double y) {

        vcb.getPose();

        if(vcb.isTargetVisible()) {
            double x= vcb.getX();
            double z= vcb.getY();
            util.updateStatus(">", "TARGET VISIBLE!!! x="+x+", y:"+z);
            encoderDrive("MR", Math.abs(vcb.getY()), 0.2);

            sleep(500);

            encoderDrive("MR", 11, 0.25);
            sleep(200);

         encoderDrive("MB", Math.abs(vcb.getX())-2, 0.2);
            sleep(200);
        }
    }/*
    double computeGyroDriveCorrectionError(double inputHeading, double currentHeading) {

        double error;
        if (inputHeading * currentHeading >= 0)
            error = currentHeading - inputHeading;
        else {
            if (Math.abs(inputHeading) > 90) {
                if (inputHeading < 0)
                    error = -((180 - currentHeading) + (180 + inputHeading));
                else
                    error = (180 + currentHeading) + (180 - inputHeading);
            } else
                error = currentHeading - inputHeading;
        }


        return error;
    }
    */

}
