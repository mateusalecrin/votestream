package com.votestream.api.dto;

import com.votestream.api.model.Poll;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PollRequest(

        @NotBlank
        String title,

        @NotBlank
        String question,

        @NotNull
        Long ownerId

) {

    public Poll toEntity() {
        return Poll.builder()
                .title(title)
                .question(question)
                .build();
    }
}
