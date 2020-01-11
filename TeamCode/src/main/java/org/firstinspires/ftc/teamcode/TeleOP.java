package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
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

    //Servos.
    private CRServo clawMovementServo;
    private Servo grabbyClawServo;

    @Override
    public void runOpMode()
    {
        //Declare hardware.

        //Wheels.
        frontLeftWheelMotor = hardwareMap.dcMotor.get("frontLeftWheelMotor");
        frontRightWheelMotor = hardwareMap.dcMotor.get("frontRightWheelMotor");
        rearLeftWheelMotor = hardwareMap.dcMotor.get("rearLeftWheelMotor");
        rearRightWheelMotor = hardwareMap.dcMotor.get("rearRightWheelMotor");

        //Intakes.
        leftIntakeMotor = hardwareMap.dcMotor.get("leftIntakeMotor");
        rightIntakeMotor = hardwareMap.dcMotor.get("rightIntakeMotor");

        //XRails.
        leftXRailMotor = hardwareMap.dcMotor.get("leftXRailMotor");
        rightXRailMotor = hardwareMap.dcMotor.get("rightXRailMotor");

        //Servos.
        clawMovementServo = hardwareMap.crservo.get("clawMovementServo");
        grabbyClawServo = hardwareMap.servo.get("grabbyClawServo");

        //Device power.

        //Wheels.
        frontLeftWheelMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightWheelMotor.setDirection(DcMotor.Direction.FORWARD);
        rearLeftWheelMotor.setDirection(DcMotor.Direction.FORWARD);
        rearRightWheelMotor.setDirection(DcMotor.Direction.FORWARD);

        //Intakes.
        leftIntakeMotor.setDirection(DcMotor.Direction.REVERSE);
        rightIntakeMotor.setDirection(DcMotor.Direction.FORWARD);

        //XRails.
        leftXRailMotor.setDirection(DcMotor.Direction.FORWARD);
        rightXRailMotor.setDirection(DcMotor.Direction.REVERSE);

        //Servos.
        clawMovementServo.setDirection(CRServo.Direction.FORWARD);
        grabbyClawServo.setDirection(Servo.Direction.FORWARD);

        //Wait for the game to start (driver presses PLAY).
        waitForStart();
        runtime.reset();

        //Initial declarations.
        double controller1SpeedModifier = 1;
        double intakeMotorsPower = 0;

        //Run until the end of the match (driver presses STOP).
        while (opModeIsActive())
        {
            //Controller 1.

            //Speed modifier controls.
            if (gamepad1.b)
                controller1SpeedModifier = 0.25;
            else if (gamepad1.y)
                    controller1SpeedModifier = 0.5;
            else if (gamepad1.x)
                controller1SpeedModifier = .75;
            else if (gamepad1.a)
                controller1SpeedModifier = 1;

            //Wheel motor controls.
            double h = Math.hypot(-gamepad1.left_stick_x, gamepad1.left_stick_y);
            double robotAngle = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;

            double leftFrontPower = h * Math.cos(robotAngle) - gamepad1.right_stick_x;
            double rightFrontPower = h * Math.sin(robotAngle) + gamepad1.right_stick_x;
            double leftRearPower = h * Math.sin(robotAngle) - gamepad1.right_stick_x;
            double rightRearPower = h * Math.cos(robotAngle) + gamepad1.right_stick_x;

            //Wheel motor power assignments.
            frontLeftWheelMotor.setPower(leftFrontPower * controller1SpeedModifier);
            frontRightWheelMotor.setPower(-rightFrontPower * controller1SpeedModifier);
            rearLeftWheelMotor.setPower(leftRearPower * controller1SpeedModifier);
            rearRightWheelMotor.setPower(-rightRearPower * controller1SpeedModifier);

            //Controller 2.

            //Intake motor speed assignments.
            if (gamepad2.right_bumper)
                intakeMotorsPower = 1;
            else if (gamepad2.start)
                intakeMotorsPower = 0;
            else if (gamepad2.left_bumper)
                intakeMotorsPower = -1;
            //Intake motor execution.
            leftIntakeMotor.setPower(intakeMotorsPower);
            rightIntakeMotor.setPower(intakeMotorsPower);

            //XRail motor speed assignments.
            double xRailMotorsPower = gamepad2.left_stick_y;

            //XRail motor execution.
            leftXRailMotor.setPower(xRailMotorsPower);
            rightXRailMotor.setPower(xRailMotorsPower);

            //Claw Movement Control.
            double clawMovementServoPower = gamepad2.right_stick_x;
            clawMovementServo.setPower(clawMovementServoPower);

            //Claw Grabbing Control.
            if (gamepad2.a)
                grabbyClawServo.setPosition(Servo.MIN_POSITION);
            else if (gamepad2.b)
                grabbyClawServo.setPosition(Servo.MAX_POSITION);
        }
    }
}