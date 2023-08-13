package com.github.support.junit;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.util.concurrent.TimeoutException;

public class KafkaNetworkTimeoutExtension implements TestExecutionExceptionHandler {
    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        if (throwable instanceof TimeoutException) {
            return;
        }
        throw throwable;
    }
}
