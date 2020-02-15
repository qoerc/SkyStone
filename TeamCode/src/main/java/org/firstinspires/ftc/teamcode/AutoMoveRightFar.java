package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "AutoMoveRight", group = "7519")
public class AutoMoveRightFar extends LinearOpMode
{
    // Declare Auto members.

    //Left and Right, front and back are all as if you're looking at the robot from the back.

    //Wheels.
    private DcMotor frontLeftMovementMotor;
    private DcMotor frontRightMovementMotor;

    private DcMotor rearLeftMovementMotor;
    private DcMotor rearRightMovementMotor;

    public void runOpMode()
    {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Hardware map.
        frontLeftMovementMotor = hardwareMap.dcMotor.get("frontLeftMovementMotor");
        frontRightMovementMotor = hardwareMap.dcMotor.get("frontRightMovementMotor");
        rearLeftMovementMotor = hardwareMap.dcMotor.get("rearLeftMovementMotor");
        rearRightMovementMotor = hardwareMap.dcMotor.get("rearRightMovementMotor");

        //Device power.
        frontLeftMovementMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMovementMotor.setDirection(DcMotor.Direction.FORWARD);
        rearLeftMovementMotor.setDirection(DcMotor.Direction.FORWARD);
        rearRightMovementMotor.setDirection(DcMotor.Direction.FORWARD);

        waitForStart(); //Wait for the user to press the play button.

        //1220 ticks = 1 full rotation.
        for(int i = 0; i < 12500; i++)
            drive(0.7, 1, 0, -1, 0);
        for(int i = 0; i < 25000; i++)
            drive(0.7, 1, -1, 0, 0);
} //End runOpMode.

    private void drive (double speed , int ticks, int lX, int lY, int rX)
    {
        //LAZY BOI PROGRAMMING BLOCK.
        double h = Math.hypot(lX, lY);
        double robotAngle = Math.atan2(lY, lX) - Math.PI / 4;
        double leftFrontPower = h * Math.cos(robotAngle) - rX;
        double rightFrontPower = h * Math.sin(robotAngle)+ rX;
        double leftRearPower = h * Math.sin(robotAngle)- rX;
        double rightRearPower = h * Math.cos(robotAngle) + rX;

        frontLeftMovementMotor.setPower(leftFrontPower * speed);
        frontLeftMovementMotor.setTargetPosition(ticks);
        frontRightMovementMotor.setPower(-rightFrontPower * speed);
        frontRightMovementMotor.setTargetPosition(-ticks);
        rearLeftMovementMotor.setPower(leftRearPower * speed);
        rearLeftMovementMotor.setTargetPosition(ticks);
        rearRightMovementMotor.setPower(-rightRearPower * speed);
        rearRightMovementMotor.setTargetPosition(-ticks);
    } //End drive.
} //End AutoMoveRight.