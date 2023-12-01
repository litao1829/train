package com.litao.train.business.config;

import com.github.javafaker.Faker;
import com.litao.train.business.domain.Student;
import com.litao.train.business.mapper.StudentMapper;
import com.litao.util.SnowUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentServiceTest {
    @Resource
    private StudentMapper studentMapper;

    @Test
    void save() {
        Faker faker = new Faker(new Locale("zh-CN"));
        Faker faker1=new Faker();
        for(int i=0;i<100000;i++){
            Student student=new Student();
            student.setId(SnowUtil.getSnowflakeNextId());
            student.setAge(new Random().nextInt(30)+1);
            student.setName(faker.name().fullName());
            student.setPhone(faker.phoneNumber().cellPhone());
            student.setEmail(faker1.internet().emailAddress());
            student.setSchool(faker.university().name());
            studentMapper.insert(student);
        }
    }
}