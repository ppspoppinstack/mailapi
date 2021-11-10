package com.poppinstack.mailapi.spring.configuration;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.runtime.Settings;
import com.poppinstack.mailapi.configuration.MailApiConfiguration;
import com.poppinstack.mailapi.configuration.MailgunApiConfiguration;
import com.poppinstack.mailapi.constant.MAIL_API_PROVIDER;
import com.poppinstack.mailapi.instance.SpringMailApiInstance;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.concurrent.TimeUnit;

@Configuration
public class MailApiSpringConfiguration {

    @Value("${mailapi.http.maxidle.connection:20}")
    private int maxIdleHttpConnection;

    @Value("${mailapi.http.keepalivetime:5}")
    private int maxIdleKeepAliveTime;

    @Bean
    public DslJson<Object> mailApiDslJson(){
        DslJson.Settings<Object> settings = Settings.withRuntime()
                .includeServiceLoader()
                .skipDefaultValues(true);
        DslJson mainDslJson = new DslJson<>(settings);
        return mainDslJson;
    }

    @Bean
    public ConnectionPool mailApiOkHttpConnectionPool() {
        return new ConnectionPool(this.maxIdleHttpConnection,this.maxIdleKeepAliveTime, TimeUnit.MINUTES);
    }

    @Bean
    public OkHttpClient mailApiOkHttpClient(ConnectionPool mailApiOkHttpConnectionPool) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectionPool(mailApiOkHttpConnectionPool);
        return builder.build();
    }

    @Bean
    public SpringMailApiInstance mailApi(Environment environment,DslJson<Object> mailApiDslJson,OkHttpClient mailApiOkHttpClient){
        String mailApiProvider = environment.getProperty("mailapi.provider");
        MAIL_API_PROVIDER mail_api_provider = MAIL_API_PROVIDER.lookup(mailApiProvider);
        MailApiConfiguration mailApiConfiguration = null;
        if(MAIL_API_PROVIDER.MAILGUN.equals(mail_api_provider)){
            String mailgunApiKey = environment.getProperty("mailapi.mailgun.apikey");
            String mailgunDomainName = environment.getProperty("mailapi.mailgun.domainName");
            MailgunApiConfiguration mailgunApiConfiguration = new MailgunApiConfiguration();
            mailgunApiConfiguration.setApiKey(mailgunApiKey);
            mailgunApiConfiguration.setDomainName(mailgunDomainName);
            mailApiConfiguration = mailgunApiConfiguration;
        }
        SpringMailApiInstance springMailApiInstance = new SpringMailApiInstance(mailApiOkHttpClient,mailApiDslJson,mailApiConfiguration,mail_api_provider);
        return springMailApiInstance;
    }
}