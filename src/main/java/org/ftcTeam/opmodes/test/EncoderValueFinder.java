package org.ftcTeam.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftcTeam.configurations.production.Team8702Prod;
import ftcbootstrap.ActiveOpMode;
import ftcbootstrap.components.operations.motors.MotorToEncoder;
import ftcbootstrap.components.utils.MotorDirection;


@Autonomous(name = "AutoModeTest", group = "Ops")
@Disabled
public class EncoderValueFinder extends ActiveOpMode {

    // 1 rotation for 40 neverest motor is 1140

    //Declare the MotorToEncoder
    private Team8702Prod robot;

    private MotorToEncoder motorToEncoderFL;


    @Override
    protected void onInit() {
        robot = Team8702Prod.newConfig(hardwareMap, getTelemetryUtil());

        motorToEncoderFL = new MotorToEncoder(this, robot.motorFL);


        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
    }

    @Override
    protected void activeLoop() throws InterruptedException {


        //Show Telemetry
        motorToEncoderFL.runToTarget(0.25, 1140, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_TO_POSITION);
        getTelemetryUtil().addData("Encoder Value", motorToEncoderFL.motorCurrentPosition());
        getTelemetryUtil().sendTelemetry();
        sleep(2000);




    }
}