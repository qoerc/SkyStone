package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class AutoPark extends LinearOpMode {
    private Robot robot;

    @Override
    public void runOpMode() {
        robot = new Robot(this);

        loopFor(new Runnable() {
            @Override
            public void run() {
                robot.move(0, 0.5, 0);
            }
        }, 0.5);

        loopFor(new Runnable() {
            @Override
            public void run() {
                robot.move(-0.5, 0, 0);
            }
        }, 1);
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
