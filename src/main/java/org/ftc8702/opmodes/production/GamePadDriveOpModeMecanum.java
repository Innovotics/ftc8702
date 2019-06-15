package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.ftc8702.configurations.production.ProdManualRobot;
import org.ftc8702.configurations.production.ProdMecanumRobotConfiguration;
import org.ftc8702.opmodes.GamePadMecanumWheelDrive;
import org.ftc8702.opmodes.GamePadOmniWheelDrive;
import org.ftcTeam.utils.GamePadDuelServo;
import ftcbootstrap.ActiveOpMode;
import ftcbootstrap.components.operations.motors.GamePadMotor;
import ftcbootstrap.components.operations.motors.GamePadTankDrive;
import ftcbootstrap.components.operations.servos.GamePadServo;


/**
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link org.ftcTeam.FTCTeamControllerActivity}
 * <p/>
 * Summary:  Use an Operation class to perform a tank drive using the gamepad joysticks.
 * See: {@link GamePadTankDrive}
 */

@TeleOp(name = "GamePadMecanumOpMode", group = "production")
public class GamePadDriveOpModeMecanum extends ActiveOpMode {

    private ProdMecanumRobotConfiguration robot;
    private GamePadOmniWheelDrive mecanumGamePad;

    //private int encoderLimitingValue = 16000;
    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = ProdMecanumRobotConfiguration.newConfig(hardwareMap, getTelemetryUtil());

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        //create the operation  to perform a tank drive using the gamepad joysticks.
        mecanumGamePad = new GamePadOmniWheelDrive(this, gamepad1, robot.motorFR, robot.motorFL, robot.motorBR, robot.motorBL);
    }
    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws
     */
    @Override
    protected void activeLoop() throws InterruptedException {
        //update the motors with the gamepad joystick values
        mecanumGamePad.update();

        //getTelemetryUtil().addData("Motor to Encoder Value: ", hookMotorToEncoder.motorCurrentPosition());
        getTelemetryUtil().addData("Joystick Power: ", gamepad2.right_stick_y);
    }
}
