package com.piranhaview.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.piranhaview.domain.Booking;

@RepositoryRestResource(collectionResourceRel = "bookings", path = "bookings")
public interface BookingRepository extends PagingAndSortingRepository<Booking, Long> {
	// add search methods here
}
