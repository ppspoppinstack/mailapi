package com.poppinstack.mailapi.sender;

import com.dslplatform.json.DslJson;
import com.poppinstack.mailapi.configuration.MailApiConfiguration;
import com.poppinstack.mailapi.model.MailParam;
import com.poppinstack.mailapi.model.SendMailResponse;
import okhttp3.OkHttpClient;

public abstract class SenderAbstract<C extends MailApiConfiguration> implements Sender {
    private OkHttpClient okHttpClient;
    private C configuration;
    private DslJson mainDslJson;

    public SenderAbstract(OkHttpClient okHttpClient, C configuration,DslJson mainDslJson) {
        this.okHttpClient = okHttpClient;
        this.configuration = configuration;
        this.mainDslJson = mainDslJson;
    }

    @Override
    public SendMailResponse sendMail(MailParam mailParam) throws Exception {
        return this.performSendMail(mailParam,this.okHttpClient,this.configuration,this.mainDslJson);
    }

    protected abstract SendMailResponse performSendMail(MailParam mailParam,OkHttpClient okHttpClient,C mailApiConfiguration,DslJson mainDslJson) throws Exception;
}
