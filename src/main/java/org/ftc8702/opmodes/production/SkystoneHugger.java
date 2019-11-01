package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import ftcbootstrap.ActiveOpMode;
public class SkystoneHugger {

    public Servo huggerTop;
    public Servo huggerBottom;

    public SkystoneHugger(Servo huggerTop, Servo huggerBottom) {
        this.huggerTop = huggerTop;
        this.huggerBottom = huggerBottom;
    }

    void HuggerBottomDown (float power) {
        huggerBottom.setDirection(Servo.Direction.FORWARD);
        huggerBottom.setPosition(power);
    }

    void HuggerBottomUp (float power) {
        huggerBottom.setDirection(Servo.Direction.REVERSE);
        huggerBottom.setPosition(-power);
    }

    void HuggerTopDown (float power) {
        huggerTop.setDirection(Servo.Direction.REVERSE);//Used to be forward
        huggerTop.setPosition(power);
    }

    void HuggerTopUp (float power) {
        huggerTop.setDirection(Servo.Direction.FORWARD);//Used to be reversed
        huggerTop.setPosition(power);
    }

    }
