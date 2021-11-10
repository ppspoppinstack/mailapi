package com.poppinstack.mailtology.pool;

import com.poppinstack.mailtology.api.MainApi;
import stormpot.Config;

public class MailApiPool extends PoolAbstract<MainApi> {

    public MailApiPool(Config<MainApi> config) {
        super(config);
    }
}
