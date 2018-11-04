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

    private BenCharisRangeConfig robotConfig;
    private ModernRoboticsI2cRangeSensor rangeSensor;

    private boolean isRightMotorStopped = false;
    private boolean isLeftMotorStopped = false;
    private double getDistance;

    @Override
    protected void onInit() {
        // do stuff at initialization stage
        robotConfig = BenCharisRangeConfig.newConfig(hardwareMap, getTelemetryUtil());
        rangeSensor = robotConfig.rangeSensor;
    }
    protected void ForwardandTurn() {
        robotConfig.motorR.setPower(isRightMotorStopped ? 0.0 : FORWARD_TURN_RIGHT_SPEED);
        robotConfig.motorL.setPower(isLeftMotorStopped ? 0.0 : FORWARD_LEFT_SPEED);
    }

    protected void Forward() {
        robotConfig.motorR.setPower(isRightMotorStopped ? 0.0 : FORWARD_SPEED);
        robotConfig.motorL.setPower(isLeftMotorStopped ? 0.0 : FORWARD_SPEED);
    }
    @Override
    protected void activeLoop() throws InterruptedException {
        getDistance = rangeSensor.getDistance(DistanceUnit.CM);

        if (getDistance > FINAL_DISTANCE) {
            telemetry.addLine("forward and turn");
            ForwardandTurn();
        }
        else {
            telemetry.addLine("forward only");
            Forward();
        }

        telemetry.addData("cm", "%.2f cm", getDistance);
        telemetry.update();
    }
}
