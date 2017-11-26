package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.ftc8702.opmodes.GamePadOmniWheelDrive;
import org.ftcTeam.configurations.production.Team8702Prod;
import org.ftcTeam.utils.GamePadDuelServo;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.GamePadMotor;

@TeleOp(name = "Team8702Teleop", group = "production")
public class Team8702Teleop extends ActiveOpMode {

    private Team8702Prod robot;
    private GamePadOmniWheelDrive gamePadOmniWheelDrive;
    private GamePadDuelServo clapperGamePadServo;
    private GamePadMotor clapperGamePadMotor;

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

        gamePadOmniWheelDrive = new GamePadOmniWheelDrive(this, gamepad1, robot.motorFL, robot.motorFR, robot.motorBR, robot.motorBL);
        //gamePadOmniWheelDrive.startRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        clapperGamePadServo = new GamePadDuelServo(this, gamepad2, robot.clapperRight, robot.clapperLeft, GamePadDuelServo.Control.X_B, 0.35);
        clapperGamePadMotor = new GamePadMotor(this, gamepad2, robot.clapperMotor, GamePadMotor.Control.UP_DOWN_BUTTONS, 0.5f);
    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {
        gamePadOmniWheelDrive.update();
        clapperGamePadServo.update();
        clapperGamePadMotor.update();
        //getTelemetryUtil().sendTelemetry();
        checkClapperTouchSensor();
    }

    private void checkClapperTouchSensor()
    {
            // send the info back to driver station using telemetry function.
            // if the digital channel returns true it's HIGH and the button is unpressed.
            if (robot.clapperTouchTop.getState() == true) {
                telemetry.addData("Top Touch Clapper Sensor", "Is Pressed");
                if (gamepad2.y) {
                    robot.clapperMotor.setPower(0);
                }
                else
                {
                    robot.clapperMotor.setPower(0.5);
                }
            }
            else if (robot.clapperTouchBottom.getState() == true){
                telemetry.addData("Bottom Touch Clapper Sensor", "Is Pressed");
                if (gamepad2.a) {
                    robot.clapperMotor.setPower(0);
                }
                else
                {
                    robot.clapperMotor.setPower(0.5);
                }
            }
            telemetry.update();
    }
}
