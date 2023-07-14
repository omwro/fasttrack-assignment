package com.airfranceklm.fasttrack.assignment.service;

import com.airfranceklm.fasttrack.assignment.repository.IHolidayRepository;
import com.airfranceklm.fasttrack.assignment.resources.Holiday;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HolidayService {

    private final IHolidayRepository holidayRepository;

    public List<Holiday> getHolidaysOverview() {
        return this.holidayRepository.findAll();
    }

    public Holiday scheduleHoliday(Holiday holiday) {
        if (!holiday.startDateIsWithinWorkingDays(LocalDateTime.now())) return null; // Return error, A holiday must be planned at least 5 working days before the start date.

        boolean isHolidayAlreadyTaken = this.getHolidaysOverview()
                .stream()
                .anyMatch(x -> x.isWithinHolidayGap(holiday));

        if (isHolidayAlreadyTaken) return null; // Return error, There should be a gap of at least 3 working days between holidays

        return this.holidayRepository.save(holiday);
    }

    public Holiday cancelHoliday(String holidayId) {
        Optional<Holiday> optionalHoliday = this.holidayRepository.findById(holidayId);
        if (!optionalHoliday.isPresent()) return null; // Return error, The following holiday does not exist
        Holiday holiday = optionalHoliday.get();
        if (!holiday.startDateIsWithinWorkingDays(LocalDateTime.now())) return null; // Return error, A holiday must be planned at least 5 working days before the start date.
        this.holidayRepository.delete(holiday);
        return holiday;
    }
}
