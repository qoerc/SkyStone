package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {private DcMotor frontLeftWheel, frontRightWheel, backLeftWheel, backRightWheel;
    private DcMotor leftArmMotor, rightArmMotor;
    private CRServo claw;
    private CRServo servoArm;
    private Servo frontArm;

    private int targetArmPosition = 0;
    private boolean isClawOpen = true;
    private long clawPowerLastSet = -1;

    public Robot(LinearOpMode opMode) {
        frontLeftWheel = opMode.hardwareMap.dcMotor.get("frontLeftWheel");
        frontRightWheel = opMode.hardwareMap.dcMotor.get("frontRightWheel");
        backLeftWheel = opMode.hardwareMap.dcMotor.get("backLeftWheel");
        backRightWheel = opMode.hardwareMap.dcMotor.get("backRightWheel");
        leftArmMotor = opMode.hardwareMap.dcMotor.get("leftArmMotor");
        rightArmMotor = opMode.hardwareMap.dcMotor.get("rightArmMotor");

        servoArm = opMode.hardwareMap.crservo.get("servoArm");
        claw = opMode.hardwareMap.crservo.get("claw");
        frontArm = opMode.hardwareMap.servo.get("frontArm");
    }

    public void update() {
        // Rotate main arm
        double offset = (targetArmPosition - leftArmMotor.getCurrentPosition()) / 1700;
        if(offset < 0.01) {
            this.rightArmMotor.setPower(Math.signum(offset) * (1 - 0.8 * Math.pow(offset, 4)));
            this.leftArmMotor.setPower(Math.signum(offset) * (1 - 0.8 * Math.pow(offset, 4)));
        }
        else {
            this.rightArmMotor.setPower(Math.signum(offset) * (1 - 0.8 * Math.pow(offset, 4)));
            this.leftArmMotor.setPower(Math.signum(offset) * (1 - 0.8 * Math.pow(offset, 4)));
        }

        // Disable claw power if necessary
        if(isClawOpen && clawPowerLastSet - System.currentTimeMillis() >= 500) {
            claw.setPower(0);
        }
    }

    public void move(double xMovement, double yMovement, double rotationSpeed) {
        // Equations for the power
        double frontLeftPower = yMovement - rotationSpeed + xMovement;
        double frontRightPower = -rotationSpeed - yMovement - xMovement;
        double backLeftPower = -rotationSpeed - yMovement - xMovement;
        double backRightPower = yMovement - rotationSpeed - xMovement;

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

        this.frontLeftWheel.setPower(frontLeftPower);
        this.frontRightWheel.setPower(frontRightPower);
        this.backLeftWheel.setPower(backLeftPower);
        this.backRightWheel.setPower(backRightPower);
    }

    public void setTargetArmPosition(double position) {
        targetArmPosition = (int)(1700 * position);
        update();
    }

    public void setArmExtensionPower(double power) {
        this.servoArm.setPower(power);
    }

    public void setSmallArmPosition(boolean up) {
        if(up) frontArm.setPosition(0);
        else frontArm.setPosition(1);
    }

    public void setClaw(boolean open) {
        if(open) {
            claw.setPower(1);
            clawPowerLastSet = System.currentTimeMillis();
        }
        else {
            claw.setPower(-1);
            clawPowerLastSet = System.currentTimeMillis();
        }
    }
}
