package org.siemens.mindverse.repos;

import org.springframework.stereotype.Repository;
import org.siemens.mindverse.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {

}
