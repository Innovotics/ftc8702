//package org.ftcTeam.opmodes.test;
//
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.ftcTeam.configurations.Team8702Prod;
//import org.ftcbootstrap.ActiveOpMode;
//import org.ftcbootstrap.components.operations.servos.GamePadServo;
//
//@TeleOp(name="TazeringElmoTest", group="test")
//@Disabled
//public class TazeringElmoTest extends ActiveOpMode {
//
//    private Team8702Prod robot;
//    private GamePadServo gamePadServo;
//
//    /**
//     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
//     */
//    @Override
//    protected void onInit() {
//
//        robot = Team8702Prod.newConfig(hardwareMap, getTelemetryUtil());
//
//        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
//        //the Driver station via the sendTelemetry command
//        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
//        getTelemetryUtil().sendTelemetry();
//
//    }
//
//    @Override
//    protected void onStart() throws InterruptedException {
//        super.onStart();
//
//        //create the operation  to perform a tank drive using the gamepad joysticks.
//      //  gamePadFourWheelDrive = new GamePadFourWheelDrive(this, gamepad1, robot.motorR, robot.motorL, robot.motorBL, robot.motorBR);
//      //  gamePadTankDrive = new GamePadTankDrive(this, gamepad1, robot.motorR, robot.motorL);
//        gamePadServo = new GamePadServo(this, gamepad1, robot.elmoReach, GamePadServo.Control.X_B, 1.0);
//
//
//    }
//    /**
//     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
//     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
//     *
//     * @throws InterruptedException
//     */
//    @Override
//    protected void activeLoop() throws InterruptedException {
//
//        //update the motors with the gamepad joystick values
//      //  gamePadTankDrive.update();
//        gamePadServo.update();
//
//        //send any telemetry that may have been added in the above operations
//        getTelemetryUtil().sendTelemetry();
//
//
//
//    }
//
//}
