package org.ftc8702.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;


import ftcbootstrap.ActiveOpMode;

@TeleOp(name = "ServoTest", group = "production")
public class ServoTest extends ActiveOpMode {

    private Servo multiRevServo;
    private CRServo crServo;

    private double position = 1;
    private double increment = 0.00025;
    private double power = 1;

    @Override
    protected void onInit() {
        multiRevServo = (Servo) hardwareMap.get("BigServo");
        crServo = (CRServo) hardwareMap.get("CrServo");
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        if (gamepad2.dpad_down && position > 0) {
            position -= increment;
            multiRevServo.setPosition(position);
            getTelemetryUtil().addData("dpad down position=", position);
            getTelemetryUtil().sendTelemetry();
        }
        else if (gamepad2.dpad_up && position < 1)
        {
            position += increment;
            multiRevServo.setPosition(position);
            getTelemetryUtil().addData("dpad up position=", position);
            getTelemetryUtil().sendTelemetry();
        }

        if (gamepad2.dpad_left) {
            crServo.setPower(power);
            getTelemetryUtil().addData("dpad left power=", crServo.getPower());
            getTelemetryUtil().sendTelemetry();
        }
        else if (gamepad2.dpad_right) {
            crServo.setPower(-1);
            getTelemetryUtil().addData("dpad right power=", crServo.getPower());
            getTelemetryUtil().sendTelemetry();
        }
        else {
            crServo.setPower(0);
        }
    }
}
