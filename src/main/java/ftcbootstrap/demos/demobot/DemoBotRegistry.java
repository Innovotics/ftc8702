package ftcbootstrap.demos.demobot;

import ftcbootstrap.BootstrapRegistrar;
import ftcbootstrap.demos.TelemetryTest;
import ftcbootstrap.demos.demobot.opmodes.DemoBotAdvancedOpMode;
import ftcbootstrap.demos.demobot.opmodes.DemoBotOpMode1;
import ftcbootstrap.demos.demobot.opmodes.DemoBotTeleOpMode;
import ftcbootstrap.demos.demobot.opmodes.EncoderMotorOpMode;
import ftcbootstrap.demos.demobot.opmodes.EncoderTankDriveOpMode;

/**
 * Register Op Modes
 */
public class DemoBotRegistry extends BootstrapRegistrar {


    protected Class[] getOpmodeClasses() {
        Class[] classes = {

                DemoBotOpMode1.class,
                DemoBotTeleOpMode.class,
                EncoderMotorOpMode.class,
                EncoderTankDriveOpMode.class,
                DemoBotAdvancedOpMode.class,
                TelemetryTest.class
        };

        return classes;

    }
}
