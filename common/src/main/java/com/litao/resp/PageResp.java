package com.litao.resp;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class PageResp<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -3643551489985850622L;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 当前页的列表
     */
    private List<T> list;
}
