package org.ftc8702.utilities;

import com.qualcomm.robotcore.hardware.DcMotor;

import ftcbootstrap.ActiveOpMode;
import ftcbootstrap.components.utils.MotorDirection;


/**
 * Operation to assist with running a DcMotor with the encoder
 */

public class MotorToEncoder{

    private DcMotor motor;
    private int targetEncoderDistance;
    private int startingEncoderPosition;
    private boolean running = false;
    //defaulted but can be changed in the app
    private int encoderResetThreshold = 3;
    private int encoderTargetThreshold = 3;

    private Double rampingUp;
    private static double INITIAL_RAMP_UP_POWER = 0.1;
    private static double RAMP_UP_PERCENTAGE = 0.01;

    private ActiveOpMode opMode;


    /**
     * Constructor for operation
     * Telemetry enabled by default.
     *
     * @param opMode
     * @param motor
     */
    public MotorToEncoder(ActiveOpMode opMode, DcMotor motor) {

        this.opMode = opMode;
        this.motor = motor;
        try {
            this.resetEncoder();
        } catch (InterruptedException e) {
            opMode.getTelemetryUtil().addData("exception on resetting encoder", e.getMessage());
        }
    }




    /**
     * @param power
     * @param targetEncoderDistance
     * @param direction             {@link MotorDirection}
     * @param runMode  {@Link DcMotor.RunMode}
     * @return boolean target reached
     * @throws InterruptedException
     */
    public boolean runToTarget(double power,
                               int targetEncoderDistance,
                               MotorDirection direction,
                               DcMotor.RunMode runMode)
            throws InterruptedException {


        if (this.isRunning()) {


            opMode.getTelemetryUtil().addData("a1" + ": ",  power);

            if ( rampingUp != null) {
                // getOpMode().getTelemetryUtil().addData("a2" + ": ", rampingUp.doubleValue());
                //getOpMode().getTelemetryUtil().addData("a3" + ": ",  (power - INITIAL_RAMP_UP_POWER) / RAMP_UP_PERCENTAGE);

                rampingUp  +=  (power - INITIAL_RAMP_UP_POWER) * RAMP_UP_PERCENTAGE;
                if ( rampingUp >=  power) {
                    rampingUp = null;
                }
                else {
                    double rampingUpPowerWithDirection = (direction == MotorDirection.MOTOR_FORWARD) ? rampingUp: -rampingUp;
                    motor.setPower(rampingUpPowerWithDirection);
                }
            }

            boolean reached = this.targetReached();
            if (reached ) {
                stop();
            }
            return reached;
        }


        if ( power >= 0.3 ) {
            rampingUp = INITIAL_RAMP_UP_POWER;
            power = rampingUp;
        }

        if ( runMode !=  motor.getMode()) {

            motor.setMode(runMode);
            opMode.waitOneFullHardwareCycle();
        }


        running = true;
        this.targetEncoderDistance = targetEncoderDistance;
        this.startingEncoderPosition =  motorCurrentPosition();

        double powerWithDirection = (direction == MotorDirection.MOTOR_FORWARD) ? power : -power;

        if (isRunToPosition()) {
            int targetDirectionFactor = powerWithDirection >= 0 ? 1 : -1;
            int newTarget = startingEncoderPosition + (targetEncoderDistance * targetDirectionFactor);
            motor.setTargetPosition(newTarget);
        }

        motor.setPower(powerWithDirection);

        return false;

    }

    /**
     * check if current encoder position is close to target
     * (choosing within targetEncoderValue of 3 as"close enough")
     *
     * @return boolean target reached
     */
    public boolean targetReached() {

        boolean reached = false;

        if (motor != null) {
            int position = Math.abs( motorCurrentPosition() - this.startingEncoderPosition);

            if (position >=
                    (this.targetEncoderDistance - encoderTargetThreshold)) {
                reached = true;

            }
        }

        return reached;

    }


    /**
     * Stop the motor
     */
    public void stop() {

        running = false;
        if ( ! isRunToPosition()) {
            motor.setPower(0);
        }
        else {
            motor.setTargetPosition(motorCurrentPosition());
        }

    }

    /**
     * Check if the operation is running.   Do this before calling runToTarget()
     *
     * @return
     */
    public boolean isRunning() {
        return running;
    }


    private void resetEncoder() throws InterruptedException {

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        while (Math.abs(motorCurrentPosition()) > encoderResetThreshold) {
            opMode.waitForNextHardwareCycle();
        }

    }

    public int motorCurrentPosition() {
        return motor.getCurrentPosition();
    }





    /**
     * set the threshold to measure whether the encoder is fully reset
     * fully reset usuall implies the that the motor position is zero but that may be off
     * from time to time due to noise.
     *
     * @param encoderResetThreshold
     */
    public void setEncoderResetThreshold(int encoderResetThreshold) {
        this.encoderResetThreshold = encoderResetThreshold;
    }

    /**
     * set the threshold to measure whether the encoder is close to the target position
     *
     * @param encoderTargetThreshold
     */
    public void setEncoderTargetThreshold(int encoderTargetThreshold) {
        this.encoderTargetThreshold = encoderTargetThreshold;
    }


    public boolean isRunToPosition() {
        return motor.getMode() == DcMotor.RunMode.RUN_TO_POSITION;
    }



}
