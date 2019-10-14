package org.ftc8702.opmodes.production;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;

import static org.ftc8702.opmodes.production.SkystoneAutoModeState.*;

import org.ftc8702.configurations.production.SkystoneAutoConfig;
import ftcbootstrap.ActiveOpMode;
import org.ftc8702.opmodes.test.BenColorSensorTest;
import org.ftc8702.utils.ColorUtil;
import org.ftc8702.utils.ColorValue;

@Autonomous(name = "SkystoneSimpleAutoMode", group = "Ops")
public class SkystoneSimpleAutoMode extends ActiveOpMode {

    public SkystoneAutoConfig robot = new SkystoneAutoConfig();
    private SkystoneAutoModeState currentState;
    private boolean accomplishedTask = false;
    private boolean finishedJob = false;
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
            case MOVE_TO_FOUNDATION:
                logStage();
                robot.driveTrain.goForward(1);
                sleep(968);
                robot.driveTrain.stop();
                currentState = LOWER_FOUNDATION_GRABBER;
                break;

            case LOWER_FOUNDATION_GRABBER:
                logStage();
                    robot.jaja.foundationGrabberRight.setPosition(1);
                    //robot.jaja.foundationGrabberLeft.setPosition(1);
                    //sleep(2000);
                    //robot.jaja.foundationGrabberLeft.setPosition(1);
                    // accpomplishedTask= true;
                    sleep(1500);
                    accomplishedTask = true;
                    currentState = MOVE_FROM_FOUNDATION;

                    break;
            case MOVE_FROM_FOUNDATION:
                logStage();
                robot.driveTrain.goBackward(1);
                sleep(1500);
                robot.driveTrain.stop();
                currentState = RAISE_FOUNDATION_GRABBER;//make this park after we fix everything
                break;

            case RAISE_FOUNDATION_GRABBER:
                logStage();
                    robot.jaja.foundationGrabberRight.setPosition(0.5);
                    //robot.jaja.foundationGrabberLeft.setPosition(1);
                    sleep(1500);
                    //robot.jaja.foundationGrabberLeft.setPosition(1);
                    //finishedJob = true;
                    finishedJob = true;
                    currentState = PARK;

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
