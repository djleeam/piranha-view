package com.piranhaview;

import java.util.PriorityQueue;

import javax.persistence.PostLoad;

public class TimeSlotEventListener {
	private PriorityQueue<Boat> boatQueue = new PriorityQueue<Boat>();

	@PostLoad
	public void postLoad(Object object) {
		TimeSlot timeSlot = (TimeSlot) object;

		for (Boat boat : timeSlot.getBoats()) {
			if (!boatQueue.contains(boat)) {
				boatQueue.add(boat);
			}
		}

		int custCount = 0;
		for (Booking booking : timeSlot.getBookings()) {
			Boat boat = boatQueue.remove();
			if (boat != null && boat.getCapacity() >= booking.getSize()) {
				boat.setCapacity(boat.getCapacity() - booking.getSize());
				boatQueue.add(boat);
				custCount += booking.getSize();
			}
		}

		int available = 0;
		if (boatQueue.peek() != null) {
			available = boatQueue.peek().getCapacity();
		}

		timeSlot.setAvailability(available);
		timeSlot.setCustomerCount(custCount);
	}
}
