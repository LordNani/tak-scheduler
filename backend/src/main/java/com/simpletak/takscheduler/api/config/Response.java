package com.simpletak.takscheduler.api.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Response<T> {

    private final String message;
    private final T data;

    public static <T> Response<T> error(String errorMessage) {
        return new Response<>(errorMessage, null);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(null, data);
    }

    public static <T> Response<T> of(String message, T data) {
        return new Response<>(message, data);
    }

}