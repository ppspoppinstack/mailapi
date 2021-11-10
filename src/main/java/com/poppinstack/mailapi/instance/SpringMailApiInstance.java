package com.poppinstack.mailapi.instance;

import com.dslplatform.json.DslJson;
import com.poppinstack.mailapi.api.MainApi;
import com.poppinstack.mailapi.configuration.MailApiConfiguration;
import com.poppinstack.mailapi.constant.MAIL_API_PROVIDER;
import com.poppinstack.mailapi.pool.MailApiPoolAllocatorManager;
import okhttp3.OkHttpClient;

public class SpringMailApiInstance {
    private OkHttpClient okHttpClient;
    private DslJson<Object> mainDslJson;
    private MailApiPoolAllocatorManager mailApiPoolAllocatorManager;
    private MAIL_API_PROVIDER mail_api_provider;

    public SpringMailApiInstance(OkHttpClient okHttpClient, DslJson<Object> mainDslJson, MailApiConfiguration mailApiConfiguration, MAIL_API_PROVIDER mail_api_provider) {
        this.mailApiPoolAllocatorManager = new MailApiPoolAllocatorManager(okHttpClient,mailApiConfiguration,mainDslJson,mail_api_provider);
    }

    public MainApi getMainApi() throws InterruptedException {
        return this.mailApiPoolAllocatorManager.getMainApi();
    }
}