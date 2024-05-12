package com.dqtri.batcher.client;

import com.dqtri.batcher.model.Resource;
import com.dqtri.batcher.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDto {
    private Long pk;
    private String content;
    private Status status = Status.ACTIVE;

    public ResourceDto(Resource resource) {
        this.pk = resource.getPk();
        this.content = resource.getContent();
        this.status = resource.getStatus();
    }

    public Resource toResource(){
        Resource resource = new Resource();
        resource.setPk(this.pk);
        resource.setContent(this.content);
        resource.setStatus(this.status);
        return resource;
    }
}
