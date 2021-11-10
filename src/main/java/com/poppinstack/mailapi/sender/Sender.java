package com.poppinstack.mailapi.sender;

import com.poppinstack.mailapi.model.MailParam;
import com.poppinstack.mailapi.model.SendMailResponse;

public interface Sender {
    SendMailResponse sendMail(MailParam mailParam) throws Exception;
}
