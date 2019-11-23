package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.CameraDevice;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/*
 * This OpMode was written for the VuforiaDemo Basics video. This demonstrates basic principles of
 * using VuforiaDemo in FTC.
 */
@Autonomous(name = "VFTest2", group = "autonomous")
public class VFTest2 extends LinearOpMode {

    VuforiaCB vcb = new VuforiaCB();

    public void runOpMode() throws InterruptedException {

        vcb.initVuforia();


        waitForStart();

        //vcb.startTracking();
        CameraDevice.getInstance().setFlashTorchMode(true);
        while (opModeIsActive()) {
            boolean targetFound = vcb.isTargetVisible();
            if(targetFound) {
                telemetry.addData("Data2", " x = " + vcb.getX() + " y = " + vcb.getY() );
                sleep(10000);
            } else {
                telemetry.addData("Status","Not Found");
                sleep(2000);
            }
            idle();
        }
        CameraDevice.getInstance().setFlashTorchMode(false);
        telemetry.addData("Data3", " x = " + vcb.getX() + " y = " + vcb.getY());
        sleep(10000);
    }

}
