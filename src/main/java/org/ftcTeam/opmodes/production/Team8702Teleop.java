package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.ftc8702.opmodes.GamePadOmniWheelDrive;
import org.ftcTeam.configurations.production.Team8702Prod;
import org.ftcTeam.configurations.production.Team8702RobotConfig;
import org.ftcTeam.utils.BumperGamePadDuelServo;
import org.ftcTeam.utils.GamePadDuelServo;
import ftcbootstrap.ActiveOpMode;
import ftcbootstrap.components.operations.motors.GamePadMotor;
import ftcbootstrap.components.operations.servos.GamePadServo;

@TeleOp(name = "Team8702Teleop", group = "production")
@Disabled
public class Team8702Teleop extends ActiveOpMode {

    private Team8702Prod robot;

    private GamePadOmniWheelDrive gamePadOmniWheelDrive;

    // save the duelServo for future use
    //private GamePadDuelServo clapperGamePadServoUpper;

    private GamePadMotor clapperGamePadMotor;
    private GamePadServo clapperGamePadLock;

    @Override
    protected void onInit() {
        robot = Team8702Prod.newConfig(hardwareMap, getTelemetryUtil());
        robot.servoTest.setPosition(0.75);

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();

        gamePadOmniWheelDrive = new GamePadOmniWheelDrive(this, gamepad1, robot.motorFL, robot.motorFR, robot.motorBR, robot.motorBL);

        // keep these servo related gamepad controls for future use since we only have 1 servo (servoTest) defined here
        // clapperGamePadServoUpper = new GamePadDuelServo(this, gamepad2, robot.servoTest, robot.clapperRight, GamePadDuelServo.Control.X_B,0.00, true);
        // clapperGamePadMotor = new GamePadMotor(this, gamepad2, robot.clapperMotor, GamePadMotor.Control.UP_DOWN_BUTTONS, 0.5f);
        // clapperGamePadLock = new GamePadServo(this, gamepad1, robot.clapperExtensionLock, GamePadServo.Control.X_B, 0.9);
    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {
        gamePadOmniWheelDrive.update();

        // keep for future use
        /*
        clapperGamePadServoUpper.update();
        clapperGamePadMotor.update();
        clapperGamePadLock.update();
        */
    }
}