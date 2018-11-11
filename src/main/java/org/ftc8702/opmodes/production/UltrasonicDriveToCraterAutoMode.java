package org.ftc8702.opmodes.production;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.ftc8702.configurations.production.Team8702ProdAuto;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class UltrasonicDriveToCraterAutoMode {
    private static final double FORWARD_TURN_RIGHT_SPEED = 0.3;
    private static final double FORWARD_LEFT_SPEED = 0.11;
    private static final double FORWARD_SPEED = 0.2;
    private static final double FINAL_DISTANCE = 30;
    private static final long PAUSE_DURATION_MS = 500;

    private long startTimeMillis;

    private Telemetry telemetry;

    private Team8702ProdAuto prodAutoConfig;

    private ModernRoboticsI2cRangeSensor rangeSensor;

    private double distanceToWallInCM;

    public UltrasonicDriveToCraterAutoMode(Team8702ProdAuto robot, Telemetry telemetry) {
        this.prodAutoConfig = robot;
        this.telemetry = telemetry;
    }
    protected void init() {
        rangeSensor = prodAutoConfig.rangeSensor;
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
        prodAutoConfig.motorR.setPower(FORWARD_TURN_RIGHT_SPEED);
        prodAutoConfig.motorL.setPower(FORWARD_LEFT_SPEED);
        sleep(PAUSE_DURATION_MS);
    }

    protected void Forward() throws InterruptedException {
        prodAutoConfig.motorR.setPower(FORWARD_SPEED);
        prodAutoConfig.motorL.setPower(FORWARD_SPEED);
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
            return true;
        }

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

        return false; // always return false if elvation change is not detected
    }

    private boolean testElevationChange() {
        // TODO - figure out how to integrate Tyler's stuff
        return ((System.currentTimeMillis() - startTimeMillis) > 5000);

    }


    private void sleep ( long millis) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(millis);
    }
}


