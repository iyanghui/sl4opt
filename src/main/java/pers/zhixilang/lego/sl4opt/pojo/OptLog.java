package pers.zhixilang.lego.sl4opt.pojo;

import pers.zhixilang.lego.sl4opt.constants.Result;

/**
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-05 20:28
 */
public class OptLog {

    /**
     * 操作结果
     */
    private Result result;

    /**
     * 内容详细信息
     */
    private String content;

    /**
     * log时间
     */
    private Long time;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 操作者对象信息
     */
    private String operator;

    public Result getResult() {
        return result;
    }

    public OptLog setResult(Result result) {
        this.result = result;
        return this;
    }

    public String getContent() {
        return content;
    }

    public OptLog setContent(String content) {
        this.content = content;
        return this;
    }

    public Long getTime() {
        return time;
    }

    public OptLog setTime(Long time) {
        this.time = time;
        return this;
    }

    public String getBizType() {
        return bizType;
    }

    public OptLog setBizType(String bizType) {
        this.bizType = bizType;
        return this;
    }

    public String getOperator() {
        return operator;
    }

    public OptLog setOperator(String operator) {
        this.operator = operator;
        return this;
    }

    @Override
    public String toString() {
        return "OptLog{" +
                "result=" + result +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", bizType='" + bizType + '\'' +
                ", operator='" + operator + '\'' +
                '}';
    }
}
