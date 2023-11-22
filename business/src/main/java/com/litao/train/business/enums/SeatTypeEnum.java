package com.litao.train.business.enums;

import cn.hutool.core.lang.hash.Hash;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

@Getter
public enum SeatTypeEnum {
    YDZ("1","一等座",new BigDecimal("0.4")),
    EDZ("2","二等座",new BigDecimal("0.3")),
    PW("3","软卧",new BigDecimal("0.6")),
    YM("4","硬卧",new BigDecimal("0.5"));

    private String code;

    private String desc;

    /**
     * 基础票价 N元/公里，0.4即为0.4元/公里
     */
    private BigDecimal price;

    SeatTypeEnum(String code, String desc, BigDecimal price) {
        this.code = code;
        this.desc = desc;
        this.price = price;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SeatTypeEnum{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", price=" + price +
                '}';
    }

    public static List<HashMap<String,String>> getEnumList(){
        List<HashMap<String,String>> list=new ArrayList<>();
        for (SeatTypeEnum anEnum: EnumSet.allOf(SeatTypeEnum.class)){
            HashMap<String,String> map=new HashMap<>();
            map.put("code",anEnum.getCode());
            map.put("desc", anEnum.getDesc());
            list.add(map);
        }
        return list;
    }

    public static SeatTypeEnum getEnumByCode(String code){
        for (SeatTypeEnum anEnum:SeatTypeEnum.values()){
            if(anEnum.getCode().equals(code)){
                return anEnum;
            }
        }
        return null;
    }
}
