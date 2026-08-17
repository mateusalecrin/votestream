package com.votestream.api.dto;

import com.votestream.api.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(

        @NotBlank
        String name,

        @NotBlank
        @Email
        String email

) {

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .build();
    }
}
