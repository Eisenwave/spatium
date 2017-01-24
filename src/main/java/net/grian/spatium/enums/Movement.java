package net.grian.spatium.enums;

import net.grian.spatium.anno.MinecraftSpecific;

import javax.annotation.Nullable;

@MinecraftSpecific
public enum Movement {
    IDLE(null),
    WALKING(IDLE),
    SPRINTING(WALKING),
    JUMPING(SPRINTING),
    FALLING(JUMPING);

    private final Movement fallback;

    Movement(Movement fallback) {
        this.fallback = fallback;
    }

    @Nullable
    public Movement fallback() {
        return fallback;
    }

    public boolean hasFallback() {
        return fallback != null;
    }

}
