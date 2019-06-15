package ftcbootstrap.demos.pushbot;

import ftcbootstrap.BootstrapRegistrar;
import ftcbootstrap.demos.TelemetryTest;
import ftcbootstrap.demos.pushbot.opmodes.PushBotAuto;
import ftcbootstrap.demos.pushbot.opmodes.PushBotAutoSensors;
import ftcbootstrap.demos.pushbot.opmodes.PushBotManual;
import ftcbootstrap.demos.pushbot.opmodes.PushBotManual2;
import ftcbootstrap.demos.pushbot.opmodes.PushBotOdsDetectEvent;
import ftcbootstrap.demos.pushbot.opmodes.PushBotOdsFollowEvent;
import ftcbootstrap.demos.pushbot.opmodes.PushBotTouchEvent;


/**
 * Register Op Modes
 */
public class PushBotRegistry extends BootstrapRegistrar {

    protected Class[] getOpmodeClasses() {
        Class[] classes = {

                PushBotAuto.class,
                PushBotAutoSensors.class,
                PushBotManual.class,
                PushBotManual2.class,
                PushBotOdsDetectEvent.class,
                PushBotOdsFollowEvent.class,
                PushBotTouchEvent.class,
                TelemetryTest.class
        };
        return classes;
    }
}
