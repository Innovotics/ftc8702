package org.ftcTeam.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.ftcTeam.configurations.Team8702Prod;
import org.ftc8702.opmodes.GamePadOmniWheelDrive;
import org.ftcTeam.utils.GamePadDuelServo;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.GamePadMotor;

@TeleOp(name="TazeringTeleopTest", group="test")

public class TazeringTeleopTest extends ActiveOpMode {
    private Team8702Prod robot;
   private GamePadOmniWheelDrive gamePadOmniWheelDrive;
   private GamePadDuelServo gamePadDuelServo;
   private GamePadMotor motorControl;

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
    gamePadDuelServo = new GamePadDuelServo(this, gamepad1, robot.clapperLeft, robot.clapperRight, GamePadDuelServo.Control.X_B, 1);
    motorControl = new GamePadMotor(this, gamepad1, robot.clapperMotor, GamePadMotor.Control.RIGHT_STICK_Y);

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
        gamePadDuelServo.update();
        motorControl.update();
        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();
    }

}
