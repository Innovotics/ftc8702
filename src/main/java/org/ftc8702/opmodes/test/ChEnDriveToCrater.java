package org.ftc8702.opmodes.test;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.ftc8702.configurations.test.BenCharisRangeConfig;
import org.ftcbootstrap.ActiveOpMode;

@Autonomous(name = "Test: ChEnDriveToCrater Test", group = "Test")
public class ChEnDriveToCrater extends ActiveOpMode {

    private static final double FORWARD_TURN_RIGHT_SPEED = 0.3;
    private static final double FORWARD_LEFT_SPEED = 0.11;
    private static final double FORWARD_SPEED = 0.2;
    private static final double FINAL_DISTANCE = 30;
    private static final long PAUSE_DURATION_MS = 500;

    private BenCharisRangeConfig robotConfig;
    private ModernRoboticsI2cRangeSensor rangeSensor;

    private double distanceToWallInCM;

    @Override
    protected void onInit() {
        // do stuff at initialization stage
        robotConfig = BenCharisRangeConfig.newConfig(hardwareMap, getTelemetryUtil());
        rangeSensor = robotConfig.rangeSensor;
    }

    protected void ForwardandTurn() {
        Forward();
        stopRobot();
        turn();
        stopRobot();
    }

    protected void stopRobot() {
        robotConfig.motorR.setPower(0.0);
        robotConfig.motorL.setPower(0.0);
        sleep(PAUSE_DURATION_MS);
    }

    protected void turn() {
        robotConfig.motorR.setPower(FORWARD_TURN_RIGHT_SPEED);
        robotConfig.motorL.setPower(FORWARD_LEFT_SPEED);
        sleep(PAUSE_DURATION_MS);
    }

    protected void Forward() {
        robotConfig.motorR.setPower(FORWARD_SPEED);
        robotConfig.motorL.setPower(FORWARD_SPEED);
        sleep(PAUSE_DURATION_MS);
    }
    @Override
    protected void activeLoop() throws InterruptedException {
        distanceToWallInCM = rangeSensor.getDistance(DistanceUnit.CM);

        if (distanceToWallInCM > FINAL_DISTANCE) {
            telemetry.addLine("forward and turn");
            ForwardandTurn();
        }
        else {
            telemetry.addLine("forward only");
            Forward();
        }

        telemetry.addData("cm", "%.2f cm", distanceToWallInCM);
        telemetry.update();
    }
}
