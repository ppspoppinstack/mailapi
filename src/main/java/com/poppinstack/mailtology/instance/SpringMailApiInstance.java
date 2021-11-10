package com.poppinstack.mailtology.instance;

import com.dslplatform.json.DslJson;
import com.poppinstack.mailtology.api.MainApi;
import com.poppinstack.mailtology.configuration.MailApiConfiguration;
import com.poppinstack.mailtology.constant.MAIL_API_PROVIDER;
import com.poppinstack.mailtology.pool.MailApiPoolAllocatorManager;
import okhttp3.OkHttpClient;

public class SpringMailApiInstance {
    private OkHttpClient okHttpClient;
    private DslJson<Object> mainDslJson;
    private MailApiPoolAllocatorManager mailApiPoolAllocatorManager;
    private MAIL_API_PROVIDER mail_api_provider;

    public SpringMailApiInstance(OkHttpClient okHttpClient, DslJson<Object> mainDslJson, MailApiConfiguration mailApiConfiguration, MAIL_API_PROVIDER mail_api_provider,int mailApiPoolSize) {
        this.mailApiPoolAllocatorManager = new MailApiPoolAllocatorManager(okHttpClient,mailApiConfiguration,mainDslJson,mail_api_provider,mailApiPoolSize);
    }

    public MainApi getMainApi() throws InterruptedException {
        return this.mailApiPoolAllocatorManager.getMainApi();
    }
}