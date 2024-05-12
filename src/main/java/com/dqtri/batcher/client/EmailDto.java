package com.dqtri.batcher.client;

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
public class EmailDto {
    private String from;
    private String to;
    private String cc;
    private String subject;
    private String body;
    private boolean isHtml = true;
}
