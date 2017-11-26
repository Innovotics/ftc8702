package org.ftcTeam.opmodes.prototype;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.ftcTeam.configurations.production.Team8702Prod;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcTeam.utils.ColorValue;
import org.ftcbootstrap.components.operations.motors.GamePadTankDrive;
import org.ftcbootstrap.components.ColorSensorComponent;


/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "DemoBot" and creating a class by the same name: {@link Team8702Prod}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;}
 * <p/>
 * Summary:
 * <p/>
 * Opmode demonstrates running a motor from and encoder
 */


@Autonomous(name="EncoderModtorOpModeWithColor", group="prototype")
@Disabled
public class EncoderMotorOpModeWithColor extends ActiveOpMode {

    private Team8702Prod robot;

    private MotorToEncoder motorToEncoderR;
    private MotorToEncoder motorToEncoderL;
    private int step;
    private GamePadTankDrive gamePadTankDrive;
    String blue = "blue";
    String red = "red";
    String green = "green";
    public ColorSensorComponent colorSensorComponent;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        //specify configuration name save from scan operation
        robot = Team8702Prod.newConfig(hardwareMap, getTelemetryUtil());
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
        //
        //
        // colorSensorComponent = new ColorSensorComponent(this, robot.mrColor1, ColorSensorComponent.ColorSensorDevice.MODERN_ROBOTICS_I2C);
        colorSensorComponent.enableLed(false);

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();


    }

    @Override
    protected void onStart() throws InterruptedException  {
        super.onStart();
        step = 1;
    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *  @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {
        getTelemetryUtil().addData("step: " + step, "current");
        boolean targetReached = false;
//
//        getTelemetryUtil().addData("red", Integer.toString(robot.mrColor1.red()));
//        getTelemetryUtil().addData("blue", Integer.toString(robot.mrColor1.blue()));
//        getTelemetryUtil().addData("green", Integer.toString(robot.mrColor1.green()));
//        getTelemetryUtil().addData("clear", Integer.toString(robot.mrColor1.alpha()));
        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();
        getTelemetryUtil().addData("Color", getColor().toString());
    }


    public ColorValue getColor() {
        int Red = colorSensorComponent.getR();
        int Blue = colorSensorComponent.getB();
        int Green = colorSensorComponent.getG();

        boolean redboolean = colorSensorComponent.isRed(Red, Blue, Green);

        if (redboolean) {
            return ColorValue.RED;
        }
        boolean blueboolean = colorSensorComponent.isBlue(Red, Blue, Green);

        if (blueboolean) {
            return ColorValue.BLUE;
        }
        return ColorValue.ZILCH;
        //send any telemetry that may have been added in the above operations


    }


}


