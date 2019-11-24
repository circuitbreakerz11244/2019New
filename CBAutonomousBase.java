package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.vuforia.CameraDevice;

import java.text.DecimalFormat;


public class CBAutonomousBase extends LinearOpMode {

    RoboUtil util = null;
    VuforiaCB vcb = new VuforiaCB();
    ColorSensor colorSensor;
    int iStoneNbr = 1;
    boolean colorLineFound = false;
    long autonomousStartTime = 0;
	
	DecimalFormat df = new DecimalFormat("0.00");

    // test to fine tune value
    final double COLOR_THRESH = 500;

    public void initialization() {

        vcb.initVuforia();
        //vcb.initVuforia(telemetry);

        util = new RoboUtil("Manual", telemetry);
        boolean IsDriveReady = util.robot.initializeDrive(hardwareMap, true);
        boolean IsArmReady = util.robot.initializeArmClaw(hardwareMap, false, false);


        colorSensor = hardwareMap.get(ColorSensor.class, "color");
        colorSensor.enableLed(true);

        util.robot.drive.resetDriveMotorsEncoder();
        util.skystoneServoOpen();
        util.robot.roboArmClaw.pullServoOpen();
        util.robot.roboArmClaw.capstoneServoOpen();

        vcb.startTracking();

        if (IsDriveReady && IsArmReady) {
            util.addStatus("Initialized Circuit Breakerz. Ver " + CBRoboConstants.CB_CODE_VERSION);
            util.updateStatus(">>", " Press Start...");
        } else {
            util.updateStatus(">>", "Not All Hardware are Initialized. Ver " + CBRoboConstants.CB_CODE_VERSION);
        }

    }

    public void flashTorchOn() {
        CameraDevice.getInstance().setFlashTorchMode(true);
    }

    public void flashTorchOff() {
        CameraDevice.getInstance().setFlashTorchMode(false);
    }

    @Override
    public void runOpMode() {
    }

    public void encoderDrive(String strDirection, double distance, double maxPwr) {
        encoderDrive(strDirection, distance, CBRoboConstants.DRIVE_MINIMUM_DRIVE_PWR, maxPwr, null);
    }

    public void encoderDrive(String strDirection, double distance, double maxPwr, String inputColor) {
        encoderDrive(strDirection, distance, CBRoboConstants.DRIVE_MINIMUM_DRIVE_PWR, maxPwr, inputColor);
    }

    public void encoderDrive(String strDirection, double distance, double maxPwr, boolean checkStone) {
        encoderDrive(strDirection, distance, CBRoboConstants.DRIVE_MINIMUM_DRIVE_PWR, maxPwr, null);
    }

    public void encoderDrive(String strDirection, double distance, double minPwr, double maxPwr) {
        encoderDrive(strDirection, distance, minPwr, maxPwr, null);
    }

    public void encoderDrive(String strDirection, double distance, double minPwr, double maxPwr, String inputColor) {

        if(distance == 0) {
            return;
        }
        if(inputColor == null) {
            inputColor = "";
        }

        //Reset All Encoders before start moving
		util.robot.drive.resetDriveMotorsEncoder();
        
		//if(strDirection.equalsIgnoreCase("ML") || strDirection.equalsIgnoreCase("MR")) {
			//distance = distance * 1.12;
        //}

        double targetPos = distance * CBRoboConstants.COUNTS_PER_INCH;

        double currentRobotPos = 0.0;

        double power = maxPwr;

        // slopes for proportional speed increase/decrease
        //double decSlope = (maxPwr - CBRoboConstants.DRIVE_MINIMUM_DRIVE_PWR) / (CBRoboConstants.DRIVE_DECELERATION_THRESHOLD);
        double decSlope = (maxPwr - minPwr) / (distance);

        while (currentRobotPos < Math.abs(targetPos)) {

            currentRobotPos = util.robot.drive.getCurrentRobotPos();

            // calculating points on trapezoidal profile graph
            power = maxPwr - decSlope * ( currentRobotPos / CBRoboConstants.COUNTS_PER_INCH);

            if (power < minPwr) {
                power = minPwr;
            }

            if(strDirection.equalsIgnoreCase("MF")) {
                util.moveForward(power);
            } else if(strDirection.equalsIgnoreCase("MB")) {
                util.moveBackward(power);
            } else if(strDirection.equalsIgnoreCase("ML")) {
                util.moveLeft(power);
            } else if(strDirection.equalsIgnoreCase("MR")) {
                util.moveRight(power);
            }

            if(inputColor.length() > 1) {

                util.updateStatus(" Color Values ", " Red = " + colorSensor.red() + " Blue = " + colorSensor.blue());
                // test to see whether to check red, blue or green
                if(inputColor.equalsIgnoreCase("red")  && colorSensor.red() > COLOR_THRESH ) {
                    util.updateStatus("RED FOUND!!!", ""+colorSensor.red());
                    colorLineFound = true;
                    break;
                }
                if(inputColor.equalsIgnoreCase("blue") && colorSensor.blue() > COLOR_THRESH ) {					
                    util.updateStatus("BLUE FOUND!!!", ""+colorSensor.blue());
                    colorLineFound = true;
                    break;
                }
            }
            util.updateStatus("EncDR", " DIR = " + strDirection + " dis " + distance + " mp "+ minPwr + " p " + df.format(power) + " ic " + inputColor);
        }
		util.updateStatus("EncDR1", " DIR = " + strDirection + " dis " + distance + " TPos "+ targetPos + " curr " + currentRobotPos);
        util.robot.drive.stopDriveMotors();
    }

    public void encoderDriveOLD(String strDirection, double distance, double minPwr, double maxPwr, String inputColor) {

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
        //double decSlope = (maxPwr - CBRoboConstants.DRIVE_MINIMUM_DRIVE_PWR) / (CBRoboConstants.DRIVE_DECELERATION_THRESHOLD);
        double decSlope = (maxPwr - minPwr) / (distance);

        while (Math.abs(currentRobotPos) < Math.abs(targetPos)) {

            double curPosLF = util.robot.drive.leftFront.getCurrentPosition() - initPosLF;
            double curPosRF = util.robot.drive.rightFront.getCurrentPosition() - initPosRF;
            double curPosLR = util.robot.drive.rightFront.getCurrentPosition() - initPosLR;
            double curPosRR = util.robot.drive.rightFront.getCurrentPosition() - initPosRR;

            currentRobotPos = (curPosLF + curPosRF + curPosLR + curPosRR) / 4;

            // calculating points on trapezoidal profile graph
            power = maxPwr - decSlope * (Math.abs(currentRobotPos) / CBRoboConstants.COUNTS_PER_INCH);

            if (power < minPwr)
                power = minPwr;

            if(strDirection.equalsIgnoreCase("MF")) {
                util.moveForward(power);
            } else if(strDirection.equalsIgnoreCase("MB")) {
                util.moveBackward(power);
            } else if(strDirection.equalsIgnoreCase("ML")) {
                util.moveLeft(power);
            } else if(strDirection.equalsIgnoreCase("MR")) {
                util.moveRight(power);
            }

            // test to see whether to check red, blue or green
            if(inputColor!=null && ((inputColor.equalsIgnoreCase("red") && colorSensor.red() > COLOR_THRESH) ) ) {
                util.updateStatus("OLD RED FOUND!!!", ""+colorSensor.red());
                colorLineFound = true;
                break;
            }

            if(inputColor!=null && ((inputColor.equalsIgnoreCase("blue") && colorSensor.blue() > COLOR_THRESH) ) ) {
                util.updateStatus("OLD BLUE FOUND!!!", ""+colorSensor.blue());
                colorLineFound = true;
                break;
            }

            util.updateStatus("OLD EncDR", " DIR = " + strDirection + " dis " + distance + " mp "+ minPwr + " p " + df.format(power) + " ic " + inputColor);
        }
        util.updateStatus("OLD EncDR1", " DIR = " + strDirection + " dis " + distance + " TPos "+ targetPos + " curr " + currentRobotPos);
        util.robot.drive.stopDriveMotors();
    }

    public void moveSide(String strDirection, double distance) {
        encoderDrive("MR", distance, 0.16, 0.2);
    }

    //public void moveToNextStone(String strDirection) {
        //moveToNextStone(iStoneNbr-1, strDirection);
    //}

    public void moveToNextStone(String strDirection) {
        encoderDrive(strDirection, CBRoboConstants.SKYSTONE_LENGTH, 0.16, 0.3);
    }

    public long getCurrTimeElapsed() {
        long currentTime = System.currentTimeMillis();
        long deltaTime = currentTime - autonomousStartTime;
        return deltaTime;
    }

    public boolean isTimeElapsed(){
        long deltaTime = getCurrTimeElapsed();
        if(deltaTime>=30000)  {
            return true;
        } else {
            return false;
        }
    }

    public void redChannelFoundationPick() {

        initialization();
        waitForStart();
        encoderDrive("MR", 11, 0.4);
        encoderDrive("MF", 34, 0.5);
        //pull foundation
        util.robot.roboArmClaw.pullServoClose();

        sleep(200);
        encoderDrive("MB", 34, 0.5);
        util.robot.roboArmClaw.pullServoOpen();
        sleep(200);

    }

    public void blueChannelFoundationPick() {

        initialization();
        waitForStart();
        encoderDrive("ML", 11, 0.4);
        encoderDrive("MF", 34, 0.5);
        //pull foundation
        util.robot.roboArmClaw.pullServoClose();
        sleep(200);

        encoderDrive("MB", 34, 0.5);
        util.robot.roboArmClaw.pullServoOpen();
        sleep(200);
    }

}
