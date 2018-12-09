package org.ftc8702.opmodes.production;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.ftc8702.configurations.production.Team8702ProdAuto;
import org.ftc8702.utilities.OrientationUtils;
import org.ftc8702.utilities.TelemetryUtil;

import java.util.List;

public class GyroSensorGetOffHook {
    private Team8702ProdAuto robot;
    //private Telemetry telemetry;
    private TelemetryUtil telemetryUtil;
    private GyroAutoMode gyroMode;
    Orientation angles;
    double currentYawAngle;
    //  private MotorToEncoder hookMotorToEncoder;
    private int LimitingEncoderValue = 14968;
    //2064.51612903225806 per inch


    public TelemetryUtil getTelemetryUtil() {
        return telemetryUtil;
    }

    public GyroSensorGetOffHook(Team8702ProdAuto robot, TelemetryUtil telemetryUtil, GyroAutoMode gyroAutoMode) {
        this.robot = robot;
        this.telemetryUtil = telemetryUtil;
        this.gyroMode = gyroMode;

    }

    public void init() {
        telemetryUtil.addData("Hook Mode", "inited");
        telemetryUtil.sendTelemetry();
    }


    public boolean moveAngle45() throws InterruptedException {
        gyroMode.goLeftAngleCondition(45);
        robot.forwardRobot(0.15);
        robot.sleep(750);
        robot.stopRobot();
        return true;
    }

    //targetReached = hookMotorToEncoder.runToTarget(0.5, LimitingEncoderValue, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
    public boolean Unhook() throws InterruptedException {
        robot.hook.setPower(0.7);
        while(robot.hook.getCurrentPosition() < LimitingEncoderValue) {

            getTelemetryUtil().addData("Encoder Value: ", robot.hook.getCurrentPosition());

            getTelemetryUtil().sendTelemetry();
        }

        robot.hook.setPower(0.0);

        robot.stopRobot();
        robot.sleep(500);
        return true;
    }

    }