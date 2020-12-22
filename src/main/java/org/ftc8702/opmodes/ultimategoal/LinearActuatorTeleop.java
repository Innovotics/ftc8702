package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;

import ftcbootstrap.ActiveOpMode;

@TeleOp(name = "LinearActuator", group = "production")
public class LinearActuatorTeleop extends ActiveOpMode {

    private UlitmateGoalLinearActuator linearActuator;
    private LinearActuatorConfig linearActuatorConfig;


    @Override
    protected void onInit() {
        linearActuatorConfig = LinearActuatorConfig.newConfig(hardwareMap, getTelemetryUtil());
    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        linearActuator = new UlitmateGoalLinearActuator(linearActuatorConfig.linearActuator);
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        if(gamepad1.b)
        {
            linearActuator.linearActuator.setDirection(DcMotorSimple.Direction.FORWARD);
            linearActuator.linearActuator.setPower(1);
        }
        else if(gamepad1.x)
        {
            linearActuator.linearActuator.setPower(0.1);
        }
        else
        {
            linearActuator.linearActuator.setPower(-1);
        }
    }
}
