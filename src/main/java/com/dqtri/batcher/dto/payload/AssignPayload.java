package com.dqtri.batcher.dto.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AssignPayload {
    @NotNull
    @Email
    private String email;
}