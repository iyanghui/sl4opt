package pers.zhixilang.lego.sl4opt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;
import pers.zhixilang.lego.sl4opt.annotation.EnableSl4opt;
import pers.zhixilang.lego.sl4opt.function.IdGenFunction;
import pers.zhixilang.lego.sl4opt.function.NickGenFunction;
import pers.zhixilang.lego.sl4opt.pojo.Person;
import pers.zhixilang.lego.sl4opt.service.BizService;

import javax.annotation.Resource;

/**
 * @author zhixilang
 * @version 1.0.0
 * date 2022-03-07 10:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BizService.class, IdGenFunction.class, NickGenFunction.class})
@SpringBootConfiguration
@EnableSl4opt
@EnableAspectJAutoProxy()
public class Sl4optMainTest {

    @Resource
    private BizService bizService;

    private final Person person = new Person();
    {
        person.setName("zs");
        person.setAge(18);
    }

    @Test
    public void parametersTest() {
        bizService.parameterDo("zs", 18);
//        bizService.parameterDo("lisi", 19);
    }

    @Test
    public void variablesTest() {
        bizService.variableDo();
    }

    @Test
    public void noArgFunctionTest() {
        bizService.noArgFunctionDo(person);
    }


    @Test
    public void argFunctionTest() {
        bizService.argFunctionDo(person);

        String name = person.getName();
        person.setName(null);
        bizService.argFunctionDo(person);

        person.setName(name);
    }


    @Test
    public void multiArgFunctionTest() {
        bizService.multiArgFunctionDo(person);
    }

    @Test
    public void globalVariableTest() {
        bizService.globalVariableDo(person);
    }
}
