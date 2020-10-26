package org.ftc8702.opmodes.production;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;

import static org.ftc8702.opmodes.production.SkystoneAutoModeState.*;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.ftc8702.configurations.production.SkystoneAutoConfig;
import ftcbootstrap.ActiveOpMode;

import org.ftc8702.configurations.production.SkystoneAutonousConfig;
import org.ftc8702.opmodes.test.BenColorSensorTest;
import org.ftc8702.utils.ColorUtil;
import org.ftc8702.utils.ColorValue;

@Autonomous(name = "LEFTBLUESimpleAutoMode", group = "Ops")
public class SkystoneLEFTBLUESimpleAutoMode  extends ActiveOpMode {

    private SkystoneAutonousConfig robot = new SkystoneAutonousConfig();
    private SkystoneAutoModeState currentState;
    private boolean accomplishedTask = false;
    private double fastRatio =  0.5;
    private boolean finishedJob = false;
    private BenColorSensorTest colorSensorTester;
    double currentYawAngle;
    double currentPitchAngle;
    double currentRollAngle;
    Orientation angle;

    private static final long TIME_OUT = 10000L;
    private long timeToPark = 0;

    @Override
    protected void onInit() {
        robot.init(hardwareMap, getTelemetryUtil());
        robot.driveTrain.setTelemetry(telemetry);
        initializeIMU();
        currentState = MOVE_TO_FOUNDATION;
    }

    public void initializeIMU() {

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        robot.imu.initialize(parameters);
        angle = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        currentYawAngle = angle.firstAngle;
        currentPitchAngle = angle.thirdAngle;
        currentRollAngle = angle.secondAngle;
    }
    @Override
    protected void activeLoop() throws InterruptedException {
        getTelemetryUtil().addData("activeLoop current state", currentState.toString());
        telemetry.update();

    switch (currentState) {
        case MOVE_TO_FOUNDATION:
            logStage();
            robot.driveTrain.goBackwardWithUltrasonic(0.5f,0.2, robot.distanceSensor,20);
            //robot.driveTrain.goBackward(1);
            //sleep((long)(968*fastRatio));
            robot.driveTrain.strafeRightWithoutPID(0.3f);
            sleep(500);
            robot.driveTrain.goBackwardWithoutPID(0.3f);
            sleep(250);
            robot.driveTrain.stop();
            currentState = LOWER_FOUNDATION_GRABBER;
            break;

        case LOWER_FOUNDATION_GRABBER:
            logStage();
            getTelemetryUtil().addData("LOWER_FOUNDATION_GRABBER", " Pressed");
            robot.jaja.JaJaLeftDown();
            robot.jaja.JaJaRightDown();
            sleep(1000);
            accomplishedTask = true;
            currentState = MOVE_FROM_FOUNDATION;

            break;
        case MOVE_FROM_FOUNDATION:
            logStage();
            robot.driveTrain.turnSmoothLeftAutonomous();
            sleep(1300);
            robot.driveTrain.pivitRight();
            sleep(900);
            robot.driveTrain.stop();
            robot.jaja.JaJaUp();
            sleep(1000);
            //robot.driveTrain.goForward(0.3f);
            //sleep(300);
            //robot.driveTrain.strafeLeft(0.4f);
            //sleep(500);
            robot.driveTrain.goBackwardWithoutPID(0.5f);
            sleep(2000);
            robot.driveTrain.strafeRightWithoutPID(0.5f);
            sleep(1000);
            robot.driveTrain.stop();
            sleep(500);
            currentState = PARK;//make this park after we fix everything
            break;

            case PARK:
                if (timeToPark == 0) {
                    timeToPark = System.currentTimeMillis();
                }
                if ((System.currentTimeMillis() - timeToPark) >= TIME_OUT)
                {
                    telemetry.addData("Time out ", "Reached");
                    robot.driveTrain.stop();
                    robot.jaja.JaJaDown();
                    currentState = DONE;
                    break;
                }

                logStage();
                robot.driveTrain.goForwardWithoutPID(.4f);
                ColorValue currentColor = ColorUtil.getColor(robot.colorSensor);

                if(currentColor == ColorValue.BLUE || currentColor == ColorValue.RED) {
                    telemetry.addData("Touching ", currentColor);
                    robot.driveTrain.stop();
                    robot.jaja.JaJaDown();
                    currentState = DONE;
                }
                else if(currentColor == ColorValue.ZILCH || currentColor == ColorValue.GREEN){
                    robot.driveTrain.goForwardWithoutPID(.4f);
                }
                break;

        case DONE: // When all operations are complete
            logStage();
            robot.driveTrain.stop();
            setOperationsCompleted();
            break;
    }
    getTelemetryUtil().sendTelemetry();
        telemetry.update();

}

    public void logStage() {
        getTelemetryUtil().addData("Stage", currentState.toString());
        getTelemetryUtil().sendTelemetry();
    }
}
