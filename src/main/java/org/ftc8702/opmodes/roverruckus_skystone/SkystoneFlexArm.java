package org.ftc8702.opmodes.roverruckus_skystone;

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
        SliderArmRight.setPower(scaledpower);
        SliderArmLeft.setPower(-scaledpower);
    }
    public void ArmDown(float scaledpower)
    {
        SliderArmRight.setPower(-scaledpower);
        SliderArmLeft.setPower(scaledpower);
    }
    public void stop()
    {
        SliderArmLeft.setPower(0);
        SliderArmRight.setPower(0);
    }

}
