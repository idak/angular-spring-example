package com.idak.tuto.api.config.security;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LoadDataCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        final String activeProfiles = context.getEnvironment().getProperty("spring.profiles.active");
        return !"test".equals(activeProfiles);
    }
}