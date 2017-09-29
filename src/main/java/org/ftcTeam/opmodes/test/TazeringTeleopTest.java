package org.ftcTeam.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.ftcTeam.configurations.Team8702Prod;
import org.ftcTeam.configurations.Team8702Servo;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.GamePadTankDrive;
import org.ftcbootstrap.components.operations.servos.GamePadServo;

@TeleOp
public class TazeringTeleopTest extends ActiveOpMode {

    private Team8702Servo robot;
   //  private GamePadTankDrive gamePadTankDrive;
    private GamePadServo gamePadServo;
   // private GamePadFourWheelDrive gamePadFourWheelDrive;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = Team8702Servo.newConfig(hardwareMap, getTelemetryUtil());

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();

        //create the operation  to perform a tank drive using the gamepad joysticks.
      //  gamePadFourWheelDrive = new GamePadFourWheelDrive(this, gamepad1, robot.motorR, robot.motorL, robot.motorBL, robot.motorBR);
      //  gamePadTankDrive = new GamePadTankDrive(this, gamepad1, robot.motorR, robot.motorL);
        gamePadServo = new GamePadServo(this, gamepad1, robot.servo1, GamePadServo.Control.X_B, 1.0);
        gamePadServo = new GamePadServo(this, gamepad1, robot.servo2, GamePadServo.Control.Y_A, 1.0);


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
      //  gamePadTankDrive.update();
        gamePadServo.update();

        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();



    }

}
