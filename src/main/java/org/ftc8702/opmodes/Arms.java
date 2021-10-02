package org.ftc8702.opmodes;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arms {

    public Servo lifter;
    public Servo armLeft;
    public Servo armRight;

    public Arms(Servo lifter, Servo armLeft, Servo armRight) {
        this.lifter = lifter;
        this.armLeft = armLeft;
        this.armRight = armRight;
    }
}
