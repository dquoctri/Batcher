package com.dqtri.batcher.annotation;

public @interface DemoTag {
    String value() default "demo 0";
    String description() default "";
}
