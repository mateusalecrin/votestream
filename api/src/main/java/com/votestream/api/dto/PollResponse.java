package com.votestream.api.dto;

import com.votestream.api.model.Poll;
import com.votestream.api.model.PollStatus;

import java.time.LocalDateTime;
import java.util.List;

public record PollResponse(
        Long id,
        String title,
        String question,
        PollStatus status,
        LocalDateTime createdAt,
        LocalDateTime closedAt,
        Long ownerId,
        String ownerName,
        List<OptionSummary> options
) {

    public static PollResponse from(Poll poll) {
        var options = poll.getOptions() == null
                ? List.<OptionSummary>of()
                : poll.getOptions().stream().map(OptionSummary::from).toList();

        return new PollResponse(
                poll.getId(),
                poll.getTitle(),
                poll.getQuestion(),
                poll.getStatus(),
                poll.getCreatedAt(),
                poll.getClosedAt(),
                poll.getOwner() != null ? poll.getOwner().getId() : null,
                poll.getOwner() != null ? poll.getOwner().getName() : null,
                options
        );
    }
}
