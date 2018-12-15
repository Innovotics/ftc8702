package org.ftc8702.opmodes.production;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.ftc8702.configurations.production.Team8702ProdAuto;
import org.ftc8702.utilities.TelemetryUtil;
import org.ftc8702.utils.ColorValue;

import java.util.concurrent.TimeUnit;

import static org.ftc8702.utils.ColorUtil.getColor;
import static org.ftc8702.utils.ColorUtil.isRedOrBlueDetected;

public class MoveToHomeDepotAutoMode {

    private static final double FORWARD_SPEED = 0.25;
    private static final long BEGIN_MOVE_FORARD_DURATION_MS = 1000;
    private static final long BACKWARD_DRURATION_MS = 750;
    private static final double BACKWARD_SPEED = -0.15;
    private static final long TIMED_OUT = 2000;

    private Team8702ProdAuto robot;
    private TelemetryUtil telemetry;

    private long startForwardTime = 0;

    private boolean isBothMotorsStopped = false;

    private boolean isRightSensorFoundColor = false;
    private boolean isLeftSensorFoundColor = false;

    private boolean needMoveBeginingForward = true;

    public MoveToHomeDepotAutoMode(Team8702ProdAuto robot, TelemetryUtil telemetry) {
        this.robot = robot;
        this.telemetry = telemetry;
    }

    public void init() {
        telemetry.addData("Init", getClass().getSimpleName() + " initialized.");
    }

    protected void moveForward() {
        robot.forwardRobot(isBothMotorsStopped ? 0.0 : FORWARD_SPEED);
    }

    protected void moveBackward() {
        robot.backwardRobot(BACKWARD_SPEED);
    }

    public boolean moveToHomeDepot() throws InterruptedException {
        startForwardTime = System.currentTimeMillis();
        // only need to move forward once at the beginning to avoid the possible color squares
        // between the depot and lander the field
        if(needMoveBeginingForward) {
            moveForward();
            robot.sleep(BEGIN_MOVE_FORARD_DURATION_MS);
        }
        boolean isCompleted = false;
        while (!isCompleted) {
            isCompleted = activeLoop();
        }

        moveBackward();
        robot.sleep(BACKWARD_DRURATION_MS);
        robot.stopRobot();

        return isCompleted;
    }

    protected boolean activeLoop() throws InterruptedException {
        // get color readings from both left and right sensors
        ColorValue rightColor = getColor(robot.colorSensorBackRight);
        telemetry.addData("Right Color: ", rightColor.name());
        ColorValue leftColor = getColor(robot.colorSensorBackLeft);
        telemetry.addData("Left Color: ", rightColor.name());

        boolean isColorDetectedByRightSensor = isRedOrBlueDetected(rightColor);
        boolean isColorDetectedByLeftSensor = isRedOrBlueDetected(leftColor);

        // Move both wheels until one of the color sensors detects the color
        if (!isColorDetectedByRightSensor && !isColorDetectedByLeftSensor) {
            robot.stopRobot(); // stop robot moving to slow down the momentum
            moveForward();
            telemetry.addData("Motor Both", "move forward");
        }
        // once a color sensor detects the color, we don't need that sensor to do more
        // sensing since it's already inside the depot
        if (!isRightSensorFoundColor && isColorDetectedByRightSensor)  {
            isRightSensorFoundColor = true;
        }
        if (!isLeftSensorFoundColor && isColorDetectedByLeftSensor)  {
            isLeftSensorFoundColor = true;
        }

        boolean isTimedOut = (System.currentTimeMillis() - startForwardTime) > TIMED_OUT;

        //If colors are detected on both sides, stop both motors
        if ((isRightSensorFoundColor && isLeftSensorFoundColor) || isTimedOut)  {
            telemetry.addData("Exit", isTimedOut ? "TimedOut" : "ColorDetected");
            return true;
        }

        telemetry.sendTelemetry();
        return false;
    }

    public void setNeedMoveBeginingForward(boolean needMoveBeginingForward) {
        this.needMoveBeginingForward = needMoveBeginingForward;
    }
}
