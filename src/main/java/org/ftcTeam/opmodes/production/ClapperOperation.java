package org.ftcTeam.opmodes.production;

import org.ftcTeam.configurations.production.Team8702ProdAuto;

/**
 * Created by Madhav on 12/14/17.
 */

public class ClapperOperation {
    final AbstractAutoMode abstractAutoMode;

    final Team8702ProdAuto robot;


    private final double CLAPPER_MOTOR_UP_POWER = 0.4;
    private final double CLAPPER_MOTOR_DOWN_POWER = -0.4;
    private final double CLAPPER_MOTOR_STOP_POWER = 0.0;

    private final long CLAPPER_MOTOR_RUNTIME = 500;

    private final double CLAPPER_RIGHT_B_INIT_POS = 0.0;
    private final double CLAPPER_RIGHT_B_GRAB_POS = 0.6;

    private final double CLAPPER_LEFT_B_INIT_POS = 0.75;
    private final double CLAPPER_LEFT_B_GRAB_POS = 0.2;

    public ClapperOperation(AbstractAutoMode abstractAutoMode){

        this.abstractAutoMode = abstractAutoMode;
        this.robot = this.abstractAutoMode.getRobot();

    }

    public void initClapperRightAndLeftMotor(){
        robot.clapperRightB.setPosition(CLAPPER_RIGHT_B_INIT_POS);
        robot.clapperLeftB.setPosition(CLAPPER_LEFT_B_INIT_POS);
    }

    public void grabGlyph(){
        robot.clapperRightB.setPosition(CLAPPER_RIGHT_B_GRAB_POS);
        robot.clapperLeftB.setPosition(CLAPPER_LEFT_B_GRAB_POS);
    }

    public void liftGlyph() throws InterruptedException{
        robot.clapperMotor.setPower(CLAPPER_MOTOR_UP_POWER);
        Thread.sleep(CLAPPER_MOTOR_RUNTIME);
        robot.clapperMotor.setPower(CLAPPER_MOTOR_STOP_POWER);
    }

    public boolean dropGlyph() throws InterruptedException {

        robot.clapperMotor.setPower(CLAPPER_MOTOR_DOWN_POWER);
        Thread.sleep(CLAPPER_MOTOR_RUNTIME);
        robot.clapperMotor.setPower(CLAPPER_MOTOR_STOP_POWER);
        Thread.sleep(CLAPPER_MOTOR_RUNTIME);

        initClapperRightAndLeftMotor();
        return true;
    }

}
