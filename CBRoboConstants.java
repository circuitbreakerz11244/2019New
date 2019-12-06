package org.firstinspires.ftc.teamcode;

public interface CBRoboConstants {

    final String CB_CODE_VERSION = "Nov 23 C2.8";

    final double DRIVE_MOTOR_MOVE_POWER   = 0.71;
    final double DRIVE_MOTOR_ROTATE_POWER = 1.00;
    final long SKYSTONE_SEARCH_TIME_MS = 1000;

    final double SKYSTONE_LENGTH = 8.0;

    final double PHONE_PULLSERVO_DIST = 7;

    final double SKYSTONE_INIT_DRIVE_DIST = 24.0;
    final double SKYSTONE_DRIVE_MIN_PWR  = 0.3;
    final double SKYSTONE_DRIVE_MAX_PWR  = 0.7;

    final double SKYSTONE_AP_CH2_DRIVE_DIST = 15.0;
    final double SKYSTONE_AFTERPICK_MIN_PWR  = 0.3;
    final double SKYSTONE_AFTERPICK_MAX_PWR  = 0.7;

    final double SKYSTONE_AP_SD_MIN_PWR  = 0.3;
    final double SKYSTONE_AP_SD_MAX_PWR  = 0.6;

    final double FOUNDATION_CHNL_1_RET_DIST  = 51;

    //drive function  constants
    final double DRIVE_MINIMUM_DRIVE_PWR = 0.16;
    //final double DRIVE_DECELERATION_THRESHOLD = 6.25;
    final double DRIVE_DECELERATION_THRESHOLD = 40;

    final double ARM_MOTOR_MOVE_POWER   = 0.71;
    final double ARM_MOTOR_ROTATE_POWER = 1.00;

    final double ARM_MAX_EXTENSION = 1;
    final double ARM_MIN_EXTENSION = 2;

    final double CLAW_SERVO_OPEN  = 0.0;
    final double CLAW_SERVO_CLOSE = 1.0;

    final double PULL_SERVO_OPEN  = 0.5;
    final double PULL_SERVO_CLOSE = 1.0; //0.3

    final double SKYSTONE_SERVO_OPEN = 0.95;
    final double SKYSTONE_SERVO_CLOSE = 0.35;

    final double CAPSTONE_SERVO_OPEN = 0.7;
    final double CAPSTONE_SERVO_CLOSE = 0.0;

    final double SERVO_INITIAL = 0.0; //move to   0 degree
    final double SERVO_MIDDLE  = 0.5; //move to  90 degrees
    final double SERVO_FINAL   = 1.0; //move to 180 degrees

    // counts per inch (CPI) calculation
    final double COUNTS_PER_MOTOR_REV = 1120;    // eg: Andymark Motor Encoder
    final double WHEEL_DIAMETER_INCHES = 4.0;     // To get the circumference
    final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV) / (WHEEL_DIAMETER_INCHES * 3.1415);

    //0 1 0 ==> -1 1 -1 1 [Direction Vector]
    double[] moveForward = {-1, 1, -1, 1};

    //0 -1 0 ==> 1 -1 1 -1 [Direction Vector]
    double[] moveBackward = {1, -1, 1, -1};

    //1 0 0 ==> -1 -1 1 1 [Direction Vector]
    double[] moveRight = {-1, -1, 1, 1};

    //-1 0 0 ==> 1 1 -1 -1 [Direction Vector]
    double[] moveLeft = {1, 1, -1, -1};

    //0 0 -1 ==> 1 1 1 1 [Direction Vector] rotate the robot in anti-clockwise direction
    double[] rotateLeft = {1, 1, 1, 1};

    //0 0 1 ==> -1 -1 -1 -1 [Direction Vector] rotate the robot in clockwise direction
    double[] rotateRight = {-1, -1, -1, -1};

    //0.71 0.71 0 ==> -1 0 0 1 [Direction Vector] move diagonally in Quad Q1
    double[] moveDiagonalQ1 = {-1, 0, 0, 1};

    //-0.71 0.71 0 ==> 0 1 -1 0 [Direction Vector] move diagonally in Quad Q2
    double[] moveDiagonalQ2 = {0, 1, -1, 0};

    //-0.71 -0.71 0 ==> 1 0 0 -1 [Direction Vector] move diagonally in Quad Q3
    double[] moveDiagonalQ3 = {1, 0, 0, -1};

    //0.71 -0.71 0 ==> 0 -1 1 0 [Direction Vector] move diagonally in Quad Q4
    double[] moveDiagonalQ4 = {0, -1, 1, 0};

}