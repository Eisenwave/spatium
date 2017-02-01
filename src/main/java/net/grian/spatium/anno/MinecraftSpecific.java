package net.grian.spatium.anno;

import net.grian.spatium.geo3.Vector3;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 *     Marks any operation in this library which's behavior is specific to Minecraft geometry. Often using these
 *     operations is more convenient or even the only way of applying rotations for example. However, if possible,
 *     using Minecraft specific operations should be avoided when creating portable systems.
 * </p>
 * <p>
 *     For example, {@link Vector3#multiply(double)} will always behave in the exact same way no matter how the axes
 *     are arranged and how yaw and pitch are defined.
 * </p>
 * <p>
 *     On the other side, {@link Vector3#setYawPitch(double, double)} would have a different meaning in a system with a
 *     different definition of yaw and pitch or a different arrangement of axes.
 * </p>
 */
@Retention(RetentionPolicy.SOURCE)
@Target(value = {ElementType.METHOD, ElementType.PACKAGE, ElementType.TYPE})
public @interface MinecraftSpecific {}
