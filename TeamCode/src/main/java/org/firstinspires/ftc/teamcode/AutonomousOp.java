package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.time.Instant;

@Autonomous
public class AutonomousOp extends LinearOpMode {
    private Robot robot;

    public void runOpMode() {
        robot = new Robot(this);

        startStageOne();
    }

    public void startStageOne() {
        loopFor(new Runnable() {
            @Override
            public void run() {
                robot.move(0, 0.5, 0);
            }
        }, 0.5);
    }

    private void loopFor(Runnable lambda, double seconds) {
        double start = time();
        while(time() - start < seconds) {
            lambda.run();
        }
    }

    private double time() {
        return System.currentTimeMillis() / 1000.0;
    }
}
