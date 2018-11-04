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
        GYRO_SENSOR_TURNER,
        DONE
    }

    boolean targetReached = false;
    private State currentState;

    private Team8702ProdAuto robot = new Team8702ProdAuto();
    private GyroAutoMode gyroMode;

    //Set ColorValue to zilch
    //ColorValue panelColor = ColorValue.ZILCH;
    //abstract ColorValue getPanelColor();

    abstract boolean park() throws InterruptedException;
    //Acceleration gravity;

    protected void onInit() {

        robot.init(hardwareMap, getTelemetryUtil());
        gyroMode = new GyroAutoMode(robot, telemetry);

        gyroMode.init();
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

        currentState = State.GYRO_SENSOR_TURNER;
        robot.stopRobot();
        robot.setRunMode();
    }

    @Override
    protected void activeLoop() throws InterruptedException {

        switch (currentState) {
            case GYRO_SENSOR_TURNER:
                logStage();
                targetReached = gyroMode.gyroSensorTurnerState(95);

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


