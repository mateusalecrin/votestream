package com.votestream.api.dto;

import com.votestream.api.model.Vote;

import java.time.LocalDateTime;

public record VoteResponse(
        Long id,
        Long pollId,
        Long optionId,
        String optionText,
        Long userId,
        String userName,
        LocalDateTime votedAt
) {

    public static VoteResponse from(Vote vote) {
        return new VoteResponse(
                vote.getId(),
                vote.getPoll().getId(),
                vote.getOption().getId(),
                vote.getOption().getText(),
                vote.getUser().getId(),
                vote.getUser().getName(),
                vote.getVotedAt()
        );
    }
}
