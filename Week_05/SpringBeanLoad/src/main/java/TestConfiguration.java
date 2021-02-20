import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

    @Bean
    public TestUserBean testUserBean() {
        TestUserBean bean = new TestUserBean();
        bean.setName("Tom");
        bean.setAge(20);

        return bean;
    }
}
