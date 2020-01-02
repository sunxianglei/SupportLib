package com.hexin.znkflib.support.bus;

/**
 * desc:
 * @author sunxianglei@myhexin.com
 * @date 2019/7/4.
 */

public class VoiceAssistantException extends RuntimeException {

    private static final long serialVersionUID = -2912559384646531411L;

    public VoiceAssistantException() {
        super();
    }

    public VoiceAssistantException(String message) {
        super(message);
    }

    public VoiceAssistantException(String message, Throwable cause) {
        super(message, cause);
    }
}
