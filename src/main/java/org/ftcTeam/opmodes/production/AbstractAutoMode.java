package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.hardware.Servo;

import org.ftcTeam.configurations.Team8702ProdAuto;
import org.ftcTeam.utils.EncoderBasedOmniWheelController;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.ColorSensorComponent;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcTeam.utils.ColorValue;

abstract class AbstractAutoMode extends ActiveOpMode {

    protected enum State {
        INIT,
        ELMO_DOWN,
        READ_JEWEL_COLOR,
        KNOCK_OFF_JEWEL,
        ELMO_UP,
        PARKING,
        DONE
    }

    //Setting Target Reached value.
    //If it is set to true then State moves to next step
    //Starting of each step, it will set to false so the the can run until
    // robot set to true
    boolean targetReached = false;

    //States
    private State currentState;
    private int testStep;

    //Declare the MotorToEncoder
    private Team8702ProdAuto robot;
    //Wheel Controller
    EncoderBasedOmniWheelController wheelController;
    private MotorToEncoder motorToEncoderFR;
    private MotorToEncoder motorToEncoderFL;
    private MotorToEncoder motorToEncoderBR;
    private MotorToEncoder motorToEncoderBL;


    ColorValue jewelColorValue = ColorValue.ZILCH;
    ColorValue panelColor = ColorValue.ZILCH;
    public ColorSensorComponent colorSensorComponent;

    abstract ColorValue getPanelColor();

    private ElmoOperation elmoOperation;

    @Override
    protected void onInit() {
        //Set state to Init
        currentState = State.INIT;
        testStep = 1;

        //Declare the Motors
        motorToEncoderFL = new MotorToEncoder(this, robot.motorFL);
        motorToEncoderFR = new MotorToEncoder(this, robot.motorFR);
        motorToEncoderBR = new MotorToEncoder(this, robot.motorBR);
        motorToEncoderBL = new MotorToEncoder(this, robot.motorBL);


        this.elmoOperation = new ElmoOperation(this);

        //Color Sensor
        colorSensorComponent = new ColorSensorComponent(this, robot.elmoColorSensor, ColorSensorComponent.ColorSensorDevice.ADAFRUIT);

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
                startTheRobot();
                if (targetReached) {
                    currentState = State.ELMO_DOWN;
                    targetReached = false;
                }
                break;

            case ELMO_DOWN: //Bring elmo down
                logStage();
                targetReached = elmoOperation.elmoDown();

                if (targetReached) {
                    currentState = State.KNOCK_OFF_JEWEL;
                    targetReached = false;
                    break;
                }

                break;
            case KNOCK_OFF_JEWEL: //Move robot to appropriate direction for color
                logStage();

                targetReached = elmoOperation.knockOffJewel();

//                //move one wheel forward
//                targetReached = motorToEncoderFL.runToTarget(1.0, 1240, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);

                if (targetReached) {
                    currentState = State.ELMO_UP;
                    targetReached = false;
                    sleep(1000);
                }
                break;
            case ELMO_UP: //Bring elmo up
                logStage();
                targetReached = elmoOperation.elmoUp();

                if (targetReached) {
                    currentState = State.PARKING;
                    targetReached = false;
                    sleep(1000);
                }
                break;

            case PARKING: //Bring elmo up
                logStage();

                targetReached = true;
                if (targetReached) {
                    currentState = State.DONE;
                    targetReached = false;
                    sleep(1000);
                }
                break;
            case DONE:
                setOperationsCompleted();
                break;
        }

    }

    private void startTheRobot() {
        // TODO logic later
        targetReached = true;
    }

    public Team8702ProdAuto getRobot() {
        return robot;
    }

    public State getCurrentState() {
        return currentState;
    }

    public ColorValue getColor() {
        ColorValue resultColor = ColorValue.ZILCH;

        //Determine which is color to call
        if (robot.elmoColorSensor.red() > robot.elmoColorSensor.blue()
                && robot.elmoColorSensor.red() > robot.elmoColorSensor.green()) {
            resultColor = ColorValue.RED;
        } else if (robot.elmoColorSensor.blue() > robot.elmoColorSensor.red()
                && robot.elmoColorSensor.green() > robot.elmoColorSensor.red()) {
            resultColor = ColorValue.BLUE;
        }
        return resultColor;
    }

    private void logStage() {
        getTelemetryUtil().addData("Stage", currentState.toString());
        getTelemetryUtil().sendTelemetry();
    }

}