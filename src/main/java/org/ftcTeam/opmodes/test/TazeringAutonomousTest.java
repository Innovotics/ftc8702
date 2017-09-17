package org.ftcTeam.opmodes.test;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftcTeam.configurations.Team8702Prod;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;

/**
 * Created by tylerkim on 8/25/17.
 */

public class TazeringAutonomousTest extends ActiveOpMode {

    private Team8702Prod robot;

    private MotorToEncoder motorToEncoder;
    int step;

    @Override
    protected void onInit() {

    //specify configuration name save
        robot = Team8702Prod.newConfig(hardwareMap, getTelemetryUtil());

        motorToEncoder = new MotorToEncoder( this, robot.motorR);
        motorToEncoder.setName("motorR");

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        step = 1;

    }

    @Override
    protected void activeLoop() throws InterruptedException {

    getTelemetryUtil().addData("step: " + step, " Current");

        boolean targetReached = false;

        switch(step) {
            case 1:
                targetReached = motorToEncoder.runToTarget(1.0, 1240, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
                targetReached = motorToEncoder.runToTarget(1.0, 1240, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
                if(targetReached) {
                    step ++;
                }
                break;
            case 2:
                stop();

        }
    }
}
