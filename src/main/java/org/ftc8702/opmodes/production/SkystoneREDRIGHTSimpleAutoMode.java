package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;

import static org.ftc8702.opmodes.production.SkystoneAutoModeState.*;

import org.ftc8702.configurations.production.SkystoneAutoConfig;
import ftcbootstrap.ActiveOpMode;
import org.ftc8702.opmodes.test.BenColorSensorTest;
import org.ftc8702.utils.ColorUtil;
import org.ftc8702.utils.ColorValue;

@Autonomous(name = "RIGHTREDSimpleAutoMode", group = "Ops")
public class SkystoneREDRIGHTSimpleAutoMode extends ActiveOpMode {

    public SkystoneAutoConfig robot = new SkystoneAutoConfig();
    private SkystoneAutoModeState currentState;
    private boolean accomplishedTask = false;
    private boolean finishedJob = false;
    private BenColorSensorTest colorSensorTester;

    @Override
    protected void onInit() {
        robot.init(hardwareMap, getTelemetryUtil());
        currentState = MOVE_TO_FOUNDATION;
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        getTelemetryUtil().addData("activeLoop current state", currentState.toString());
        telemetry.update();

        switch (currentState) {
            case MOVE_TO_FOUNDATION:
                logStage();
                robot.driveTrain.goBackward(1);
                sleep(968);
                robot.driveTrain.strafeLeft(0.3f);
                sleep(700);
                robot.driveTrain.goBackward(0.3f);
                sleep(450);
                robot.driveTrain.stop();
                currentState = LOWER_FOUNDATION_GRABBER;
                break;

            case LOWER_FOUNDATION_GRABBER:
                logStage();
                robot.jaja.JaJaLeftDown();
                robot.jaja.JaJaRightDown();
                    sleep(1000);
                    accomplishedTask = true;
                    currentState = MOVE_FROM_FOUNDATION;

                    break;
            case MOVE_FROM_FOUNDATION:
                logStage();
                robot.driveTrain.goForward(0.3f);
                sleep(3800);
                robot.driveTrain.rotateRight(0.5f);
                sleep(700);
                robot.jaja.JaJaUp();
                sleep(1000);
                //robot.driveTrain.rotateLeft(0.3f);
                //sleep(400);
                robot.driveTrain.stop();
                currentState = PARK;//make this park after we fix everything
                break;

            case RAISE_FOUNDATION_GRABBER:
                logStage();
                robot.driveTrain.goForward(.5f);
                sleep(1000);
                robot.driveTrain.rotateLeft(0.3f);
                sleep(500);
                currentState = PARK;

                    break;

            case PARK:
                logStage();
                robot.driveTrain.goForward(.3f);
                ColorValue currentColor = ColorUtil.getColor(robot.colorSensor);

                if(currentColor == ColorValue.BLUE || currentColor == ColorValue.RED) {
                    telemetry.addData("Touching ", currentColor);
                    robot.driveTrain.stop();
                     currentState = DONE;
                }
                else if(currentColor == ColorValue.ZILCH || currentColor == ColorValue.GREEN){
                    robot.driveTrain.goForward(.3f);
                }
                break;

            case SPIN_TO_WIN:
                logStage();
                robot.driveTrain.rotateLeft(0.5f);
                sleep(5000);
//                robot.jaja.foundationGrabberLeft.setPosition(1);
//                robot.jaja.foundationGrabberRight.setPosition(1);
//                sleep(1000);
//                robot.jaja.foundationGrabberRight.setPosition(0);
//                robot.jaja.foundationGrabberLeft.setPosition(0);
                sleep(1000);
                robot.driveTrain.stop();
                currentState = DONE;
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
