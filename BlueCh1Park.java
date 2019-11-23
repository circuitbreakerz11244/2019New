package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "BlueF Channel 1 Park", group = "autonomous")
public class BlueCh1Park extends CBAutonomousBase{

        String inputColor = "blue";

        @Override
        public void runOpMode() {

            initialization();
            waitForStart();

            encoderDrive("MR", 10, 0.25, inputColor);

        }
    }







