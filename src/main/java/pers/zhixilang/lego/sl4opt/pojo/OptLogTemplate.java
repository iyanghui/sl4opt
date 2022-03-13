package pers.zhixilang.lego.sl4opt.pojo;

/**
 * log模板
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-07 11:49
 */
public class OptLogTemplate {
    /**
     * success模板
     */
    private String success;

    /**
     * fail模板
     */
    private String fail;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * operator模板
     */
    private String operator;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getFail() {
        return fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
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
