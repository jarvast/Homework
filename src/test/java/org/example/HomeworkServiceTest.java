package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HomeworkServiceTest {

    private final HomeworkService homeworkService = new HomeworkService();

    @Test
    public void calculateDueDate_success_happyPath() {
        // when
        LocalDateTime actual = homeworkService.calculateDueDate(of(2023, 6, 6, 14, 12), 18);

        // then
        assertEquals(of(2023, 6, 8, 16, 12), actual);
    }

    @Test
    public void calculateDueDate_success_dueDateOnNextDay() {
        // when
        LocalDateTime actual = homeworkService.calculateDueDate(of(2023, 6, 6, 14, 12), 7);

        // then
        assertEquals(of(2023, 6, 7, 13, 12), actual);
    }

    @Test
    public void calculateDueDate_success_dueDateOnNextWeek() {
        // when
        LocalDateTime actual = homeworkService.calculateDueDate(of(2023, 6, 6, 14, 12), 40);

        // then
        assertEquals(of(2023, 6, 13, 14, 12), actual);
    }

    @Test
    public void calculateDueDate_illegalArgumentException_invalidTurnaroundTime() {
        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> homeworkService.calculateDueDate(of(2023, 6, 6, 14, 12), 0));

        // then
        assertEquals("Turnaround time should be a positive value.", exception.getMessage());
    }

    @Test
    public void calculateDueDate_illegalArgumentException_submitDateNotInWorkingHours() {
        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> homeworkService.calculateDueDate(of(2023, 6, 6, 17, 12), 1));

        // then
        assertEquals("Submit date/time should be within working hours and on a working day.", exception.getMessage());
    }

    @Test
    public void calculateDueDate_illegalArgumentException_submitDateNotInWorkingDays() {
        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> homeworkService.calculateDueDate(of(2023, 6, 10, 14, 12), 0));

        // then
        assertEquals("Submit date/time should be within working hours and on a working day.", exception.getMessage());
    }
}