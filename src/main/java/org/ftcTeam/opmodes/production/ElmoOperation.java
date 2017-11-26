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
        REACH_MOVING_FORWARD(1.0),
        SPIN_TO_HIT_BALL(0.7),
        SPIN_RECOIL(0.5),
        REACH_MOVING_BACKWARD(0.0),
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

    private static ElmoState currentElmoState = ElmoState.START_POSITION;
    private static final double REACH_DELTA = 0.005, SPIN_DELTA = 0.005, HIT_BALL_DELTA = 0.01;


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

        robot.elmoReach.setPosition(0.0);
        robot.elmoSpin.setPosition(0.0);

        currentElmoState = ElmoState.START_POSITION;

    }

    public boolean elmoDown() throws InterruptedException {

        // elmoDown called in incorrect elmo state
        // Just return true so that operation is not stuck.
        if (currentElmoState != ElmoState.START_POSITION) {
            return true;
        }

        boolean done = false;

        do {
            this.abstractAutoMode.getTelemetryUtil().addData("elmoDown() Beginning of do-while loop: ", this.abstractAutoMode.getCurrentState().name());
            this.abstractAutoMode.getTelemetryUtil().sendTelemetry();
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
        } while (done == false);

        return done;
    }

    public boolean elmoUp() throws InterruptedException {

        // elmoUp called in incorrect elmo state
        // Just return true so that operation is not stuck.
        if (currentElmoState != ElmoState.REACH_MOVING_BACKWARD) {
            return true;
        }

        boolean done = false;

        do {
            this.abstractAutoMode.getTelemetryUtil().addData("elmoUp() Beginning of do-while loop: ", this.abstractAutoMode.getCurrentState().name());
            this.abstractAutoMode.getTelemetryUtil().sendTelemetry();
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
        } while (done == false);

        return done;
    }

    public boolean knockOffJewel() throws InterruptedException {

        // knockOffJewel called in incorrect elmo state.
        // Just return true so that operation is not stuck.
        if (currentElmoState != ElmoState.SPIN_TO_HIT_BALL) {
            return true;
        }

        boolean done = false;

        do {
            this.abstractAutoMode.getTelemetryUtil().addData("knockOffJewel() Beginning of do-while loop: ", this.abstractAutoMode.getCurrentState().name());
            this.abstractAutoMode.getTelemetryUtil().sendTelemetry();

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

        if (this.abstractAutoMode.getPanelColor() != this.abstractAutoMode.jewelColorValue) {
            direction = SPIN_DIRECTION.NEGATIVE;
        }

        return direction;
    }
}
