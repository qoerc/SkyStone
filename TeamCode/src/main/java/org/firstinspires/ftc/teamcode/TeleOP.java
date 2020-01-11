package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
        frontLeftWheelMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightWheelMotor.setDirection(DcMotor.Direction.FORWARD);
        rearLeftWheelMotor.setDirection(DcMotor.Direction.REVERSE);
        rearRightWheelMotor.setDirection(DcMotor.Direction.FORWARD);

        //Intakes.
        leftIntakeMotor.setDirection(DcMotor.Direction.REVERSE);
        rightIntakeMotor.setDirection(DcMotor.Direction.FORWARD);

        //XRails
        leftXRailMotor.setDirection(DcMotor.Direction.FORWARD); //Figure. out later.
        rightXRailMotor.setDirection(DcMotor.Direction.REVERSE); //Figure out later.

        //Wait for the game to start (driver presses PLAY).
        waitForStart();
        runtime.reset();

        //Initial declarations.
        double controller1SpeedModifier = 1;
        double controller2SpeedModifier = 1;
        double intakeMotorsPower = 0;

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

            //Wheel motor controls.
            double xMovement = controller1SpeedModifier * this.gamepad1.left_stick_x;
            double yMovement = controller1SpeedModifier * this.gamepad1.right_stick_y;
            double rotationSpeed = controller1SpeedModifier * this.gamepad1.left_stick_x;
            move(xMovement, yMovement, rotationSpeed);

            //Controller 2.

            //Intake motor speed assignments.
            if (gamepad2.right_bumper)
                intakeMotorsPower = 1;
            else if (gamepad2.start)
                intakeMotorsPower = 0;
            else if (gamepad2.left_bumper)
                intakeMotorsPower = -1;
            //Intake motor execution.
            leftIntakeMotor.setPower(intakeMotorsPower * controller2SpeedModifier);
            rightIntakeMotor.setPower(intakeMotorsPower * controller2SpeedModifier);

            //XRail motor speed assignments.
            double xRailMotorsPower = gamepad2.left_stick_y;

            //XRail motor execution.
            leftXRailMotor.setPower(xRailMotorsPower);
            rightXRailMotor.setPower(xRailMotorsPower);

            //Claw Movement Control.

        }
    }
    //Servs
    private void move(double xMovement, double yMovement, double rotationSpeed) {
        double temp = rotationSpeed;
        rotationSpeed = xMovement;
        xMovement = temp;
        // Equations for the power
        double frontLeftPower = Math.sqrt(.5) * (yMovement - xMovement) + rotationSpeed;
        double frontRightPower = Math.sqrt(.5) * (xMovement + yMovement) - rotationSpeed;
        double backLeftPower = Math.sqrt(.5) * (xMovement + yMovement) + rotationSpeed;
        double backRightPower = Math.sqrt(.5) * (yMovement - xMovement) - rotationSpeed;

        frontLeftPower *= -1;
        backLeftPower *= -1;

        // Normalize power so it is between -1 and 1
        double maxPower = Math.max(Math.max(frontLeftPower, frontRightPower), Math.max(backLeftPower, backRightPower));
        if(maxPower > 1) {
            frontLeftPower /= maxPower;
            frontRightPower /= maxPower;
            backLeftPower /= maxPower;
            backRightPower /= maxPower;
        }

        double minPower = Math.min(Math.min(frontLeftPower, frontRightPower), Math.min(backLeftPower, backRightPower));
        if(minPower < -1) {
            frontLeftPower /= -minPower;
            frontRightPower /= -minPower;
            backLeftPower /= -minPower;
            backRightPower /= -minPower;
        }

        this.frontLeftWheelMotor.setPower(frontLeftPower);
        this.frontRightWheelMotor.setPower(frontRightPower);
        this.rearLeftWheelMotor.setPower(backLeftPower);
        this.rearRightWheelMotor.setPower(backRightPower);
    }
}