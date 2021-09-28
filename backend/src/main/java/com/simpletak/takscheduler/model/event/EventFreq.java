package com.simpletak.takscheduler.model.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EventFreq {
    DAILY("DAILY"),
    WEEKLY("WEEKLY");

    private final String type;
}