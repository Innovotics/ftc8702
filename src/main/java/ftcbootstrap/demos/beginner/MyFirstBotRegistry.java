package ftcbootstrap.demos.beginner;

import ftcbootstrap.BootstrapRegistrar;
import ftcbootstrap.demos.TelemetryTest;
import ftcbootstrap.demos.beginner.opmodes.OpMode1RunForTime;
import ftcbootstrap.demos.beginner.opmodes.OpMode3RunForTime;
import ftcbootstrap.demos.beginner.opmodes.OpMode4RunUntilTouch;
import ftcbootstrap.demos.beginner.opmodes.OpMode5StateMachine;
import ftcbootstrap.demos.beginner.opmodes.OpMode6DriveWithGamepad;
import ftcbootstrap.demos.beginner.opmodes.OpMode7ServoWithGamepad;


/**
 * Register Op Modes
 */
public class MyFirstBotRegistry extends BootstrapRegistrar {


  protected Class[] getOpmodeClasses() {
    Class[] classes = {

            OpMode1RunForTime.class,
            OpMode3RunForTime.class,
            OpMode4RunUntilTouch.class,
            OpMode5StateMachine.class,
            OpMode6DriveWithGamepad.class,
            OpMode7ServoWithGamepad.class,
            TelemetryTest.class
    };

    return classes;
  }


}
