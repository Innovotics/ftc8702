package org.ftc8702.opmodes.production;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;

import static org.ftc8702.opmodes.production.SkystoneAutoModeState.*;

import org.ftc8702.configurations.production.SkystoneAutoConfig;
import ftcbootstrap.ActiveOpMode;
import org.ftc8702.opmodes.test.BenColorSensorTest;
import org.ftc8702.utils.ColorUtil;
import org.ftc8702.utils.ColorValue;

//@Autonomous(name = "LeftBlueExprimentMode", group = "Ops")
public class SkystoneExprimentLeftBlueSimpleMode extends ActiveOpMode {

    public SkystoneAutoConfig robot = new SkystoneAutoConfig();
    private SkystoneAutoModeState currentState;
    private boolean accomplishedTask = false;
    private boolean finishedJob = false;
    private BenColorSensorTest colorSensorTester;

    private static final long TIME_OUT = 10000L;
    private long timeToPark = 0;

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
                //logStage();
                robot.driveTrain.goBackward(1);
                sleep(968);
                robot.driveTrain.strafeRight(0.3f);
                sleep(800);
                robot.driveTrain.goBackward(0.3f);
                sleep(450);
                robot.driveTrain.stop();
                currentState = LOWER_FOUNDATION_GRABBER;
                break;

            case LOWER_FOUNDATION_GRABBER:
                //logStage();
                getTelemetryUtil().addData("LOWER_FOUNDATION_GRABBER", " Pressed");
                robot.jaja.JaJaLeftDown();
                robot.jaja.JaJaRightDown();
                sleep(1000);
                accomplishedTask = true;
                currentState = MOVE_FOUNDATION_VERTICAL;
                break;

            case MOVE_FOUNDATION_VERTICAL:
                //logStage();
                robot.driveTrain.turnSmoothLeft();
                sleep(1500);
                robot.driveTrain.rotateLeft(0.5f);
                sleep(2000);
                //robot.driveTrain.goForward(0.4f);
                //sleep(700);
                robot.driveTrain.turnSmoothLeftAutonomous();
                sleep(1000);
                robot.driveTrain.stop();
                sleep(400);
                robot.jaja.JaJaUp();
                sleep(1000);
                /*robot.driveTrain.goBackward(0.4f);
                sleep(600);
                robot.driveTrain.stop();
                sleep(300);
                robot.jaja.JaJaDown();
                sleep(500);
                robot.driveTrain.rotateLeft(0.4f);
                sleep(4000);
                robot.driveTrain.stop();
                *///sleep(500);
                currentState = PUSH_FOUNDATION;
                break;

            case PUSH_FOUNDATION:
                //logStage();
                robot.jaja.JaJaUp();
                sleep(1000);
                //robot.driveTrain.strafeLeft(0.6f);
                //sleep(800);
                robot.driveTrain.goBackward(0.7f);
                sleep(1500);
                robot.driveTrain.goForward(0.4f);
                sleep(600);
                currentState = PARK_EXPRIMENT;

            case PARK_EXPRIMENT:
                //logStage();
                robot.driveTrain.strafeRight(.5f);
                ColorValue currentColor = ColorUtil.getColor(robot.colorSensor);

                if(currentColor == ColorValue.BLUE || currentColor == ColorValue.RED) {
                    telemetry.addData("Touching ", currentColor);
                    robot.driveTrain.stop();
                    //robot.jaja.JaJaDown();
                    currentState = DONE;
                }
                else if(currentColor == ColorValue.ZILCH || currentColor == ColorValue.GREEN){
                    robot.driveTrain.strafeRight(.5f);
                }
                break;

            case DONE: // When all operations are complete
                //logStage();
                robot.driveTrain.stop();
                setOperationsCompleted();
                break;
        }
    }
}
