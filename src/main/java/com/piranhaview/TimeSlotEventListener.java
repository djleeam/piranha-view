package com.piranhaview;

import java.util.List;

import javax.persistence.PostLoad;

public class TimeSlotEventListener {

	@PostLoad
	public void postLoad(Object object) {
		TimeSlot timeSlot = (TimeSlot) object;

		List<Booking> bookings = timeSlot.getBookings();
		List<Boat> boats = timeSlot.getBoats();

		int custCount = 0;
		int available = 0;

		if (bookings == null || bookings.isEmpty()) {
			if (boats != null && !boats.isEmpty()) {
				for (Boat boat : boats) {
					if (boat.getCapacity() > available) {
						available = boat.getCapacity();
					}
				}
			}
		}

		if (bookings != null && !bookings.isEmpty()) {
			for (Booking booking : bookings) {
				if (boats != null && !boats.isEmpty()) {
					for (Boat boat : boats) {
						if (boat.getCapacity() >= booking.getSize()) {
							available = boat.getCapacity() - booking.getSize();
							boat.setCapacity(boat.getCapacity() - booking.getSize());
							custCount += booking.getSize();
						}
						
						available = Math.max(available, boat.getCapacity());
					}
				}
			}
		}

		timeSlot.setCustomerCount(custCount);
		timeSlot.setAvailability(available);
	}

}
