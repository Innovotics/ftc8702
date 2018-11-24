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
    private GamePadCRServo gamePadServo;
    private GamePadMotor gamePadTransformingMotor;

    private MotorToEncoder slideMotorToEncoder;
    private GamePadEncoderMotor gamePadSliderMotor;
    private int encoderLimitingValue = 3600;
    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = ProdManualRobot.newConfig(hardwareMap, getTelemetryUtil());

        slideMotorToEncoder = new MotorToEncoder(this, robot.slideExtender);
        slideMotorToEncoder.setName(InnovoticsRobotProperties.LINEAR_SLIDE_ENXTENSION);






        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().addData("Motor Encoder Value: ", slideMotorToEncoder.motorCurrentPosition());

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        //create the operation  to perform a tank drive using the gamepad joysticks.
        gamePadTankDrive = new GamePadTankDrive(this, gamepad1, robot.motorR, robot.motorL);
        gamePadMotor = new GamePadMotor(this, gamepad1, robot.hook, GamePadMotor.Control.UP_DOWN_BUTTONS);
        gamePadServo = new GamePadCRServo(this, gamepad2, robot.intakeSystem, GamePadCRServo.Control.Y_A,0.0);
        //gamePadMotor = new GamePadMotor(this, gamepad2, robot.belt, GamePadMotor.Control.LB_RB_BUTTONS);
        gamePadTransformingMotor = new GamePadMotor(this, gamepad2, robot.transformingMotor, GamePadMotor.Control.RIGHT_STICK_X);

        //motor encoder
        gamePadSliderMotor = new GamePadEncoderMotor(this, gamepad2, robot.slideExtender, GamePadEncoderMotor.Control.RIGHT_STICK_Y, slideMotorToEncoder.motorCurrentPosition(), slideMotorToEncoder);

        gamePadSliderMotor.startRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        gamePadSliderMotor.startRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
       gamePadServo.update();
       gamePadSliderMotor.update(slideMotorToEncoder.motorCurrentPosition());
       gamePadTransformingMotor.update();

        getTelemetryUtil().addData("Motor to Encoder Value: ", slideMotorToEncoder.motorCurrentPosition());

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
        if(slideMotorToEncoder.motorCurrentPosition() > encoderLimitingValue || slideMotorToEncoder.motorCurrentPosition() < 0) {
            robot.slideExtender.setPower(0.0);
            getTelemetryUtil().addData("Robot Stopped", slideMotorToEncoder.motorCurrentPosition());

        }
    }


}
