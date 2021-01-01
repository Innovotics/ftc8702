package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.utils.SleepUtils;

import ftcbootstrap.ActiveOpMode;

@Autonomous(name = "Blue Side ultimate goal", group = "Ops")
public class BlueSideAutonomous extends ActiveOpMode {

    public enum State {
        INIT, RING_DETECT, DRIVE_TO_SITE_A, DRIVE_TO_SITE_B, DRIVE_TO_SITE_C, PARK, DONE
    }

    private State currentState;

    private UltimateGoalConfiguration driveTrainConfig;
    private MecanumWheelDriveTrain driveTrain;
    private GoToSite goToSite;
    private Parking parking;
    private UltimateGoalArm wobbleArm;
    private UltimateGoalShooter shooter;
    private RingDetection ringDetection;
    public  RingDetection.Position site;

    // odometer 1446 ticks = 4.7 inches (1 circumference = 1.5 inch diameter * pi = 4.7 inches)
    int targetLeftValue = 1446;
    int targetRightValue = 1446;
    int targetCenterValue = 0;


    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    public void onInit() {

        driveTrainConfig = UltimateGoalConfiguration.newConfig(hardwareMap, getTelemetryUtil());

        currentState = State.DRIVE_TO_SITE_B;
        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");

    }

    @Override
    public void onStart() throws InterruptedException {

        super.onStart();
        driveTrain = new MecanumWheelDriveTrain(driveTrainConfig.motorFL, driveTrainConfig.motorFR, driveTrainConfig.motorBL, driveTrainConfig.motorBR, telemetry, driveTrainConfig.imu);
        wobbleArm = new UltimateGoalArm(driveTrainConfig.wobbleMotor, driveTrainConfig.claw);
        ringDetection = new RingDetection(hardwareMap, this);
        ringDetection.initialize();
        shooter = new UltimateGoalShooter(driveTrainConfig.shooter, driveTrainConfig.pusher, driveTrainConfig.lifterRight, driveTrainConfig.lifterLeft);
        goToSite = new GoToSite(driveTrain, wobbleArm, shooter);

        driveTrain.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        driveTrain.frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveTrain.frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveTrain.backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveTrain.backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Enocoders",  "Starting at " + driveTrain.frontRightMotor.getCurrentPosition() + "," +
                driveTrain.frontLeftMotor.getCurrentPosition() + "," + driveTrain.backRightMotor.getCurrentPosition());
        telemetry.update();

    }

    @Override
    protected void activeLoop() throws InterruptedException {

        switch(currentState){
            case RING_DETECT:

                site = ringDetection.detectRings();
                if (site == RingDetection.Position.ASITE)
                {
                    telemetry.addData("Site",  "A site");
                    telemetry.update();
                    currentState = State.DRIVE_TO_SITE_A;
                }
                else if (site == RingDetection.Position.BSITE)
                {
                    telemetry.addData("Site",  "B site");
                    telemetry.update();
                    currentState = State.DRIVE_TO_SITE_B;
                }
                else
                {
                    telemetry.addData("Site",  "C site");
                    telemetry.update();
                    currentState = State.DRIVE_TO_SITE_C;
                }
                break;

            case DRIVE_TO_SITE_A:
                goToSite.shootBlueSide();
                telemetry.addData("Going to A Site", "Now");
                telemetry.update();
                wobbleArm.CloseClaw();
                goToSite.GoToASite();
                goToSite.dropWobble();
                currentState = State.DONE;
                break;

            case DRIVE_TO_SITE_B:
                telemetry.addData("Going to B Site", "Now");
                telemetry.update();
                goToSite.shootBlueSide();
                goToSite.GoToBSite();
                goToSite.dropWobble();
                driveTrain.strafeRight((float)0.5);
                SleepUtils.sleep(500);
                currentState = State.DONE;
                break;

            case DRIVE_TO_SITE_C:
                telemetry.addData("Going to C Site", "Now");
                telemetry.update();
                goToSite.shootBlueSide();
                goToSite.GoToCSite();
                goToSite.dropWobble();
                currentState = State.PARK;
                break;

            case PARK:
                driveTrain.goBackwardWithColor((float)0.3, driveTrainConfig.colorSensor);
                currentState = State.DONE;
                break;

            case DONE:
                driveTrain.stop();
                getTelemetryUtil().sendTelemetry();
                telemetry.update();
                setOperationsCompleted();
        }
    }
}
