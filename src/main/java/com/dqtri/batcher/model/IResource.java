package com.dqtri.batcher.model;

import com.dqtri.batcher.model.enums.Status;

public interface IResource {
    Long getPk();
    String getContent();
    Status getStatus();
}
