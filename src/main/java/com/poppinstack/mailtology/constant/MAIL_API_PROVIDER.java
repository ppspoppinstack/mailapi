package com.poppinstack.mailtology.constant;

public enum MAIL_API_PROVIDER {
    MAILGUN("mailgun"),
    SENDGRID("sendgrid"),
    AMAZON_SES("amazon_ses");

    private String name;
    MAIL_API_PROVIDER(String name){
        this.name = name;
    }

    public static MAIL_API_PROVIDER lookup(String providerName){
        if("mailgun".equals(providerName)){
            return MAILGUN;
        }else if("sendgrid".equals(providerName)){
            return SENDGRID;
        }else if("amazon_ses".equals(providerName)){
            return AMAZON_SES;
        }
        return null;
    }
}
