package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftcTeam.configurations.Team8702Prod;
import org.ftcTeam.utils.EncoderBasedOmniWheelController;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;
import org.ftcTeam.utils.ColorValue;
import org.ftcbootstrap.components.ColorSensorComponent;
import org.ftcTeam.opmodes.production.JewelColorSensorComponent;

abstract class AbstractAutoMode extends ActiveOpMode {

    private enum State {
        INIT,
        ELMO_DOWN,
        READ_JEWEL_COLOR,
        KNOCK_OFF_JEWEL,
        ELMO_UP,
        DONE
    }

    //States
    private State currentState;

    //Declare the MotorToEncoder
    private Team8702Prod robot;
    private MotorToEncoder motorToEncoderFR;
    private MotorToEncoder motorToEncoderFL;
    private MotorToEncoder motorToEncoderBR;
    private MotorToEncoder motorToEncoderBL;

    //Wheel Controller
    EncoderBasedOmniWheelController wheelController;

    //Setting Target Reached to false
    boolean targetReached = false;

    JewelColorSensorComponent jewelColorSensorComponent;

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
        jewelColorSensorComponent = new JewelColorSensorComponent(this, robot);

        ColorSensorComponent = new ColorSensorComponent(this, robot.elmoColorSensor, ColorSensorComponent.ColorSensorDevice.ADAFRUIT);

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
    }

    @Override
    protected void activeLoop() throws InterruptedException {

        switch (currentState) {

            case INIT: //Set everything

                //Show Telemetry
                getTelemetryUtil().addData("Stage", currentState.toString());
                getTelemetryUtil().sendTelemetry();

                //Set targetReached to true
                startTheRobot();

                //Condition if targetReached is true
                if (targetReached) {
                    currentState = State.ELMO_DOWN;
                }
                targetReached = false;
                break;

            case ELMO_DOWN: //Bring elmo down
                //Set 1 second delay
                sleep(1000);

                //set targetReached to false
                targetReached = false;

                //Show Telemetry
                getTelemetryUtil().addData("Stage", currentState.toString());
                getTelemetryUtil().sendTelemetry();

                //targetReached is true
                startTheRobot();

                //If task is complete move to next state
                if(targetReached) {
                    currentState = State.READ_JEWEL_COLOR;
                }

                break;

            case READ_JEWEL_COLOR: //Read jewel color
                //Set delay for 1 second
                sleep(1000);

                //Set targetReached to false
                targetReached = false;

                //Show Telemetry
                getTelemetryUtil().addData("Stage", currentState.toString());
                getTelemetryUtil().sendTelemetry();

                //Set targetReached to true
                startTheRobot();

                if(targetReached) {
                    currentState = State.KNOCK_OFF_JEWEL;
                }

                break;
            case KNOCK_OFF_JEWEL: //Move robot to appropriate direction for color

                //Delay for 1 second
                sleep(1000);

                //Set targetReached to false
                targetReached = false;

                //Show Telemetry
                getTelemetryUtil().addData("Stage: ", currentState.toString());
                getTelemetryUtil().sendTelemetry();

                //move one wheel forward
                targetReached = motorToEncoderFL.runToTarget(1.0, 1240, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);

                //If task is complete move to next state
                if (targetReached) {
                    currentState = State.ELMO_UP;
                }

                break;

            case ELMO_UP: //Bring elmo up
                //Set 1 second delay
                sleep(1000);

                //Set targetReached to false
                targetReached = false;

                //Show Telemetry
                getTelemetryUtil().addData("Stage", currentState.toString());
                getTelemetryUtil().sendTelemetry();

                //Set targetReached to true
                startTheRobot();

                //If task is complete move to next stage
                if (targetReached) {
                    currentState = State.DONE;
                }

                break;

            case DONE: //Complete autonomous
                //Set 1 second delay
                sleep(1000);

                //Set targetReached to false
                targetReached = false;

                //Show Telemetry
                getTelemetryUtil().addData("Stage", currentState.toString());
                getTelemetryUtil().sendTelemetry();

                //Set targetReached to true
                startTheRobot();

                setOperationsCompleted();
                break;
        }

    }

    private void startTheRobot() {

        // TODO logic later
        targetReached = true;
    }
    abstract ColorValue getPanelColor();
}
