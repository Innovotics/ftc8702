package org.ftc8702.opmodes.test;

import static org.ftc8702.utils.ColorUtil.getColor;
import static org.ftc8702.utils.ColorUtil.isRedOrBlueDetected;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftc8702.configurations.test.ColorSensorTest;
import org.ftc8702.utils.ColorValue;
import org.ftcbootstrap.ActiveOpMode;

@Autonomous(name = "Test: BenCharisMoveToHomeDepotTest", group = "Test")
public class BenCharisMoveToHomeDepotTest extends ActiveOpMode {
    private static final double FORWARD_SPEED = 0.15;
    private static final long BEGIN_MOVE_FORARD_DURATION_MS = 3750;
    private static final long BACKWARD_DRURATION_MS = 1750;
    private static final double BACKWARD_SPEED = -0.15;

    private ColorSensorTest robotConfig;

    private boolean isBothMotorsStopped = false;

    private boolean isOneTimeMovedForward = false;
    private boolean isOneTimeMovedBackward = false;

    private boolean isRightSensorFoundColor= false;
    private boolean isLeftSensorFoundColor = false;

    @Override
    protected void onInit() {
        robotConfig = ColorSensorTest.newConfig(hardwareMap, getTelemetryUtil());
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
    }

    protected void moveForward() {
    }

    protected void pauseMovement() {

    }

    protected void moveBackward() {
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        // only need to move forward once at the beginning to avoid the possible color squares
        // between the depot and lander the field
        if (!isOneTimeMovedForward) {
            moveForward();
            sleep(BEGIN_MOVE_FORARD_DURATION_MS);
            isOneTimeMovedForward = true;
        }}}

        // get color readings from both left and right sensors
//        ColorValue rightColor = getColor(robotConfig.colorSensorBackRight);
//        getTelemetryUtil().addData("Right Color: ", rightColor.name());
//        ColorValue leftColor = getColor(robotConfig.colorSensorBackLeft);
//        getTelemetryUtil().addData("Left Color: ", rightColor.name());

//        boolean isColorDetectedByRightSensor = isRedOrBlueDetected(rightColor);
//        boolean isColorDetectedByLeftSensor = isRedOrBlueDetected(leftColor);

        // Move both wheels until one of the color sensors detects the color
//        if (!isColorDetectedByRightSensor && !isColorDetectedByLeftSensor) {
//            pauseMovement(); // stop robot moving to slow down the momentum
//            moveForward();
//            getTelemetryUtil().addData("Motor Both", "move forward");
//        }
//
//        // once a color sensor detects the color, we don't need that sensor to do more
//        // sensing since it's already inside the depot
//        if (!isRightSensorFoundColor && isColorDetectedByRightSensor)  {
//            isRightSensorFoundColor = true;
//        }
//        if (!isLeftSensorFoundColor && isColorDetectedByLeftSensor)  {
//            isLeftSensorFoundColor = true;
//        }
//
//        //If colors are detected on both sides, stop both motors
//        if (isRightSensorFoundColor && isLeftSensorFoundColor) {
//            pauseMovement();
//            isBothMotorsStopped = true;
//            // TODO: Drop Team Marker
//            getTelemetryUtil().addData("Both Motors", "stopped");
//        }
//
//        // only go inside this condition when motors have been stopped, meaning that, we
//        // are ready to back out of the depot for next step
//        if (!isOneTimeMovedBackward && isBothMotorsStopped) {
//            moveBackward();
//            sleep(BACKWARD_DRURATION_MS);
//            isOneTimeMovedBackward = true;
//            // TODO: exit and go to next step
//        }
//
//        getTelemetryUtil().sendTelemetry();
//        // Move on to next Phase
//        // Play Uptown Funk By Bruno Mars
//    }
//}
