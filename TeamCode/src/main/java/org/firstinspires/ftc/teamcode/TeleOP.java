/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")
@Disabled
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

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        //Declarations.
        double speedModifier = 1;
        boolean skip360;
        double h;
        double robotAngle;
        while (opModeIsActive())
        {
            //Reset needed things.
            skip360 = false;
            //Trigger turning.
            if (gamepad1.left_trigger > 0 || gamepad1.right_trigger > 0)
            {
                h = Math.hypot((gamepad1.right_trigger - gamepad1.left_trigger), (gamepad1.right_trigger - gamepad1.left_trigger));
                robotAngle = Math.atan2((gamepad1.right_trigger - gamepad1.left_trigger), (gamepad1.right_trigger + gamepad1.left_trigger)) - Math.PI / 4;
            }
            //Stick turning.
            else
             {
                h = Math.hypot(gamepad1.right_stick_x, gamepad1.right_stick_y);
                robotAngle = Math.atan2(gamepad1.right_stick_y, gamepad1.right_stick_x) - Math.PI / 4;
            }
            double frontLeftWheelMotorPower = 0;
            double frontRightWheelMotorPower = 0;
            double rearLeftWheelMotorPower = 0;
            double rearRightWheelMotorPower = 0;

            //D-Pad Strafing.
            // Left diagonals.
            if (gamepad1.dpad_left && (gamepad1.dpad_up || gamepad1.dpad_down))
            {
                //Set angle.
                if (gamepad1.dpad_up)
                    robotAngle = Math.atan2(-0.5, -0.5) - Math.PI / 4;
                else
                    robotAngle = Math.atan2(0.5, -0.5) - Math.PI / 4;
                //Set power.
                frontLeftWheelMotorPower = h * Math.cos(robotAngle) - .5;
                frontRightWheelMotorPower = h * Math.sin(robotAngle) + .5;
                rearLeftWheelMotorPower = h * Math.sin(robotAngle) - .5;
                rearRightWheelMotorPower = h * Math.cos(robotAngle) + .5;
                skip360 = true;
            } //End if.
            // Right diagonals.
            else if (gamepad1.dpad_right && (gamepad1.dpad_up || gamepad1.dpad_down))
            {
                //Set angle.
                if (gamepad1.dpad_up)
                    robotAngle = Math.atan2(-0.5, 0.5) - Math.PI / 4;
                else
                    robotAngle = Math.atan2(0.5, 0.5) - Math.PI / 4;
                //Set power.
                frontLeftWheelMotorPower = h * Math.cos(robotAngle) + .5;
                frontRightWheelMotorPower = h * Math.sin(robotAngle) - .5;
                rearLeftWheelMotorPower = h * Math.sin(robotAngle) + .5;
                rearRightWheelMotorPower = h * Math.cos(robotAngle) - .5;
                skip360 = true;
            } //End else if.
            //Individual directions.
            if (gamepad1.dpad_up || gamepad1.dpad_down || gamepad1.dpad_left || gamepad1.dpad_right)
            {
                //Set angle.
                if (gamepad1.dpad_up)
                    robotAngle = Math.atan2(-1, 0) - Math.PI / 4;
                else if (gamepad1.dpad_down)
                    robotAngle = Math.atan2(1, 0) - Math.PI / 4;
                else if (gamepad1.dpad_left)
                    robotAngle = Math.atan2(0, -1) - Math.PI / 4;
                else
                    robotAngle = Math.atan2(0, 1) - Math.PI / 4;
                //Set left or right power.
                if (!gamepad1.dpad_left && !gamepad1.dpad_right)
                {
                    frontLeftWheelMotorPower = h * Math.cos(robotAngle);
                    frontRightWheelMotorPower = h * Math.sin(robotAngle);
                    rearLeftWheelMotorPower = h * Math.sin(robotAngle);
                    rearRightWheelMotorPower = h * Math.cos(robotAngle);
                } //End if.
                //Set left power.
                else if (gamepad1.dpad_left)
                {
                    frontLeftWheelMotorPower = h * Math.cos(robotAngle) - 1;
                    frontRightWheelMotorPower = h * Math.sin(robotAngle) + 1;
                    rearLeftWheelMotorPower = h * Math.sin(robotAngle) - 1;
                    rearRightWheelMotorPower = h * Math.cos(robotAngle) + 1;
                } //End else if.
                //Set right power.
                else
                {
                    frontLeftWheelMotorPower = h * Math.cos(robotAngle) + 1;
                    frontRightWheelMotorPower = h * Math.sin(robotAngle) - 1;
                    rearLeftWheelMotorPower = h * Math.sin(robotAngle) + 1;
                    rearRightWheelMotorPower = h * Math.cos(robotAngle) - 1;
                } //End else.
                skip360 = true;
            } //End if.

            //360 Degree Strafing.
            if (!skip360)
            {
                frontLeftWheelMotorPower = h * Math.cos(robotAngle) + gamepad1.left_stick_x;
                frontRightWheelMotorPower = h * Math.sin(robotAngle) - gamepad1.left_stick_x;
                rearLeftWheelMotorPower = h * Math.sin(robotAngle) + gamepad1.left_stick_x;
                rearRightWheelMotorPower = h * Math.cos(robotAngle) - gamepad1.left_stick_x;
            }

            //Speed Modifier setting.
            if (gamepad1.y)
                speedModifier = 0.25;
            else if (gamepad1.x)
                speedModifier = .5;
            else if (gamepad1.b)
                speedModifier = 0.75;
            else if (gamepad1.a)
                speedModifier = 1;

            //Control execution.
            frontLeftWheelMotor.setPower(frontLeftWheelMotorPower * speedModifier);
            frontRightWheelMotor.setPower(-frontRightWheelMotorPower * speedModifier);
            rearLeftWheelMotor.setPower(rearLeftWheelMotorPower * speedModifier);
            rearRightWheelMotor.setPower(-rearRightWheelMotorPower * speedModifier);
        }
    }
}