package org.ftc8702.opmodes.production;

import org.ftc8702.configurations.production.Team8702ProdAuto;
import org.ftc8702.utilities.TelemetryUtil;

public class SecondaryGetToCrater {
    private static final double FORWARD_SPEED = 0.15;
    private static final double BACKWARD_SPEED = -0.15;
    private static final double TURN_SPEED_RIGHT = 0.5;
    private static final double TURN_SPEED_LEFT = 0.5;
    private static final long BACKWARD_ADJUST_MILLIS = 1000;

    private static final double TURN_BACKWORD_SPEED_RIGHT = -0.18;
    private static final double TURN_BACKWORD_SPEED_LEFT = -0.23;

    private static final long TIME_OUT = 10_000; // 10 seconds

    private Team8702ProdAuto robot;
    private TelemetryUtil telemetry;

    private long startTime = 0;

    public SecondaryGetToCrater (Team8702ProdAuto robot, TelemetryUtil telemetry) {
        this.robot = robot;
        this.telemetry = telemetry;
    }

    public void init() {
        telemetry.addData("Init", getClass().getSimpleName() + " initialized.");
    }





}
