package com.airfranceklm.fasttrack.assignment.resources;

import com.airfranceklm.fasttrack.assignment.model.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
public class Holiday {
    @Id
    private final String holidayId;
    private String holidayLabel;
    private String employeeId;
    private LocalDateTime startOfHoliday;
    private LocalDateTime endOfHoliday;
    private Status status;

    public Holiday() {
        this.holidayId = String.valueOf(UUID.randomUUID());
    }

    public Holiday(String holidayLabel, String employeeId, String startOfHoliday, String endOfHoliday, Status status) {
        this.holidayId = String.valueOf(UUID.randomUUID());
        this.holidayLabel = holidayLabel;
        this.employeeId = employeeId;
        this.startOfHoliday = LocalDateTime.parse(startOfHoliday, DateTimeFormatter.ISO_DATE_TIME);
        this.endOfHoliday = LocalDateTime.parse(endOfHoliday, DateTimeFormatter.ISO_DATE_TIME);
        this.status = status;
    }

    public String getHolidayId() {
        return holidayId;
    }

    public String getHolidayLabel() {
        return holidayLabel;
    }

    public void setHolidayLabel(String holidayLabel) {
        this.holidayLabel = holidayLabel;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDateTime getStartOfHoliday() {
        return startOfHoliday;
    }

    public void setStartOfHoliday(String startOfHoliday) {
        this.startOfHoliday = LocalDateTime.parse(startOfHoliday, DateTimeFormatter.ISO_DATE_TIME);
    }

    public LocalDateTime getEndOfHoliday() {
        return endOfHoliday;
    }

    public void setEndOfHoliday(String endOfHoliday) {
        this.endOfHoliday = LocalDateTime.parse(endOfHoliday, DateTimeFormatter.ISO_DATE_TIME);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getWorkingDayDifference(LocalDateTime date1, LocalDateTime date2) {
        return date1.toLocalDate().until(date2.toLocalDate(), ChronoUnit.DAYS);
    }

    public boolean newDateIsBetweenCurrentHoliday(LocalDateTime dateTime) {
        return (dateTime.isAfter(this.getStartOfHoliday()) && dateTime.isBefore(this.getEndOfHoliday())) ||
                dateTime.isEqual(this.getStartOfHoliday()) || dateTime.isEqual(this.getEndOfHoliday());
    }

    public boolean isWithinHolidayGap(Holiday newHoliday) {
        final int GAP_WORKING_DAYS = 3;

        LocalDateTime holidayStart = this.getStartOfHoliday();
        LocalDateTime holidayEnd = this.getEndOfHoliday();
        LocalDateTime newHolidayStart = newHoliday.getStartOfHoliday();
        LocalDateTime newHolidayEnd = newHoliday.getEndOfHoliday();

        return this.newDateIsBetweenCurrentHoliday(newHoliday.getStartOfHoliday()) ||
                this.newDateIsBetweenCurrentHoliday(newHoliday.getEndOfHoliday()) ||
                (newHolidayStart.isBefore(holidayStart) && Math.abs(this.getWorkingDayDifference(newHolidayStart, holidayStart)) < GAP_WORKING_DAYS) ||
                (newHolidayEnd.isBefore(holidayEnd) && Math.abs(this.getWorkingDayDifference(newHolidayEnd, holidayEnd)) < GAP_WORKING_DAYS);

    }

    public boolean startDateIsWithinWorkingDays(LocalDateTime newHolidayStartDate) {
        if (this.getStartOfHoliday() == null || newHolidayStartDate == null) return false;

        final int BEFORE_START_WORKING_DAYS = 5;

        // Compare the new holiday with the current holiday and get the difference in days
        long differenceInDays = this.getWorkingDayDifference(newHolidayStartDate,this.getStartOfHoliday());

        return differenceInDays >= BEFORE_START_WORKING_DAYS;
    }
}
