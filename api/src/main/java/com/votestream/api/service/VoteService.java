package com.votestream.api.service;

import com.votestream.api.dto.OptionResult;
import com.votestream.api.dto.PollResultsResponse;
import com.votestream.api.exception.BusinessRuleException;
import com.votestream.api.exception.ConflictException;
import com.votestream.api.exception.ResourceNotFoundException;
import com.votestream.api.model.Vote;
import com.votestream.api.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.votestream.api.repository.OptionRepository;

import com.votestream.api.model.PollStatus;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PollService pollService;
    private final UserService userService;
    private final OptionRepository optionRepository;

    public Vote cast(Long pollId, Long optionId, Long userId) {
        var poll = pollService.findById(pollId);

        if (poll.getStatus() != PollStatus.OPEN) {
            throw new BusinessRuleException("Poll is not open");
        }

        if (voteRepository.existsByPollIdAndUserId(pollId, userId)) {
            throw new ConflictException("User already voted in this poll");
        }

        var option = optionRepository.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found"));

        if (!option.getPoll().getId().equals(pollId)) {
            throw new BusinessRuleException("Option does not belong to this poll");
        }

        var user = userService.findById(userId);

        var vote = Vote.builder()
                .poll(poll)
                .option(option)
                .user(user)
                .build();

        return voteRepository.save(vote);
    }

    public PollResultsResponse getResults(Long pollId, Long userId) {
        var poll = pollService.findById(pollId);

        var optionResults = optionRepository.findByPollId(pollId).stream()
                .map(option -> new OptionResult(
                        option.getId(),
                        option.getText(),
                        voteRepository.countByOptionId(option.getId())))
                .toList();

        Long userVotedOptionId = userId == null
                ? null
                : voteRepository.findByPollIdAndUserId(pollId, userId)
                        .map(vote -> vote.getOption().getId())
                        .orElse(null);

        return new PollResultsResponse(
                poll.getId(),
                poll.getTitle(),
                poll.getQuestion(),
                poll.getStatus(),
                optionResults,
                userVotedOptionId);
    }
}