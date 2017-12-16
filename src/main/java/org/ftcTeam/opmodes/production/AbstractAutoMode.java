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
        ADJUST_TO_BOX,
        DROP_GLYPH,
        PARKING,
        DONE
    }

    private int initialDistance;
    private static final int RANGE_BAR = 30;
    private static final int RANGE_CRYPT = 34;
    private int currentBarHopping = 0;

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

    private ClapperOperation clapperOperation;

    Orientation angles;
    Acceleration gravity;

    double initialAngle;

    @Override
    protected void onInit() {

        robot = Team8702ProdAuto.newConfig(hardwareMap, getTelemetryUtil());

        //Set state to Init
        currentState = State.INIT;



        this.elmoOperation = new ElmoOperation(this);

        this.clapperOperation = new ClapperOperation(this);

        //Color Sensor
        colorSensorComponent = new ColorSensorComponent(this, robot.elmoColorSensor, ColorSensorComponent.ColorSensorDevice.MODERN_ROBOTICS_I2C);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();


        robot.imu.initialize(parameters);
        angles =  robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        initVuforia();


        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        initialAngle = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);
        getTelemetryUtil().addData("Heading Angle", formatAngle(angles.angleUnit, angles.firstAngle) );
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
                //set targetReached to true
                startTheRobot();

                //test if targetReached is true
                if (targetReached) {
                    currentState  = State.ELMO_DOWN;
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
                    currentState = State.VUFORIA_DETECTION;
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
                    currentState = State.SWITCH_TO_CLAPPER;

                    //resets targetReached
                    targetReached = false;
                }

                break;

            case SWITCH_TO_CLAPPER: //Rotate 180 degrees
                logStage();

                Thread.sleep(500);

                this.clapperOperation.initClapperRightAndLeftMotor();

                Thread.sleep(500);

                this.clapperOperation.grabGlyph();

                Thread.sleep(500);

                this.clapperOperation.liftGlyph();

                targetReached = true;
                if (targetReached) {
                    currentState = State.GET_OFF_PLATFORM;
                    targetReached = false;
                }
                break;
            case GET_OFF_PLATFORM:
                logStage();

                RobotAutonomousUtils.offFromPlatform(robot.motorFR, robot.motorFL, robot.motorBR, robot.motorBL);
                targetReached = true;
                getTelemetryUtil().addData("Angle PlATFORM", formatAngle(angles.angleUnit, angles.firstAngle) );
                if (targetReached) {
                    currentState = State.ROTATE;
                    targetReached = false;
                }
                break;
            case ROTATE: //Rotate 180/90 degrees
                logStage();
                RobotAutonomousUtils.rotateMotor180(initialAngle, robot.imu, robot.motorFR, robot.motorFL, robot.motorBR, robot.motorBL, getTelemetryUtil());
                targetReached = true;
                if (targetReached) {
                    currentState = State.ADJUST_TO_BOX;
                    targetReached = false;
                }
                break;
            case ADJUST_TO_BOX:
                logStage();
                RobotAutonomousUtils.adjustStrafRight(cryptoBoxLocation, robot.motorFR, robot.motorFL, robot.motorBR, robot.motorBL);
                targetReached = true;
                if (targetReached) {
                    currentState = State.DROP_GLYPH;
                    targetReached = false;
                }
                break;
            case DROP_GLYPH:
                logStage();
                targetReached = clapperOperation.dropGlyph();
                if (targetReached) {
                    currentState = State.PARKING;
                }
                break;
            case PARKING: //Parks the robot to appropriate location
                logStage();

                RobotAutonomousUtils.pushGlyph(robot.motorFR, robot.motorFL, robot.motorBR, robot.motorBL);
                targetReached = true;
                if (targetReached) {
                    currentState = State.DONE;
                    targetReached = false;
                    sleep(1000);
                }
                break;

            case DONE: // When all operations are complete
                logStage();

                //set telemetry
                //getTelemetryUtil().addData("VuMark", cryptoBoxLocation);
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

    // Slide until detect the right bar
    // Adjust left to fit into to slide
    private boolean slideToDetect() throws InterruptedException {
        telemetry.addData("raw ultrasonic", robot.rangeSensorL.rawUltrasonic());
        int distance = robot.rangeSensorL.rawUltrasonic();
        if( (initialDistance - 20) < distance && distance < (initialDistance - 4)) {
            getTelemetryUtil().addData("Ultra value: ", distance);

            if(currentBarHopping == cryptoBoxLocation) {
                RobotAutonomousUtils.pauseMotor(robot.motorFR, robot.motorFL, robot.motorBR, robot.motorBL);
                Thread.sleep(1000);
                RobotAutonomousUtils.strafAdjustLeft(robot.motorFR, robot.motorFL, robot.motorBR, robot.motorBL);
                return true;
            } else {
                currentBarHopping ++;
                sleep(1200);
            }
        }
        getTelemetryUtil().addData("Heading Angle", formatAngle(angles.angleUnit, angles.firstAngle) );
        getTelemetryUtil().sendTelemetry();

        return false;
    }


    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }


    String formatDegrees(double degrees) {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

}

