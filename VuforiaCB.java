package org.firstinspires.ftc.teamcode;

import com.vuforia.CameraDevice;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

public class VuforiaCB {

    private final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private final boolean PHONE_IS_PORTRAIT = false  ;

    private boolean targetVisible = false;

    //private final String VUFORIA_KEY = "" + "ASwEgMP/////AAABmdQJynV3uUiZkMDgNwGQ8s4wDc4J8BAGOfNmTToehVo5LSxdc41ZqzPkYw9/V+eFpYDFviY/BvIakfySp59sFELF+rW0se7z99Aia6tFLLZTY8MXaujTgadltyS/NBFGCK9siXDdj8ajGLS9i/wa4mSziIxnR0yZkUq6bh+GMpnKrcr7RDgQm0UvNvFbZX6yFdZBrqzl0IGtrk4z4lqCD/JfwIzXcLLCe7aHkzOmOy/A6//A7ohWvPIXjyZXhgevbpWP2EU5wVMjB8rnMbdgPC2nIhzu1uk2l3KOjN8pah7T3zB+BrP1nIZ9BgChXs3WFflk0TiqnlfCn8P0gFs4w8blaPzjVQb8Ed39ZcEojPjs";
    private final String VUFORIA_KEY = "AeQMSCT/////AAABmRb4S2BgB0sJkefm94uCi7Y6A5XqPgL3Gswck9sgCdbelWA7pDQZTtW8fhCQ75zr3xkJYIHWpRUuq+VNN0VbXKgbsG3WQBO5+V0GUyrdNmKRdO1ms2pRs0oMfQZNsrhEvQig7BBj0QOOkUk2rAjCHlMWCLKjo4wXHMa+uT/y2ZB7P/QxUprJ9kgzC0ktzaQmJx4XD+qfY+nL8ekq6KBnZU7sVjwkOqYhmujVDMuL9+v3J8F73XjgmWej2pUGNYevgVN5+DGxHKBD0EibUdxxJIeNoHUcGjBRaZDkpRujvfKIJiKrPh1pG8dmdRD4bJ44s3AMjimsWpX9OvlrdhUcnFsHUCMAIih5YljPOrQ2Am3D";
    private final float mmPerInch = 25.4f;

    private final float stoneZ = 2.00f * mmPerInch;

    private VuforiaLocalizer vuforia = null;
    private float phoneXRotate    = 0;
    private float phoneYRotate    = 0;
    private float phoneZRotate    = 0;
    //VuforiaTrackable trackable;

    private double x = 0;
    private double y = 0;

    public VuforiaTrackables targetsSkyStone = null;

    List<VuforiaTrackable> allTrackables;
    VuforiaTrackable stoneTarget;

    VuforiaLocalizer.Parameters parameters;

    OpenGLMatrix robotFromCamera;
    private OpenGLMatrix lastLocation = null;


    public VuforiaCB() {

    }

    public void initVuforia(){
        parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection   = CAMERA_CHOICE;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

        stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");

        allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsSkyStone);

        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90 ;
        }

//        final float CAMERA_FORWARD_DISPLACEMENT  = 0.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot center
//        final float CAMERA_VERTICAL_DISPLACEMENT = 0.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
//        final float CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line
/**kavi changes start**/
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        final float CAMERA_FORWARD_DISPLACEMENT  = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot center
        final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line
/**kavi changes end**/
        robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        /*  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }

    }

//    public boolean targetVisible() {
//        targetsSkyStone.activate();
//        // check all the trackable targets to see which one (if any) is visible.
//        for (VuforiaTrackable track : allTrackables) {
//            if (((VuforiaTrackableDefaultListener)track.getListener()).isVisible()) {
//                trackable = track;
//                return true;
//            }
//        }
//        return false;
//
//    }
//
//    double getX() {
//       return x;
//    }
//
//    double getY(){
//        return y;
//    }
//
//    public void getPose() {
//
//        targetVisible();
//
//        if (targetVisible()) {
//
//            OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
//            if (robotLocationTransform != null) {
//                    lastLocation = robotLocationTransform;
//                }
//
//            // express position (translation) of robot in inches.
//            VectorF translation = lastLocation.getTranslation();
//            x = translation.get(0) / mmPerInch;
//            y = translation.get(1) / mmPerInch;
//
//            // express the rotation of the robot in degrees.
////            Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
////            telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
//        }
//    }

    public boolean isTargetVisible() {
        getPose();
        return this.targetVisible;
    }

    double getX() {
        return x;
    }

    double getY(){
        return y;
    }

    public void getPose() {
        targetsSkyStone.activate();

        // check all the trackable targets to see which one (if any) is visible.
        for (VuforiaTrackable trackable : allTrackables) {
            if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                this.targetVisible = true;


                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
                break;
            } else {
                this.targetVisible = false;
            }
        }

        // Provide feedback as to where the robot is located (if we know).
        if (targetVisible) {
            // express position (translation) of robot in inches.
            VectorF translation = lastLocation.getTranslation();
            x = translation.get(0) / mmPerInch;
            y = translation.get(1) / mmPerInch + 6.0;

            // express the rotation of the robot in degrees.
//            Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
//            telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
        }
    }


}

