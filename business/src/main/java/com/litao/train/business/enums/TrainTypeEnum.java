package com.litao.train.business.enums;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.*;

@Getter
public enum TrainTypeEnum {
    G("G","高铁",new BigDecimal("1.2")),
    D("D","动车",new BigDecimal("1")),
    K("k","快速",new BigDecimal("0.8"));

    private String code;

    private String desc;

    /**
     * 票价比例：例：1.1，则票价=1.1*每公里单价（SeatTypeEnum.price）*公里（station.km）
     */
    private BigDecimal priceRate;

    TrainTypeEnum(String code, String desc, BigDecimal priceRate) {
        this.code = code;
        this.desc = desc;
        this.priceRate = priceRate;
    }

    @Override
    public String toString() {
        return "TrainTypeEnum{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", priceRate=" + priceRate +
                '}';
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPriceRate(BigDecimal priceRate) {
        this.priceRate = priceRate;
    }


    public static List<HashMap<String,String>> getEnumList(){
        List<HashMap<String,String>> list=new ArrayList<>();
        for (TrainTypeEnum anEnum: EnumSet.allOf(TrainTypeEnum.class)){
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("code",anEnum.getCode());
            hashMap.put("dedc",anEnum.getDesc());
            list.add(hashMap);
        }
        return list;
    }
}
