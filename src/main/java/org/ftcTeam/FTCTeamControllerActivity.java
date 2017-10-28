package org.ftcTeam;

import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;
import org.ftcTeam.opmodes.test.test;


import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;


//TODO Remove this class since ftc_app uses annotation

public class FTCTeamControllerActivity extends FtcRobotControllerActivity {

  @Override
  protected OpModeRegister createOpModeRegister() {

     // return new test();
    // return new Production();
    // return new Prototype();
     return new test();
   //return null;

  }

}
