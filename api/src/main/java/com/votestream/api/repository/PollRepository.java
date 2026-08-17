package com.votestream.api.repository;

import com.votestream.api.model.Poll;
import com.votestream.api.model.PollStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {

    List<Poll> findByStatus(PollStatus status);
    List<Poll> findByOwnerId(Long ownerId);
}