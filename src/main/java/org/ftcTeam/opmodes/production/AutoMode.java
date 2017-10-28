package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftcTeam.configurations.Team8702Prod;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;


@Autonomous(name = "AutoMode", group = "Ops")
public class AutoMode extends ActiveOpMode {

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

    boolean targetReached = false;

    @Override
    protected void onInit() {
        currentState = State.INIT;

//        motorToEncoderFL = new MotorToEncoder(this, robot.motorFL);
//        motorToEncoderFR = new MotorToEncoder(this, robot.motorFR);
//        motorToEncoderBR = new MotorToEncoder(this, robot.motorBR);
//        motorToEncoderBL = new MotorToEncoder(this, robot.motorBL);

        //Color Sensor
        //  colorSensorComponent = new ColorSensorComponent(this, robot.ColorSensor1, ColorSensorComponent.ColorSensorDevice.ADAFRUIT);

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
                //Set targetReached to true
                startTheRebot();

                //Show Telemetry
                getTelemetryUtil().addData("Stage", currentState.toString());
                getTelemetryUtil().sendTelemetry();

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

                //Set targetReached to true
                startTheRebot();

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
                startTheRebot();

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
                getTelemetryUtil().addData("Stage", currentState.toString());
                getTelemetryUtil().sendTelemetry();

                //Move forward 1 rotation
//                targetReached = motorToEncoderBL.runToTarget(1.0, 1440, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER) &&
//                        motorToEncoderBR.runToTarget(1.0, 1440, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER) &&
//                        motorToEncoderFL.runToTarget(1.0, 1440, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER) &&
//                        motorToEncoderFR.runToTarget(1.0, 1440, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
//

                startTheRebot();
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
                startTheRebot();

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
                startTheRebot();

                setOperationsCompleted();
                break;
        }
    }

    private void startTheRebot() {

        // TODO logic later
        targetReached = true;
    }

}
