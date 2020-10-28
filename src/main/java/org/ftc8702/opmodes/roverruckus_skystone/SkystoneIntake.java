package org.ftc8702.opmodes.roverruckus_skystone;

import com.qualcomm.robotcore.hardware.DcMotor;

public class SkystoneIntake {

    public DcMotor IntakeWheelLeft;
    public DcMotor IntakeWheelRight;

    public SkystoneIntake(DcMotor IntakeWheelLeft, DcMotor IntakeWheelRight) {
        this.IntakeWheelLeft = IntakeWheelLeft;
        this.IntakeWheelRight = IntakeWheelRight;
    }

    public void Intake(float scaledpower)
    {
        IntakeWheelLeft.setPower(-1);
        IntakeWheelRight.setPower(1);
    }
    public void Output(float scaledpower)
    {
        IntakeWheelLeft.setPower(1);
        IntakeWheelRight.setPower(-1);
    }
    public void pushFoundation ()
    {
        IntakeWheelLeft.setPower(1);
        IntakeWheelRight.setPower(1);

    }
    public void pullFoundation ()
    {
        IntakeWheelRight.setPower(-1);
        IntakeWheelLeft.setPower(-1);
    }
    public void stop(float scaledpower)
    {
        IntakeWheelLeft.setPower(0);
        IntakeWheelRight.setPower(0);
    }
}
