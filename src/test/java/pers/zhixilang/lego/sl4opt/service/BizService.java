package pers.zhixilang.lego.sl4opt.service;

import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.zhixilang.lego.sl4opt.annotation.Sl4opt;
import pers.zhixilang.lego.sl4opt.core.Sl4optContext;
import pers.zhixilang.lego.sl4opt.pojo.Person;

import java.util.UUID;

/**
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-06 15:11
 */
public class BizService {

    private static final Logger logger = LoggerFactory.getLogger(BizService.class);

    @Sl4opt(success = "hello @#name@", bizType = "1",
            fail = "失败: @#_err@")
    @Sl4opt(success = "新增成功", bizType = "@#bizType@", operator = "test")
    public String parameterDo(String name, Integer age) {
        if (age <= 0) {
            throw new RuntimeException("age不合法");
        }

        Sl4optContext.putVariable("bizType", 60001);
        return "=> name: " + name + "; age:" + age;
    }

    @Sl4opt(success = "hello @#name@, 结果=@#_res@", bizType = "1",
            fail = "失败: @#_err@")
    public String variableDo() {
        Sl4optContext.putVariable("name", "zs");
        return "success";
    }

    @Sl4opt(success = "新增用户「@#person.name@」成功, ID=「@id_func@」",
            bizType = "1")
    public void noArgFunctionDo(Person person) {
        System.out.println("...");
    }


    @Sl4opt(success = "新增用户「@#person.name@」成功, ID=「@id_func@」，自动生成昵称「@nick_func#person.name@」",
            fail = "新增用户「@#person.name@」失败, 原因=「@#_err@」",
            bizType = "1")
    public void argFunctionDo(Person person) {
        if (StringUtils.isBlank(person.getName())) {
            throw new IllegalArgumentException("name can't blank");
        }
        System.out.println("...");
    }


    @Sl4opt(success = "新增用户「@#person.name@」成功, 自动生成昵称「@nick_func#person.name,#person.age@」, 耗时「@#_time@」",
            bizType = "1")
    public void multiArgFunctionDo(Person person) {
        System.out.println("...");
    }

    @Sl4opt(success = "新增用户「@#person.name@」成功, ID=「@#_res.id@」 自动生成昵称「@nick_func#person.name,#person.age@」",
            bizType =
            "1",
            fail = "新增用户「@#person.name@」失败：@#_err@")
    public Person globalVariableDo(Person person) {
        String id = UUID.randomUUID().toString();
        person.setId(id);

        logger.info("id=「{}」", id);

        return person;
    }



}
