package storm.commonlib.common.http;

/**
 * 异常信息
 */
public class MessageException extends Exception {
    private ExceptionTypes exceptionType = ExceptionTypes.UnknownException;
    /**
     * 附加信息
     */
    private String additionalInfo = "";

    public MessageException(ExceptionTypes exceptionType) {
        this(exceptionType, "");
    }

    public MessageException(ExceptionTypes exceptionType, String additionalInfo) {
        this.exceptionType = exceptionType;
        if (additionalInfo != null && !additionalInfo.isEmpty()) {
            this.additionalInfo = "：" + additionalInfo;
        }
    }

    /**
     * 获取异常代码
     */
    public int getCode() {
        return exceptionType.getValue();
    }

    /**
     * 获取异常的提示信息
     */
    public String getInfo() {
        return exceptionType.getName() + additionalInfo;
    }

    @Override
    public String getMessage() {
        return getInfo();
    }

    @Override
    public String toString() {
        return getInfo();
    }

    @Override
    public Throwable fillInStackTrace() {
        // 不填充异常栈
        return this;
    }
}
