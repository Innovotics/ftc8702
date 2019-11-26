package ftcbootstrap.demos.onemc;

import ftcbootstrap.BootstrapRegistrar;
import ftcbootstrap.demos.TelemetryTest;


/**
 * Register Op Modes
 */
public class OneMCRegistry extends BootstrapRegistrar {


    protected Class[] getOpmodeClasses() {
        Class[] classes = {

                TelemetryTest.class
        };

        return classes;


    }
}
