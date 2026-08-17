package com.votestream.api.dto;

import com.votestream.api.model.PollStatus;

import java.util.List;

public record PollResultsResponse(
        Long pollId,
        String title,
        String question,
        PollStatus status,
        List<OptionResult> options,
        Long userVotedOptionId
) {
}
