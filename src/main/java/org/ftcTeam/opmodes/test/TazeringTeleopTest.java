package org.ftcTeam.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.ftcTeam.configurations.Team8702Prod;
import org.ftc8702.opmodes.GamePadOmniWheelDrive;
import org.ftcbootstrap.ActiveOpMode;

@TeleOp
public class TazeringTeleopTest extends ActiveOpMode {

    private Team8702Prod robot;
   //  private GamePadTankDrive gamePadTankDrive;
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

        //create the operation  to perform a tank drive using the gamepad joysticks.
      //  gamePadFourWheelDrive = new GamePadFourWheelDrive(this, gamepad1, robot.motorR, robot.motorL, robot.motorBL, robot.motorBR);
      //  gamePadTankDrive = new GamePadTankDrive(this, gamepad1, robot.motorR, robot.motorL);
    gamePadOmniWheelDrive = new GamePadOmniWheelDrive(this, gamepad1, robot.motorFL, robot.motorFR, robot.motorBR, robot.motorBL);

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
      //  gamePadTankDrive.update();]
        gamePadOmniWheelDrive.update();

        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();



    }

}
