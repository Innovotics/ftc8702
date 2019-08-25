package ftcbootstrap.demos.onemc.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import ftcbootstrap.ActiveOpMode;
import ftcbootstrap.components.operations.motors.GamePadTankDrive;
import ftcbootstrap.demos.onemc.OneMCBot;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "NavBot" and creating a class by the same name: {@link OneMCBot}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in
 * Summary: Use a single  gamepad's joysticks to Tank Drive, to operate Arm with up/down buttons and
 * operate the claw with left/right buttons
 * <p/>
 * See:
 * <p/>
 * {@link org.ftcbootstrap.components},
 * <p/>
 * {@link org.ftcbootstrap.components.operations.servos},
 * <p/>
 * {@link org.ftcbootstrap.components.operations.motors}
 * <p/>
 * Also see: {@link OneMCBot} for the saved configuration
 */

@TeleOp
@Disabled
public class OneMCTeleOp extends ActiveOpMode {

    private GamePadTankDrive tankDrive;

    private OneMCBot robot;


    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = OneMCBot.newConfig(hardwareMap, getTelemetryUtil());

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();

        //set up tank drive operation to use the gamepad joysticks
        tankDrive = new GamePadTankDrive(this, gamepad1, robot.leftDrive, robot.rightDrive);
        tankDrive.startRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }


    @Override
    protected void activeLoop() throws InterruptedException {

        tankDrive.update();

        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();

    }


}
