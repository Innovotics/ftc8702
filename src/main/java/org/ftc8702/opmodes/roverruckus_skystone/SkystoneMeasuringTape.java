package org.ftc8702.opmodes.roverruckus_skystone;

import com.qualcomm.robotcore.hardware.CRServo;

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
