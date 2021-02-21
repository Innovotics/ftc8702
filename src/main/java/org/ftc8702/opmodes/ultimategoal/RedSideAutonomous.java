package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.utils.SleepUtils;

import ftcbootstrap.ActiveOpMode;

@Autonomous(name = "Red Side ultimate goal", group = "Ops")
public class RedSideAutonomous extends ActiveOpMode {

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
    private  UltimateGoalIntake intake;

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
        driveTrain = new MecanumWheelDriveTrain(driveTrainConfig.motorFL, driveTrainConfig.motorFR, driveTrainConfig.motorBL, driveTrainConfig.motorBR, telemetry, driveTrainConfig.imu);
        wobbleArm = new UltimateGoalArm(driveTrainConfig.wobbleMotor, driveTrainConfig.claw);
        shooter = new UltimateGoalShooter(driveTrainConfig.shooter, driveTrainConfig.pusher, driveTrainConfig.lifterRight, driveTrainConfig.lifterLeft);
        goToSite = new GoToSite(driveTrain, wobbleArm, shooter);
        intake = new UltimateGoalIntake(driveTrainConfig.intakeLeft, driveTrainConfig.intakeRight);

        wobbleArm.CloseClaw();
        //shooter.setLiftLeft(0.4);
        //shooter.setLiftRight(0.5);

        shooter.liftLeft2();
        shooter.liftRight1();

        currentState = State.RING_DETECT;
        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");

    }

    @Override
    public void onStart() throws InterruptedException {

        super.onStart();
        ringDetection = new RingDetection(hardwareMap, this);
        ringDetection.initialize();

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
                goToSite.shootRedSide();
                telemetry.addData("Going to A Site", "Now");
                telemetry.update();
                wobbleArm.CloseClaw();
                goToSite.GoToASiteRed();
                goToSite.dropWobbleSlow();
                driveTrain.strafeRight(1);
                SleepUtils.sleep(500);
                //goToSite.GoToASiteSecond();
                /*
                driveTrain.strafeRight(0.4f);
                SleepUtils.sleep(300);
                driveTrain.goForward(0.4f);
                SleepUtils.sleep(500);
                 */
                currentState = State.DONE;
                break;

            case DRIVE_TO_SITE_B:
                telemetry.addData("Going to B Site", "Now");
                telemetry.update();
                goToSite.shootRedSide();
                goToSite.GoToBSiteRed();
                goToSite.dropWobbleSlow();
                driveTrain.strafeRight(0.4f);
                SleepUtils.sleep(400);
                currentState = State.PARK;
                break;

            case DRIVE_TO_SITE_C:
                telemetry.addData("Going to C Site", "Now");
                telemetry.update();
                goToSite.shootRedSide();
                goToSite.GoToCSiteRed();
                goToSite.dropWobble();
                driveTrain.strafeRight(0.8f);
                SleepUtils.sleep(1000);
                driveTrain.rotateLeftWithGyro(0.3f, 0);
                driveTrain.goBackwardWithColor(0.3f, driveTrainConfig.colorSensor);
                /*
                shooter.liftRight2();
                shooter.liftLeft1();
                intake.intake();
                goToSite.goToCSiteRedSecond();
                goToSite.goToCSiteRedThird();
                intake.stop();
                driveTrain.rotateRightWithGyro(0.3f, 0);
                shooter.liftLeft2();
                shooter.liftRight1();
                goToSite.shootRedSideSecond();
                driveTrain.goForward(0.3f);
                SleepUtils.sleep(400);
                 */
                currentState = State.DONE;
                break;

            case PARK:
                driveTrain.goBackwardWithColor((float)0.2, driveTrainConfig.colorSensor);
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
