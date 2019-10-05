package org.ftc8702.opmodes.production;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import static org.ftc8702.opmodes.production.SkystoneAutoModeState.*;

import org.ftc8702.configurations.production.SkystoneAutoConfig;
import ftcbootstrap.ActiveOpMode;
import org.ftc8702.opmodes.test.BenColorSensorTest;
import org.ftc8702.utils.ColorUtil;
import org.ftc8702.utils.ColorValue;

@Autonomous(name = "SkystoneSimpleAutoMode", group = "Ops")
public class SkystoneSimpleAutoMode extends ActiveOpMode {

    private SkystoneAutoConfig robot = new SkystoneAutoConfig();
    private SkystoneAutoModeState currentState;
    private boolean accomplishedTask = false;
    private BenColorSensorTest colorSensorTester;

    @Override
    protected void onInit() {
        robot.init(hardwareMap, getTelemetryUtil());
        currentState = PARK;
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        getTelemetryUtil().addData("activeLoop current state", currentState.toString());
        telemetry.update();
        getTelemetryUtil().addData("Color: ", ColorUtil.getColor(robot.colorSensor).name());


        switch (currentState) {
            case LOWER_FOUNDATION_GRABBER:
                logStage();
                accomplishedTask = false;
                if(accomplishedTask == false) {
                    robot.foundationGrabberLeft.setPower(.5);
                    sleep(5000);
                    robot.foundationGrabberLeft.setPower(0);
                    accomplishedTask = true;

                } else if(accomplishedTask == true) {
                    currentState = PARK;
                }

            case PARK:
                logStage();
                robot.driveTrain.goBackward(.3f);
                ColorValue currentColor = ColorUtil.getColor(robot.colorSensor);

                if(currentColor == ColorValue.BLUE || currentColor == ColorValue.RED) {
                    telemetry.addData("Touching ", currentColor);
                    currentState = DONE;
                }
                break;

            case DONE: // When all operations are complete
                logStage();
                robot.driveTrain.stop();
                setOperationsCompleted();
                break;
        }
        getTelemetryUtil().sendTelemetry();
        telemetry.update();

    }

    public void logStage() {
        getTelemetryUtil().addData("Stage", currentState.toString());
        getTelemetryUtil().sendTelemetry();
    }
}
