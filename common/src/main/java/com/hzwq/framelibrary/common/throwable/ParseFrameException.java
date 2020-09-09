package com.hzwq.framelibrary.common.throwable;

/**
 * created by tsinling on: 2019/5/1 21:30
 * description:
 */
public class ParseFrameException extends RuntimeException {

    public ParseFrameException() {
    }

    public ParseFrameException(String message) {
        super(message);
    }

    public ParseFrameException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseFrameException(Throwable cause) {
        super(cause);
    }

    public ParseFrameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
