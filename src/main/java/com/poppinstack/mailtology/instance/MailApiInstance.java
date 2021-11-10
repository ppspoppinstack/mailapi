package com.poppinstack.mailtology.instance;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.runtime.Settings;
import com.poppinstack.mailtology.api.MainApi;
import com.poppinstack.mailtology.configuration.MailApiConfiguration;
import com.poppinstack.mailtology.constant.MAIL_API_PROVIDER;
import com.poppinstack.mailtology.pool.MailApiPoolAllocatorManager;
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
        this.mailApiPoolAllocatorManager = new MailApiPoolAllocatorManager(okHttpClient,mailApiConfiguration,mainDslJson,mail_api_provider,300);
    }

    public static void initial(MailApiConfiguration mailApiConfiguration,MAIL_API_PROVIDER mail_api_provider){
        MailApiInstance.mailApiInstance = new MailApiInstance(mailApiConfiguration,mail_api_provider);
    }

    public static MainApi getMainApi() throws InterruptedException {
        return mailApiInstance.mailApiPoolAllocatorManager.getMainApi();
    }
}