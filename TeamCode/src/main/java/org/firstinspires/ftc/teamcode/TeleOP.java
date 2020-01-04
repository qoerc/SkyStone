package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")
public class TeleOP extends LinearOpMode
{

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    //Left and Right, front and back are all as if you're looking at the robot from the back.

    //Wheels.
    private DcMotor frontLeftWheelMotor;
    private DcMotor frontRightWheelMotor;
    private DcMotor rearLeftWheelMotor;
    private DcMotor rearRightWheelMotor;

    //Intakes.
    private DcMotor leftIntakeMotor;
    private DcMotor rightIntakeMotor;

    //XRails.
    private DcMotor leftXRailMotor;
    private DcMotor rightXRailMotor;

    @Override
    public void runOpMode()
    {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Declare motors.

        //Wheels.
        frontLeftWheelMotor = hardwareMap.dcMotor.get("frontLeftWheelMotor");
        frontRightWheelMotor = hardwareMap.dcMotor.get("frontRightWheelMotor");
        rearLeftWheelMotor = hardwareMap.dcMotor.get("rearLeftWheelMotor");
        rearRightWheelMotor = hardwareMap.dcMotor.get("rearRightWheelMotor");

        //Intakes.
        leftIntakeMotor = hardwareMap.dcMotor.get("leftIntakeMotor");
        rightIntakeMotor = hardwareMap.dcMotor.get("rightIntakeMotor");

        //XRails
        leftXRailMotor = hardwareMap.dcMotor.get("leftXRailMotor");
        rightXRailMotor = hardwareMap.dcMotor.get("rightXRailMotor");

        //Motor power.

        //Wheels.
        frontLeftWheelMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightWheelMotor.setDirection(DcMotor.Direction.REVERSE);
        rearLeftWheelMotor.setDirection(DcMotor.Direction.FORWARD);
        rearRightWheelMotor.setDirection(DcMotor.Direction.REVERSE);

        //Intakes.
        leftIntakeMotor.setDirection(DcMotor.Direction.REVERSE);
        rightIntakeMotor.setDirection(DcMotor.Direction.FORWARD);

        //XRails
        leftXRailMotor.setDirection(DcMotor.Direction.FORWARD); //Figure out later.
        rightXRailMotor.setDirection(DcMotor.Direction.REVERSE); //Figure out later.

        //Wait for the game to start (driver presses PLAY).
        waitForStart();
        runtime.reset();

        //Initial declarations.
        double controller1SpeedModifier = 1;
        double controller2SpeedModifier = 1;

        //Run until the end of the match (driver presses STOP).
        while (opModeIsActive())
        {
            //Controller 1.

            //Speed modifier controls.
            if (gamepad1.y)
                controller1SpeedModifier = 0.25;
            else if (gamepad1.x)
                controller1SpeedModifier = .5;
            else if (gamepad1.b)
                controller1SpeedModifier = 0.75;
            else if (gamepad1.a)
                controller1SpeedModifier = 1;

            //Stick turning.
            final double h = Math.hypot(gamepad1.right_stick_x, gamepad1.right_stick_y);
            final double robotAngle = Math.atan2(gamepad1.right_stick_y, gamepad1.right_stick_x) - Math.PI / 4;

            //Wheel motor speed assignments.
            final double frontLeftWheelMotorPower = ((h * Math.sin(robotAngle)) - gamepad1.left_stick_x) * controller1SpeedModifier;
            final double frontRightWheelMotorPower = ((h * Math.cos(robotAngle)) + gamepad1.left_stick_x) * controller1SpeedModifier;
            final double rearLeftWheelMotorPower = ((h * Math.cos(robotAngle)) - gamepad1.left_stick_x) * controller1SpeedModifier;
            final double rearRightWheelMotorPower = ((h * Math.sin(robotAngle)) + gamepad1.left_stick_x) * controller1SpeedModifier;

            //Wheel motor execution.
            frontLeftWheelMotor.setPower(frontLeftWheelMotorPower);
            frontRightWheelMotor.setPower(frontRightWheelMotorPower);
            rearLeftWheelMotor.setPower(rearLeftWheelMotorPower);
            rearRightWheelMotor.setPower(rearRightWheelMotorPower);

            //Controller 2.

            //Speed modifier controls.
            if (gamepad2.y)
                controller2SpeedModifier = 0.25;
            else if (gamepad2.x)
                controller2SpeedModifier = .5;
            else if (gamepad2.b)
                controller2SpeedModifier = 0.75;
            else if (gamepad2.a)
                controller2SpeedModifier = 1;

            //Intake motor speed assignments.
            final double intakeMotorsPower = (gamepad2.right_trigger - gamepad2.left_trigger) * controller2SpeedModifier;

            //Intake motor execution.
            leftIntakeMotor.setPower(intakeMotorsPower);
            rightIntakeMotor.setPower(intakeMotorsPower);

            //XRail motor speed assignments.

        }
    }
}