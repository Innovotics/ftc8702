package ftcbootstrap.demos.pushbot.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import ftcbootstrap.ActiveOpMode;
import ftcbootstrap.components.operations.motors.TankDriveToODS;
import ftcbootstrap.components.utils.DriveDirection;
import ftcbootstrap.demos.pushbot.PushBot;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "Pushbot" and creating a class by the same name: {@link PushBot}.
 * <p/>
 * Summary:
 * <p/>
 * Refactored from the original Qualcomm PushBot examples to demonstrate the use of the latest
 * reusable components and operations
 * See:
 * <p/>
 * {@link org.ftcbootstrap.components},
 * <p/>
 * {@link org.ftcbootstrap.components.operations.servos},
 * <p/>
 * {@link org.ftcbootstrap.components.operations.motors}
 * <p/>
 * Also see: {@link PushBot} for the saved configuration
 */

@Autonomous
@Disabled
public class PushBotOdsFollowEvent extends ActiveOpMode {

    private PushBot robot;
    private TankDriveToODS tankDriveToODS;

    private int step;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {
        //specify configuration name save from scan operation
        robot = PushBot.newConfig(hardwareMap, getTelemetryUtil());
        tankDriveToODS = new TankDriveToODS(this, robot.ods, robot.leftDrive, robot.rightDrive);


        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        tankDriveToODS.setName("starting line follow");
        step = 1;
    }
    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *  @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {


        switch (step) {
            case 1:
                double power = 0.1;
                //brightness assumes fixed distance from the target
                //i.e. line follow or stop on white line
                double targetBrightness = 0.5;
                double targetTime = 5;  //seconds
                if (tankDriveToODS.lineFollowForTime( power, targetBrightness, targetTime, DriveDirection.PIVOT_FORWARD_RIGHT)) {
                    step++;
                }
                break;

            default:
                setOperationsCompleted();
                break;
        }

        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();

    }


}
