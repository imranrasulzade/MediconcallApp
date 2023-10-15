package com.matrix.mediconcallapp.service.utility;

import com.matrix.mediconcallapp.model.dto.response.TimeDto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimeUtility {
    public static List<TimeDto> getAllTimes() {
        List<TimeDto> timeDtoList = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now().plusDays(1);
        LocalDateTime end = now.plusWeeks(1);
        LocalDateTime current = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 9, 0);
        while (current.isBefore(end) && current.getHour() < 18) {
            if (current.getDayOfWeek() != DayOfWeek.SUNDAY) {
                TimeDto t = new TimeDto();
                t.setDay(current.getDayOfMonth());
                t.setHour(current.getHour());
                t.setMonth(current.getMonthValue());

                current = current.plusHours(1);
                if (current.getHour() == 13) {
                    current = current.plusHours(1);
                }
                if (current.getHour() == 18) {
                    current = current.plusDays(1).withHour(9).withMinute(0);
                }
                timeDtoList.add(t);
            }else{
                // Bazar gununu at
                current = current.plusDays(1).withHour(9).withMinute(0);
            }
        }
        return timeDtoList;
    }

}
