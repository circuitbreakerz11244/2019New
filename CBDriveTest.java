package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class CBDriveTest extends LinearOpMode {

    RoboUtil util = null;

    public void initialization() {

        util  = new RoboUtil("Manual", telemetry);
        util.robot.initializeDrive(hardwareMap,true);
        if(util.robot.drive.getDriveInitializationStatus()) {
            util.addStatus("Initialized Circuit Breakerz. Ver " + CBRoboConstants.CB_CODE_VERSION);
            util.updateStatus(">>", " Press Start...");
        } else {
            util.updateStatus(">>", "Not All Hardware are Initialized. Ver " + CBRoboConstants.CB_CODE_VERSION);
        }
        util.robot.drive.resetDriveMotorsEncoder();
    }

    public void sleepInSecs(int iSeconds) {
        sleep(iSeconds * 1000);
    }

    @Override
    public void runOpMode() {

        initialization();
        waitForStart();
		util.robot.drive.resetMotorEncoderValues();

        //util.moveForward();
        util.moveLeft();
        sleepInSecs(1);
        util.robot.drive.applyDriveMotorsBrake();
        util.robot.drive.stopDriveMotors();
        util.displayDriveEncoderValues();

        sleepInSecs(10);

        /*
        util.robot.drive.resetMotorEncoderValues();
        util.moveLeft();
        sleepInSecs(1);
        util.robot.drive.applyDriveMotorsBrake();
        util.robot.drive.stopDriveMotors();
        util.displayDriveEncoderValues();

        sleepInSecs(10);

        util.robot.drive.resetMotorEncoderValues();
        util.moveRight();
        sleepInSecs(1);
        util.robot.drive.applyDriveMotorsBrake();
        util.robot.drive.stopDriveMotors();
        util.displayDriveEncoderValues();

        sleepInSecs(10);

        util.robot.drive.resetMotorEncoderValues();
        util.moveBackward();
        sleepInSecs(1);
        util.robot.drive.applyDriveMotorsBrake();
        util.robot.drive.stopDriveMotors();
        util.displayDriveEncoderValues();
		
		sleepInSecs(10);
*/
    }
}
