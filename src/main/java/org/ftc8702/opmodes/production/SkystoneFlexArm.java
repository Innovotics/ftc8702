package org.ftc8702.opmodes.production;

import com.qualcomm.hardware.motors.TetrixMotor;
import com.qualcomm.robotcore.hardware.DcMotor;

public class SkystoneFlexArm {

    public DcMotor SliderArmLeft;
    public DcMotor SliderArmRight;

    public SkystoneFlexArm(DcMotor SliderArmLeft, DcMotor SliderArmRight) {
        this.SliderArmLeft = SliderArmLeft;
        this.SliderArmRight = SliderArmRight;
    }

    public void ArmUp(float scaledpower)
    {
        SliderArmRight.setPower(1);
        SliderArmLeft.setPower(1);
    }
    public void ArmDown(float scaledpower)
    {
        SliderArmRight.setPower(-1);
        SliderArmLeft.setPower(-1);
    }
    public void stop(float scaledpower)
    {
        SliderArmLeft.setPower(0);
        SliderArmRight.setPower(0);
    }

}
