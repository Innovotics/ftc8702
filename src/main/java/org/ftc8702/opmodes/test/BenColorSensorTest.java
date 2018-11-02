package org.ftc8702.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftc8702.configurations.test.BenTestConfig;
import org.ftc8702.utils.ColorValue;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.ColorSensorComponent;

@Autonomous(name = "Test: BenColorSensor", group = "Test")
public class BenColorSensorTest extends ActiveOpMode {

    public ColorSensorComponent colorSensorComponent;
    private BenTestConfig robotConfig;

    @Override
    protected void onInit() {
        // do stuff at initialization stage
        robotConfig = BenTestConfig.newConfig(hardwareMap, getTelemetryUtil());

        //Color Sensor
        colorSensorComponent = new ColorSensorComponent(this, robotConfig.colorSensor, ColorSensorComponent.ColorSensorDevice.MODERN_ROBOTICS_I2C);
        colorSensorComponent.enableLed(false);

        //Telemetry
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        // do what you need to do after initialized
        getTelemetryUtil().addData("Color: ", getColor().name());
        getTelemetryUtil().sendTelemetry();
        getTelemetryUtil().addData("red", Integer.toString(robotConfig.colorSensor.red()));
        getTelemetryUtil().addData("blue", Integer.toString(robotConfig.colorSensor.blue()));
        getTelemetryUtil().addData("green", Integer.toString(robotConfig.colorSensor.green()));
        getTelemetryUtil().addData("red-blue-diff", Integer.toString(Math.abs(robotConfig.colorSensor.red() - robotConfig.colorSensor.blue())));
    }

    public ColorValue getColor() {
        //Helping fix the red color sense correctly, 20 is to offset the color sensor bias toward red.
        int FixRed = 20;

        //Determine which is color to call
        if (robotConfig.colorSensor.red() - robotConfig.colorSensor.blue() > FixRed
                &&(robotConfig.colorSensor.red() -  robotConfig.colorSensor.green()) > FixRed) {
            return ColorValue.RED;
        }

        if (robotConfig.colorSensor.blue() > robotConfig.colorSensor.red() && robotConfig.colorSensor.blue() > robotConfig.colorSensor.green()) {
            return ColorValue.BLUE;
        }

        if (robotConfig.colorSensor.green() > robotConfig.colorSensor.red() && robotConfig.colorSensor.green() > robotConfig.colorSensor.blue()) {
            return ColorValue.GREEN;
        }

        return ColorValue.ZILCH;
    }
}
