package com.dqtri.batcher.audit;

public class AuditInfoHolder {
    private AuditInfoHolder(){}
    private static class SingletonHelper {
        private static final AuditInfoHolder INSTANCE = new AuditInfoHolder();
    }
    public static AuditInfoHolder getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private final ThreadLocal<AuditInfo> delegate = new ThreadLocal<>();

    public AuditInfo getCurrent() {
        return delegate.get();
    }

    public void setCurrentContext(AuditInfo auditInfo) {
        delegate.set(auditInfo);
    }

    public void incorrectCleanup() {
        delegate.remove(); // Compliant
    }
}
