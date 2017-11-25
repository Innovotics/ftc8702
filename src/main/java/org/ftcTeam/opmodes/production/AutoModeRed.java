package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftcTeam.configurations.Team8702ProdAuto;
import org.ftcTeam.utils.ColorValue;
import org.ftcTeam.utils.EncoderBasedOmniWheelController;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;


@Autonomous(name = "AutoModeRed", group = "Ops")
public class AutoModeRed extends AbstractAutoMode{

    //Set color to Red
    protected ColorValue getPanelColor() {

        return ColorValue.RED;
    }

}
