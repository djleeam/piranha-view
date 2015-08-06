package com.piranhaview.data;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.piranhaview.domain.Assignment;

@RepositoryRestResource(collectionResourceRel = "assignments", path = "assignments")
public interface AssignmentRepository extends PagingAndSortingRepository<Assignment, Long> {

	@Transactional
	@Modifying
	@Query("DELETE FROM Assignment WHERE boatId = :boatId AND timeSlotId != :timeSlotId")
	void removeAssignments(@Param("boatId") long boatId, @Param("timeSlotId") long timeSlotId);
}
