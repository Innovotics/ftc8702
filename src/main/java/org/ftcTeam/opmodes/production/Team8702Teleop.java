package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.ftc8702.opmodes.GamePadOmniWheelDrive;
import org.ftcTeam.configurations.production.Team8702Prod;
import org.ftcTeam.configurations.production.Team8702RobotConfig;
import org.ftcTeam.utils.BumperGamePadDuelServo;
import org.ftcTeam.utils.GamePadDuelServo;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.GamePadMotor;
import org.ftcbootstrap.components.operations.servos.GamePadServo;

@TeleOp(name = "Team8702Teleop", group = "production")
public class Team8702Teleop extends ActiveOpMode {

    private Team8702Prod robot;
    private GamePadOmniWheelDrive gamePadOmniWheelDrive;
    private GamePadDuelServo clapperGamePadServoUpper;
    private BumperGamePadDuelServo clapperGamePadServoBottom;
    private GamePadMotor clapperGamePadMotor;
    private GamePadServo clapperGamePadLock;

    @Override
    protected void onInit() {
        robot = Team8702Prod.newConfig(hardwareMap, getTelemetryUtil());

        if (Team8702RobotConfig.ELMO_ON) {
            robot.elmoReach.setPosition(0.95);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            robot.elmoSpin.setPosition(0.0);
        }
        if (Team8702RobotConfig.CLAPPER_ON) {
            robot.clapperExtensionLock.setPosition(.5);
            robot.clapperRight.setPosition(-0.25);
            robot.clapperLeft.setPosition(0.75);
            robot.clapperRightB.setPosition(-0.25);
            robot.clapperLeftB.setPosition(0.75);
        }
        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();

        gamePadOmniWheelDrive = new GamePadOmniWheelDrive(this, gamepad1, robot.motorFL, robot.motorFR, robot.motorBR, robot.motorBL);
        if (Team8702RobotConfig.CLAPPER_ON) {
            clapperGamePadServoUpper = new GamePadDuelServo(this, gamepad2, robot.clapperRight, robot.clapperLeft, GamePadDuelServo.Control.X_B,0.00, true);
            clapperGamePadServoBottom = new BumperGamePadDuelServo(this, gamepad2, robot.clapperRightB, robot.clapperLeftB, BumperGamePadDuelServo.Control.LB_RB, 0.00, true);
            clapperGamePadMotor = new GamePadMotor(this, gamepad2, robot.clapperMotor, GamePadMotor.Control.UP_DOWN_BUTTONS, 0.5f);
            clapperGamePadLock = new GamePadServo(this, gamepad1, robot.clapperExtensionLock, GamePadServo.Control.X_B, 0.9);
        }
    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {
        if (Team8702RobotConfig.MOTOR_ON) {
            gamePadOmniWheelDrive.update();
        }
        if (Team8702RobotConfig.CLAPPER_ON) {
            clapperGamePadServoUpper.update();
            clapperGamePadServoBottom.update();
            clapperGamePadMotor.update();
            clapperGamePadLock.update();
        }
        //getTelemetryUtil().sendTelemetry();
    }
}