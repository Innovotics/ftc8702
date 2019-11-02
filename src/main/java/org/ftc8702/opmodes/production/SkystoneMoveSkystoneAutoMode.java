package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftc8702.opmodes.test.BenColorSensorTest;
import org.ftc8702.utils.ColorUtil;
import org.ftc8702.utils.ColorValue;

import static org.ftc8702.opmodes.production.SkystoneAutoModeState.DONE;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.PARK;

@Autonomous(name = "SkystoneMoveSkystoneAutoMode", group = "Ops")
public class SkystoneMoveSkystoneAutoMode extends SkystoneAbstractAutoMode {

    private boolean accomplishedTask = false;
    private BenColorSensorTest colorSensorTester;

    @Override
    protected void onInit() {
        super.init();
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        getTelemetryUtil().addData("activeLoop current state", getCurrentState().toString());
        telemetry.update();
       // getTelemetryUtil().addData("Color: ", ColorUtil.getColor(super.getRobot().colorSensor).name());


        switch (getCurrentState()) {
            case LOWER_FOUNDATION_GRABBER:
                logStage();
                accomplishedTask = false;
                if(accomplishedTask == false) {
                    robot.jaja.foundationGrabberLeft.setPosition(0);
                    sleep(5000);
                    robot.jaja.foundationGrabberLeft.setPosition(0);
                    accomplishedTask = true;

                } else if(accomplishedTask == true) {
                    setCurrentState(PARK);
                }

            case PARK:
                logStage();
                getRobot().driveTrain.goBackward(.3f);
              //  ColorValue currentColor = ColorUtil.getColor(getRobot().colorSensor);

//                if(currentColor == ColorValue.BLUE || currentColor == ColorValue.RED) {
//                    telemetry.addData("Touching ", currentColor);
//                    setCurrentState(DONE);
//                }
                break;

            case DONE: // When all operations are complete
                logStage();
                getRobot().driveTrain.stop();
                setOperationsCompleted();
                break;
        }
        getTelemetryUtil().sendTelemetry();
        telemetry.update();

    }

    public void logStage() {
        getTelemetryUtil().addData("Stage", getCurrentState().toString());
        getTelemetryUtil().sendTelemetry();
    }
}
