package com.piranhaview.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.piranhaview.domain.Boat;

@RepositoryRestResource(collectionResourceRel = "boats", path = "boats")
public interface BoatRepository extends PagingAndSortingRepository<Boat, Long> {
	// add search methods here
}
