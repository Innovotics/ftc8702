package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class UlitmateGoalLinearActuator {
    public CRServo linearActuator;

    public UlitmateGoalLinearActuator(CRServo linearActuator) {
            this.linearActuator = linearActuator;
    }

}
