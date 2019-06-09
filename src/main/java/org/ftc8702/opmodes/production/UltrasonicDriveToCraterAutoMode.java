package org.ftc8702.opmodes.production;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.ftc8702.configurations.production.Team8702ProdAuto;
import org.ftc8702.utilities.TelemetryUtil;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class UltrasonicDriveToCraterAutoMode {
    private static final double FORWARD_TURN_RIGHT_SPEED = 0.35;
    private static final double FORWARD_LEFT_SPEED = 0.16;
    private static final double FORWARD_SPEED = 0.3;
    private static final double FINAL_DISTANCE = 30;
    private static final long PAUSE_DURATION_MS = 500;
    private static final int ROLL_LIMIT_DEGREE = 15;
    private static final int PITCH_LIMIT_DEGREE = 11;
    private static final long TOTAL_TIME_LIMIT_MS = 5000;


    private long startTimeMillis;

    private TelemetryUtil telemetry;

    private Team8702ProdAuto prodAutoConfig;

    private ModernRoboticsI2cRangeSensor rangeSensor;

    private GyroAutoMode gyroAutoMode;

    private double distanceToWallInCM;

    public UltrasonicDriveToCraterAutoMode(Team8702ProdAuto robot, TelemetryUtil telemetry, GyroAutoMode gyroAutoMode) {
        this.prodAutoConfig = robot;
        this.telemetry = telemetry;
        this.gyroAutoMode = gyroAutoMode;
    }

    protected void init() {
    //    rangeSensor = prodAutoConfig.rangeSensor;
    }

    protected void ForwardandTurn() throws InterruptedException {
        Forward();
        stopRobot();
        turn();
        stopRobot();
    }

    protected void stopRobot() throws InterruptedException {
        prodAutoConfig.stopRobot();
        sleep(PAUSE_DURATION_MS);
    }

    protected void turn() throws InterruptedException {
        prodAutoConfig.forwardRobot(1);

        sleep(PAUSE_DURATION_MS);
    }

    protected void Forward() throws InterruptedException {
        prodAutoConfig.forwardRobot(1);
        sleep(PAUSE_DURATION_MS);
    }
    public boolean ultrasonicDriveToCrater() throws InterruptedException {
        boolean isCompleted = false;
        startTimeMillis = System.currentTimeMillis();
        while (!isCompleted) {
            isCompleted = activeLoop();
        }
        return isCompleted;
    }

    protected boolean activeLoop() throws InterruptedException {
        if (testElevationChange()) {
            telemetry.addData("elevation changed", "roll=" + gyroAutoMode.currentRollAngle + ":pitch=" + gyroAutoMode.currentPitchAngle);
            telemetry.sendTelemetry();
            stopRobot();
            return true;
        }

        distanceToWallInCM = rangeSensor.getDistance(DistanceUnit.CM);
        telemetry.addData("Distance", String.format("cm", "%.2f cm", distanceToWallInCM));
        telemetry.sendTelemetry();

        if (distanceToWallInCM > FINAL_DISTANCE) {
            telemetry.addData("forward", "turn");
            ForwardandTurn();
        } else {
            telemetry.addData("forward", "only");
            Forward();
        }

        return false; // always return false if elvation change is not detected
    }

    private boolean testElevationChange() {
        long duration = System.currentTimeMillis() - startTimeMillis;
        return gyroAutoMode.isRollPitchChange(ROLL_LIMIT_DEGREE, PITCH_LIMIT_DEGREE)
                || (duration > TOTAL_TIME_LIMIT_MS);
    }

    private void sleep ( long millis) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(millis);
    }
}


