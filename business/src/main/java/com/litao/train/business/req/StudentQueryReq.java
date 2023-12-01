package com.litao.train.business.req;

import com.litao.req.PageReq;

public class StudentQueryReq extends PageReq {
    String name;
    String school;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
public String toString() {
return "StudentQueryReq{" +
"} " + super.toString();
}
}