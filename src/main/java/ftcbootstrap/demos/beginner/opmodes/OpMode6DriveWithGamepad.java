package ftcbootstrap.demos.beginner.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import ftcbootstrap.ActiveOpMode;
import ftcbootstrap.components.operations.motors.GamePadTankDrive;
import ftcbootstrap.demos.beginner.MyFirstBot;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "MyFirstBot" and creating a class by the same name: {@link MyFirstBot}.

 * Summary:  Use an Operation class to perform a tank drive using the gamepad joysticks.
 * See: {@link GamePadTankDrive}
 */

@TeleOp
@Disabled
public class OpMode6DriveWithGamepad extends ActiveOpMode {

    private MyFirstBot robot;
    private GamePadTankDrive gamePadTankDrive;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = MyFirstBot.newConfig(hardwareMap, getTelemetryUtil());

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        //create the operation  to perform a tank drive using the gamepad joysticks.
        gamePadTankDrive = new GamePadTankDrive(this,gamepad1, robot.motor1, robot.motor2);
        gamePadTankDrive.setOpModeLogLevel(1);

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

        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();


    }

}
