package com.airfranceklm.fasttrack.assignment.repository;

import com.airfranceklm.fasttrack.assignment.resources.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHolidayRepository extends JpaRepository<Holiday, String> { }
