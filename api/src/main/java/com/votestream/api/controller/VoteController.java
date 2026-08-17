package com.votestream.api.controller;

import com.votestream.api.dto.VoteRequest;
import com.votestream.api.dto.VoteResponse;
import com.votestream.api.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<VoteResponse> cast(@RequestBody @Valid VoteRequest request) {
        var vote = voteService.cast(request.pollId(), request.optionId(), request.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(VoteResponse.from(vote));
    }
}