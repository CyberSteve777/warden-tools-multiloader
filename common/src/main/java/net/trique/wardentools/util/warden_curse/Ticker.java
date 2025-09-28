package net.trique.wardentools.util.warden_curse;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;

public class Ticker {
    public static int INFINITE_DURATION = -1;
    private int duration;

    public Ticker(int duration) {
        this.duration = duration;
    }

    public boolean tick() {
        if (hasRemainingDuration()) {
            tickDownDuration();
        }
        return hasRemainingDuration();
    }

    public boolean hasRemainingDuration() {
        return this.isInfiniteDuration() || this.duration > 0;
    }

    public boolean isInfiniteDuration() {
        return duration == INFINITE_DURATION;
    }

    private int mapDuration(Int2IntFunction mapper) {
        return !this.isInfiniteDuration() && duration != 0 ? mapper.applyAsInt(duration) : duration;
    }

    private void tickDownDuration() {
        duration = this.mapDuration(value -> value - 1);
    }

}
