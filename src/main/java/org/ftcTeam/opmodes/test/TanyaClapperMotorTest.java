package org.ftcTeam.opmodes.test;

/**
 * Created by tanya_000 on 11/7/2017.
 */

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.ftcTeam.configurations.TanyaClapperMotor;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.GamePadMotor;

@TeleOp(name="TanyaClapperMotorTest", group="test")
public class TanyaClapperMotorTest extends ActiveOpMode {

    //private Team8702Prod robot;
    private TanyaClapperMotor robot;
    private GamePadMotor motorControl;
    // private GamePadFourWheelDrive gamePadFourWheelDrive;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = TanyaClapperMotor.newConfig(hardwareMap, getTelemetryUtil());

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
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
            //if (robot.clapperMotor.getTargetPosition() == 0)
           // {
           //     robot.clapperMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            //}
            //else if (robot.clapperMotor.getTargetPosition() == 1)
           // {
           //     robot.clapperMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            //}
        //update the motors with the gamepad joystick values
        motorControl.update();
        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();
    }

}
