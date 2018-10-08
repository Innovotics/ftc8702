package org.ftc8702.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftc8702.opmodes.configurations.test.BenTestConfig;
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
        colorSensorComponent = new ColorSensorComponent(this, robotConfig.colorSensor, ColorSensorComponent.ColorSensorDevice.ADAFRUIT);
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
    }

    public ColorValue getColor() {
        int Red = colorSensorComponent.getR();
        int Blue = colorSensorComponent.getB();
        int Green = colorSensorComponent.getG();

        //Boolean Values
        boolean redBoolean = colorSensorComponent.isRed(Red, Blue, Green);
        boolean blueBoolean = colorSensorComponent.isBlue(Red, Blue, Green);
        boolean greenBoolean = colorSensorComponent.isGreen(Red, Blue, Green);

        //Determine which is color to call

        if (robotConfig.colorSensor.red() > robotConfig.colorSensor.blue() && robotConfig.colorSensor.red() > robotConfig.colorSensor.green()) {
            redBoolean = true;
        }

        if (robotConfig.colorSensor.blue() > robotConfig.colorSensor.red() && robotConfig.colorSensor.blue() > robotConfig.colorSensor.red()) {
            blueBoolean = true;
        }

        if (robotConfig.colorSensor.green() > robotConfig.colorSensor.red() && robotConfig.colorSensor.green() > robotConfig.colorSensor.blue()) {
            greenBoolean = true;
        }

        if (redBoolean) {
            return ColorValue.RED;
        } else if (blueBoolean) {
            return ColorValue.BLUE;
        } else if (greenBoolean) {
            return ColorValue.GREEN;
        }
        return ColorValue.ZILCH;
    }
}
