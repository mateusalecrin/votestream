package com.votestream.api.controller;

import com.votestream.api.dto.PollRequest;
import com.votestream.api.dto.PollResponse;
import com.votestream.api.dto.PollResultsResponse;
import com.votestream.api.model.PollStatus;
import com.votestream.api.service.PollService;
import com.votestream.api.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/polls")
@RequiredArgsConstructor
public class PollController {

    private final PollService pollService;
    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<PollResponse> create(@RequestBody @Valid PollRequest request) {
        var created = pollService.create(request.toEntity(), request.ownerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(PollResponse.from(created));
    }

    @GetMapping
    public ResponseEntity<List<PollResponse>> findAll(
            @RequestParam(required = false) PollStatus status) {
        var polls = status != null ? pollService.findByStatus(status) : pollService.findAll();
        return ResponseEntity.ok(polls.stream().map(PollResponse::from).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PollResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(PollResponse.from(pollService.findById(id)));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<PollResponse> close(@PathVariable Long id) {
        return ResponseEntity.ok(PollResponse.from(pollService.close(id)));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<PollResponse> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(PollResponse.from(pollService.cancel(id)));
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<PollResultsResponse> results(
            @PathVariable Long id,
            @RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(voteService.getResults(id, userId));
    }
}