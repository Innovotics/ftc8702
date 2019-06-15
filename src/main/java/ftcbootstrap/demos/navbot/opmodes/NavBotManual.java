package ftcbootstrap.demos.navbot.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import ftcbootstrap.ActiveOpMode;
import ftcbootstrap.components.operations.motors.GamePadMotor;
import ftcbootstrap.components.operations.motors.GamePadTankDrive;
import ftcbootstrap.components.operations.servos.GamePadClaw;
import ftcbootstrap.components.operations.servos.GamePadServo;
import ftcbootstrap.demos.navbot.NavBot;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "NavBot" and creating a class by the same name: {@link NavBot}.
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
 * Also see: {@link NavBot} for the saved configuration
 */

@TeleOp
@Disabled
public class NavBotManual extends ActiveOpMode {

    private GamePadTankDrive tankDrive;
    private GamePadMotor armMotorStick;
    private GamePadClaw claw;

    private NavBot robot;


    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = NavBot.newConfig(hardwareMap, getTelemetryUtil());

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();

        //set up tank drive operation to use the gamepad joysticks
        tankDrive = new GamePadTankDrive(this, gamepad1, robot.leftDrive, robot.rightDrive);
        tankDrive.startRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //set up arm motor operation using the up/down buttons
        armMotorStick = new GamePadMotor(this, gamepad1, robot.arm, GamePadMotor.Control.UP_DOWN_BUTTONS);

        //operate the claw with GamePadServo.Control. Use the X and B buttons for up and down and the  X and B buttons for left and right
        claw = new GamePadClaw(this, gamepad1, robot.leftClaw, robot.rightClaw, GamePadServo.Control.X_B, 0.8);

    }


    @Override
    protected void activeLoop() throws InterruptedException {

        tankDrive.update();
        armMotorStick.update();
        claw.update();

        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();

    }


}
