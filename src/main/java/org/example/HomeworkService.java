package org.example;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.LocalTime.of;

public class HomeworkService {
    private static final int WORKING_HOURS_PER_DAY = 8;
    private static final LocalTime WORKING_HOURS_START = of(9, 0);
    private static final LocalTime WORKING_HOURS_END = of(17, 0);

    public LocalDateTime calculateDueDate(LocalDateTime submitDateTime, int turnaroundTime) {
        validateSubmitDateTime(submitDateTime);
        validateTurnaroundTime(turnaroundTime);

        LocalDateTime dueDateTime = submitDateTime;
        int remainingDays = turnaroundTime / WORKING_HOURS_PER_DAY;
        int remainingHours = turnaroundTime % WORKING_HOURS_PER_DAY;

        while (remainingDays > 0 || remainingHours > 0) {
            if (remainingDays > 0) {
                dueDateTime = incrementNextWorkingDay(dueDateTime);
                remainingDays--;
            }
            if (remainingHours > 0) {
                dueDateTime = incrementNextWorkingHour(dueDateTime);
                remainingHours--;
            }
        }
        return dueDateTime;
    }

    private void validateSubmitDateTime(LocalDateTime submitDateTime) {
        if (isNonWorkingHour(submitDateTime)) {
            throw new IllegalArgumentException("Submit date/time should be within working hours and on a working day.");
        }
    }

    private void validateTurnaroundTime(int turnaroundTime) {
        if (turnaroundTime <= 0) {
            throw new IllegalArgumentException("Turnaround time should be a positive value.");
        }
    }

    private boolean isNonWorkingHour(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();

        boolean isBeforeWorkingHours = time.isBefore(WORKING_HOURS_START);
        boolean isAfterWorkingHours = time.isAfter(WORKING_HOURS_END);
        boolean isWeekend = dayOfWeek == SATURDAY || dayOfWeek == SUNDAY;

        return isBeforeWorkingHours || isAfterWorkingHours || isWeekend;
    }

    private LocalDateTime incrementNextWorkingDay(LocalDateTime dateTime) {
        LocalDateTime nextDay = dateTime.plusDays(1);
        while (isNonWorkingHour(nextDay)) {
            nextDay = nextDay.plusDays(1);
        }
        return nextDay;
    }

    private LocalDateTime incrementNextWorkingHour(LocalDateTime dateTime) {
        LocalDateTime nextHour = dateTime.plusHours(1);
        while (isNonWorkingHour(nextHour)) {
            nextHour = nextHour.plusHours(1);
        }
        return nextHour;
    }
}
