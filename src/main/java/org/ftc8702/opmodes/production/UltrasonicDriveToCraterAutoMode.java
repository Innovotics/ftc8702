package org.ftc8702.opmodes.production;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.ftc8702.configurations.test.BenCharisRangeConfig;
import org.ftcbootstrap.ActiveOpMode;

import java.util.concurrent.TimeUnit;

public class UltrasonicDriveToCraterAutoMode {
    private static final double FORWARD_TURN_RIGHT_SPEED = 0.3;
    private static final double FORWARD_LEFT_SPEED = 0.11;
    private static final double FORWARD_SPEED = 0.2;
    private static final double FINAL_DISTANCE = 30;
    private static final long PAUSE_DURATION_MS = 500;

    private Telemetry telemetry;
    private BenCharisRangeConfig robot;
    private ModernRoboticsI2cRangeSensor rangeSensor;

    private double distanceToWallInCM;

    protected void init() {
        rangeSensor = robot.rangeSensor;
    }

    protected void ForwardandTurn() throws InterruptedException {
        Forward();
        stopRobot();
        turn();
        stopRobot();
    }

    protected void stopRobot() throws InterruptedException {
        robot.motorR.setPower(0.0);
        robot.motorL.setPower(0.0);
        sleep(PAUSE_DURATION_MS);
    }

    protected void turn() throws InterruptedException {
        robot.motorR.setPower(FORWARD_TURN_RIGHT_SPEED);
        robot.motorL.setPower(FORWARD_LEFT_SPEED);
        sleep(PAUSE_DURATION_MS);
    }

    protected void Forward() throws InterruptedException {
        robot.motorR.setPower(FORWARD_SPEED);
        robot.motorL.setPower(FORWARD_SPEED);
        sleep(PAUSE_DURATION_MS);
    }

    protected void activeLoop() throws InterruptedException {
        distanceToWallInCM = rangeSensor.getDistance(DistanceUnit.CM);

        if (distanceToWallInCM > FINAL_DISTANCE) {
            telemetry.addLine("forward and turn");
            ForwardandTurn();
        } else {
            telemetry.addLine("forward only");
            Forward();
        }

        telemetry.addData("cm", "%.2f cm", distanceToWallInCM);
        telemetry.update();
    }

    private void sleep(long millis) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(millis);
    }

}


