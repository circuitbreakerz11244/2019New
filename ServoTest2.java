package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "ServoTest2", group = "autonomous")
public class ServoTest2 extends LinearOpMode {

    Servo capstoneServo;

    @Override
    public void runOpMode() {

        capstoneServo = hardwareMap.servo.get("capstoneServo");

        telemetry.addData("Status","Servo Press Start>>" + CBRoboConstants.CB_CODE_VERSION);
        telemetry.update();
        waitForStart();

        capstoneServo.setPosition(0.4);
        telemetry.addData("Status1", "  Curr1 " + capstoneServo.getPosition());
        telemetry.update();
        sleep(2000);

        capstoneServo.setPosition(0.5);
        telemetry.addData("Status2", "  Curr1 " + capstoneServo.getPosition());
        telemetry.update();
        sleep(5000);

        capstoneServo.setPosition(0.6);
        telemetry.addData("Status3", "  Curr1 " + capstoneServo.getPosition());
        telemetry.update();
        sleep(2000);

        /*
        capstoneServo.setPosition(0.0);
        telemetry.addData("Status1", "  Curr1 " + capstoneServo.getPosition());
        telemetry.update();
        sleep(5000);
        capstoneServo.setPosition(0.5);
        telemetry.addData("Status2", "  Curr1 " + capstoneServo.getPosition());
        telemetry.update();
        sleep(5000);
        capstoneServo.setPosition(1.0);
        telemetry.addData("Status3", "  Curr1 " + capstoneServo.getPosition());
        sleep(5000);
        telemetry.update();

        sleep(5000);
        capstoneServo.setPosition(0.0);
        telemetry.addData("Status4", "  Curr1 " + capstoneServo.getPosition());
        sleep(5000);
        telemetry.update();

        sleep(5000);
        capstoneServo.setPosition(-0.5);
        telemetry.addData("Status5", "  Curr1 " + capstoneServo.getPosition());
        sleep(5000);
        telemetry.update();

        sleep(5000);
        capstoneServo.setPosition(-1.0);
        telemetry.addData("Status6", "  Curr1 " + capstoneServo.getPosition());
        sleep(5000);
        telemetry.update();
*/

    }
}
