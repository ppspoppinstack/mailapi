package com.poppinstack.mailapi.api;

import com.dslplatform.json.DslJson;
import com.poppinstack.mailapi.configuration.MailApiConfiguration;
import com.poppinstack.mailapi.configuration.MailgunApiConfiguration;
import com.poppinstack.mailapi.constant.MAIL_API_PROVIDER;
import com.poppinstack.mailapi.model.MailParam;
import com.poppinstack.mailapi.model.SendMailResponse;
import com.poppinstack.mailapi.sender.MailgunSenderImpl;
import com.poppinstack.mailapi.sender.Sender;
import okhttp3.OkHttpClient;
import stormpot.Poolable;
import stormpot.Slot;

public class MainApi implements Poolable {
    private Slot slot;
    private OkHttpClient okHttpClient;
    private MailApiConfiguration mailApiConfiguration;
    private Sender sender;
    private DslJson mainDslJson;

    public MainApi(Slot slot, OkHttpClient okHttpClient, MailApiConfiguration mailApiConfiguration, DslJson mainDslJson, MAIL_API_PROVIDER mail_api_provider) {
        this.slot = slot;
        this.okHttpClient = okHttpClient;
        this.mailApiConfiguration = mailApiConfiguration;
        this.mainDslJson = mainDslJson;
        if(MAIL_API_PROVIDER.MAILGUN.equals(mail_api_provider)) {
            this.sender = new MailgunSenderImpl(this.okHttpClient, (MailgunApiConfiguration) this.mailApiConfiguration, this.mainDslJson);
        }
    }

    public SendMailResponse sendMail(MailParam mailParam) throws Exception{
        SendMailResponse sendMailResponse = this.sender.sendMail(mailParam);
        return sendMailResponse;
    }

    @Override
    public void release() {
        this.slot.release(this);
    }
}