package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.hardware.Servo;

import org.ftcTeam.configurations.production.Team8702ProdAuto;

/**
 * Created by Madhav on 11/24/17.
 */

public class ElmoOperation {

    private enum ElmoState {
        START_POSITION(0.0),
        SPIN_FIRST(0.5),
        REACH_MOVING_FORWARD(0.3),
        SPIN_TO_HIT_BALL(0.6),
        SPIN_RECOIL(0.5),
        REACH_MOVING_BACKWARD(0.95),
        SPIN_RESET(0.0),
        END_POSITION(0.0);

        private final double position;

        private ElmoState(double position) {
            this.position = position;
        }

        public double position() {
            return this.position;
        }
    }

    private static ElmoState elmoState = ElmoState.START_POSITION;
    private static final double REACH_DELTA = 0.005, SPIN_DELTA = 0.005, HIT_BALL_DELTA = 0.02;


    private enum SPIN_DIRECTION {
        POSITIVE(1.0),
        NEGATIVE(-1.0);

        private final double direction;

        private SPIN_DIRECTION(double direction) {
            this.direction = direction;
        }

        public double getDirection() {
            return direction;
        }
    }


    final AbstractAutoMode abstractAutoMode;

    final Team8702ProdAuto robot;

    public ElmoOperation(AbstractAutoMode abstractAutoMode){

        this.abstractAutoMode = abstractAutoMode;
        this.robot = this.abstractAutoMode.getRobot();

        resetServoPositions();

        elmoState = ElmoState.START_POSITION;

    }

    private void resetServoPositions() {
        try {
            robot.elmoReach.setPosition(0.95);
            Thread.sleep(500);
            robot.elmoSpin.setPosition(0.0);
        } catch(InterruptedException ignored) {}
    }

    public boolean elmoDown() throws InterruptedException {

        // elmoDown called in incorrect elmo state
        // Just return true so that operation is not stuck.
        if (elmoState != ElmoState.START_POSITION) {
            return true;
        }

        boolean done = false;

        do {
            this.abstractAutoMode.getTelemetryUtil().addData("elmoDown() Beginning of do-while loop: ", this.abstractAutoMode.getCurrentState().name());
            this.abstractAutoMode.getTelemetryUtil().sendTelemetry();
            switch (elmoState) {
                case START_POSITION:
                    elmoState = ElmoState.SPIN_FIRST;
                    break;
                case SPIN_FIRST:
                    rotateServo(robot.elmoSpin, ElmoState.SPIN_FIRST.position(), SPIN_DELTA);
                    Thread.sleep(500);
                    elmoState = ElmoState.REACH_MOVING_FORWARD;
                    break;

                case REACH_MOVING_FORWARD:
                    rotateServo(robot.elmoReach, ElmoState.REACH_MOVING_FORWARD.position(), REACH_DELTA);
                    Thread.sleep(500);
                    elmoState = ElmoState.SPIN_TO_HIT_BALL;
                    done = true;
                    break;
            }
        } while (done == false);

        return done;
    }

    public boolean elmoUp() throws InterruptedException {

        // elmoUp called in incorrect elmo state
        // Just return true so that operation is not stuck.
        if (elmoState != ElmoState.REACH_MOVING_BACKWARD) {
            return true;
        }

        boolean done = false;

        do {
            this.abstractAutoMode.getTelemetryUtil().addData("elmoUp() Beginning of do-while loop: ", this.abstractAutoMode.getCurrentState().name());
            this.abstractAutoMode.getTelemetryUtil().sendTelemetry();
            switch (elmoState) {

                case REACH_MOVING_BACKWARD:
                    rotateServo(robot.elmoReach, ElmoState.REACH_MOVING_BACKWARD.position(), REACH_DELTA);
                    Thread.sleep(500);
                    elmoState = ElmoState.SPIN_RESET;
                    break;
                case SPIN_RESET:
                    rotateServo(robot.elmoSpin, ElmoState.SPIN_RESET.position(), SPIN_DELTA);
                    Thread.sleep(500);
                    elmoState = ElmoState.END_POSITION;
                    done = true;
                    break;
            }
        } while (done == false);

        return done;
    }

    public boolean knockOffJewel() throws InterruptedException {

        // knockOffJewel called in incorrect elmo state.
        // Just return true so that operation is not stuck.
        if (elmoState != ElmoState.SPIN_TO_HIT_BALL) {
            return true;
        }

        final double beginHitPosition = this.robot.elmoSpin.getPosition();
        boolean done = false;

        do {
            this.abstractAutoMode.getTelemetryUtil().addData("knockOffJewel() Beginning of do-while loop: ", this.abstractAutoMode.getCurrentState().name());
            this.abstractAutoMode.getTelemetryUtil().sendTelemetry();

            switch (elmoState) {

                case SPIN_TO_HIT_BALL:

                    double endHitposition = ElmoState.SPIN_TO_HIT_BALL.position();
                    SPIN_DIRECTION spinDirection = getSpinDirection();

                    // For example, if currentPosition = 0.5 and final spin position is 0.7
                    // and spin direction is negative, then final spin position should be 0.3
                    if (spinDirection == SPIN_DIRECTION.NEGATIVE) {
                        endHitposition = beginHitPosition - (ElmoState.SPIN_TO_HIT_BALL.position() - beginHitPosition);
                    }

                    this.abstractAutoMode.getTelemetryUtil().addData("knockOffJewel() Moving Spin position to: ", new String("" + endHitposition));
                    this.abstractAutoMode.getTelemetryUtil().sendTelemetry();

                    rotateServo(robot.elmoSpin, endHitposition, HIT_BALL_DELTA);
                    printPositions();
                    Thread.sleep(500);
                    elmoState = ElmoState.SPIN_RECOIL;
                    break;

                case SPIN_RECOIL:
                    this.abstractAutoMode.getTelemetryUtil().addData("knockOffJewel() Moving Spin position to: ", new String(""+ beginHitPosition));
                    this.abstractAutoMode.getTelemetryUtil().sendTelemetry();

                    rotateServo(robot.elmoSpin, beginHitPosition, HIT_BALL_DELTA);
                    Thread.sleep(500);
                    elmoState = ElmoState.REACH_MOVING_BACKWARD;
                    done = true;
                    break;
            }
        } while (done == false);

        return done;
    }

    /**
     * Move a {@link Servo} a given {@code #finalPosition} at the speed decided by {@code #delta}
     * <p>
     * The {@code servoDelta} shall be adjusted based on the required speed at which the arm needs to move.
     *
     * @param servo
     * @param finalPosition
     * @param delta
     */
    private double rotateServo(Servo servo, double finalPosition, double delta) throws InterruptedException {

        double servoPosition = servo.getPosition(), startPosition = servo.getPosition(), prevPosition = servo.getPosition();

        Servo.Direction direction = (finalPosition > servoPosition) ? Servo.Direction.FORWARD : Servo.Direction.REVERSE;

        String msg = String.format("[start:%.3f][final:%.3f]", servoPosition, finalPosition);

        this.abstractAutoMode.getTelemetryUtil().addData("rotateServo : ", msg);

        this.abstractAutoMode.getTelemetryUtil().sendTelemetry();

        // while comparing floating point numbers, never use "equals" due to problems with precision
        while (Math.abs(finalPosition - servoPosition) > 0.001) {

            prevPosition = servoPosition;

            if (direction == Servo.Direction.FORWARD) {

                servoPosition += delta;

            } else {

                servoPosition -= delta;
            }

            servo.setPosition(servoPosition);

            msg = String.format("[old:%.3f][new:%.3f]", prevPosition, servoPosition);

            this.abstractAutoMode.getTelemetryUtil().addData("rotateServo : ", msg);

            this.abstractAutoMode.getTelemetryUtil().sendTelemetry();

            //Thread.sleep(2000);
        }

        return servoPosition;
    }

    private SPIN_DIRECTION getSpinDirection() {

        SPIN_DIRECTION direction = SPIN_DIRECTION.POSITIVE;

        printColors();

        if (this.abstractAutoMode.getPanelColor() != this.abstractAutoMode.getElmoColor()) {
            direction = SPIN_DIRECTION.NEGATIVE;
        }

        this.abstractAutoMode.getTelemetryUtil().addData("knockOffJewel() Sping direction: ", direction.name());
        this.abstractAutoMode.getTelemetryUtil().sendTelemetry();

        return direction;
    }

    private void printPositions() {
        this.abstractAutoMode.getTelemetryUtil().addData("Current State: ", this.abstractAutoMode.getCurrentState().name());
        String msg = String.format("[Spin=%f][Reach=%f]", this.robot.elmoSpin.getPosition(), this.robot.elmoReach.getPosition());
        this.abstractAutoMode.getTelemetryUtil().addData("Current Positions: ", msg);
        this.abstractAutoMode.getTelemetryUtil().sendTelemetry();
    }

    private void printColors() {
        this.abstractAutoMode.getTelemetryUtil().addData("Panel Color: ", this.abstractAutoMode.getPanelColor().name());
        this.abstractAutoMode.getTelemetryUtil().addData("Sensor Color: ", this.abstractAutoMode.getElmoColor().name());
        this.abstractAutoMode.getTelemetryUtil().sendTelemetry();
    }

}
