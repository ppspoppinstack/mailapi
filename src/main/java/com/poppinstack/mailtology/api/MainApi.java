package com.poppinstack.mailtology.api;

import com.dslplatform.json.DslJson;
import com.poppinstack.mailtology.configuration.MailApiConfiguration;
import com.poppinstack.mailtology.configuration.MailgunApiConfiguration;
import com.poppinstack.mailtology.constant.MAIL_API_PROVIDER;
import com.poppinstack.mailtology.model.MailParam;
import com.poppinstack.mailtology.model.SendMailResponse;
import com.poppinstack.mailtology.sender.MailgunSenderImpl;
import com.poppinstack.mailtology.sender.Sender;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stormpot.Poolable;
import stormpot.Slot;

public class MainApi implements Poolable {
    private Slot slot;
    private OkHttpClient okHttpClient;
    private MailApiConfiguration mailApiConfiguration;
    private Sender sender;
    private DslJson mainDslJson;
    private MAIL_API_PROVIDER mail_api_provider;

    private static final Logger logger = LoggerFactory.getLogger(MainApi.class);

    public MainApi(Slot slot, OkHttpClient okHttpClient, MailApiConfiguration mailApiConfiguration, DslJson mainDslJson, MAIL_API_PROVIDER mail_api_provider) {
        this.slot = slot;
        this.okHttpClient = okHttpClient;
        this.mailApiConfiguration = mailApiConfiguration;
        this.mainDslJson = mainDslJson;
        logger.info("MAIL API PROVIDER : {} ",mail_api_provider);
        this.mail_api_provider = mail_api_provider;
        if(MAIL_API_PROVIDER.MAILGUN.equals(mail_api_provider)) {
            this.sender = new MailgunSenderImpl(this.okHttpClient, (MailgunApiConfiguration) this.mailApiConfiguration, this.mainDslJson);
        }
    }

    public SendMailResponse sendMail(MailParam mailParam) throws Exception{
        logger.info("MAIL API PROVIDER : {} ",mail_api_provider);
        SendMailResponse sendMailResponse = this.sender.sendMail(mailParam);
        return sendMailResponse;
    }

    @Override
    public void release() {
        this.slot.release(this);
    }
}