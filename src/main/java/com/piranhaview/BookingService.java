package com.piranhaview;

import java.util.List;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class BookingService implements IBookingService {

	private final BookingRepository bookingRepository;
    private final TimeSlotRepository timeSlotRepository;
	private final AssignmentService assignmentService;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
    		TimeSlotRepository timeSlotRepository, AssignmentService assignmentService) {
        this.bookingRepository = bookingRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.assignmentService = assignmentService;
    }

	@Override
	public Booking create(Booking booking) {
		Booking booked = bookingRepository.save(booking);
        processBooking(booked);
        return booked;
	}

	private void processBooking(Booking booked) {
		TimeSlot timeSlot = timeSlotRepository.findOne(booked.getTimeslotId());

		PriorityQueue<Boat> boatQueue = new PriorityQueue<Boat>();

		for (Boat boat : timeSlot.getBoats()) {
			boatQueue.add(boat);
		}
		
		int custCount = 0;
		Boat boat = (!boatQueue.isEmpty()) ? boatQueue.remove() : null;
		if (boat != null && boat.getCapacity() >= booked.getSize()) {
			boat.setCapacity(boat.getCapacity() - booked.getSize());
			boatQueue.add(boat);

			// un-assign boat from other time slots
			assignmentService.removeBoatAssignments(boat.getId(), timeSlot.getId());

			custCount += booked.getSize();
		}
		
		int available = 0;
		if (boatQueue.peek() != null) {
			available = boatQueue.peek().getCapacity();
		}

		timeSlot.setAvailability(available);
		timeSlot.setCustomerCount(custCount);

		timeSlotRepository.save(timeSlot);
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
