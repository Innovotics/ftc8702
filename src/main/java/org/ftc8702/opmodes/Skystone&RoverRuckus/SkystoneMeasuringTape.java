package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

public class SkystoneMeasuringTape {

    // class variable, property, attribute
    public CRServo measuringTape;

    public SkystoneMeasuringTape(CRServo measuringTape) {
        this.measuringTape = measuringTape;
    }

    // methods
    void TapeOut()
    {
        measuringTape.setPower(1);
    }
   void TapeIn()
   {
       measuringTape.setPower(-1);
   }
   void TapeStop()
   {
       measuringTape.setPower(0);
   }
}
