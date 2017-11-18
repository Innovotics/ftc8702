package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftc8702.opmodes.GamePadOmniWheelDrive;
import org.ftcTeam.configurations.Team8702Prod;
import org.ftcbootstrap.ActiveOpMode;

@TeleOp(name="Team8702Teleop", group="production")
public class Team8702Teleop extends ActiveOpMode {

    private Team8702Prod robot;
   private GamePadOmniWheelDrive gamePadOmniWheelDrive;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = Team8702Prod.newConfig(hardwareMap, getTelemetryUtil());

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();

    gamePadOmniWheelDrive = new GamePadOmniWheelDrive(this, gamepad1, robot.motorFL, robot.motorFR, robot.motorBR, robot.motorBL);
    gamePadOmniWheelDrive.startRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        //update the motors with the gamepad joystick values
        gamePadOmniWheelDrive.update();
        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();
    }

}
