package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public class RingDetection {

    public enum Position {
        ASITE, BSITE, CSITE
    }

    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    private static final String VUFORIA_KEY =
            "AQS2wc3/////AAABmV4y40wtI0NTiDVppnsR0X4hXBhaWWEte7ELugFonHehHBGMxC3naFDRqAzfJgI+lsHLDKDzY9WToVuDv1F0LemRTdhErihcnopla9IOqFdPvRKwdhYTZUi5LGQBMY3hZH1VQpKlAsHyEFG+uVtPysf6kr3fTMgVX+WgFE/tJIwTFshJL3lYcgOmKfjBoMpt4q3xqaZ3rv4AZIsaiKHQi5/UwaTzTnb6BaV+ELkPqdBYlzAa7Es0bNQVuuADKbZvBPcVkh10/XOnexdqD/ZxCrTaHkZI3zMTApcdlOYUVDPKNBa3VJwZ8HCUUFG1n/BX9mjgYjF+XfUQ6gqBTkBnHEKqKiMEQA1tc09ajF1UMa5d";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    private HardwareMap hardwareMap;
    private LinearOpMode linearOpMode;
    private double timeout = 3000;
    public double startTime;

    public RingDetection(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        this.hardwareMap = hardwareMap;
        this.linearOpMode = linearOpMode;
    }

    public void initialize() {
        initVuforia();
        initTfod(hardwareMap);
        startTime = System.currentTimeMillis();
    }

    public Position detectRings() {
        try
        {
            return internalDetectRings();
        }
        finally
        {
            if (tfod != null) {
                tfod.shutdown();
            }
        }
    }

    public Position internalDetectRings() {

        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(2.5, 1.78);
        }

        /** Wait for the game to begin */
        linearOpMode.telemetry.addData(">", "Press Play to start op mode");
        linearOpMode.telemetry.update();
        linearOpMode.waitForStart();

        Position result = null;
        if (linearOpMode.opModeIsActive()) {
            while (linearOpMode.opModeIsActive()) {
                if((System.currentTimeMillis()-startTime) > timeout){
                    result = Position.ASITE;
                    return result;
                }
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        linearOpMode.telemetry.addData("# Object Detected", updatedRecognitions.size());

                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        for (Recognition recognition : updatedRecognitions) {
                            linearOpMode.telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            linearOpMode.telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            linearOpMode.telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());

                            if (recognition.getLabel() == "Quad") {
                                result = Position.CSITE;
                                return result;
                            } else if (recognition.getLabel() == "Single") {
                                result = Position.BSITE;
                                return result;
                            } else {
                                result = Position.ASITE;
                                return result;
                            }
                        }
                        linearOpMode.telemetry.update();
                    }
                }
            }
        }
        linearOpMode.telemetry.addData("Could not find site, ", "defaulting to A site");
        linearOpMode.telemetry.update();
        return Position.ASITE;
    }

    private void initTfod(HardwareMap hardwareMap) {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }
}
