package com.poppinstack.mailapi.pool;

import com.dslplatform.json.DslJson;
import com.poppinstack.mailapi.api.MainApi;
import com.poppinstack.mailapi.configuration.MailApiConfiguration;
import com.poppinstack.mailapi.constant.MAIL_API_PROVIDER;
import okhttp3.OkHttpClient;
import stormpot.Config;

public class MailApiPoolAllocatorManager {
    private MailApiPool mailApiPool;
    private MailApiConfiguration mailApiConfiguration;
    private DslJson mainDslJson;
    private MAIL_API_PROVIDER mail_api_provider;

    public MailApiPoolAllocatorManager(OkHttpClient okHttpClient,MailApiConfiguration mailApiConfiguration,DslJson mainDslJson,MAIL_API_PROVIDER mail_api_provider) {
        MailApiPoolAllocator mailApiPoolAllocator = new MailApiPoolAllocator(okHttpClient,mailApiConfiguration,mainDslJson,mail_api_provider);
        Config<MainApi> config = new Config<MainApi>();
        config.setAllocator(mailApiPoolAllocator);
        config.setSize(300);
        this.mailApiPool = new MailApiPool(config);
    }

    public MainApi getMainApi() throws InterruptedException{
        return this.mailApiPool.claim();
    }
}
