package org.ftcTeam.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.ftcTeam.configurations.Team8702Elmo;
import org.ftcbootstrap.ActiveOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="TazeringElmoServerTest", group="test")
@Disabled
public class TazeringElmoServoTest extends ActiveOpMode {

    //Declare the robot
    private Team8702Elmo robot;

    //Declare Elmo Servo
    private Servo elmoSpin;
    private Servo elmoReach;

    @Override
    protected void onInit() {

    //specify configuration name save
        robot = Team8702Elmo.newConfig(hardwareMap, getTelemetryUtil());

        elmoSpin.setPosition(0.0);

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();

    }

    @Override
    protected void activeLoop() throws InterruptedException {
    }
}
