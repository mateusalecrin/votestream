package com.votestream.api.dto;

import jakarta.validation.constraints.NotNull;

public record VoteRequest(

        @NotNull
        Long pollId,

        @NotNull
        Long optionId,

        @NotNull
        Long userId

) {
}
