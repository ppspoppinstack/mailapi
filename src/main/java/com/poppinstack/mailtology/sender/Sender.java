package com.poppinstack.mailtology.sender;

import com.poppinstack.mailtology.model.MailParam;
import com.poppinstack.mailtology.model.SendMailResponse;

public interface Sender {
    SendMailResponse sendMail(MailParam mailParam) throws Exception;
}
