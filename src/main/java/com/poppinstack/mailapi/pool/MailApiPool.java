package com.poppinstack.mailapi.pool;

import com.poppinstack.mailapi.api.MainApi;
import stormpot.Config;

public class MailApiPool extends PoolAbstract<MainApi> {

    public MailApiPool(Config<MainApi> config) {
        super(config);
    }
}
