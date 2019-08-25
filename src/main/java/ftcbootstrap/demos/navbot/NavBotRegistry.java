package ftcbootstrap.demos.navbot;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorMRColor;

import ftcbootstrap.BootstrapRegistrar;
import ftcbootstrap.demos.TelemetryTest;
import ftcbootstrap.demos.navbot.opmodes.EncoderMotorTest;
import ftcbootstrap.demos.navbot.opmodes.EncoderTestFromGamepadButtons;
import ftcbootstrap.demos.navbot.opmodes.EncoderTestFromGamepadSticks;
import ftcbootstrap.demos.navbot.opmodes.EncodersWithEventOpmode;
import ftcbootstrap.demos.navbot.opmodes.NavBotManual;
import ftcbootstrap.demos.navbot.opmodes.NavBotTelemetry;
import ftcbootstrap.demos.navbot.opmodes.NavigateWithEncoders;

/**
 * Register Op Modes
 */
public class NavBotRegistry extends BootstrapRegistrar {


    protected Class[] getOpmodeClasses() {
        Class[] classes = {

                NavigateWithEncoders.class,
                NavBotManual.class,
                EncodersWithEventOpmode.class,
                EncoderMotorTest.class,
                EncoderTestFromGamepadSticks.class,
                EncoderTestFromGamepadButtons.class,
                NavBotTelemetry.class,
                SensorMRColor.class,
                TelemetryTest.class
        };

        return classes;

    }
}
