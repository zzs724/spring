package com.zzs;


import com.zzs.service.IAccountService;
import com.zzs.service.IHelloService;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class MySpringTest {

    @Test
    public void test() {
        Resource resource = new ClassPathResource("bean.xml");
        BeanFactory beanFactory = new XmlBeanFactory(resource);
        IHelloService helloService = beanFactory.getBean("helloService", IHelloService.class);
    }

    @Test
    public void test2() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService accountService = applicationContext.getBean("accountService", IAccountService.class);
        accountService.saveAccount();
    }

}
