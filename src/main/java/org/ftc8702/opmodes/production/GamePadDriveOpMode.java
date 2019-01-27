package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftc8702.components.motors.GamePadEncoderMotor;
import org.ftc8702.components.servo.GamePadCRServo;
import org.ftc8702.configurations.production.ProdManualRobot;
import org.ftc8702.utils.InnovoticsRobotProperties;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.GamePadTankDrive;
import org.ftcbootstrap.components.operations.motors.GamePadMotor;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.operations.servos.GamePadServo;


/**
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link org.ftcTeam.FTCTeamControllerActivity}
 * <p/>
 * Summary:  Use an Operation class to perform a tank drive using the gamepad joysticks.
 * See: {@link GamePadTankDrive}
 */

@TeleOp(name = "GamePadDriveOpMode", group = "production")
public class GamePadDriveOpMode extends ActiveOpMode {

    private ProdManualRobot robot;
    private GamePadTankDrive gamePadTankDrive;
    private GamePadMotor gamePadHookMotor;
    private GamePadMotor gamePadShortArm;
    private GamePadMotor gamePadLongArm;

    private GamePadServo gamePadClawA;
    private GamePadServo gamePadClawB;
   private int encoderLimitingValue;
    //private int encoderLimitingValue = 16000;
    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = ProdManualRobot.newConfig(hardwareMap, getTelemetryUtil());

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        //create the operation  to perform a tank drive using the gamepad joysticks.
        gamePadTankDrive = new GamePadTankDrive(this, gamepad1, robot.motorR, robot.motorL);
        gamePadHookMotor = new GamePadMotor(this, gamepad2, robot.hook, GamePadMotor.Control.UP_DOWN_BUTTONS);
        gamePadShortArm = new GamePadMotor (this, gamepad2, robot.shortArm, GamePadMotor.Control.RIGHT_STICK_Y);
        gamePadLongArm = new GamePadMotor (this, gamepad2, robot.longArm, GamePadMotor.Control.LEFT_STICK_Y);

        gamePadClawA = new GamePadServo(this, gamepad1, robot.clawA, GamePadServo.Control.Y_A, 0.0);
        gamePadClawB = new GamePadServo(this, gamepad1, robot.clawB, GamePadServo.Control.X_B, 0.0);
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
       gamePadHookMotor.update();
       gamePadShortArm.update();
       gamePadLongArm.update();

       gamePadClawA.update();
       gamePadClawB.update();

        //getTelemetryUtil().addData("Motor to Encoder Value: ", hookMotorToEncoder.motorCurrentPosition());

        getTelemetryUtil().addData("Joystick Power: ", gamepad2.right_stick_y);

//       Runnable r = new Runnable() {
//           @Override
//           public void run() {
//               stopOnEncoderValue();
//           }
//       };
//       new Thread(r).start();
//        getTelemetryUtil().sendTelemetry();
//
//    }
//
            // stopOnEncoderValue();

            getTelemetryUtil().sendTelemetry();
            telemetry.update();
    }

//    private void stopOnEncoderValue() {
//        if(hookMotorToEncoder.motorCurrentPosition() > encoderLimitingValue || hookMotorToEncoder.motorCurrentPosition() < 0) {
//            robot.hook.setPower(0.0);
//            getTelemetryUtil().addData("Robot Stopped", hookMotorToEncoder.motorCurrentPosition());
//
//        }
//    }


}
