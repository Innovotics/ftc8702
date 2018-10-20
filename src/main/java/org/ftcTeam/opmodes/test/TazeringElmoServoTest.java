package org.ftcTeam.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftcTeam.configurations.test.Team8702Elmo;
import org.ftcbootstrap.ActiveOpMode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;

import java.text.SimpleDateFormat;
import java.util.Date;

@Autonomous(name="TazeringElmoServerTest", group="Autonomous")
@Disabled
public class TazeringElmoServoTest extends ActiveOpMode {

    //Declare the robot
    private Team8702Elmo robot;


    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static boolean debug = true;


    private enum ElmoState {
        START_POSITION (0.0)

        , SPIN_FIRST(0.5)

        , REACH_MOVING_FORWARD(1.0)

        , SPIN_TO_HIT_BALL(0.7)

        , SPIN_RECOIL(0.6)

        , REACH_MOVING_BACKWARD(0.0)

        , SPIN_RESET(0.0)

        , END_POSITION(0.0);

        private double position;

        private ElmoState(double position) {
            this.position = position;
        }

        public double position(){
            return this.position;
        }
    };

    private static ElmoState currentState = ElmoState.START_POSITION;

    private static final double REACH_DELTA = 0.005, SPIN_DELTA = 0.005, HIT_BALL_DELTA = 0.01;

    @Override
    protected void onInit() {

        //specify configuration name save
        robot = Team8702Elmo.newConfig(hardwareMap, getTelemetryUtil());

        robot.elmoReach.setPosition(0.0);
        robot.elmoSpin.setPosition(0.0);

        currentState = ElmoState.START_POSITION;

      //  printServoPositions("init()");

    }

    @Override
    protected void onStart() throws InterruptedException {


        super.onStart();

    }

    @Override
    protected void activeLoop() throws InterruptedException {

        long startTime = System.currentTimeMillis();


        getTelemetryUtil().addData("activeLoop() Timestamp : ", sdf.format(new Date()));
        getTelemetryUtil().addData("activeLoop() Current State: ", currentState.name());
        getTelemetryUtil().sendTelemetry();

        do {
            getTelemetryUtil().addData("activeLoop() Beginning of do-while loop: ", currentState.name());
            getTelemetryUtil().sendTelemetry();
            switch (currentState) {
                case START_POSITION:
                    currentState = ElmoState.SPIN_FIRST;
                    break;
                case SPIN_FIRST:
                    rotateServo(robot.elmoSpin, ElmoState.SPIN_FIRST.position(), SPIN_DELTA);
                    Thread.sleep(500);
                    currentState = ElmoState.REACH_MOVING_FORWARD;
                    break;

                case REACH_MOVING_FORWARD:
                    rotateServo(robot.elmoReach, ElmoState.REACH_MOVING_FORWARD.position(), REACH_DELTA);
                    Thread.sleep(500);
                    currentState = ElmoState.SPIN_TO_HIT_BALL;
                    break;

                case SPIN_TO_HIT_BALL:
                    rotateServo(robot.elmoSpin, ElmoState.SPIN_TO_HIT_BALL.position(), HIT_BALL_DELTA);
                    Thread.sleep(500);
                    currentState = ElmoState.SPIN_RECOIL;
                    break;

                case SPIN_RECOIL:
                    rotateServo(robot.elmoSpin, ElmoState.SPIN_RECOIL.position(), SPIN_DELTA);
                    Thread.sleep(500);
                    currentState = ElmoState.REACH_MOVING_BACKWARD;
                    break;

                case REACH_MOVING_BACKWARD:
                    rotateServo(robot.elmoReach, ElmoState.REACH_MOVING_BACKWARD.position(), REACH_DELTA);
                    Thread.sleep(500);
                    currentState = ElmoState.SPIN_RESET;
                    break;
                case SPIN_RESET:
                    rotateServo(robot.elmoSpin, ElmoState.SPIN_RESET.position(), SPIN_DELTA);
                    currentState = ElmoState.END_POSITION;
                    break;
                case END_POSITION:
                    // do nothing
                    break;
            }
        } while (currentState != ElmoState.END_POSITION);

        printServoPositions("End activeLoop()");

        long endTime = System.currentTimeMillis();

//        if (endTime - startTime < 30000) {
//            Thread.sleep(30000 - (endTime - startTime));
//        }

        return;


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

    /**
     * Print servo state
     * @param state
     */
    private void printServoPositions(String state){
        if (debug) {

            getTelemetryUtil().addData(state  + " ElmoReach Port: " , robot.elmoReach.getPortNumber());

            getTelemetryUtil().addData(state  + " ElmoReach Position: " , robot.elmoReach.getPosition());

            getTelemetryUtil().addData(state  + " ElmoSpin Port: " , robot.elmoSpin.getPortNumber());

            getTelemetryUtil().addData(state  + " ElmoSpin Position: " , robot.elmoSpin.getPosition());

            getTelemetryUtil().addData(state  + " Current State: " , currentState.name());

            getTelemetryUtil().sendTelemetry();
        }

    }
}
