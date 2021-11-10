package com.poppinstack.mailtology.pool;

import com.dslplatform.json.DslJson;
import com.poppinstack.mailtology.api.MainApi;
import com.poppinstack.mailtology.configuration.MailApiConfiguration;
import com.poppinstack.mailtology.constant.MAIL_API_PROVIDER;
import okhttp3.OkHttpClient;
import stormpot.Allocator;
import stormpot.Slot;

public class MailApiPoolAllocator implements Allocator<MainApi> {
    private OkHttpClient okHttpClient;
    private MailApiConfiguration mailApiConfiguration;
    private DslJson mainDslJson;
    private MAIL_API_PROVIDER mail_api_provider;

    public MailApiPoolAllocator(OkHttpClient okHttpClient,MailApiConfiguration mailApiConfiguration,DslJson mainDslJson,MAIL_API_PROVIDER mail_api_provider) {
        this.okHttpClient = okHttpClient;
        this.mailApiConfiguration = mailApiConfiguration;
        this.mainDslJson = mainDslJson;
        this.mail_api_provider=mail_api_provider;
    }

    @Override
    public MainApi allocate(Slot slot) throws Exception {
        return new MainApi(slot,this.okHttpClient,this.mailApiConfiguration,this.mainDslJson,this.mail_api_provider);
    }

    @Override
    public void deallocate(MainApi mainApi) throws Exception {
    }
}
