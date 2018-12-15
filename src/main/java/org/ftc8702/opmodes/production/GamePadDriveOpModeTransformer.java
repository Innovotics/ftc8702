package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.ftc8702.configurations.production.ProdManualRobot;
import org.ftc8702.configurations.production.ProdManualTransformerRobot;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.GamePadMotor;
import org.ftcbootstrap.components.operations.motors.GamePadTankDrive;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.operations.servos.GamePadServo;


/**
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link org.ftcTeam.FTCTeamControllerActivity}
 * <p/>
 * Summary:  Use an Operation class to perform a tank drive using the gamepad joysticks.
 * See: {@link GamePadTankDrive}
 */

@TeleOp(name = "GamePadDriveOpModeTransformer", group = "production")
public class GamePadDriveOpModeTransformer extends ActiveOpMode {

    private ProdManualTransformerRobot robot;
    private GamePadTankDrive gamePadTankDrive;

    private GamePadMotor gamePadPrimaryMotor;
    private GamePadMotor gamePadSecondaryMotor;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = ProdManualTransformerRobot.newConfig(hardwareMap, getTelemetryUtil());

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        //create the operation  to perform a tank drive using the gamepad joysticks.
        gamePadTankDrive = new GamePadTankDrive(this, gamepad1, robot.motorR, robot.motorL);
        gamePadPrimaryMotor = new GamePadMotor(this, gamepad1, robot.primaryArm, GamePadMotor.Control.RIGHT_STICK_X);
        gamePadSecondaryMotor = new GamePadMotor(this, gamepad1, robot.secondaryArm, GamePadMotor.Control.LEFT_STICK_X);

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
       gamePadTankDrive.update();
       gamePadPrimaryMotor.update();
        gamePadSecondaryMotor.update();


        getTelemetryUtil().addData("Joystick Power: ", gamepad2.right_stick_y);


            getTelemetryUtil().sendTelemetry();
            telemetry.update();
    }



}
