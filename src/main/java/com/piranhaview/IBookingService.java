package com.piranhaview;

import java.util.List;

public interface IBookingService {

	Booking create(Booking booking);

	Booking findOne(Long id);

	List<Booking> findAll();

}
