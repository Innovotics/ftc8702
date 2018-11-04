package org.ftc8702.opmodes.production;



import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.ftc8702.opmodes.InnovoticsActiveOpMode;
import org.ftc8702.configurations.production.Team8702ProdAuto;
import org.ftc8702.utilities.OrientationUtils;
import org.ftc8702.utils.ColorValue;


abstract class AbstractAutoMode extends InnovoticsActiveOpMode {

    //States for actual autonomous
    protected enum State {
        INIT,
        DROP_DOWN,                  // Drop the robot
        ADJUST_TO_BASELINE,
        KNOCK_BLOCK,                // Move forward and knock the block
        DROP_MARKER,                // Drop the marker into the home
        GO_BACK_TO_LANDER,          // Move the robot back b/t base and home
        GYRO_SENSOR_ADJUSTER,       // Turn robot left
        ULTRASONIC_ADJUSTER,        // Run the robot along with wall
        GYRO_SENSOR_TURNER,
        ULTRASONIC_SENSOR_TO_DEPOT,
        COLOR_SENSOR_STOP,
        MOVE_TO_PARK,
        STOP_ON_RAMP,
        DONE
    }

    boolean targetReached = false;
    private State currentState;

    //Declare the MotorToEncoder
    private Team8702ProdAuto robot = new Team8702ProdAuto();
    private GyroAutoMode gyroMode;


    //Set ColorValue to zilch
    //ColorValue panelColor = ColorValue.ZILCH;
    //abstract ColorValue getPanelColor();

    abstract boolean park() throws InterruptedException;

    Orientation angles;
    //Acceleration gravity;

    protected void onInit() {

        robot.init(hardwareMap, getTelemetryUtil());
        gyroMode = new GyroAutoMode(robot, telemetry);
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

        currentState = State.INIT;
    }

    @Override
    protected void activeLoop() throws InterruptedException {


        switch (currentState) {
            case INIT: //Set everything
                logStage();
                targetReached = executeInitState();
                //test if targetReached is true
                if (targetReached) {
                    // currentState  = InnovoticsAbstractAutoMode.State.;

                    currentState = State.DROP_DOWN;
                    targetReached = false;
                }
                break;

            case DROP_DOWN: //Bring elmo down
                logStage();
                targetReached = dropDownState();

                if (targetReached) {
                    currentState = State.GYRO_SENSOR_TURNER;
                    targetReached = false;
                    break;
                }

                break;
            case GYRO_SENSOR_TURNER: //Bring elmo down
                logStage();
                targetReached = gyroMode.gyroSensorTurnerState(90);

                if (targetReached) {
                    currentState = State.DONE;
                    targetReached = false;
                    break;
                }

                break;
            case DONE: // When all operations are complete
                logStage();
                break;
        }

    }


    private boolean executeInitState() {
        return true;
    }

    private boolean dropDownState() {
        return true;
    }


    private void logStage() {
        getTelemetryUtil().addData("Stage", currentState.toString());
    }

}


