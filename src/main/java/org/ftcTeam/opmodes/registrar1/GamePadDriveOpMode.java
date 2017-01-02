package org.ftcTeam.opmodes.registrar1;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.ftcTeam.configurations.Team8702Prod;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.GamePadMotor;
import org.ftcbootstrap.components.operations.motors.GamePadTankDrive;
import org.ftcbootstrap.components.operations.servos.GamePadServo;



/**
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link org.ftcTeam.FTCTeamControllerActivity}
 * <p/>
 * Summary:  Use an Operation class to perform a tank drive using the gamepad joysticks.
 * See: {@link GamePadTankDrive}
 */

@TeleOp
public class GamePadDriveOpMode extends ActiveOpMode {

    private Team8702Prod robot;
    private GamePadTankDrive gamePadTankDrive;
    private GamePadMotor liftGamePad;
    private GamePadMotor intakeGamePad;
    private GamePadServo servoGamePad;
    private GamePadMotor clawGamePad;

    //private GamePadMotor trebuchetGamePad;
    //private GamePadMotor conveyorGamePad;
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

                      //create the operation  to perform a tank drive using the gamepad joysticks.
        gamePadTankDrive = new GamePadTankDrive(this, gamepad1, robot.motorR, robot.motorL);
        liftGamePad = new GamePadMotor(this, gamepad2, robot.liftMotor, GamePadMotor.Control.RIGHT_STICK_Y);
        clawGamePad = new GamePadMotor(this, gamepad2, robot.clawMotor, GamePadMotor.Control.LEFT_STICK_X);
        //trebuchetGamePad =    new GamePadMotor(this, gamepad2, robot.trebuchetMotor, GamePadMotor.Control.RIGHT_BUMPER);
        //conveyorGamePad = new GamePadMotor(this, gamepad2, robot.conveyorMotor, GamePadMotor.Control.A_BUTTON);
        intakeGamePad =       new GamePadMotor(this, gamepad2, robot.IntakeMotor, GamePadMotor.Control.LEFT_STICK_Y);
        double initialPosition = 0.0;
        servoGamePad = new GamePadServo(this, gamepad2, robot.reachingServo, GamePadServo.Control.Y_A, initialPosition);

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
        liftGamePad.update();
        intakeGamePad.update();
       // trebuchetGamePad.update();
       // conveyorGamePad.update();
        servoGamePad.update();
        clawGamePad.update();

        getTelemetryUtil().sendTelemetry();

    }
}
