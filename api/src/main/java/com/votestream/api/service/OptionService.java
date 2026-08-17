package com.votestream.api.service;

import com.votestream.api.exception.BusinessRuleException;
import com.votestream.api.model.Option;
import com.votestream.api.model.PollStatus;
import com.votestream.api.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;
    private final PollService pollService;

    public Option create(Long pollId, Option option) {
        var poll = pollService.findById(pollId);

        if (poll.getStatus() != PollStatus.OPEN) {
            throw new BusinessRuleException("Cannot add options to a poll that is not open");
        }

        option.setPoll(poll);
        return optionRepository.save(option);
    }

    public List<Option> findByPollId(Long pollId) {
        pollService.findById(pollId);
        return optionRepository.findByPollId(pollId);
    }
}
