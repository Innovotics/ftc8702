package org.ftc8702.opmodes.production;
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
        currentState =MOVE_TO_FOUNDATION ;
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        getTelemetryUtil().addData("activeLoop current state", currentState.toString());
        telemetry.update();
        getTelemetryUtil().addData("Color: ", ColorUtil.getColor(robot.colorSensor).name());


        switch (currentState) {
            case MOVE_TO_FOUNDATION://not yet
                logStage();
                robot.driveTrain.goForward(1);
                sleep(918);
                currentState =LOWER_FOUNDATION_GRABBER;
                break;

            case LOWER_FOUNDATION_GRABBER:
                logStage();
                accomplishedTask = false;
                if(accomplishedTask == false) {
                    robot.foundationGrabberLeft.setPower(.5);
                    sleep(5000);
                    robot.foundationGrabberLeft.setPower(0);
                    accomplishedTask = true;
                    robot.foundationGrabberRight.setPower(.5);
                    sleep(5000);
                    robot.foundationGrabberRight.setPower(0);
                    accomplishedTask = true;

                } else if(accomplishedTask == true) {
                    currentState = MOVE_FROM_FOUNDATION;
                }
                    break;
            case MOVE_FROM_FOUNDATION:
                logStage();
                robot.driveTrain.goBackward(1);
                sleep(2000);
                currentState =DONE;//make this park after we fix everything
                break;

            case MOVE_FOR_TEST:
                logStage();
                robot.driveTrain.goForward(1);
                sleep(818);
                robot.driveTrain.stop();

                    currentState = DONE;
                    break;

            case PARK:
                logStage();
                robot.driveTrain.strafeRight(.3f);
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
