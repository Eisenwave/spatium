package net.grian.spatium.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks methods that return a normalized vector or fields that contain a normalized vector.
 * This annotation may also be used for arrays of vectors or matrices that consist of (ortho)normal vectors.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface Normalized {}
