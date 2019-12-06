package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "CBArmTestV1", group = "teleop")
public class CBArmTestV1 extends OpMode {

    RoboUtil util = null;
    double throttle = 0;
    double throttleIncr = 0.001;

    public void initialization() {

        String strVersion = "Nov 10 v1.2";
        util  = new RoboUtil("Manual", telemetry);
        util.robot.initializeArmClaw(hardwareMap,false);

        boolean IsDriveReady = util.robot.initializeDrive(hardwareMap, false);
        boolean IsArmReady = util.robot.initializeArmClaw(hardwareMap,false);

        if(IsDriveReady && IsArmReady) {
            util.addStatus("Initialized Circuit Breakerz. Ver " + strVersion);
            util.updateStatus(">>", " Press Start...");
        } else {
            util.updateStatus(">>", "Not All Hardware are Initialized. Ver " + strVersion);
        }
        util.robot.roboArmClaw.stopArmMotor();
        util.robot.roboArmClaw.resetArmMotorEncoder();

        //util.robot.roboArmClaw.stopClawMotor();
    }

    @Override
    public void init() {
        initialization();
    }

    @Override
    public void loop() {

       /*
        double leftY = -gamepad2.left_stick_y;
        util.updateStatus("Stick>>"+leftY);
        util.robot.roboArmClaw.armMotor.setPower(leftY);

        double righBumper = gamepad2.right_trigger;
        // if the left trigger is pressed, go in reverse
        if (gamepad2.left_trigger != 0.0) {
            throttle = throttle + throttleIncr;
        }
        // if the right trigger is pressed, go in reverse
        if (gamepad2.right_trigger != 0.0) {
            throttle = throttle - throttleIncr;
        }
        util.updateStatus("throttle>>"+throttle);
        throttle = Range.clip(throttle,-0.1,0.1);
        //TEST AND UNCOMMENT
        //util.robot.roboArmClaw.clawMotor.setPower(throttle);

        if(gamepad2.x) {
            util.robot.roboArmClaw.clawMotor.setPower(0);
            util.robot.roboArmClaw.clawMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }


        if(gamepad2.dpad_up) {
            util.robot.roboArmClaw.clawOpen();
        }

        if(gamepad2.dpad_down) {
            util.robot.roboArmClaw.clawClose();
        }
        /*

         */

/*
        if (util.robot.roboArmClaw.arm.getCurrentPosition() <= CBRoboConstants.ARM_MAX_EXTENSION &&
                util.robot.roboArmClaw.arm.getCurrentPosition() >= CBRoboConstants.ARM_MIN_EXTENSION) {
            if (gamepad2.dpad_up)
                extensionMotor.setPower(EXTENSION_POWER);
            else if (gamepad2.dpad_down)
                extensionMotor.setPower(-EXTENSION_POWER);
            else
                extensionMotor.setPower(0.0);
        } else
            extensionMotor.setPower(0.0);

        if (gamepad2.dpad_up)
            extensionMotor.setPower(EXTENSION_POWER);
        else if (gamepad2.dpad_down)
            extensionMotor.setPower(-EXTENSION_POWER);
        else
            extensionMotor.setPower(0.0);

        // Telemetry output for driver assistance
        telemetry.addData(">", "Press Stop to end program." );
        telemetry.addData(">", "linear slide encoder value: " + extensionMotor.getCurrentPosition());
        telemetry.addData(">", "rotation encoder value: " + rotationMotor.getCurrentPosition());
        telemetry.addData(">", "right motor position: " + rightRear.getCurrentPosition());
        telemetry.addData(">", "left motor position: " + leftRear.getCurrentPosition());
    */

        //Drive Code START
        double leftX  = gamepad1.left_stick_x;
        double leftY  = -gamepad1.left_stick_y;    //Gamepad Moving up is giving -ve value.So fix it by reversing it
        double rightX = gamepad1.right_stick_x;

        //Get the desired power settings based on given x, y and z (z is rotation --> rightX used for rotation)
        //Call the reusable method to get PowerVector
        double[] powerVectorArray = AppUtil.getVectorArrays(leftX, leftY, rightX);
        double v1 = powerVectorArray[0];
        double v2 = powerVectorArray[1];
        double v3 = powerVectorArray[2];
        double v4 = powerVectorArray[3];

        //It is necessary to set one side of motor to REVERSE
        //Motor is facing other directions so reverse it.-- Kadhir
        v1 = -v1;
        v3 = -v3;

        //Display the values in the Driver Station for Tank Drive
        util.addStatus(" G1.Motor.x " + leftX + " y " + leftY + " z " + rightX);
        //set the power for the Mecanum wheel drive
        util.setPower(v1, v2, v3, v4);

        //Drive Code END

        //ARM Code START
        //ARM Move Up or Down Functionality
        double leftY2 = -gamepad2.left_stick_y;
        util.updateStatus(" G2.ArmMotor.Y>>" + leftY2);
        Range.clip(leftY2, -1, 1);
        util.robot.roboArmClaw.armMotor.setPower(leftY2);

        //Use DPAD Up/Down for Open/Close Claw
        if(gamepad2.dpad_up) {
            util.robot.roboArmClaw.clawOpen();
        } else if(gamepad2.dpad_down) {
            util.robot.roboArmClaw.clawClose();
        }

        //Claw Up/Down Motor Moving up and Down
        double leftX2 = -gamepad2.left_stick_x;
        util.updateStatus(" G2.ClawMotor.X>>" + leftY2);
        leftY2 = Range.clip(leftY2, -0., 0.1);
        //util.robot.roboArmClaw.clawMotor.setPower(leftY2);

    }

}
