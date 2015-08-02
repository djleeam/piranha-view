package com.piranhaview.service;

import java.util.List;

import com.piranhaview.domain.Booking;

public interface IBookingService {

	Booking create(Booking booking);

	Booking findOne(Long id);

	List<Booking> findAll();

}
