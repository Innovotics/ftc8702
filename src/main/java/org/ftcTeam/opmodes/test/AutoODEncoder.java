package org.ftcTeam.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftcTeam.opmodes.utils.BeaconHitter;
import org.ftcTeam.opmodes.utils.ColorValue;
import org.ftcTeam.opmodes.utils.RobotAutonomousUtils;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.TankDriveToEncoder;
import org.ftcbootstrap.components.operations.motors.TankDriveToODS;
import org.ftcbootstrap.components.utils.DriveDirection;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "DemoBot" and creating a class by the same name: {@link Team8702Auto}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;}
 * <p/>
 * Summary:
 * <p/>
 * Opmode demonstrates running a motor from and encoder
 */

@Autonomous
public class AutoODEncoder extends ActiveOpMode {

    private Team8702Auto robot;
    private TankDriveToODS tankDriveToODS;
    private TankDriveToEncoder tankDriveToEncoder;
    private int majorStep;
    ColorValue rainbowValue;

    BeaconHitter firstBeacon;
    BeaconHitter secondBeacon;
    boolean targetReached = false;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        //specify configuration name save from scan operation
        robot = Team8702Auto.newConfig(hardwareMap, getTelemetryUtil());
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
        tankDriveToEncoder = new TankDriveToEncoder(this, robot.motorL, robot.motorR);
        tankDriveToODS = new TankDriveToODS( this, robot.ods, robot.motorL, robot.motorR );
        getTelemetryUtil().sendTelemetry();
        //firstBeacon = new BeaconHitter(getTelemetryUtil(), rainbowValue);
        //secondBeacon = new BeaconHitter(getTelemetryUtil(), rainbowValue);



    }

    @Override
    protected void onStart() throws InterruptedException  {
        super.onStart();
        tankDriveToODS.setName("driving to white line");
        majorStep = 1;

    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *  @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {
        getTelemetryUtil().addData("Current Major Step: ", majorStep);


        //send any telemetry that may have been added in the above operations
        switch(majorStep) {

            case 1: // Go straight towards the beacon
                boolean reachedDestination = false;
                getTelemetryUtil().addData("Current Major Step: ", majorStep);
                // Need to change the motors to run without encoder to use OD
                robot.motorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.motorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                while(!reachedDestination) {
                    reachedDestination =  tankDriveToODS.runToTarget(0.075, 0.5, DriveDirection.DRIVE_FORWARD);
                }
                if( reachedDestination) {
                    majorStep++;
                    getTelemetryUtil().addData("Current Major Step: ", majorStep);
                    getTelemetryUtil().sendTelemetry();
                    RobotAutonomousUtils.pauseMotor(robot.motorR, robot.motorL);
                }
                break;
            case 2: // Run 12 inches
                targetReached = false;
                getTelemetryUtil().addData("Current Major Step: ", majorStep);
                while(!targetReached) {
                    targetReached  = tankDriveToEncoder.runToTarget(0.2, 700 ,
                            DriveDirection.DRIVE_FORWARD,DcMotor.RunMode.RUN_USING_ENCODER);
                }
                RobotAutonomousUtils.pauseMotor(robot.motorR, robot.motorL);
                getTelemetryUtil().sendTelemetry();
                majorStep = 99;

                break;
            case 3: //Turn left towards the beacon
                targetReached = false;
                getTelemetryUtil().addData("Current Major Step: ", majorStep);
                while(!targetReached) {
                    targetReached  = tankDriveToEncoder.runToTarget(0.1, 900 ,
                            DriveDirection.SPIN_RIGHT,DcMotor.RunMode.RUN_USING_ENCODER);
                }
                RobotAutonomousUtils.pauseMotor(robot.motorR, robot.motorL);
                getTelemetryUtil().sendTelemetry();
                majorStep++;

                break;
            case 4: //Go straight to in front of the beacon
                targetReached = false;
                double power = 0.05;
                //brightness assumes fixed distance from the target
                //i.e. line follow or stop on white line
                double targetBrightness = 0.5;
                double targetTime = 5;  //seconds
                getTelemetryUtil().addData("Current Major Step: ", majorStep);
                while(!targetReached) {
                    if (tankDriveToODS.lineFollowForTime( power, targetBrightness, targetTime, DriveDirection.PIVOT_FORWARD_RIGHT)) {
                 //       step++;
                    }
                }
                //majorStep++;
                majorStep = 99;
                break;

            case 99:
                getTelemetryUtil().addData("Running: ", "99: ");
                robot.motorR.setPower(0);
                robot.motorL.setPower(0);
                break;
        }
        getTelemetryUtil().sendTelemetry();
    }
}
