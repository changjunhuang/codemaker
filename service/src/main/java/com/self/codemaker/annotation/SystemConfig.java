package com.self.codemaker.annotation;

public @interface SystemConfig {

    String name() default "lin";

    int age();

}
