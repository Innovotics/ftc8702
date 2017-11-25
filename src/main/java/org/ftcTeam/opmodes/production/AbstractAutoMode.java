package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.hardware.Servo;

import org.ftcTeam.configurations.Team8702ProdAuto;
import org.ftcTeam.utils.EncoderBasedOmniWheelController;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.ColorSensorComponent;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
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

    private enum ElmoState {
        START_POSITION (0.0)
        , SPIN_FIRST(0.5)
        , REACH_MOVING_FORWARD(1.0)
        , SPIN_TO_HIT_BALL(0.7)
        , SPIN_RECOIL(0.5)
        , REACH_MOVING_BACKWARD(0.0)
        , SPIN_RESET(0.0)
        , END_POSITION(0.0);

        private final double position;

        private ElmoState(double position) {
            this.position = position;
        }
        public double position(){
            return this.position;
        }
    };

    private static ElmoState currentElmoState = ElmoState.START_POSITION;
    private static final double REACH_DELTA = 0.005, SPIN_DELTA = 0.005, HIT_BALL_DELTA = 0.01;


    private enum SPIN_DIRECTION {
        POSITIVE(1.0)
        , NEGATIVE(-1.0);

        private final double direction;

        private SPIN_DIRECTION(double direction){
            this.direction = direction;
        }

        public double getDirection() {
            return direction;
        }
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


        robot.elmoReach.setPosition(0.0);
        robot.elmoSpin.setPosition(0.0);
        currentElmoState = ElmoState.START_POSITION;

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
                targetReached = elmoDown();

                if(targetReached) {
                    testStep = 2;
                    break;
                }
            case 2: // Reset Encoders
                //sleep for 2 seconds
                sleep(2000);

                //Reset Encoder on all Wheels
                targetReached = motorToEncoderFR.runToTarget(0, 0, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.STOP_AND_RESET_ENCODER) &&
                        motorToEncoderFL.runToTarget(0, 0, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.STOP_AND_RESET_ENCODER) &&
                        motorToEncoderBR.runToTarget(0, 0, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.STOP_AND_RESET_ENCODER) &&
                        motorToEncoderBL.runToTarget( 0, 0, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                //Test if targetReached is true
                if(targetReached) {
                    currentState = State.KNOCK_OFF_JEWEL;
                    targetReached = false;
                    sleep(1000);
                }

                break;
            case KNOCK_OFF_JEWEL: //Move robot to appropriate direction for color
                logStage();

                targetReached = knockOffJewel();

//                //move one wheel forward
//                targetReached = motorToEncoderFL.runToTarget(1.0, 1240, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);

                if (targetReached) {
                    currentState = State.ELMO_UP;
                    targetReached = false;
                    sleep(1000);
                }
            case 3:
                //sleep for 2 seconds
                sleep(2000);

                //Move all wheels to make robot move left
                targetReached = motorToEncoderFR.runToTarget(0.5, 1140, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_TO_POSITION) &&
                        motorToEncoderFL.runToTarget(0.5, 1140, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_TO_POSITION) &&
                        motorToEncoderBL.runToTarget(0.5, 1140, MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_TO_POSITION) &&
                        motorToEncoderBR.runToTarget(0.5, 1140, MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_TO_POSITION);

            case ELMO_UP: //Bring elmo up
                logStage();
                targetReached = elmoUp();

                if (targetReached) {
                    currentState = State.DONE;
                    targetReached = false;
                    sleep(1000);
                }
            case 4:
                setOperationsCompleted();

        }

//        switch (currentState) {
//            case INIT: //Set everything
//                logStage();
//                startTheRobot();
//                if (targetReached) {
//                    currentState = State.ELMO_DOWN;
//                    targetReached = false;
//                }
//                break;
//
//            case ELMO_DOWN: //Bring elmo down
//                logStage();
//                startTheRobot();
//
//                if(targetReached) {
//                    currentState = State.READ_JEWEL_COLOR;
//                    targetReached = false;
//                    sleep(1000);
//                }
//
//                break;
//
//            case READ_JEWEL_COLOR: //Read jewel color
//                jewelColorValue = getColor();
//
//                getTelemetryUtil().addData("Jewel Color:", jewelColorValue.toString());
//                getTelemetryUtil().sendTelemetry();
//                if (jewelColorValue == ColorValue.RED || jewelColorValue == ColorValue.BLUE) {
//                    targetReached = true;
//                }
//
//                if(targetReached) {
//                    currentState = State.KNOCK_OFF_JEWEL;
//                    targetReached = false;
//                    sleep(1000);
//                }
//
//                break;
//            case KNOCK_OFF_JEWEL: //Move robot to appropriate direction for color
//                logStage();
//                //move one wheel forward
//                targetReached = motorToEncoderFL.runToTarget(1.0, 1240, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
//
//                if (targetReached) {
//                    currentState = State.ELMO_UP;
//                    targetReached = false;
//                    sleep(1000);
//                }
//
//                break;
//
//            case ELMO_UP: //Bring elmo up
//                logStage();
//                startTheRobot();
//
//                if (targetReached) {
//                    currentState = State.DONE;
//                    targetReached = false;
//                    sleep(1000);
//                }
//
//                break;
//
//            case DONE: //Complete autonomous
//                logStage();
//                targetReached = true;
//                setOperationsCompleted();
//                break;
//        }

    }

    private void startTheRobot() {
        // TODO logic later
        targetReached = true;
    }

    private boolean elmoDown() throws InterruptedException {

        // elmoDown called in incorrect elmo state
        // Just return true so that operation is not stuck.
        if (currentElmoState != ElmoState.START_POSITION){
            return true;
        }

        boolean done = false;

        do {
            getTelemetryUtil().addData("elmoDown() Beginning of do-while loop: ", currentState.name());
            getTelemetryUtil().sendTelemetry();
            switch (currentElmoState) {
                case START_POSITION:
                    currentElmoState = ElmoState.SPIN_FIRST;
                    break;
                case SPIN_FIRST:
                    rotateServo(robot.elmoSpin, ElmoState.SPIN_FIRST.position(), SPIN_DELTA);
                    Thread.sleep(500);
                    currentElmoState = ElmoState.REACH_MOVING_FORWARD;
                    break;

                case REACH_MOVING_FORWARD:
                    rotateServo(robot.elmoReach, ElmoState.REACH_MOVING_FORWARD.position(), REACH_DELTA);
                    Thread.sleep(500);
                    currentElmoState = ElmoState.SPIN_TO_HIT_BALL;
                    done = true;
                    break;
            }
        }while(done == false);

        return done;
    }

    private boolean elmoUp() throws InterruptedException {

        // elmoUp called in incorrect elmo state
        // Just return true so that operation is not stuck.
        if (currentElmoState != ElmoState.REACH_MOVING_BACKWARD){
            return true;
        }

        boolean done = false;

        do {
            getTelemetryUtil().addData("elmoUp() Beginning of do-while loop: ", currentState.name());
            getTelemetryUtil().sendTelemetry();
            switch (currentElmoState) {

                case REACH_MOVING_BACKWARD:
                    rotateServo(robot.elmoReach, ElmoState.REACH_MOVING_BACKWARD.position(), REACH_DELTA);
                    Thread.sleep(500);
                    currentElmoState = ElmoState.SPIN_RESET;
                    break;
                case SPIN_RESET:
                    rotateServo(robot.elmoSpin, ElmoState.SPIN_RESET.position(), SPIN_DELTA);
                    currentElmoState = ElmoState.END_POSITION;
                    done = true;
                    break;
            }
        } while(done == false);

        return done;
    }

    private boolean knockOffJewel() throws InterruptedException {

        // knockOffJewel called in incorrect elmo state.
        // Just return true so that operation is not stuck.
        if (currentElmoState != ElmoState.SPIN_TO_HIT_BALL){
            return true;
        }

        boolean done = false;

        do {
            getTelemetryUtil().addData("knockOffJewel() Beginning of do-while loop: ", currentState.name());
            getTelemetryUtil().sendTelemetry();

            double position = ElmoState.SPIN_TO_HIT_BALL.position();

            SPIN_DIRECTION spinDirection = getSpinDirection();

            // For example, if currentPosition = 0.5 and final spin position is 0.7
            // and spin direction is negative, then final spin position should be 0.3
            if (spinDirection == SPIN_DIRECTION.NEGATIVE) {
                position = robot.elmoReach.getPosition() - (ElmoState.SPIN_TO_HIT_BALL.position() - robot.elmoReach.getPosition());
            }

            switch (currentElmoState) {

                case SPIN_TO_HIT_BALL:
                    rotateServo(robot.elmoSpin, position, HIT_BALL_DELTA);
                    Thread.sleep(500);
                    currentElmoState = ElmoState.SPIN_RECOIL;
                    break;

                case SPIN_RECOIL:
                    rotateServo(robot.elmoSpin, ElmoState.SPIN_RECOIL.position(), SPIN_DELTA);
                    Thread.sleep(500);
                    currentElmoState = ElmoState.REACH_MOVING_BACKWARD;
                    done = true;
                    break;
            }
        } while(done == false);

        return done;
    }

    public ColorValue getColor() {
        ColorValue resultColor = ColorValue.ZILCH;

        //Determine which is color to call
        if (robot.elmoColorSensor.red() > robot.elmoColorSensor.blue()
                && robot.elmoColorSensor.red() > robot.elmoColorSensor.green()) {
            resultColor = ColorValue.RED;
        }
        else if (robot.elmoColorSensor.blue() > robot.elmoColorSensor.red()
                && robot.elmoColorSensor.green() > robot.elmoColorSensor.red()) {
            resultColor = ColorValue.BLUE;
        }
        return resultColor;
    }

    private void logStage() {
        getTelemetryUtil().addData("Stage", currentState.toString());
        getTelemetryUtil().sendTelemetry();
    }

        /**
         * Move a {@link Servo} a given {@code #finalPosition} at the speed decided by {@code #delta}
         *
         * The {@code servoDelta} shall be adjusted based on the required speed at which the arm needs to move.
         * @param servo
         * @param finalPosition
         * @param delta
         */
        private double rotateServo(Servo servo, double finalPosition, double delta) throws InterruptedException{

            double servoPosition = servo.getPosition(), startPosition = servo.getPosition(), prevPosition = servo.getPosition();

            Servo.Direction direction = (finalPosition > servoPosition)? Servo.Direction.FORWARD: Servo.Direction.REVERSE;

            String msg = String.format("[start:%.3f][final:%.3f]", servoPosition, finalPosition);

            getTelemetryUtil().addData("rotateServo : ", msg);

            getTelemetryUtil().sendTelemetry();

            // while comparing floating point numbers, never use "equals" due to problems with precision
            while (Math.abs(finalPosition - servoPosition) > 0.001 ) {

                prevPosition = servoPosition;

                if (direction == Servo.Direction.FORWARD) {

                    servoPosition += delta;

                } else {

                    servoPosition -= delta;
                }

                servo.setPosition(servoPosition);

                msg = String.format("[old:%.3f][new:%.3f]", prevPosition, servoPosition);

                getTelemetryUtil().addData("rotateServo : ", msg);

                getTelemetryUtil().sendTelemetry();

                //Thread.sleep(2000);
            }

            return servoPosition;
        }

        private SPIN_DIRECTION getSpinDirection(){

            SPIN_DIRECTION direction = SPIN_DIRECTION.POSITIVE;

            if (this.getPanelColor() != this.jewelColorValue){
                direction = SPIN_DIRECTION.NEGATIVE;
            }

            return direction;
        }
}
