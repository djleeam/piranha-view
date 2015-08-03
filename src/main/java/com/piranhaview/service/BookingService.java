package com.piranhaview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.piranhaview.data.BookingRepository;
import com.piranhaview.domain.Boat;
import com.piranhaview.domain.Booking;
import com.piranhaview.domain.TimeSlot;

@Service
public class BookingService implements IBookingService {

	private final BookingRepository bookingRepository;
	private final AssignmentService assignmentService;
    private final TimeSlotService timeSlotService;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
    		AssignmentService assignmentService, TimeSlotService timeSlotService) {
        this.bookingRepository = bookingRepository;
        this.assignmentService = assignmentService;
        this.timeSlotService = timeSlotService;
    }

	@Override
	public Booking create(Booking booking) {
		Booking booked = bookingRepository.save(booking);
        processBooking(booked);
        return booked;
	}

	private void processBooking(Booking booked) {
		TimeSlot timeSlot = timeSlotService.findOne(booked.getTimeslotId());

		int custCount = 0;
		Boat boat = (!timeSlot.getBoatQueue().isEmpty()) ? timeSlot.getBoatQueue().remove() : null;
		if (boat != null && boat.getCapacity() >= booked.getSize()) {
			boat.setCapacity(boat.getCapacity() - booked.getSize());
			timeSlot.getBoatQueue().add(boat);

			// un-assign boat from other time slots
			assignmentService.removeBoatAssignments(boat.getId(), timeSlot.getId());

			custCount += booked.getSize();
		}

		timeSlot.setCustomerCount(custCount);
		timeSlotService.save(timeSlot);
	}

	@Override
	public Booking findOne(Long id) {
		return bookingRepository.findOne(id);
	}

	@Override
	public List<Booking> findAll() {
		return Lists.newArrayList(bookingRepository.findAll());
	}
}
