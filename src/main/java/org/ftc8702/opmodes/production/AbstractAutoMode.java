package org.ftc8702.opmodes.production;


import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.ftc8702.opmodes.InnovoticsActiveOpMode;
import org.ftc8702.configurations.production.Team8702ProdAuto;
import org.ftc8702.utils.ColorValue;



abstract class AbstractAutoMode extends InnovoticsActiveOpMode {

    //States for actual autonomous
    protected enum State {
        INIT,
        DROP_DOWN,
        ADJUST_TO_BASELINE,
        KNOCK_BLOCK,
        GO_BACK_TO_LANDER,
        GYRO_SENSOR_ADJUSTER,
        ULTRASONIC_ADJUSTER,
        GYRO_SENSOR_TURNER,
        ULTRASONIC_SENSOR_TO_DEPOT,
        COLOR_SENSOR_STOP,
        DROP_MARKER,
        MOVE_TO_PARK,
        GYRO_SENSOR_TO_STOP,
        DONE
    }
    boolean targetReached = false;
    private State currentState;

    //Declare the MotorToEncoder
    private Team8702ProdAuto robot = new Team8702ProdAuto();


    //Set ColorValue to zilch
    ColorValue panelColor = ColorValue.ZILCH;


    abstract ColorValue getPanelColor();

    abstract boolean park() throws InterruptedException;

    Orientation angles;
    Acceleration gravity;

    protected void onInit() {

        robot.init(hardwareMap, getTelemetryUtil());
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
    }

    @Override
    protected void activeLoop() throws InterruptedException {


        switch (currentState) {
            case INIT: //Set everything
                //logStage();
                //set targetReached to true
 //               startTheRobot();

                //test if targetReached is true
                if (targetReached) {
                   // currentState  = InnovoticsAbstractAutoMode.State.;

                    currentState = State.DROP_DOWN;
                    targetReached = false;
                }
                break;

            case DROP_DOWN: //Bring elmo down
                //logStage();
                targetReached = true;

                if (targetReached) {
                    currentState = State.DONE;
                    targetReached = false;
                    break;
                }

                break;
            case DONE: // When all operations are complete
//                logStage();
                break;
        }

    }


}


