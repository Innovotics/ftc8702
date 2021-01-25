package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.utils.SleepUtils;

import ftcbootstrap.ActiveOpMode;

@Autonomous(name = "RedParking", group = "Ops")
public class RedParkingAutonomous extends ActiveOpMode {

    public enum State {
        INIT, SHOOTING, PARK, DONE
    }

    private State currentState;

    private UltimateGoalConfiguration driveTrainConfig;
    private MecanumWheelDriveTrain driveTrain;
    private GoToSite goToSite;
    private Parking parking;
    private UltimateGoalArm wobbleArm;
    private UltimateGoalShooter shooter;
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
        driveTrain = new MecanumWheelDriveTrain(driveTrainConfig.motorFL, driveTrainConfig.motorFR, driveTrainConfig.motorBL, driveTrainConfig.motorBR, telemetry, driveTrainConfig.imu);
        wobbleArm = new UltimateGoalArm(driveTrainConfig.wobbleMotor, driveTrainConfig.claw);
        shooter = new UltimateGoalShooter(driveTrainConfig.shooter, driveTrainConfig.pusher, driveTrainConfig.lifterRight, driveTrainConfig.lifterLeft);
        goToSite = new GoToSite(driveTrain, wobbleArm, shooter);

        wobbleArm.CloseClaw();
        shooter.liftRight2();
        shooter.liftLeft1();
        //shooter.setLiftLeft(0.5);
        //shooter.setLiftRight(0.4);

        currentState = State.SHOOTING;
        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");

    }

    @Override
    public void onStart() throws InterruptedException {

        super.onStart();

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
            case SHOOTING:
                goToSite.shootRedPark();
                currentState = State.PARK;
                break;

            case PARK:
                driveTrain.goBackwardWithColor(0.2f, driveTrainConfig.colorSensor);
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
