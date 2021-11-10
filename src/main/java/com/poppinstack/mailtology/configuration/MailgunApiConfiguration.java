package com.poppinstack.mailtology.configuration;

public class MailgunApiConfiguration extends MailApiConfiguration {
    private String domainName;
    private String apiKey;

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
}
