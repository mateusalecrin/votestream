package com.votestream.api.dto;

import com.votestream.api.model.Option;
import jakarta.validation.constraints.NotBlank;

public record OptionRequest(

        @NotBlank
        String text

) {

    public Option toEntity() {
        return Option.builder()
                .text(text)
                .build();
    }
}
