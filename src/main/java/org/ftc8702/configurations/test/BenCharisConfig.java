package org.ftc8702.configurations.test;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.ftc8702.utils.InnovoticsRobotProperties;
import org.ftcbootstrap.RobotConfiguration;
import org.ftcbootstrap.components.utils.TelemetryUtil;

public class BenCharisConfig  extends RobotConfiguration {

    public ColorSensor colorSensorFrontLeft;
    public ColorSensor colorSensorFrontRight;
    public ColorSensor colorSensorBackLeft;
    public ColorSensor colorSensorBackRight;

    public DcMotor motorL;
    public DcMotor motorR;

    @Override
    protected void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        setTelemetry(telemetryUtil);

        //colorSensorFrontLeft = (ColorSensor) getHardwareOn(InnovoticsRobotProperties.COLOR_SENSOR_FRONT_LEFT, hardwareMap.colorSensor);
        //getTelemetryUtil().addData("Left Front Color component: ", colorSensorFrontLeft.toString());

        //colorSensorFrontRight = (ColorSensor) getHardwareOn(InnovoticsRobotProperties.COLOR_SENSOR_FRONT_RIGHT, hardwareMap.colorSensor);
        //getTelemetryUtil().addData("Right Front Color component: ", colorSensorFrontRight.toString());

        colorSensorBackRight = (ColorSensor) getHardwareOn(InnovoticsRobotProperties.COLOR_SENSOR_BACK_RIGHT, hardwareMap.colorSensor);
        getTelemetryUtil().addData("Right Back Color component: ", colorSensorBackRight.toString());

        colorSensorBackLeft = (ColorSensor) getHardwareOn(InnovoticsRobotProperties.COLOR_SENSOR_BACK_LEFT, hardwareMap.colorSensor);
        getTelemetryUtil().addData("Left Back Color component: ", colorSensorBackLeft.toString());

        // Front Motors
        motorR = (DcMotor) getHardwareOn(InnovoticsRobotProperties.MOTOR_RIGHT, hardwareMap.dcMotor);
        motorL = (DcMotor) getHardwareOn(InnovoticsRobotProperties.MOTOR_LEFT, hardwareMap.dcMotor);

        getTelemetryUtil().sendTelemetry();
    }

    public static BenCharisConfig newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        BenCharisConfig config = new BenCharisConfig();
        config.init(hardwareMap, telemetryUtil);
        return config;
    }
}
