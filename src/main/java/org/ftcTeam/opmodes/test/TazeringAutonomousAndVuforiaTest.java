//package org.ftcTeam.opmodes.test;
//
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
//import org.ftcTeam.configurations.production.Team8702Vuforia;
//import org.ftcTeam.configurations.test.Team8702Vuforia;
//import org.ftcTeam.utils.ColorValue;
//import org.ftcTeam.utils.RobotProperties;
//import org.ftcbootstrap.ActiveOpMode;
//import org.ftcbootstrap.components.ColorSensorComponent;
//import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
//import org.ftcbootstrap.components.utils.MotorDirection;
//
//
//@TeleOp(name="TazeringVuforiaTest", group="test")
//@Disabled
//public class TazeringAutonomousAndVuforiaTest extends ActiveOpMode {
//
//    //Declare the Vuforia
//    private Team8702Vuforia robot;
//
//    //Declare the steps
//    new VuforiaSteps currentStep;
//
//    //Steps
//    private enum VuforiaSteps {
//        MOVE_TIL_DETECT,
//        DETECT_IMAGE,
//        MOVE_TO_POSITION,
//        OPEN_CLAPPER,
//        PUSH_TO_CRYPT_BOX;
//    }
//
//    @Override
//    protected void onInit() {
//
//    //specify configuration name save
//        robot = Team8702Vuforia.newConfig(hardwareMap, getTelemetryUtil());
//
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(RobotProperties.VUFORIA, RobotProperties.VUFORIA_DEFTYPE,
//        hardwareMap.appContext.getPackageName());
//        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
//
//        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
//        getTelemetryUtil().sendTelemetry();
//    }
//
//    @Override
//    protected void onStart() throws InterruptedException {
//        super.onStart();
//        step = 1;
//
//    }
//
//    @Override
//    protected void activeLoop() throws InterruptedException {
//
//    getTelemetryUtil().addData("step: " + currentStep.toString(), " Current");
//
//        boolean targetReached = false;
//
//        switch() {
//
//            case 1: // Go right until
//
//
//                if(targetReached) {
//                    step ++;
//                }
//                break;
//
//            case 2:
//
//                setOperationsCompleted();
//                break;
//        }
//    }
//}
