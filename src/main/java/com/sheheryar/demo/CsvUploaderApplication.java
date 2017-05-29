package com.sheheryar.demo;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(JpaConfiguration.class)
@SpringBootApplication
public class CsvUploaderApplication /*extends SpringBootServletInitializer*/{

	private int maxUploadSizeInMb = 10 * 1024 * 1024; // 10 MB
	
	public static void main(String[] args) {
		SpringApplication.run(CsvUploaderApplication.class, args);
	}
	
	//Tomcat large file upload connection reset
    //http://www.mkyong.com/spring/spring-file-upload-and-connection-reset-issue/
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {

        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

        tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                //-1 means unlimited
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });

        return tomcat;

    }
    
    
    
}
