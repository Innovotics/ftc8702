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
    private GamePadMotor gamePadMotor;
    private GamePadServo gamePadPlowServo;

//    private GamePadCRServo gamePadServo;
//    private GamePadMotor gamePadTransformingMotor;

    private MotorToEncoder hookMotorToEncoder;
    private GamePadEncoderMotor gamePadHookMotor;
    private int encoderLimitingValue = 16000;
    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = ProdManualRobot.newConfig(hardwareMap, getTelemetryUtil());

        hookMotorToEncoder = new MotorToEncoder(this, robot.hook);
        hookMotorToEncoder.setName(InnovoticsRobotProperties.MOTOR_HOOK);

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().addData("Motor Encoder Value: ", hookMotorToEncoder.motorCurrentPosition());

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        //create the operation  to perform a tank drive using the gamepad joysticks.
        gamePadTankDrive = new GamePadTankDrive(this, gamepad1, robot.motorR, robot.motorL);
        gamePadMotor = new GamePadMotor(this, gamepad1, robot.hook, GamePadMotor.Control.UP_DOWN_BUTTONS);
//        gamePadServo = new GamePadCRServo(this, gamepad2, robot.intakeSystem, GamePadCRServo.Control.Y_A,0.0);
        //gamePadMotor = new GamePadMotor(this, gamepad2, robot.belt, GamePadMotor.Control.LB_RB_BUTTONS);
//        gamePadTransformingMotor = new GamePadMotor(this, gamepad2, robot.transformingMotor, GamePadMotor.Control.RIGHT_STICK_X);

        //motor encoder
        gamePadHookMotor = new GamePadEncoderMotor(this, gamepad2, robot.hook, GamePadEncoderMotor.Control.RIGHT_STICK_Y, hookMotorToEncoder.motorCurrentPosition(), hookMotorToEncoder);
        gamePadPlowServo = new GamePadServo(this, gamepad2, robot.plow, GamePadServo.Control.Y_A, 0);


        gamePadHookMotor.startRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        gamePadHookMotor.startRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
       gamePadMotor.update();
//       gamePadServo.update();
       gamePadHookMotor.update(hookMotorToEncoder.motorCurrentPosition());
//       gamePadTransformingMotor.update();
        gamePadPlowServo.update();

        getTelemetryUtil().addData("Motor to Encoder Value: ", hookMotorToEncoder.motorCurrentPosition());

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
            stopOnEncoderValue();

            getTelemetryUtil().sendTelemetry();
            telemetry.update();
    }

    private void stopOnEncoderValue() {
        if(hookMotorToEncoder.motorCurrentPosition() > encoderLimitingValue || hookMotorToEncoder.motorCurrentPosition() < 0) {
            robot.hook.setPower(0.0);
            getTelemetryUtil().addData("Robot Stopped", hookMotorToEncoder.motorCurrentPosition());

        }
    }


}
