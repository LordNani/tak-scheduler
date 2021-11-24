package com.simpletak.takscheduler.api.model.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EventFreq {
    DAILY("DAILY"),
    WEEKLY("WEEKLY");

    private final String type;
}