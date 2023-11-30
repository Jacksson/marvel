package co.jackson.marvel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan("co.jackson")
//@ComponentScan(basePackages = {"co.jackson", "co.jackson.marvel.aspect"})
@EnableAspectJAutoProxy
public class App
{
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        System.out.println( "MARVELS CHARACTERS TEST BY JEMR" );
        ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);

        String dataSourceUri = ctx.getEnvironment().getProperty("spring.datasource.url");
        LOG.info("Connected to H2: " + dataSourceUri);
    }
}
