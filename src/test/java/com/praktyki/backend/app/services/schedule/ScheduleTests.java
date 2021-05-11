package com.praktyki.backend.app.services.schedule;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.services.schedule.ScheduleService;
import com.praktyki.backend.business.entities.dates.MonthlyDateScheduleCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootTest(classes = {
        ScheduleService.class,
        MonthlyDateScheduleCalculator.class
})
public class ScheduleTests {

    @Autowired
    private ScheduleService mScheduleService;

    private ObjectMapper mObjectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

    @Test
    public void test() throws IOException {
        List<ScheduleTestCase> testCases = mObjectMapper
                .readValue(
                        new File("src/test/resources/ScheduleTests.json"),
                        new TypeReference<List<ScheduleTestCase>>() {}
                );

        testCases.forEach(this::testSingleTestCase);
    }

    public void testSingleTestCase(ScheduleTestCase testCase) {
        List<Installment> installments = mScheduleService.createSchedule(testCase.configuration);

        Assertions.assertEquals(installments.size(), testCase.installments.size());

        for(int i = 0; i < installments.size(); i++) {
            Assertions.assertEquals(testCase.installments.get(i), installments.get(i));
        }

    }

}
