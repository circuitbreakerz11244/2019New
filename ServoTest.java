package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "ServoTest", group = "teleop")
public class ServoTest extends OpMode {

    //Servo pullServo;
    //Servo skystoneServo;
    Servo capstoneServo;

    public void initialization() {

        //pullServo = hardwareMap.servo.get("pullServo");
        //skystoneServo = hardwareMap.servo.get("skystoneServo");
        capstoneServo = hardwareMap.servo.get("capstoneServo");

        telemetry.addData("Status","Nov 16 1.11 Servo Press Start>>");
        telemetry.update();


    }

    @Override
    public void init() {
        initialization();
    }

    @Override
    public void loop() {

        double leftX  = gamepad1.left_stick_x;
        leftX = Range.clip(leftX,0.0,1.0);
/*
        if(gamepad1.a) {
            pullServo.setPosition(leftX);
            pullServo.getPosition();
            telemetry.addData("Status1", " leftX>> " + leftX + " Curr " + pullServo.getPosition());
            telemetry.update();
        }

        if(gamepad1.b) {
            skystoneServo.setPosition(leftX);
            skystoneServo.getPosition();
            telemetry.addData("Status2", " leftX>> " + leftX + " Curr " + skystoneServo.getPosition());
            telemetry.update();
        }

        if(gamepad1.x) {
            capstoneServo.setPosition(leftX);
            capstoneServo.getPosition();
            telemetry.addData("Status3", " leftX>> " + leftX + " Curr " + capstoneServo.getPosition());
            telemetry.update();
        }

        if(gamepad2.dpad_up) {
            pullServo.setPosition(1.0);
        } else if(gamepad2.dpad_down) {
            pullServo.setPosition(0.3);
        }

        if(gamepad1.dpad_up) {
            skystoneServo.setPosition(0.95);
        } else if(gamepad1.dpad_down) {
            skystoneServo.setPosition(0.35);
        }
*/

        if(gamepad1.x) {
            capstoneServo.setPosition(leftX);
            capstoneServo.getPosition();
            telemetry.addData("Status3", " leftX>> " + leftX + " Curr " + capstoneServo.getPosition());
            telemetry.update();
        }

        if(gamepad2.right_bumper) {
            capstoneServo.setPosition(0.95);
        } else if(gamepad2.left_bumper) {
            capstoneServo.setPosition(0.35);
        }


    }

}
