package pers.zhixilang.middleware.sl4opt;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;
import pers.zhixilang.middleware.sl4opt.annotation.EnableSl4opt;
import pers.zhixilang.middleware.sl4opt.service.ParameterService;

import javax.annotation.Resource;

/**
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-07 10:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ParameterService.class})
@SpringBootConfiguration
@EnableSl4opt
@EnableAspectJAutoProxy()
public class Sl4optMainTest {

    @Resource
    private ParameterService parameterService;

    @Test
    public void parametersTest() {
        String str = parameterService.print("zs", 18);
        Assert.assertNotNull("return", str);
    }

    @Test
    public void variablesTest() {

    }

    @Test
    public void noArgFunctionTest() {

    }

    @Test
    public void FunctionTest() {

    }
}
