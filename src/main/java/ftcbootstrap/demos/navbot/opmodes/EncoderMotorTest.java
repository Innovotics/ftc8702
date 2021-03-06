package ftcbootstrap.demos.navbot.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import ftcbootstrap.ActiveOpMode;
import ftcbootstrap.components.operations.motors.MotorToEncoder;
import ftcbootstrap.components.utils.MotorDirection;
import ftcbootstrap.demos.navbot.NavBot;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "NavBot" and creating a class by the same name: {@link NavBot}.
 * Summary:
 * <p/>
 *
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

@Autonomous
@Disabled
public class EncoderMotorTest extends ActiveOpMode {

    private NavBot robot;

    private MotorToEncoder leftMotorToEncoder;
    private MotorToEncoder rightMotorToEncoder;
    private int step;
    private int stepCounter;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        //specify configuration name save from scan operation
        robot = NavBot.newConfig(hardwareMap, getTelemetryUtil());

        leftMotorToEncoder = new MotorToEncoder(  this, robot.leftDrive);
        leftMotorToEncoder.setName("left runToTarget");
        rightMotorToEncoder = new MotorToEncoder( this, robot.rightDrive);
        rightMotorToEncoder.setName("right runToTarget");
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException  {
        super.onStart();
        step = 1;

        //telemetry rows written for everthing added ( not grouped by key)
        getTelemetryUtil().setSortByTime(true);
    }


    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *  @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        // RUN ONE MOTOR but OBSERVE encoder positions on both
        switch (step) {
            case 1:
                handleSingleMotorOperation(1, 2000, MotorDirection.MOTOR_FORWARD);
                break;

            case 2:
                handleSingleMotorOperation(1, 1000, MotorDirection.MOTOR_BACKWARD);
                break;


            case 3:
                handleSingleMotorOperation(1, 4000, MotorDirection.MOTOR_BACKWARD);
                break;

            case 4:
                handleSingleMotorOperation(1, 2000, MotorDirection.MOTOR_FORWARD);
                break;


            case 5:
                handleSingleMotorOperation(1, 6000, MotorDirection.MOTOR_FORWARD);
                break;



            default:
                setOperationsCompleted();
                getTelemetryUtil().addData("OperationsCompleted Left ", robot.leftDrive.getCurrentPosition());
                getTelemetryUtil().addData("OperationsCompleted Right", robot.rightDrive.getCurrentPosition());

                break;

        }


        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();


    }

    private void handleSingleMotorOperation(int power, int encoderDistance, MotorDirection motorDirection) throws InterruptedException {

        boolean targetReached = false;

        stepCounter++;
        if ( stepCounter == 1) {
            //  getTelemetryUtil().addData("step" + step + " A: Left Position", robot.getLeftDrive().getCurrentPosition());
        }
        if (stepCounter == 5 ) {
            stepCounter = 0;
        }

        targetReached =  leftMotorToEncoder.runToTarget(power, encoderDistance,
                motorDirection, DcMotor.RunMode.RUN_USING_ENCODER);

        if (targetReached) {
            getTelemetryUtil().addData("step" + step + " A: Left target reached ", robot.leftDrive.getCurrentPosition());
            getTelemetryUtil().addData("step" + step + " A: Right Position", robot.rightDrive.getCurrentPosition());
            step++;
            stepCounter = 0;
        }

    }


}
