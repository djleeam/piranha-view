package com.piranhaview;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "assignments", path = "assignments")
public interface AssignmentRepository extends PagingAndSortingRepository<Assignment, Long> {
	// add search methods here
}
