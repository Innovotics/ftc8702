package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.configurations.production.OdometerRobotConfiguration;

import ftcbootstrap.ActiveOpMode;

@Autonomous(name = "OdometerAutonomous", group = "Ops")
public class OdometerAutonomous extends ActiveOpMode {

    private OdometerRobotConfiguration driveTrainConfig;
    private MecanumWheelDriveTrain driveTrain;

    // odometer 1446 ticks = 4.7 inches (1 circumference = 1.5 inch diameter * pi = 4.7 inches)
    int targetLeftValue = 1446;
    int targetRightValue = 1446;
    int targetCenterValue = 0;


    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    public void onInit() {

        driveTrainConfig = OdometerRobotConfiguration.newConfig(hardwareMap, getTelemetryUtil());

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");

    }

    @Override
    public void onStart() throws InterruptedException {
        super.onStart();
        driveTrain = new MecanumWheelDriveTrain(driveTrainConfig.motorFL, driveTrainConfig.motorFR, driveTrainConfig.motorBL, driveTrainConfig.motorBR, telemetry);

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
        //driveTrain.goForwardWithOdometer(1000,1000,0.1);
    }

    @Override
    protected void activeLoop() throws InterruptedException {

        // driveTrain.goForwardWithOdometer(targetLeftValue,targetRightValue,0.2);
        driveTrain.goForwardByInches(24.0,0.4);

        telemetry.addData("Enocoders",  "Starting at  right: " + driveTrain.frontRightMotor.getCurrentPosition() + ", left: " +
                driveTrain.frontLeftMotor.getCurrentPosition() + ", middle: " + driveTrain.backRightMotor.getCurrentPosition() + ", difference: "+ (driveTrain.frontRightMotor.getCurrentPosition() - -driveTrain.frontLeftMotor.getCurrentPosition()));
        telemetry.update();
        //driveTrain.goForward((float) 0.5);
    }
}
