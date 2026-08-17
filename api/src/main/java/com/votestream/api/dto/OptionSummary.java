package com.votestream.api.dto;

import com.votestream.api.model.Option;

public record OptionSummary(Long id, String text) {

    public static OptionSummary from(Option option) {
        return new OptionSummary(option.getId(), option.getText());
    }
}
