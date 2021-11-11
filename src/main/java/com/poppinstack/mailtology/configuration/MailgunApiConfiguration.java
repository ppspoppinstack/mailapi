package com.poppinstack.mailtology.configuration;

import com.poppinstack.mailtology.constant.MAILGUN_DOMAIN_REGION;

public class MailgunApiConfiguration extends MailApiConfiguration {
    private String domainName;
    private String apiKey;
    private MAILGUN_DOMAIN_REGION domainRegion;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public MAILGUN_DOMAIN_REGION getDomainRegion() {
        return domainRegion;
    }

    public void setDomainRegion(MAILGUN_DOMAIN_REGION domainRegion) {
        this.domainRegion = domainRegion;
    }
}
