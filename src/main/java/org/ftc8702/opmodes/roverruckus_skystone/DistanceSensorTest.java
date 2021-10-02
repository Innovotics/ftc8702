/*
Copyright (c) 2018 FIRST

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of FIRST nor the names of its contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.ftc8702.opmodes.roverruckus_skystone;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.ftc8702.utils.InnovoticsRobotProperties;

import ftcbootstrap.RobotConfiguration;
import ftcbootstrap.components.utils.TelemetryUtil;

//@TeleOp(name = "Sensor: REV2mDistance", group = "Sensor")

public class DistanceSensorTest extends LinearOpMode {

    private DistanceSensor sensorRange;

    @Override
    public void runOpMode() {
        // you can use this as a regular DistanceSensor.
        sensorRange = hardwareMap.get(DistanceSensor.class, "sensor_range");

        // you can also cast this to a Rev2mDistanceSensor if you want to use added
        // methods associated with the Rev2mDistanceSensor class.
        Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor)sensorRange;

        telemetry.addData(">>", "Press start to continue");
        telemetry.update();

        waitForStart();
        while(opModeIsActive()) {
            // generic DistanceSensor methods.
            telemetry.addData("deviceName",sensorRange.getDeviceName() );
            //telemetry.addData("range", String.format("%.01f cm", sensorRange.getDistance(DistanceUnit.CM)));

            // Rev2mDistanceSensor specific methods.
            telemetry.addData("ID", String.format("%x", sensorTimeOfFlight.getModelID()));
            telemetry.addData("did time out", Boolean.toString(sensorTimeOfFlight.didTimeoutOccur()));

            telemetry.update();
        }
    }

    /**
     * FTCTeamRobot Saved Configuration
     * <p/>
     * It is assumed that there is a configuration on the phone running the Robot Controller App with the same name as this class and
     * that  configuration is the one that is marked 'activated' on the phone.
     * It is also assumed that the device names in the 'init()' method below are the same  as the devices named for the
     * saved configuration on the phone.
     */
    public static class SkystoneRobotConfiguration extends RobotConfiguration {
        //51.4 = 1 inch
        //mecanum motors
        public DcMotor motorFR;
        public DcMotor motorFL;
        public DcMotor motorBR;
        public DcMotor motorBL;
        public DcMotor SliderArmLeft;
        public DcMotor SliderArmRight;
        public DcMotor IntakeWheelLeft;
        public DcMotor IntakeWheelRight;
        public BNO055IMU imu;



        public static SkystoneRobotConfiguration newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

            SkystoneRobotConfiguration config = new SkystoneRobotConfiguration();
            config.init(hardwareMap, telemetryUtil);
            return config;
        }


        @Override
        protected void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

            setTelemetry(telemetryUtil);

            //Mecanum Motors
            motorFR = (DcMotor) getHardwareOn(InnovoticsRobotProperties.MOTOR_FR, hardwareMap.dcMotor);
            motorFL = (DcMotor) getHardwareOn(InnovoticsRobotProperties.MOTOR_FL, hardwareMap.dcMotor);
            motorBR = (DcMotor) getHardwareOn(InnovoticsRobotProperties.MOTOR_BR, hardwareMap.dcMotor);
            motorBL = (DcMotor) getHardwareOn(InnovoticsRobotProperties.MOTOR_BL, hardwareMap.dcMotor);

            //Arm Motors
            SliderArmRight = (DcMotor) getHardwareOn(InnovoticsRobotProperties.SLIDER_ARM_RIGHT, hardwareMap.dcMotor);
            SliderArmLeft = (DcMotor) getHardwareOn(InnovoticsRobotProperties.SLIDER_ARM_LEFT, hardwareMap.dcMotor);

            //Intake Motors
            IntakeWheelLeft = (DcMotor) getHardwareOn(InnovoticsRobotProperties.INTAKE_WHEEL_LEFT, hardwareMap.dcMotor);
            IntakeWheelRight = (DcMotor) getHardwareOn(InnovoticsRobotProperties.INTAKE_WHEEL_RIGHT, hardwareMap.dcMotor);

            imu = hardwareMap.get(BNO055IMU.class, "imu");

        }
    }
}