package org.ftc8702.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import ftcbootstrap.ActiveOpMode;

@TeleOp(name = "TankDriving", group = "production")
public class GamePadTankDrive extends ActiveOpMode {

    public DcMotor motorFR;
    public DcMotor motorFL;
    public DcMotor motorBR;
    public DcMotor motorBL;
    public Telemetry telemetry;

    @Override
    protected void onInit() {
        telemetry.addData("Tank drive", "ready :D");
        telemetry.update();
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        tankDrive();
    }

    public void tankDrive()
    {
        float throttle = -gamepad1.right_stick_x;
        float direction = -gamepad1.left_stick_y;
        
        float FR = throttle + direction;
        float FL = throttle - direction;
        float BR = throttle + direction;
        float BL = throttle - direction;

        FR = Range.clip(FR, -1, 1);
        FL = Range.clip(FL, -1, 1);
        BR = Range.clip(BR, -1, 1);
        BL = Range.clip(BL, -1, 1);

        if(gamepad1.right_bumper){
            motorFL.setPower(-0.2);
            motorFR.setPower(-0.2);
            motorBL.setPower(-0.2);
            motorBR.setPower(-0.2);
        }else if(gamepad1.left_bumper){
            motorFL.setPower(0.2);
            motorFR.setPower(0.2);
            motorBL.setPower(0.2);
            motorBR.setPower(0.2);
        } else{
            motorFR.setPower(FR);
            motorFL.setPower(FL);
            motorBR.setPower(BR);
            motorBL.setPower(BL);
        }
    }
}
