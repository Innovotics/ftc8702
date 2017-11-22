package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.ftc8702.opmodes.GamePadOmniWheelDrive;
import org.ftcTeam.configurations.Team8702Prod;
import org.ftcTeam.utils.GamePadDuelServo;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.GamePadMotor;

@TeleOp(name = "Team8702Teleop", group = "production")
public class Team8702Teleop extends ActiveOpMode {

    private Team8702Prod robot;
    private GamePadOmniWheelDrive gamePadOmniWheelDrive;
    private GamePadDuelServo gamePadServo;
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
        gamePadOmniWheelDrive.startRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        gamePadServo = new GamePadDuelServo(this, gamepad2, robot.clapperLeft, robot.clapperRight, GamePadDuelServo.Control.X_B, 1.0);
        motorControl = new GamePadMotor(this, gamepad2, robot.clapperMotor, GamePadMotor.Control.UP_DOWN_BUTTONS, 0.1f);

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
        gamePadServo.update();
        motorControl.update();
        //getTelemetryUtil().sendTelemetry();
    }

}
