package com.poppinstack.mailtology.model;

public class MailAttachment {
    String name;
    byte[] attachmentData;
    String attachmentType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getAttachmentData() {
        return attachmentData;
    }

    public void setAttachmentData(byte[] attachmentData) {
        this.attachmentData = attachmentData;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }
}