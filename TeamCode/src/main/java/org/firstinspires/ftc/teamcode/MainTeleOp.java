package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class MainTeleOp extends LinearOpMode {
    private Robot robot;
    private double targetArmPos = 0;

    @Override
    public void runOpMode() {
        robot = new Robot(this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        double deltaTime;
        long lastTick = System.currentTimeMillis();
        while(this.opModeIsActive()) {
            telemetry.clearAll();

            deltaTime = System.currentTimeMillis() - lastTick;
            lastTick += deltaTime;
            deltaTime /= 1000.0;

            updateClaw(); // Bumpers
            updateMainArmRotation(deltaTime); // Triggers
            updateMovement(); // Sticks
            updateArmExtensionPower(); // DPad
            updateSmallArm(); // Y
            telemetry.update();
        }
    }

    private void updateClaw() {
        if(this.gamepad1.right_bumper) robot.setClaw(true);
        else if(this.gamepad1.left_bumper) robot.setClaw(false);
    }

    private void updateMovement() {
        // Calculate x movement and y movement, then move
        double xMovement = this.gamepad1.left_stick_x;
        double yMovement = this.gamepad1.left_stick_y;
        telemetry.addData("xMovement", xMovement);
        telemetry.addData("yMovement", yMovement);

        double rotationSpeed = 0.5 * this.gamepad1.right_stick_x;
        robot.move(xMovement, yMovement, rotationSpeed);
    }

    private void updateMainArmRotation(double deltaTime) {
        targetArmPos += deltaTime * (gamepad1.right_trigger - gamepad1.left_trigger);
        targetArmPos = Math.max(0, Math.min(1, targetArmPos));
        robot.setTargetArmPosition(targetArmPos);
    }

    private void updateArmExtensionPower() {
        double extPower = 0;
        if(gamepad1.dpad_up) extPower = 1;
        else if(gamepad1.dpad_down) extPower = -1;
        robot.setArmExtensionPower(extPower);
    }

    private void updateSmallArm() {
        robot.setSmallArmPosition(this.gamepad1.y);
    }
}
