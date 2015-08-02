package com.piranhaview;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class BookingService implements IBookingService {

	private final BookingRepository repository;

    @Autowired
    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

	@Override
	public Booking create(Booking booking) {
        return repository.save(booking);
	}

	@Override
	public Booking findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<Booking> findAll() {
		return Lists.newArrayList(repository.findAll());
	}
}
