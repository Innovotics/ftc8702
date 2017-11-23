//package org.ftcTeam.opmodes.production;
//
///**
// * Created by tylerkim on 11/5/17.
// */
//import android.graphics.Color;
//
//import org.ftcTeam.configurations.Team8702Prod;
//import org.ftcbootstrap.components.ColorSensorComponent;
//import org.ftcTeam.utils.ColorValue;
//import org.ftcbootstrap.ActiveOpMode;
//
//public class AdaFruitColorSensor {
//
//    //Declare Robot
//    private Team8702ProdAuto robot;
//
//    //Declare Color Sensor
//    public ColorSensorComponent colorSensorComponent;
//
//    public AdaFruitColorSensor(ActiveOpMode opmode, Team8702Prod robot) {
//        this.robot = robot;
//        colorSensorComponent = new ColorSensorComponent(opmode, robot.elmoColorSensor, ColorSensorComponent.ColorSensorDevice.ADAFRUIT);
//    }
//
//    public ColorValue getColor() {
//        ColorValue resultColor = ColorValue.ZILCH;
//
//        //Determine which is color to call
//        if (robot.elmoColorSensor.red() > robot.elmoColorSensor.blue()
//                && robot.elmoColorSensor.red() > robot.elmoColorSensor.green()) {
//            resultColor = ColorValue.RED;
//        }
//        else if (robot.elmoColorSensor.blue() > robot.elmoColorSensor.red()
//                && robot.elmoColorSensor.green() > robot.elmoColorSensor.red()) {
//            resultColor = ColorValue.BLUE;
//        }
//        return resultColor;
//    }
//}
