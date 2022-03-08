package pers.zhixilang.lego.sl4opt.exception;

/**
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-07 22:08
 */
public class Sl4optException extends RuntimeException {
    private String expressionStr;

    public Sl4optException(String expressionStr, String message) {
        super(message);
        this.expressionStr = expressionStr;
    }

    public Sl4optException(String expressionStr, String message, Throwable e) {
        super(message, e);
        this.expressionStr = expressionStr;
    }

    @Override
    public String getMessage() {
        return "expressionStr: 「" + this.expressionStr + "」; detail: 「" + super.getMessage() + "」";
    }
}
