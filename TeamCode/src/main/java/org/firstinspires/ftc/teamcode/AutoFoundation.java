package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name = "AutoFoundation", group = "7519")
public class AutoFoundation extends LinearOpMode
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
        drive(0.7, 11200);
        rotate(0.7, 11200);
        drive(0.7, 11200);
    } //End runOpMode.

    private void drive (double speed , int ticks)
    {
        frontLeftMovementMotor.setPower(speed);
        frontLeftMovementMotor.setTargetPosition(ticks);
        frontRightMovementMotor.setPower(-speed);
        frontRightMovementMotor.setTargetPosition(-ticks);
        rearLeftMovementMotor.setPower(speed);
        rearLeftMovementMotor.setTargetPosition(ticks);
        rearRightMovementMotor.setPower(-speed);
        rearRightMovementMotor.setTargetPosition(-ticks);
    } //End drive.

    private void rotate (double speed , int ticks)
    {
        frontLeftMovementMotor.setPower(speed);
        frontLeftMovementMotor.setTargetPosition(ticks);
        frontRightMovementMotor.setPower(speed);
        frontRightMovementMotor.setTargetPosition(ticks);
        rearLeftMovementMotor.setPower(speed);
        rearLeftMovementMotor.setTargetPosition(ticks);
        rearRightMovementMotor.setPower(speed);
        rearRightMovementMotor.setTargetPosition(ticks);
    } //End rotate.
} //End AutoFoundation.