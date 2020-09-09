package com.hzwq.framelibrary.protocol698.obj;

/**
 * Created by qinling on 2019/1/31 11:26
 * Description:
 */
public class FrameParserException extends Exception {
    public FrameParserException() {
        super(new Exception("帧解析失败"));
    }
    public FrameParserException(Exception exception) {
        super(exception.getMessage(),exception.getCause());
    }
    public FrameParserException(String message) {
        super(message);
    }

    public FrameParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrameParserException(Throwable cause) {
        super(cause);
    }


}
