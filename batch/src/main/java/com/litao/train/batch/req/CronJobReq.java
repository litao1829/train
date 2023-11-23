package com.litao.train.batch.req;

import lombok.Data;
import org.bouncycastle.jce.provider.JCEDHPrivateKey;

@Data
public class CronJobReq {
    private String group;

    private String name;

    private String description;

    private String cronExpression;
}
