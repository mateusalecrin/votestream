package com.votestream.api.repository;

import com.votestream.api.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    boolean existsByPollIdAndUserId(Long pollId, Long userId);
    List<Vote> findByPollId(Long pollId);
    long countByOptionId(Long optionId);
}