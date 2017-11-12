package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftcTeam.configurations.Team8702Prod;
import org.ftcTeam.utils.EncoderBasedOmniWheelController;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;
import org.ftcTeam.utils.ColorValue;

abstract class AbstractAutoMode extends ActiveOpMode {

    private enum State {
        INIT,
        ELMO_DOWN,
        READ_JEWEL_COLOR,
        KNOCK_OFF_JEWEL,
        ELMO_UP,
        DONE
    }
    //Setting Target Reached value.
    //If it is set to true then State moves to next step
    //Starting of each step, it will set to false so the the can run until
    // robot set to true
    boolean targetReached = false;

    //States
    private State currentState;

    //Declare the MotorToEncoder
    private Team8702Prod robot;
    //Wheel Controller
    EncoderBasedOmniWheelController wheelController;
    private MotorToEncoder motorToEncoderFR;
    private MotorToEncoder motorToEncoderFL;
    private MotorToEncoder motorToEncoderBR;
    private MotorToEncoder motorToEncoderBL;

    AdaFruitColorSensor jewelColorSensor;
    ColorValue jewelColorValue = ColorValue.ZILCH;

    ColorValue panelColor = ColorValue.ZILCH;

    @Override
    protected void onInit() {
        //Set state to Init
        currentState = State.INIT;
        wheelController = new EncoderBasedOmniWheelController(robot);

        //Declare the Motors
        motorToEncoderFL = new MotorToEncoder(this, robot.motorFL);
        motorToEncoderFR = new MotorToEncoder(this, robot.motorFR);
        motorToEncoderBR = new MotorToEncoder(this, robot.motorBR);
        motorToEncoderBL = new MotorToEncoder(this, robot.motorBL);

        //Color Sensor
        jewelColorSensor = new AdaFruitColorSensor(this, robot);

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
    }

    @Override
    protected void onStart() throws InterruptedException {
        // Read color of panel (Red or Blue)
        panelColor = getPanelColor();
        super.onStart();
    }

    @Override
    protected void activeLoop() throws InterruptedException {

        switch (currentState) {
            case INIT: //Set everything
                logStage();
                //Set targetReached to true
                startTheRobot();

                //Condition if targetReached is true
                if (targetReached) {
                    currentState = State.ELMO_DOWN;
                    targetReached = false;
                }
                break;

            case ELMO_DOWN: //Bring elmo down
                //Set 1 second delay
                logStage();
                //targetReached is true
                startTheRobot();

                //If task is complete move to next state
                if(targetReached) {
                    currentState = State.READ_JEWEL_COLOR;
                    targetReached = false;
                    sleep(1000);
                }

                break;

            case READ_JEWEL_COLOR: //Read jewel color
                jewelColorValue = jewelColorSensor.getColor();

                getTelemetryUtil().addData("Jewel Color:", jewelColorValue.toString());
                getTelemetryUtil().sendTelemetry();
                if (jewelColorValue == ColorValue.RED || jewelColorValue == ColorValue.BLUE) {
                    targetReached = true;
                }

                if(targetReached) {
                    currentState = State.KNOCK_OFF_JEWEL;
                    targetReached = false;
                    sleep(1000);
                }

                break;
            case KNOCK_OFF_JEWEL: //Move robot to appropriate direction for color
                logStage();

                //move one wheel forward
                targetReached = motorToEncoderFL.runToTarget(1.0, 1240, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);

                //If task is complete move to next state
                if (targetReached) {
                    currentState = State.ELMO_UP;
                    targetReached = false;
                    sleep(1000);
                }

                break;

            case ELMO_UP: //Bring elmo up
                logStage();
                //Set targetReached to true
                startTheRobot();

                //If task is complete move to next stage
                if (targetReached) {
                    currentState = State.DONE;
                    targetReached = false;
                    sleep(1000);
                }

                break;

            case DONE: //Complete autonomous
                //Set 1 second delay
                logStage();

                //Set targetReached to false
                targetReached = false;
                setOperationsCompleted();
                break;
        }

    }

    private void startTheRobot() {
        // TODO logic later
        targetReached = true;
    }

    private void logStage() {
        getTelemetryUtil().addData("Stage", currentState.toString());
        getTelemetryUtil().sendTelemetry();
    }
    abstract ColorValue getPanelColor();
}
