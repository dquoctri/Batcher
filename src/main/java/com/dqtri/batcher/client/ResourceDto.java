package com.dqtri.batcher.client;

import com.dqtri.batcher.model.Resource;
import com.dqtri.batcher.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceDto {
    private Long id;
    private String content;
    private Status status = Status.ACTIVE;

    public ResourceDto(Resource resource) {
        this.id = resource.getPk();
        this.content = resource.getContent();
        this.status = resource.getStatus();
    }

    public Resource toResource(){
        Resource resource = new Resource();
        resource.setPk(this.id);
        resource.setContent(this.content);
        resource.setStatus(this.status);
        return resource;
    }
}
