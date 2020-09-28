package org.ftc8702.opmodes.Sensors;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.ftc8702.configurations.production.Team8702ProdAuto;

import java.util.List;

import ftcbootstrap.components.utils.TelemetryUtil;

public class ObjectDetectionAutoMode {
    public enum Position {
        LEFT, RIGHT, CENTER;
    }

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "ASVx607/////AAABmeYAtysWv0AXpZe726GhwxofjFOd04VMHXb225G3ekEFMyTp6Wb9dJcGjGpeDNyRQBzGLKn2BMDTmBb5fMFIUBrN/LdHRaR1XtWhBnAusAVpP5nhLPAAdNIT6duwXmcijvtNKrHg4Eh/dA8UPFBRdx/uFkWpRYwEntXDWYor3Fo03J02mLPUvic76qSUlNBWhDM3pe/V1I82oGRt/X4yEsXKRk3YiDFnAMbxziGnYAV2I5rX9oVPriZ9y+JB5YvfSZIIYgmp3GYxQVJjIqUNNYM5+PBaBxBy012laaKhqf40BYxX41QEfbCq+KNx76JSCOSvVRKEay39+czt1JAyaBMIWadXSHrrmPI12JRAG+57";

    private static final long TIME_OUT = 5000;

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    private TelemetryUtil telemetry;
    private Team8702ProdAuto robot;
   // private GyroAutoMode gyroMode;

    private boolean isCompleted = false;
    private double angleToGoldMineral = 0;


//    public ObjectDetectionAutoMode(Team8702ProdAuto robot, TelemetryUtil telemetry, GyroAutoMode gyroMode) {
//        this.telemetry = telemetry;
//        this.robot = robot;
//        this.gyroMode = gyroMode;
//    }

    public void init() {
        initVuforia();
        telemetry.addData("vuforia", "inited");
        telemetry.sendTelemetry();


    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;


        telemetry.addData("vuforia parameter", "inited");
        telemetry.sendTelemetry();

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        telemetry.addData("vuforia", "created");
        telemetry.sendTelemetry();
        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = robot.getHardwareMap().appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", robot.getHardwareMap().appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
        telemetry.sendTelemetry();
    }

//    public double getGoldMineralAngle() {
//        boolean isFound = false;
//
//        long start = System.currentTimeMillis();
////        while (!isFound) {
//           detectGoldMineral();
////            long duration = System.currentTimeMillis() - start;
////            telemetry.addData("time out duration ms", duration);
////            if (duration > TIME_OUT) {
////                telemetry.addData("time out", "reached");
////                break;
//           // }
//            telemetry.sendTelemetry();x
//        }

//        return angleToGoldMineral;
//    }

    public boolean knockDownGoldMineral(double goldAngle) throws InterruptedException {
        telemetry.addData("Turn robot to ", String.format("%.2f degree", goldAngle));
        telemetry.sendTelemetry();

//        if (goldAngle > 0) {
//            gyroMode.goRightToAngleDegree(goldAngle);
//        } else {
//            gyroMode.goLeftAngleCondition(goldAngle);
//        }
//        robot.forwardRobot(0.4);
//        robot.sleep(2000);
//        robot.stopRobot();
//        if (goldAngle > 0) {
//            gyroMode.goLeftAngleCondition(goldAngle);
//        } else {
//            gyroMode.goRightToAngleDegree(goldAngle);
//        }
        robot.forwardRobot(0.4);
        robot.sleep(2000);
        robot.stopRobot();

        return isCompleted;
    }

    public Position detectGoldMineral() throws InterruptedException {
        initTfod();
        if (tfod != null) {
            tfod.activate();
        }
        //This sleep of for letting the program get new information, if removed the camera's information
        // will not transfer to the phone
        robot.sleep(500);

        Position goldPosition = Position.LEFT;

        // getUpdatedRecognitions() will return null if no new information is available since
        // the last time that call was made.
        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
        if (updatedRecognitions != null) {
            telemetry.addData("# Object Detected", updatedRecognitions.size());
            if (updatedRecognitions.size() == 3) {
                int goldMineralX = -1;
                int silverMineral1X = -1;
                int silverMineral2X = -1;
                for (Recognition recognition : updatedRecognitions) {
                    if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                        goldMineralX = (int) recognition.getLeft();
                        angleToGoldMineral = recognition.estimateAngleToObject(AngleUnit.DEGREES);
                    } else if (silverMineral1X == -1) {
                        silverMineral1X = (int) recognition.getLeft();
                    } else {
                        silverMineral2X = (int) recognition.getLeft();
                    }
                }
                if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                    if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                        goldPosition = Position.LEFT;
                        telemetry.addData("Gold Mineral Position", "Left");
                    } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
//                        if(angleToGoldMineral > 0) {
//                            angleToGoldMineral = angleToGoldMineral * -1;
//                        }
                        goldPosition = Position.RIGHT;
                        telemetry.addData("Gold Mineral Position", "Right");
                    } else {
                        goldPosition = Position.CENTER;
                        telemetry.addData("Gold Mineral Position", "Center");
                    }
                }
            }
            telemetry.sendTelemetry();
        }

        return goldPosition;
    }
}
