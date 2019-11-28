/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.ftc8702.opmodes.Sensors;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

/**
 * This 2019-2020 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the Skystone game elements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@TeleOp(name = "Skystone Detector", group = "Autonomous")

public class ObjectDetectionAutoModeWebcam extends LinearOpMode {

    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";

    public static class RecognitionResult
    {
        public int position; // 1, 2 or 3
        public double angleToPosition;

        public RecognitionResult(int position, double angleToPosition) {
            this.position = position;
            this.angleToPosition = angleToPosition;
        }
    }

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY = "ASVx607/////AAABmeYAtysWv0AXpZe726GhwxofjFOd04VMHXb225G3ekEFMyTp6Wb9dJcGjGpeDNyRQBzGLKn2BMDTmBb5fMFIUBrN/LdHRaR1XtWhBnAusAVpP5nhLPAAdNIT6duwXmcijvtNKrHg4Eh/dA8UPFBRdx/uFkWpRYwEntXDWYor3Fo03J02mLPUvic76qSUlNBWhDM3pe/V1I82oGRt/X4yEsXKRk3YiDFnAMbxziGnYAV2I5rX9oVPriZ9y+JB5YvfSZIIYgmp3GYxQVJjIqUNNYM5+PBaBxBy012laaKhqf40BYxX41QEfbCq+KNx76JSCOSvVRKEay39+czt1JAyaBMIWadXSHrrmPI12JRAG+57";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    public void initialize(HardwareMap hardwareMap, Telemetry telemetry)
    {
        super.hardwareMap = hardwareMap;
        super.telemetry = telemetry;
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();

        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
            telemetry.update();

        }

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

        }

        telemetry.update();
    }

    @Override
    public void runOpMode() {

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                detect();
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    // return 0 = skystone is at position 1, 1 = position 2, 2 = position 3, negative = unknown
    public RecognitionResult detect(){

        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());
                // step through the list of recognitions and display boundary info.
                int i = 0;

                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals("Skystone")) {
                            double angle = recognition.estimateAngleToObject(AngleUnit.DEGREES);
                            telemetry.addData("Angle: ", recognition.estimateAngleToObject(AngleUnit.DEGREES));
                            //find positions
                            if (angle < -15) {
                                telemetry.addData("Left", " Position");
                                telemetry.addData("Angle: ", angle);
                                telemetry.update();
                                return new RecognitionResult(1, angle);

                            } else if (angle >= -15 && angle < 23) {
                                telemetry.addData("Center", " Position");
                                telemetry.addData("Angle: ", angle);
                                telemetry.update();
                                return new RecognitionResult(2, angle);

                            } else if (angle >= 23) {
                                telemetry.addData("Right", " Position");
                                telemetry.addData("Angle: ", angle);
                                telemetry.update();
                                return new RecognitionResult(3, angle);

                            }
                        }
                    }




                telemetry.update();


            }
        }
        return null;
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
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    //find adjustment vector
    public int getPosition(double angle) {

        //find positions
        if(angle < -20) {
            telemetry.addData("Right", " Position");
            telemetry.addData("Angle: ", angle);
            telemetry.update();
            return 1;

        } else if(angle > - 20 && angle < 10) {
            telemetry.addData("Center", " Position");
            telemetry.addData("Angle: ", angle);
            telemetry.update();
        return 2;

        } else if(angle >= 10) {
            telemetry.addData("Left", " Position");
            telemetry.addData("Angle: ", angle);
            telemetry.update();
        return 3;

        } else {
            return 0;
        }

    }
}
