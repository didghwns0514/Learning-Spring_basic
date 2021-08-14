package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac;

    @BeforeEach
    public void beforeEach(){
        ac  = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    @DisplayName("Spring-Container 안의 Bean을 찾기 위한 테스트")
    public void findAllBean(){
        String[] beanDefinitionNames= ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("beanDefinitionName = " + beanDefinitionName + " object = " + bean);
        }
    }

    @Test
    @DisplayName("Spring-Container 안의 내가 등록한 애플리케이션 Bean만을 찾기 위한 테스트")
    public void findApplicationBean(){
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);


            //Role ROLE_APPLICATION: 직접 등록한 애플리케이션 빈
            //Role ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){

                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("beanDefinitionNames = " + beanDefinitionName + " object = " + bean);
            }
        }
    }


}
