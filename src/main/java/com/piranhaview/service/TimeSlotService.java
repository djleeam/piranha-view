package com.piranhaview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.piranhaview.data.TimeSlotRepository;
import com.piranhaview.domain.TimeSlot;

@Service
public class TimeSlotService implements ITimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public TimeSlotService(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

	@Override
	public TimeSlot create(TimeSlot timeSlot) {
		return timeSlotRepository.save(timeSlot);
	}

	@Override
	public TimeSlot findOne(Long id) {
		return timeSlotRepository.findOne(id);
	}

	@Override
	public List<TimeSlot> findAll() {
		return Lists.newArrayList(timeSlotRepository.findAll());
	}

	@Override
	public List<TimeSlot> findByStartTime(String startTime) {
		return Lists.newArrayList(timeSlotRepository.findByStartTime(startTime));
	}

	@Override
	public List<TimeSlot> findByDuration(Long duration) {
		return Lists.newArrayList(timeSlotRepository.findByDuration(duration));
	}

	@Override
	public void save(TimeSlot timeSlot) {
		timeSlotRepository.save(timeSlot);
	}

	@Override
	public void updateTimeSlotAvailability(Long id) {
		TimeSlot timeSlot = timeSlotRepository.findOne(id);
		updateAvailability(timeSlot);
	}

	@Override
	public void updateTimeSlotAvailabilities() {
		for (TimeSlot timeSlot : timeSlotRepository.findAll()) {
			updateAvailability(timeSlot);
		}
	}

	private void updateAvailability(TimeSlot timeSlot) {
		int maxAvailable = 0;

		// retrieve capacity of largest available boat
		if (!timeSlot.getBoatQueue().isEmpty()) {
			maxAvailable = timeSlot.getBoatQueue().peek().getCapacity();
		}

		timeSlot.setAvailability(maxAvailable);
		timeSlotRepository.save(timeSlot);
	}

	@Override
	public void removeOne(Long id) {
		timeSlotRepository.delete(id);
	}
}
