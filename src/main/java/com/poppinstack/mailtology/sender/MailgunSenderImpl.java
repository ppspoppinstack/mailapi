package com.poppinstack.mailtology.sender;

import com.dslplatform.json.DslJson;
import com.poppinstack.mailtology.configuration.MailgunApiConfiguration;
import com.poppinstack.mailtology.model.MailAttachment;
import com.poppinstack.mailtology.model.MailParam;
import com.poppinstack.mailtology.model.MailgunSendEmailResponse;
import com.poppinstack.mailtology.model.SendMailResponse;
import okhttp3.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MailgunSenderImpl extends SenderAbstract<MailgunApiConfiguration> implements  MailgunSender{
    private static final Logger logger = LoggerFactory.getLogger(MailgunSenderImpl.class);
    private static final boolean isDebugEnabled = logger.isDebugEnabled();

    public MailgunSenderImpl(OkHttpClient okHttpClient, MailgunApiConfiguration configuration, DslJson mainDslJson) {
        super(okHttpClient, configuration,mainDslJson);
    }

    @Override
    protected SendMailResponse performSendMail(MailParam mailParam, OkHttpClient okHttpClient, MailgunApiConfiguration mailApiConfiguration,DslJson mainDslJson) throws Exception {
        logger.info("Start send mail by Mailgun");
        String domainName = mailApiConfiguration.getDomainName();
        String apiKey = mailApiConfiguration.getApiKey();
        StringBuilder requestUrlStringBuilder = new StringBuilder();
        requestUrlStringBuilder.append("https://api.mailgun.net/v3/");
        requestUrlStringBuilder.append(domainName);
        requestUrlStringBuilder.append("/messages");
        String requestUrl = requestUrlStringBuilder.toString();
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(requestUrl);
        String apiAuth = Credentials.basic("api",apiKey);
        requestBuilder.addHeader("Authorization",apiAuth);
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        String sendSubject = mailParam.getSubject();
        multipartBodyBuilder.addFormDataPart("subject",sendSubject);
        String sendFrom = mailParam.getSendFrom();
        multipartBodyBuilder.addFormDataPart("from",sendFrom);
        List<String> sendTo = mailParam.getSendTo();
        if(CollectionUtils.isNotEmpty(sendTo)){
            String sendToStr = StringUtils.join(sendTo,",");
            multipartBodyBuilder.addFormDataPart("to",sendToStr);
        }
        List<String> cc = mailParam.getCc();
        if(CollectionUtils.isNotEmpty(cc)){
            String ccStr = StringUtils.join(cc,",");
            multipartBodyBuilder.addFormDataPart("cc",ccStr);
        }
        List<String> bcc = mailParam.getCc();
        if(CollectionUtils.isNotEmpty(bcc)){
            String bccStr = StringUtils.join(bcc,",");
            multipartBodyBuilder.addFormDataPart("bcc",bccStr);
        }
        String body = mailParam.getBody();
        multipartBodyBuilder.addFormDataPart("html",body);
        List<MailAttachment> attachments = mailParam.getAttachments();
        if(CollectionUtils.isNotEmpty(attachments)){
            for(MailAttachment attachment:attachments){
                String fileName = attachment.getName();
                String fileType = attachment.getAttachmentType();
                byte[] dataByte = attachment.getAttachmentData();
                RequestBody attachmentBody = RequestBody.create(dataByte,MediaType.parse(fileType));
                multipartBodyBuilder.addFormDataPart("attachment",fileName,attachmentBody);
            }
        }
        if(isDebugEnabled){
            logger.debug("TO : {} ",sendTo);
            logger.debug("CC : {} ",cc);
            logger.debug("BCC : {} ",bcc);
            logger.debug("BODY : {} ",body);
        }

        MultipartBody multipartBody = multipartBodyBuilder.build();
        requestBuilder.post(multipartBody);
        Request request = requestBuilder.build();
        MailgunSendEmailResponse sendMailResponse = new MailgunSendEmailResponse();
        try(
                Response response = okHttpClient.newCall(request).execute();

                //InputStream inputStream = response.body().byteStream()
        ){
            byte[] responsByte = response.body().bytes();
            logger.info("responsByte : {} ",new String(responsByte));
            int responseCode = response.code();
            Map dataMap = (Map) mainDslJson.deserialize(LinkedHashMap.class,responsByte,responsByte.length);
            String responseId = (String) dataMap.get("id");
            String responseMessage = (String) dataMap.get("message");
            sendMailResponse.setId(responseId);
            sendMailResponse.setMessage(responseMessage);
            sendMailResponse.setResponseCode(String.valueOf(responseCode));
            sendMailResponse.setResponseMessage(responseMessage);
        }
        if(isDebugEnabled){
            logger.debug("Response id : {} ",sendMailResponse.getId());
            logger.debug("Response code : {} ",sendMailResponse.getResponseCode());
            logger.debug("Response Message : {} ",sendMailResponse.getMessage());
        }
        return sendMailResponse;
    }
}