package com.airfranceklm.fasttrack.assignment.controller;

import java.util.List;

import com.airfranceklm.fasttrack.assignment.resources.Holiday;
import com.airfranceklm.fasttrack.assignment.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/holidays")
public class HolidaysApi {

    @Autowired
    private HolidayService holidayService;

    @GetMapping(path = "")
    @ResponseBody
    public List<Holiday> getHolidaysOverview() {
        return this.holidayService.getHolidaysOverview();
    }

    @PostMapping(path = "")
    @ResponseBody
    public Holiday scheduleHoliday(@RequestBody Holiday holiday) {
        return this.holidayService.scheduleHoliday(holiday);
    }

    @DeleteMapping(path = "{holidayId}")
    @ResponseBody
    public Holiday cancelHoliday(@PathVariable String holidayId) {
        return this.holidayService.cancelHoliday(holidayId);
    }
}
