package org.ftcTeam.opmodes.test;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftcTeam.configurations.Team8702Prod;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;
import org.ftcbootstrap.components.ColorSensorComponent;
import org.ftcTeam.utils.ColorValue;


/**
 * Created by tylerkim on 8/25/17.
 */

public class TazeringAutonomousTest extends ActiveOpMode {

    //Declare the MotorToEncoder
    private Team8702Prod robot;
    private MotorToEncoder motorToEncoderFR;
    private MotorToEncoder motorToEncoderFL;
    private MotorToEncoder motorToEncoderBR;
    private MotorToEncoder motorToEncoderBL;
    int step;

    //Declare sensors
    public ColorSensorComponent colorSensorComponent;


        //colors
        String blue = "blue";
        String red = "red";
        String green = "green";


    @Override
    protected void onInit() {

    //specify configuration name save
        robot = Team8702Prod.newConfig(hardwareMap, getTelemetryUtil());

        // Right Motors
        motorToEncoderFR = new MotorToEncoder( this, robot.motorFR);
        motorToEncoderFR.setName("motorRF");

        motorToEncoderBR = new MotorToEncoder( this, robot.motorBR);
        motorToEncoderBR.setName("motorRB");

        //Left Motors
        motorToEncoderFL = new MotorToEncoder( this, robot.motorFL);
        motorToEncoderFL.setName("motorLF");

        motorToEncoderBL = new MotorToEncoder( this, robot.motorBL);
        motorToEncoderBL.setName("motorLB");

        //Color Sensor
       // colorSensorComponent.enableLed(false);
      //  colorSensorComponent = new ColorSensorComponent(this, robot.ColorSensor1, ColorSensorComponent.ColorSensorDevice.ADAFRUIT);

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        step = 1;

    }

    @Override
    protected void activeLoop() throws InterruptedException {

    getTelemetryUtil().addData("step: " + step, " Current");

        boolean targetReached = false;

        switch(step) {

            case 1: // Go straight for one rotation all four wheels
                targetReached = motorToEncoderBL.runToTarget(1.0, 1240, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);


                if(targetReached) {
                    step ++;
                }
                break;

            case 2:

                setOperationsCompleted();
                break;
        }
    }

    // Color Values
    public ColorValue getColor() {
        int Red = colorSensorComponent.getR();
        int Blue = colorSensorComponent.getB();
        int Green = colorSensorComponent.getG();

        boolean redBoolean = colorSensorComponent.isRed( Red, Blue, Green );

        // test if color is red
        if(redBoolean) {
            return ColorValue.RED;
        }
        boolean blueBoolean = colorSensorComponent.isBlue( Red, Blue, Green );

        //test if color is blue
        if(blueBoolean) {
            return ColorValue.BLUE;
        }
        //zilch color
        return ColorValue.ZILCH;

    }

}
