package org.siemens.mindverse.repos;

import org.siemens.mindverse.model.Aspect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AspectRepository  extends JpaRepository<Aspect, Long> {
	

}
