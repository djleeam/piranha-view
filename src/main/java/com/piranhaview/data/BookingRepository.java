package com.piranhaview.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.piranhaview.domain.Booking;

@Repository
public interface BookingRepository extends PagingAndSortingRepository<Booking, Long> {
	// add search methods here
}
