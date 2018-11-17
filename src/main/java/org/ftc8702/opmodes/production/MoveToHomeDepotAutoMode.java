package org.ftc8702.opmodes.production;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.ftc8702.configurations.production.Team8702ProdAuto;
import org.ftc8702.utils.ColorValue;

import java.util.concurrent.TimeUnit;

import static org.ftc8702.utils.ColorUtil.getColor;
import static org.ftc8702.utils.ColorUtil.isRedOrBlueDetected;

public class MoveToHomeDepotAutoMode {

    private static final double FORWARD_SPEED = 0.25;
    private static final long BEGIN_MOVE_FORARD_DURATION_MS = 2000;
    private static final long BACKWARD_DRURATION_MS = 1750;
    private static final double BACKWARD_SPEED = -0.15;

    private Team8702ProdAuto robot;
    private Telemetry telemetry;

    private boolean isBothMotorsStopped = false;

    private boolean isOneTimeMovedForward = false;
    private boolean isOneTimeMovedBackward = false;

    private boolean isRightSensorFoundColor= false;
    private boolean isLeftSensorFoundColor = false;

    public MoveToHomeDepotAutoMode(Team8702ProdAuto robot, Telemetry telemetry) {
        this.robot = robot;
        this.telemetry = telemetry;
    }

    public void init() {
        telemetry.addData("Init", getClass().getSimpleName() + " initialized.");
    }

    protected void moveForward() {
        robot.motorR.setPower(isBothMotorsStopped ? 0.0 : FORWARD_SPEED);
        robot.motorL.setPower(isBothMotorsStopped ? 0.0 : FORWARD_SPEED);
    }

    protected void pauseMovement() {
        robot.motorL.setPower(0.0);
        robot.motorR.setPower(0.0);
    }

    protected void moveBackward() {
        robot.motorL.setPower(BACKWARD_SPEED);
        robot.motorR.setPower(BACKWARD_SPEED);
    }

    public boolean moveToHomeDepot() throws InterruptedException {
        boolean isCompleted = false;
        while (!isCompleted) {
            isCompleted = activeLoop();
        }
        return isCompleted;
    }

    protected boolean activeLoop() throws InterruptedException {
        // only need to move forward once at the beginning to avoid the possible color squares
        // between the depot and lander the field
        if (!isOneTimeMovedForward) {
            moveForward();
            sleep(BEGIN_MOVE_FORARD_DURATION_MS);
            isOneTimeMovedForward = true;
        }

        // get color readings from both left and right sensors
        ColorValue rightColor = getColor(robot.colorSensorBackRight);
        telemetry.addData("Right Color: ", rightColor.name());
        ColorValue leftColor = getColor(robot.colorSensorBackLeft);
        telemetry.addData("Left Color: ", rightColor.name());

        boolean isColorDetectedByRightSensor = isRedOrBlueDetected(rightColor);
        boolean isColorDetectedByLeftSensor = isRedOrBlueDetected(leftColor);

        // Move both wheels until one of the color sensors detects the color
        if (!isColorDetectedByRightSensor && !isColorDetectedByLeftSensor) {
            pauseMovement(); // stop robot moving to slow down the momentum
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

        //If colors are detected on both sides, stop both motors
        if (isRightSensorFoundColor && isLeftSensorFoundColor) {
            pauseMovement();

            isBothMotorsStopped = true;

            //Drop Team marker
            dropTeamMarker();

            telemetry.addData("Both Motors", "stopped");
        }

        // only go inside this condition when motors have been stopped, meaning that, we
        // are ready to back out of the depot for next step
        if (!isOneTimeMovedBackward && isBothMotorsStopped) {
            moveBackward();
            sleep(BACKWARD_DRURATION_MS);
            isOneTimeMovedBackward = true;
            // TODO: exit and go to next step
        }

        telemetry.update();
        // Move on to next Phase
        // Play Uptown Funk By Bruno Mars
        return isOneTimeMovedBackward;
    }

    private void sleep(long millis) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(millis);
    }

    private void dropTeamMarker() {
        robot.stopRobot();
        robot.markerDropper.setPosition(0.5);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.markerDropper.setPosition(0);
    }
}
