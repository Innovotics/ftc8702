package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.ftc8702.opmodes.InnovoticsActiveOpMode;
import org.ftc8702.configurations.production.Team8702ProdAuto;
import org.ftc8702.utils.InnovoticsRobotProperties;
import org.ftc8702.utilities.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;


abstract class AbstractAutoMode extends InnovoticsActiveOpMode {

    //States for actual autonomous
    protected enum State {
        INIT,
        HOOK,
        TURN_TO_UNHOOK,
        COLOR_SENSOR_SELF_ADJUST,
        MOVE_TO_HOME_DEPOT,
        GYRO_SENSOR_TURNER,
        ULTRASONIC_DRIVE_TO_CRATER,
        GO_OVER_RAMP,
        DONE
    }

    private State currentState;

    private Team8702ProdAuto robot = new Team8702ProdAuto();
    private GyroAutoMode gyroMode;

    private double currentAngle;

    private ColorSensorAdjustmentAutoMode colorSensorAdjustMode;
    private MoveToHomeDepotAutoMode moveToHomeDepotMode;
    private UltrasonicDriveToCraterAutoMode ultrasonicDriveToCrater;
    private boolean targetReached = false;
  //  private MotorToEncoder hookMotorToEncoder;
    private int LimitingEncoderValue = 14968;
    //2064.51612903225806 per inch


    //Set ColorValue to zilch
    //ColorValue panelColor = ColorValue.ZILCH;
    //abstract ColorValue getPanelColor();

    protected void onInit() {

        robot.init(hardwareMap, getTelemetryUtil());

        colorSensorAdjustMode = new ColorSensorAdjustmentAutoMode(robot, getTelemetryUtil());
        colorSensorAdjustMode.init();

        moveToHomeDepotMode = new MoveToHomeDepotAutoMode(robot, getTelemetryUtil());
        moveToHomeDepotMode.init();

        gyroMode = new GyroAutoMode(robot, getTelemetryUtil());

        ultrasonicDriveToCrater = new UltrasonicDriveToCraterAutoMode(robot, getTelemetryUtil(), gyroMode);
        ultrasonicDriveToCrater.init();
robot.hook.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
robot.hook.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
        getTelemetryUtil().addData("Motor Encoder Value: ", robot.hook.getCurrentPosition());


        currentState = State.HOOK;
        robot.stopRobot();
        robot.setRunMode();

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();


    }

    @Override
    protected void activeLoop() throws InterruptedException {
        getTelemetryUtil().addData("activeLoop current state", currentState.toString());
        getTelemetryUtil().sendTelemetry();

        getTelemetryUtil().sendTelemetry();
        telemetry.update();

        switch (currentState) {

            case HOOK:
                logStage();

                //targetReached = hookMotorToEncoder.runToTarget(0.5, LimitingEncoderValue, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
                robot.hook.setPower(0.7);
                while(robot.hook.getCurrentPosition() < LimitingEncoderValue) {

                    getTelemetryUtil().addData("Encoder Value: ", robot.hook.getCurrentPosition());

                    getTelemetryUtil().sendTelemetry();
                }

                robot.hook.setPower(0.0);
                targetReached = true;

                if(targetReached) {
                    currentState = State.TURN_TO_UNHOOK;
                    targetReached = false;

                    robot.stopRobot();
                    sleep(500);
                }

                break;

            case TURN_TO_UNHOOK:
                logStage();


                break;

            case COLOR_SENSOR_SELF_ADJUST:
                logStage();
                targetReached = colorSensorAdjustMode.startAdjustment();
                if (targetReached) {
                    currentState = State.MOVE_TO_HOME_DEPOT;
                    gyroMode.init();
                    targetReached = false;

                    robot.stopRobot();
                    sleep(500);
                }
                break;

            case MOVE_TO_HOME_DEPOT:
                logStage();
                targetReached = moveToHomeDepotMode.moveToHomeDepot();
                if (targetReached) {
                    currentState = State.GYRO_SENSOR_TURNER;
                    targetReached = false;

                    robot.stopRobot();
                    sleep(500);
                }
                break;

            case GYRO_SENSOR_TURNER:
                logStage();
                targetReached = gyroMode.runWithAngleCondition(110);
                if(targetReached) {
                    currentState = State.ULTRASONIC_DRIVE_TO_CRATER;
                    targetReached = false;

                    robot.stopRobot();
                    sleep(500);
                }
                break;

            case ULTRASONIC_DRIVE_TO_CRATER:
                logStage();
                targetReached = ultrasonicDriveToCrater.ultrasonicDriveToCrater();
                if (targetReached) {
                    currentState = State.GO_OVER_RAMP;
                    targetReached = false;

                    robot.stopRobot();
                    sleep(500);
                }
                break;

            case GO_OVER_RAMP:
                logStage();
                targetReached = gyroMode.testElevationChange(15, 11);
                if(targetReached) {
                    currentState = State.DONE;
                    targetReached = false;

                    robot.stopRobot();
                    sleep(500);
                }
                break;

            case DONE: // When all operations are complete
                logStage();
                break;
        }

        telemetry.update();
    }

    private void logStage() {
        getTelemetryUtil().addData("Stage", currentState.toString());
        getTelemetryUtil().sendTelemetry();
    }


}


