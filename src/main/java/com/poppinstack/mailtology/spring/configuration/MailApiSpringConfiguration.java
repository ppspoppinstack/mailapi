package com.poppinstack.mailtology.spring.configuration;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.runtime.Settings;
import com.poppinstack.mailtology.configuration.MailApiConfiguration;
import com.poppinstack.mailtology.configuration.MailgunApiConfiguration;
import com.poppinstack.mailtology.constant.MAILGUN_DOMAIN_REGION;
import com.poppinstack.mailtology.constant.MAIL_API_PROVIDER;
import com.poppinstack.mailtology.instance.SpringMailApiInstance;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.concurrent.TimeUnit;

@Configuration
public class MailApiSpringConfiguration {

    @Value("${mailtology.http.maxidle.connection:20}")
    private int maxIdleHttpConnection;

    @Value("${mailtology.http.keepalivetime:5}")
    private int maxIdleKeepAliveTime;

    @Value("${mailtology.provider}")
    private String mailApiProvider;

    @Value("${mailtology.mailgun.apikey}")
    private String mailgunApiKey;

    @Value("${mailtology.mailgun.domainName}")
    private String mailgunDomainName;

    @Value("${mailtology.mailgun.region:us}")
    private String mailgunRegion;

    @Value("${mailtology.pool.size:300}")
    private int mailApiPoolSize;

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
    public SpringMailApiInstance mailApi(Environment env,DslJson<Object> mailApiDslJson,OkHttpClient mailApiOkHttpClient){
        MAIL_API_PROVIDER mail_api_provider = MAIL_API_PROVIDER.lookup(mailApiProvider);
        MailApiConfiguration mailApiConfiguration = null;
        if(MAIL_API_PROVIDER.MAILGUN.equals(mail_api_provider)){
            MAILGUN_DOMAIN_REGION mailgun_domain_region = MAILGUN_DOMAIN_REGION.lookup(mailgunRegion);
            MailgunApiConfiguration mailgunApiConfiguration = new MailgunApiConfiguration();
            mailgunApiConfiguration.setDomainRegion(mailgun_domain_region);
            mailgunApiConfiguration.setApiKey(mailgunApiKey);
            mailgunApiConfiguration.setDomainName(mailgunDomainName);
            mailApiConfiguration = mailgunApiConfiguration;
        }
        SpringMailApiInstance springMailApiInstance = new SpringMailApiInstance(mailApiOkHttpClient,mailApiDslJson,mailApiConfiguration,mail_api_provider,mailApiPoolSize);
        return springMailApiInstance;
    }
}