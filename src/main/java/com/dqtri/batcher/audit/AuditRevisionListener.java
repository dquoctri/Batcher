package com.dqtri.batcher.audit;

import com.dqtri.batcher.model.AuditRevisionEntity;
import org.hibernate.envers.RevisionListener;

public class AuditRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {
        AuditInfo audit = AuditInfoHolder.getInstance().getCurrent();
        if (revisionEntity instanceof AuditRevisionEntity auditRevisionEntity){
            auditRevisionEntity.setAction(audit.getAction());
            auditRevisionEntity.setMethod(audit.getMethod());
            auditRevisionEntity.setUri(audit.getUri());
            auditRevisionEntity.setEmail(audit.getEmail());
            auditRevisionEntity.setDescription(audit.getDescription());
        }
    }
}
