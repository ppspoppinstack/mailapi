package com.poppinstack.mailapi.instance;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.runtime.Settings;
import com.poppinstack.mailapi.api.MainApi;
import com.poppinstack.mailapi.configuration.MailApiConfiguration;
import com.poppinstack.mailapi.constant.MAIL_API_PROVIDER;
import com.poppinstack.mailapi.pool.MailApiPoolAllocatorManager;
import okhttp3.OkHttpClient;

public class MailApiInstance {
    private static MailApiInstance mailApiInstance = null;
    private MailApiPoolAllocatorManager mailApiPoolAllocatorManager = null;

    private MailApiInstance(MailApiConfiguration mailApiConfiguration, MAIL_API_PROVIDER mail_api_provider){
        OkHttpClient okHttpClient = new OkHttpClient();
        DslJson.Settings<Object> settings = Settings.withRuntime()
                .includeServiceLoader()
                .skipDefaultValues(true);
        DslJson mainDslJson = new DslJson<>(settings);
        this.mailApiPoolAllocatorManager = new MailApiPoolAllocatorManager(okHttpClient,mailApiConfiguration,mainDslJson,mail_api_provider);
    }

    public static void initial(MailApiConfiguration mailApiConfiguration,MAIL_API_PROVIDER mail_api_provider){
        MailApiInstance.mailApiInstance = new MailApiInstance(mailApiConfiguration,mail_api_provider);
    }

    public static MainApi getMainApi() throws InterruptedException {
        return mailApiInstance.mailApiPoolAllocatorManager.getMainApi();
    }
}