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
            AutoRedLauren.class,
            GamePadDriveOpMode.class,
            EncoderMotorOpMode.class,
            AutoODEncoder.class,
            GamePadDriveOpModeTeletest.class
    };

    return classes;

  }
}
