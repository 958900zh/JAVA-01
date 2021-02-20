import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @program: demo123
 * @author: zhangxidong
 * @create: 2021-02-21
 **/

public class LoadBeanDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);
        TestUserBean configBean = context.getBean(TestUserBean.class);

        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring.xml");
        TestUserBean xmlBena = classPathXmlApplicationContext.getBean(TestUserBean.class);
    }
}
