package com.piranhaview;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "boats", path = "boats")
public interface BoatRepository extends PagingAndSortingRepository<Boat, Long> {
	// add search methods here
}
