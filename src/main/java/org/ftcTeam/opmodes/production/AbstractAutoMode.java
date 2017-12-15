package org.ftcTeam.opmodes.production;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.ftcTeam.configurations.production.Team8702ProdAuto;
import org.ftcTeam.configurations.production.Team8702RobotConfig;
import org.ftcTeam.utils.ColorValue;
import org.ftcTeam.utils.CryptoBoxLocation;
import org.ftcTeam.utils.RobotAutonomousUtils;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.ColorSensorComponent;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;

import java.util.Locale;


abstract class AbstractAutoMode extends ActiveOpMode {

    //States for actual autonomous
    protected enum State {
        INIT,
        ELMO_DOWN,
        READ_JEWEL_COLOR,
        KNOCK_OFF_JEWEL,
        ELMO_UP,
        VUFORIA_DETECTION,
        SWITCH_TO_CLAPPER,
        GET_OFF_PLATFORM,
        ROTATE,
        DETECT_INITIAL_DISTANCE,
        SLIDE_TO_DETECT,
        PARKING,
        DONE
    }

    private int initialDistance;
    private static final int RANGE_BAR = 30;
    private static final int RANGE_CRYPT = 34;
    private int currentBarHopping = CryptoBoxLocation.LEFT;

    //Setting Target Reached value.
    //If it is set to true then State moves to next step
    //Starting of each step, it will set to false so the the can run until
    // robot set to true
    boolean targetReached = false;

    //States
    private State currentState;

    //Declare the MotorToEncoder
    private Team8702ProdAuto robot;

    //Wheel Controller
    EncoderBasedOmniWheelController wheelController;

    //Set ColorValue to zilch
    ColorValue panelColor = ColorValue.ZILCH;

    //Declared colorSensorComponent
    public ColorSensorComponent colorSensorComponent;

    //Set cryptoBoxLocation to Unknown
    private int cryptoBoxLocation = CryptoBoxLocation.UNKNOWN;

    //Adding Vuforia
    VuforiaLocalizer vuforia = null;
    VuforiaTrackable relicTemplate = null;
    VuforiaTrackables relicTrackables = null;

    //Set abstract ColorValue
    abstract ColorValue getPanelColor();
    abstract void setGlyphPosition() throws InterruptedException;

    abstract boolean park() throws InterruptedException;

    private ElmoOperation elmoOperation;


    Orientation angles;
    Acceleration gravity;

    @Override
    protected void onInit() {

        robot = Team8702ProdAuto.newConfig(hardwareMap, getTelemetryUtil());

        //Set state to Init
        currentState = State.INIT;

        this.elmoOperation = new ElmoOperation(this);

        //Color Sensor
        colorSensorComponent = new ColorSensorComponent(this, robot.elmoColorSensor, ColorSensorComponent.ColorSensorDevice.MODERN_ROBOTICS_I2C);

        //Motor to Encoders
//        robot.motorToEncoderFL = new MotorToEncoder(this, robot.motorFL);
//        robot.motorToEncoderFR = new MotorToEncoder(this, robot.motorFR);
//        robot.motorToEncoderBL = new MotorToEncoder(this, robot.motorBL);
//        robot.motorToEncoderBR = new MotorToEncoder(this, robot.motorBR);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit =  BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        robot.imu.initialize(parameters);
        angles =  robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().addData("Heading Angle", formatAngle(angles.angleUnit, angles.firstAngle) );
        getTelemetryUtil().sendTelemetry();
    }

    @Override
    protected void onStart() throws InterruptedException {
        // Read color of panel (Red or Blue)
        panelColor = getPanelColor();

        initVuforia();

        super.onStart();
    }

    @Override
    protected void activeLoop() throws InterruptedException {

        switch (currentState) {
            case INIT: //Set everything
                logStage();

                //set targetReached to true
                startTheRobot();

                //test if targetReached is true
                if (targetReached) {
                    if (Team8702RobotConfig.ELMO_ON) {
                        currentState = State.VUFORIA_DETECTION;
                    } else {
                        currentState = State.VUFORIA_DETECTION;
                    }

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

            case VUFORIA_DETECTION: //Detect vuforia
                logStage();

                //Read crypto message
                readCryptoMessage();

                //Test if cryptoBox is not UNKNOWN
                if (cryptoBoxLocation != CryptoBoxLocation.UNKNOWN) {
                    targetReached = true;
                    getTelemetryUtil().addData("VuMark Location: ", cryptoBoxLocation);
                    relicTrackables.deactivate();
                }

                //test if targetReached is true
                if (targetReached == true) {
                    //parks
                    currentState = State.DETECT_INITIAL_DISTANCE;

                    //resets targetReached
                    targetReached = false;
                }

                break;

            case SWITCH_TO_CLAPPER: //Rotate 180 degrees
                logStage();
                // TODO - Lift off glyph
                targetReached = true;
                if (targetReached) {
                    currentState = State.GET_OFF_PLATFORM;
                    targetReached = false;
                }
                break;
            case GET_OFF_PLATFORM: //Rotate 180 degrees
                logStage();

                setGlyphPosition() ;
                targetReached = true;
                if (targetReached) {
                    currentState = State.ROTATE;
                    targetReached = false;
                }
                break;
            case ROTATE: //Rotate 180/90 degrees
                logStage();

                targetReached = true;
                if (targetReached) {
                    currentState = State.DETECT_INITIAL_DISTANCE;
                    targetReached = false;
                }
                break;
            case DETECT_INITIAL_DISTANCE: //Rotate 180 degrees
                logStage();

                initialDistance = robot.rangeSensorL.rawUltrasonic();
                targetReached = true;
                if (targetReached) {
                    currentState = State.SLIDE_TO_DETECT;
                    targetReached = false;
                    RobotAutonomousUtils.continuousStrafRight(robot.motorFR, robot.motorFL, robot.motorBR, robot.motorBL);
                    // Temporary
                    cryptoBoxLocation = CryptoBoxLocation.CENTER;
                }
                break;
            case SLIDE_TO_DETECT: //Rotate 180 degrees
                logStage();
                telemetry.addData("raw ultrasonic", robot.rangeSensorL.rawUltrasonic());
                // Use ultra sonic sensor
                // 1. Strafe right until detect bar

                if(robot.rangeSensorL.rawUltrasonic() < initialDistance - 3) {
                    sleep(10000);

                  //  RobotAutonomousUtils.pauseMotor(robot.motorFR, robot.motorFL, robot.motorBR, robot.motorBL);
                    if(currentBarHopping == cryptoBoxLocation) {
                        RobotAutonomousUtils.pauseMotor(robot.motorFR, robot.motorFL, robot.motorBR, robot.motorBL);
                        getTelemetryUtil().addData("currentBarHopping", currentBarHopping);

                        //to do open clapper and push
                        targetReached = true;
                    } else {
                        currentBarHopping ++;
                        sleep(10000);
                        getTelemetryUtil().addData(" Con currentBarHopping", currentBarHopping);
                       // RobotAutonomousUtils.continuousStrafRight(robot.motorFR, robot.motorFL, robot.motorBR, robot.motorBL);
                    }
                }

                getTelemetryUtil().addData("Heading Angle", formatAngle(angles.angleUnit, angles.firstAngle) );
                getTelemetryUtil().sendTelemetry();

//                if(robot.rangeSensorL.rawUltrasonic() < RANGE_BAR ) {
//                    if(currentBarHopping == cryptoBoxLocation) {
//                        RobotAutonomousUtils.pauseMotor(robot.motorFR, robot.motorFL, robot.motorBR, robot.motorBL);
//                        //to do open clapper and push
//                        targetReached = true;
//                    } else {
//                        currentBarHopping ++;
//                        Thread.sleep(200);
//                    }
//
//                }
                if (targetReached) {
                    currentState = State.DONE;
                }
                break;
            case PARKING: //Parks the robot to appropriate location
                logStage();

                if (!Team8702RobotConfig.AUTO_PARKING_ON) {
                    // Skip this
                    targetReached = true;
                } else {
                    targetReached = park();

//                    if(panelColor.equals(ColorValue.BLUE)){
//                        //move the robot right for parking
//                        robot.motorFL.setPower(.2 * (-1));
//                        robot.motorFR.setPower(.2 * (-1));
//                        robot.motorBL.setPower(.2);
//                        robot.motorBR.setPower(.2);
//                        sleep(2000);
//                        targetReached = true;
//                    } else if(panelColor.equals(ColorValue.RED)) {
//                        //move the robot left for parking
//                        robot.motorFL.setPower(.2);
//                        robot.motorFR.setPower(.2);
//                        robot.motorBL.setPower(.2 * (-1));
//                        robot.motorBR.setPower(.2 * (-1));
//                        sleep(2000);
//                        targetReached = true;
//                    }
                }
                if (targetReached) {
                    currentState = State.DONE;
                    targetReached = false;
                    sleep(1000);
                }
                break;

            case DONE: // When all operations are complete
                logStage();

                //set telemetry
                getTelemetryUtil().addData("VuMark", cryptoBoxLocation);
                getTelemetryUtil().sendTelemetry();

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

    //Determines Color Value of Elmo Color Sensor
    public ColorValue getElmoColor() {

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

    //telemetry for logs
    private void logStage() {
        getTelemetryUtil().addData("Stage", currentState.toString());
    }

    //Method that Initializes vuforia
    private void initVuforia() {

        //Sets camera monitor
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        //Set license
        parameters.vuforiaLicenseKey = "ARCdiMD/////AAAAGZaUI5b2cEcKvP272hyUYZsbGU1BKSfR6eaOxeU3TcCop8KqmtC2LczpH4/9gwSE38zJYRRFXQNVIyvvMZXjMe93lQiLHCPya9eBokUe/Vwl2T6La6sUwCGxINckaFGwODclWrvSP92ZdmU6Kz5+JN44Jutplpv3QszrWk8OA92L9zkce3nF1ZPkhB0yEQdwkY2nxqwXrCtUYBYSsNhemo/2NZibO/ui6NnvA1Pm90QkxNfg5Zi8MBNK0pxjV2O58pQoCDtmEqYJn1d1yxFxBuhXptpVLLUIH7CT8/nsJw1r2a6YtmQFiIkJOI8TOK1wbHCFqgtFZH97VYI9xSIpeezOZdQZz1P5kAiGsuSAlX5h";

        //Set camera direction and class factory
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        //Set relic tracker
        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        //activate relic tracker
        relicTrackables.activate();

    }

    //Format method
    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

    //Read crypto box
    private void readCryptoMessage() {
        OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose();

        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
            getTelemetryUtil().addData("VuMark", vuMark.toString());

            switch (vuMark) {
                case LEFT:
                    cryptoBoxLocation = CryptoBoxLocation.LEFT;
                    break;
                case RIGHT:
                    cryptoBoxLocation = CryptoBoxLocation.RIGHT;
                    break;
                case CENTER:
                    cryptoBoxLocation = CryptoBoxLocation.CENTER;
                    break;
            }

            telemetry.addData("Pose", format(pose));

            if (pose != null) {
                VectorF trans = pose.getTranslation();
                Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                // Extract the X, Y, and Z components of the offset of the target relative to the robot
                double tX = trans.get(0);
                double tY = trans.get(1);
                double tZ = trans.get(2);

                // Extract the rotational components of the target relative to the robot
                double rX = rot.firstAngle;
                double rY = rot.secondAngle;
                double rZ = rot.thirdAngle;
            }
        } else {
            telemetry.addData("VuMark", "not visible");
        }

        telemetry.update();
    }

    private void adjustRobot() {

    }

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees) {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

}

