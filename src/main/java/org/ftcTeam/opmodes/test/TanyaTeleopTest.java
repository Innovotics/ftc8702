package org.ftcTeam.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.ftcTeam.configurations.Team8702Clapper;
import org.ftcTeam.utils.GamePadDuelServo;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.GamePadMotor;

@TeleOp(name="TanyaTeleopTest", group="test")
public class TanyaTeleopTest extends ActiveOpMode {

    //private Team8702Prod robot;
    private Team8702Clapper robot;
    private GamePadDuelServo gamePadServo;
    private GamePadMotor motorControl;
   // private GamePadFourWheelDrive gamePadFourWheelDrive;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = Team8702Clapper.newConfig(hardwareMap, getTelemetryUtil());

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();

        gamePadServo = new GamePadDuelServo(this, gamepad1, robot.clapperLeft, robot.clapperRight, GamePadDuelServo.Control.X_B, 1.0);
        motorControl = new GamePadMotor(this, gamepad1, robot.clapperMotor, GamePadMotor.Control.UP_DOWN_BUTTONS, 1);

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
        gamePadServo.update();
        motorControl.update();

        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();



    }

}
