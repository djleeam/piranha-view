package com.piranhaview.service;

import java.util.List;

import com.piranhaview.domain.TimeSlot;

public interface ITimeSlotService {

	TimeSlot create(TimeSlot timeSlot);

	TimeSlot findOne(Long id);

	List<TimeSlot> findAll();

	void save(TimeSlot timeSlot);

	void updateTimeSlotAvailability(Long timeSlotId);

	void updateTimeSlotAvailabilities();

}
