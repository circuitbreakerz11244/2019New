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

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

import com.vuforia.HINT;
import com.vuforia.Vuforia;


public class VuforiaCB {

    //Telemetry telemetry;
    private final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private final boolean PHONE_IS_PORTRAIT = false  ;

    private boolean targetVisible = false;

    //private final String VUFORIA_KEY = "" + "ASwEgMP/////AAABmdQJynV3uUiZkMDgNwGQ8s4wDc4J8BAGOfNmTToehVo5LSxdc41ZqzPkYw9/V+eFpYDFviY/BvIakfySp59sFELF+rW0se7z99Aia6tFLLZTY8MXaujTgadltyS/NBFGCK9siXDdj8ajGLS9i/wa4mSziIxnR0yZkUq6bh+GMpnKrcr7RDgQm0UvNvFbZX6yFdZBrqzl0IGtrk4z4lqCD/JfwIzXcLLCe7aHkzOmOy/A6//A7ohWvPIXjyZXhgevbpWP2EU5wVMjB8rnMbdgPC2nIhzu1uk2l3KOjN8pah7T3zB+BrP1nIZ9BgChXs3WFflk0TiqnlfCn8P0gFs4w8blaPzjVQb8Ed39ZcEojPjs";
    private final String VUFORIA_KEY = "AeQMSCT/////AAABmRb4S2BgB0sJkefm94uCi7Y6A5XqPgL3Gswck9sgCdbelWA7pDQZTtW8fhCQ75zr3xkJYIHWpRUuq+VNN0VbXKgbsG3WQBO5+V0GUyrdNmKRdO1ms2pRs0oMfQZNsrhEvQig7BBj0QOOkUk2rAjCHlMWCLKjo4wXHMa+uT/y2ZB7P/QxUprJ9kgzC0ktzaQmJx4XD+qfY+nL8ekq6KBnZU7sVjwkOqYhmujVDMuL9+v3J8F73XjgmWej2pUGNYevgVN5+DGxHKBD0EibUdxxJIeNoHUcGjBRaZDkpRujvfKIJiKrPh1pG8dmdRD4bJ44s3AMjimsWpX9OvlrdhUcnFsHUCMAIih5YljPOrQ2Am3D";
    private final float MM_PER_INCH = 25.4f;

    // Constant for Stone Target
    private final float stoneZ = 2.00f * MM_PER_INCH;

    // Variables to be used for later
    private VuforiaLocalizer vuforia;
    private VuforiaLocalizer.Parameters parameters;
    private VuforiaTrackables targetsSkyStone;

    private VuforiaTrackable stoneTarget;
    private VuforiaTrackableDefaultListener listener;

    private OpenGLMatrix lastKnownLocation;
    private OpenGLMatrix phoneLocation;

    private float phoneXRotate    = 0;
    private float phoneYRotate    = 0;
    private float phoneZRotate    = 0;

    private float robotX = 0;
    private float robotY = 0;
    public float robotAngle = 0;

    public double x = 0;
    public double y = 0;

    public VuforiaCB() {

    }

    public void initVuforia() {

        //this.telemetry = pTelemetry;

        // Setup parameters to create localizer
        // To remove the camera view from the screen, remove the R.id.cameraMonitorViewId
        //parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection   = CAMERA_CHOICE;

        parameters.useExtendedTracking = false; // NEEDED
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // These are the vision targets that we want to use
        // The string needs to be the name of the appropriate .xml file in the assets folder
        targetsSkyStone = vuforia.loadTrackablesFromAsset("Skystone");
        //Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 1); // NEEDED ?

        // Setup the target to be tracked
        stoneTarget = targetsSkyStone.get(0); // 0 corresponds to the wheels target
        stoneTarget.setName("Stone Target");
        stoneTarget.setLocation(createMatrixXYZ(0, 0, stoneZ, 90, 0, -90));

        // We need to rotate the camera around it's long axis to bring the correct camera forward.
        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90 ;
        }

        // Set phone location on robot
        phoneLocation = createMatrixYZX(0, 0, 0, phoneYRotate, phoneZRotate, phoneXRotate);

        // Setup listener and inform it of phone information
        listener = (VuforiaTrackableDefaultListener)stoneTarget.getListener();
        listener.setPhoneInformation(phoneLocation, parameters.cameraDirection);
    }

    private OpenGLMatrix createMatrixXYZ(float x, float y, float z, float u, float v, float w) {
        return OpenGLMatrix.translation(x, y, z)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, u, v, w));
    }

    // Creates a matrix for determining the locations and orientations of objects
    // Units are millimeters for x, y, and z, and degrees for u, v, and w
    private OpenGLMatrix createMatrixYZX(float x, float y, float z, float u, float v, float w) {
        return OpenGLMatrix.translation(x, y, z)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, u, v, w));
    }

    // Formats a matrix into a readable string
    private String formatMatrix(OpenGLMatrix matrix) {
        return matrix.formatAsTransform();
    }

    public void startTracking() {
        //CameraDevice.getInstance().setFlashTorchMode(true);
        targetsSkyStone.activate();
    }

    public void stopTracking() {
        targetsSkyStone.deactivate();
        //CameraDevice.getInstance().setFlashTorchMode(false);
    }

    public double getX() {
        return x;
    }

    public double getY(){
        return y;
    }

    public boolean getPose(long targetTime) {

        long lStartTime = System.currentTimeMillis();
        long lEndTime   = 0;
        long timeTaken  = 0;


        while(true) {

            // Ask the listener for the latest information on where the robot is
            OpenGLMatrix latestLocation = listener.getUpdatedRobotLocation();

            // The listener will sometimes return null, so we check for that to prevent errors
            if(latestLocation != null) {

                VectorF translation = latestLocation.getTranslation();
                float[] coordinates = translation.getData();
                robotX = coordinates[0];
                robotY = coordinates[1];

                robotAngle = Orientation.getOrientation(latestLocation, EXTRINSIC, XYZ, DEGREES).thirdAngle;

                x = translation.get(0) / MM_PER_INCH;
                y = translation.get(1) / MM_PER_INCH;

                targetVisible = true;

				/*
				// Send information about whether the target is visible, and where the robot is
				telemetry.addData("Tracking " + stoneTarget.getName(), listener.isVisible());
				telemetry.addData("Last Known Location", formatMatrix(lastKnownLocation));
				telemetry.addData("Data1", " x = " + x + " y = " + y + " robotAngle = " + robotAngle);

				// Send telemetry and idle to let hardware catch up
				telemetry.update();
				*/
            }
            //Target visible. No need to continue to scan
            if(targetVisible) {
                break;
            }
            //continue to scan for the give time
            lEndTime = System.currentTimeMillis();
            timeTaken = lEndTime - lStartTime;
            if(timeTaken >= targetTime) {
                break;
            }

        }
        return targetVisible;
    }

    public boolean isTargetVisible() {
        return targetVisible;
    }

    //initVuforia();
    //waitForStart();
    // Start tracking the targets
    //startTracking();
    /*
    public void runOpMode() throws InterruptedException {

        while(opModeIsActive()) {
        }
    }
    */

}
