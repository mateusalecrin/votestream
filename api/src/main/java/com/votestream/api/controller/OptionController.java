package com.votestream.api.controller;

import com.votestream.api.dto.OptionRequest;
import com.votestream.api.dto.OptionSummary;
import com.votestream.api.service.OptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/polls/{pollId}/options")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;

    @PostMapping
    public ResponseEntity<OptionSummary> create(@PathVariable Long pollId, @RequestBody @Valid OptionRequest request) {
        var created = optionService.create(pollId, request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(OptionSummary.from(created));
    }

    @GetMapping
    public ResponseEntity<List<OptionSummary>> findByPollId(@PathVariable Long pollId) {
        var options = optionService.findByPollId(pollId).stream().map(OptionSummary::from).toList();
        return ResponseEntity.ok(options);
    }
}
