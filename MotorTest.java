package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "CB MotorTest", group = "teleop")
public class MotorTest extends OpMode {

    DcMotor armMotor;
    public void initialization() {
        armMotor  = hardwareMap.get(DcMotor.class,  "armMotor"  );

    }

    @Override
    public void init() {
        initialization();
    }

    @Override
    public void loop() {

        double leftY2 = -gamepad2.left_stick_y;
        armMotor.setPower(leftY2);
        telemetry.addData("leftY2 >>",""+leftY2);
    }
}