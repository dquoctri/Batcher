package com.dqtri.batcher.audit;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AuditActionAspectExecutor {
    @Before(value = "execution(* *(..)) && @annotation(action)", argNames = "action")
    public void setAuditAction(AuditAction action) {
        AuditInfo audit = AuditInfoHolder.getInstance().getCurrent();
        if (ActionType.SYSTEM.equals(action.type())
                || ActionType.SCHEDULER.equals(action.type())) {
            audit = new AuditInfo();
            audit.setMethod(action.type().name());
        }
        if (audit == null) {
            throw new IllegalArgumentException("Audit Holder is not configured correctly");
        }
        if (audit.getAction() == null || "undefined".equalsIgnoreCase(audit.getAction())) {
            audit.setAction(action.value());
            audit.setDescription(action.value());
        }
        AuditInfoHolder.getInstance().setCurrentContext(audit);
    }
}
