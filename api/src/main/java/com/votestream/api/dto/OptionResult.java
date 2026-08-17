package com.votestream.api.dto;

public record OptionResult(Long optionId, String text, long voteCount) {
}
