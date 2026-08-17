package com.votestream.api.service;

import com.votestream.api.exception.BusinessRuleException;
import com.votestream.api.exception.ResourceNotFoundException;
import com.votestream.api.model.Poll;
import com.votestream.api.model.PollStatus;
import com.votestream.api.repository.PollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PollService {

    private final PollRepository pollRepository;
    private final UserService userService;

    public Poll create(Poll poll, Long ownerId) {
        var owner = userService.findById(ownerId);
        poll.setOwner(owner);
        return pollRepository.save(poll);
    }

    public Poll findById(Long id) {
        return pollRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Poll not found"));
    }

    public List<Poll> findAll() {
        return pollRepository.findAll();
    }

    public List<Poll> findByStatus(PollStatus status) {
        return pollRepository.findByStatus(status);
    }

    public Poll close(Long id) {
        var poll = findById(id);
        if (poll.getStatus() != PollStatus.OPEN) {
            throw new BusinessRuleException("Poll is not open");
        }
        poll.setStatus(PollStatus.CLOSED);
        poll.setClosedAt(LocalDateTime.now());
        return pollRepository.save(poll);
    }

    public Poll cancel(Long id) {
        var poll = findById(id);
        if (poll.getStatus() == PollStatus.CLOSED) {
            throw new BusinessRuleException("Poll is already closed");
        }
        if (poll.getStatus() == PollStatus.CANCELLED) {
            throw new BusinessRuleException("Poll is already cancelled");
        }
        poll.setStatus(PollStatus.CANCELLED);
        return pollRepository.save(poll);
    }
}