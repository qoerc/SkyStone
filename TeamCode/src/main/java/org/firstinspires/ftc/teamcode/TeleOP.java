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
    private DcMotor frontLeftMovementMotor;
    private DcMotor frontRightMovementMotor;

    private DcMotor rearLeftMovementMotor;
    private DcMotor rearRightMovementMotor;

    //Intakes.
    private DcMotor leftIntakeMotor;
    private DcMotor rightIntakeMotor;

    //XRails.
    private DcMotor leftXRailMotor;
    private DcMotor rightXRailMotor;

    //Servos.
    private CRServo clawMovementServo;
    private CRServo clawActuationServo;
    private CRServo leftFoundationActuationServo;
    private CRServo rightFoundationActuationServo;

    @Override
    public void runOpMode()
    {
        //Declare hardware.

        //Wheels.
        frontLeftMovementMotor = hardwareMap.dcMotor.get("frontLeftMovementMotor");
        frontRightMovementMotor = hardwareMap.dcMotor.get("frontRightMovementMotor");
        rearLeftMovementMotor = hardwareMap.dcMotor.get("rearLeftMovementMotor");
        rearRightMovementMotor = hardwareMap.dcMotor.get("rearRightMovementMotor");

        //Intakes.
        leftIntakeMotor = hardwareMap.dcMotor.get("leftIntakeMotor");
        rightIntakeMotor = hardwareMap.dcMotor.get("rightIntakeMotor");

        //XRails.
        leftXRailMotor = hardwareMap.dcMotor.get("leftXRailMotor");
        rightXRailMotor = hardwareMap.dcMotor.get("rightXRailMotor");

        //Servos.
        clawMovementServo = hardwareMap.crservo.get("clawMovementServo");
        clawActuationServo = hardwareMap.crservo.get("clawActuationServo");
        leftFoundationActuationServo = hardwareMap.crservo.get("leftFoundationActuationServo");
        rightFoundationActuationServo = hardwareMap.crservo.get("rightFoundationActuationServo");

        //Device power.

        //Wheels.
        frontLeftMovementMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMovementMotor.setDirection(DcMotor.Direction.FORWARD);
        rearLeftMovementMotor.setDirection(DcMotor.Direction.FORWARD);
        rearRightMovementMotor.setDirection(DcMotor.Direction.FORWARD);

        //Intakes.
        leftIntakeMotor.setDirection(DcMotor.Direction.REVERSE);
        rightIntakeMotor.setDirection(DcMotor.Direction.FORWARD);

        //XRails.
        leftXRailMotor.setDirection(DcMotor.Direction.REVERSE);
        rightXRailMotor.setDirection(DcMotor.Direction.FORWARD);

        //Servos.
        clawMovementServo.setDirection(CRServo.Direction.FORWARD);
        clawActuationServo.setDirection(CRServo.Direction.FORWARD);
        leftFoundationActuationServo.setDirection(CRServo.Direction.FORWARD);
        rightFoundationActuationServo.setDirection(CRServo.Direction.REVERSE);

        //Wait for the game to start (driver presses PLAY)..

        waitForStart();
        runtime.reset();

        //Initial declarations.
        double controller1SpeedModifier = 1;
        double intakeMotorsPower = 0;
        int foundationActuationPower = 1;

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
            frontLeftMovementMotor.setPower(leftFrontPower * 0.7 * controller1SpeedModifier);
            frontRightMovementMotor.setPower(-rightFrontPower * 0.7 * controller1SpeedModifier);
            rearLeftMovementMotor.setPower(leftRearPower * 0.7 * controller1SpeedModifier);
            rearRightMovementMotor.setPower(-rightRearPower * 0.7 * controller1SpeedModifier);

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

            //Claw Actuation Control.
            if (gamepad2.a)
                clawActuationServo.setPower(1);
            else if (gamepad2.b)
                clawActuationServo.setPower(-1);
            else
                clawActuationServo.setPower(0.5);

            //Foundation Actuation Control.
            if (gamepad2.y)
                foundationActuationPower = 1;
            else if (gamepad2.x)
                foundationActuationPower = -1;
            leftFoundationActuationServo.setPower(foundationActuationPower);
            rightFoundationActuationServo.setPower(foundationActuationPower);
        } //End while.
    } //End runOpMode.
} //End TeleOp