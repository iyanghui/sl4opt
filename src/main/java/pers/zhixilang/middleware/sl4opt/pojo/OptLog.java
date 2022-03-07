package pers.zhixilang.middleware.sl4opt.pojo;

import pers.zhixilang.middleware.sl4opt.constants.Result;

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
     * 发生时间
     */
    private Long execTime;

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

    public void setResult(Result result) {
        this.result = result;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getExecTime() {
        return execTime;
    }

    public void setExecTime(Long execTime) {
        this.execTime = execTime;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
