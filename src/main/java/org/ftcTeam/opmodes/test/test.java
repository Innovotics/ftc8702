package org.ftcTeam.opmodes.test;

import org.ftcTeam.opmodes.production.GamePadDriveOpMode;
import org.ftcTeam.opmodes.production.GamePadDriveOpModeTeletest;
import org.ftcbootstrap.BootstrapRegistrar;


/**
 * Register Op Modes
 */
public class test extends BootstrapRegistrar {


  protected Class[] getOpmodeClasses() {
    Class[] classes = {

            TanyaTeleopTest.class,
            TazeringTeleopTestFourWheelDrive.class,
            TazeringTeleopTest.class,
            TazeringAutonomousTest.class

    };

    return classes;

  }
}
