package org.ftcTeam.opmodes.production;

import org.ftcTeam.configurations.production.Team8702ProdAuto;

/**
 * Created by Madhav on 12/14/17.
 */

public class ClapperOperation {
    final AbstractAutoMode abstractAutoMode;

    final Team8702ProdAuto robot;

    public ClapperOperation(AbstractAutoMode abstractAutoMode){

        this.abstractAutoMode = abstractAutoMode;
        this.robot = this.abstractAutoMode.getRobot();

    }

    public void init(){
        robot.clapperRightB.setPosition(-0.25);
        robot.clapperLeftB.setPosition(0.75);
        //robot.clapperMotor.setTargetPosition(0);
    }

    public void grabGlyph(){
        robot.clapperRightB.setPosition(0.0);
        robot.clapperLeftB.setPosition(0.5);
    }

    public void liftGlyph(){
        robot.clapperMotor.setTargetPosition(1);
    }
}
