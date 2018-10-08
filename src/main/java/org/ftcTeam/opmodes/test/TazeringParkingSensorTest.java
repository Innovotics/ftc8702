package org.ftcTeam.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftcTeam.configurations.test.Team8702ParkingSensor;
import org.ftcTeam.utils.ColorValue;
import org.ftcTeam.utils.RobotProperties;
import org.ftcbootstrap.ActiveOpMode;


/**
 * Created by tylerkim on 8/25/17.
 */


@Autonomous(name = "Test: Parking", group = "Test")
public class TazeringParkingSensorTest extends ActiveOpMode {

    //Declare the MotorToEncoder
    private Team8702ParkingSensor robot;

   // private ParkingColorSensor parkingColorSensor;
    @Override
    protected void onInit() {

        robot = Team8702ParkingSensor.newConfig(hardwareMap, getTelemetryUtil());
        //parkingColorSensor = new ParkingColorSensor(robot.parkingColorSensor);

        //Color Sensor
        robot.parkingColorSensor = hardwareMap.colorSensor.get(RobotProperties.COLOR_PARKING);

        //Telemetry
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();

    }

    @Override
    protected void activeLoop() throws InterruptedException {

        //getTelemetryUtil().addData("step: " + step, " Current");
        //getTelemetryUtil().addData("Color: ", parkingColorSensor.parkingOn(ColorValue.BLUE));
        getTelemetryUtil().sendTelemetry();
        getTelemetryUtil().addData("red", Integer.toString(robot.parkingColorSensor.red()));
        getTelemetryUtil().addData("blue", Integer.toString(robot.parkingColorSensor.blue()));
        getTelemetryUtil().addData("green", Integer.toString(robot.parkingColorSensor.green()));
        getTelemetryUtil().sendTelemetry();
    }

    // Color Values
    // TODO - Refactor this

}
