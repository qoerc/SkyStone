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

@Autonomous(name = "AutoStones", group = "7519")
public class AutoStones                                                                                                                                                                                          extends LinearOpMode {
    // Declare Auto members.

    //Left and Right, front and back are all as if you're looking at the robot from the back.

    //Wheels.
    private DcMotor frontLeftMovementMotor;
    private DcMotor frontRightMovementMotor;

    private DcMotor rearLeftMovementMotor;
    private DcMotor rearRightMovementMotor;

    //Timer declaration.
    ElapsedTime timer = new ElapsedTime();
    char vuforiaPosition = ' ';

    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Hardware map.
        frontLeftMovementMotor = hardwareMap.dcMotor.get("frontLeftMovementMotor");
        frontRightMovementMotor = hardwareMap.dcMotor.get("frontRightMovementMotor");
        rearLeftMovementMotor = hardwareMap.dcMotor.get("rearLeftMovementMotor");
        rearRightMovementMotor = hardwareMap.dcMotor.get("rearRightMovementMotor");

        waitForStart(); //Wait for the user to press the play button.
    } //End runOpMode.

    public void drive(double power, int inches)
    {
        double rpm = Math.abs(160 * power);
        int ticks = 1120;
        double circ = 4 * Math.PI;
        double time = (rpm * ticks) / 60;
        double move = (ticks / circ) * inches;
        double setTimer = move / time;

        //Set drivetrain to run using encoder.
        frontLeftMovementMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMovementMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearLeftMovementMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearRightMovementMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        timer.reset();
        while(opModeIsActive() && timer.seconds()<setTimer)
        {
            //Set the power of the drivetrain.
            frontLeftMovementMotor.setPower(-power);
            frontRightMovementMotor.setPower(-power);
            rearLeftMovementMotor.setPower(-power);
            rearRightMovementMotor.setPower(-power);
        }//End while.

        //Reset drivetrain power.
        frontLeftMovementMotor.setPower(0);
        frontRightMovementMotor.setPower(0);
        rearLeftMovementMotor.setPower(0);
        rearRightMovementMotor.setPower(0);

        //Reset the motor encoders and put motors in stop mode.
        frontLeftMovementMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMovementMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearLeftMovementMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearRightMovementMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(250); //Wait a quarter of a second.
    } //End driveForward.
}