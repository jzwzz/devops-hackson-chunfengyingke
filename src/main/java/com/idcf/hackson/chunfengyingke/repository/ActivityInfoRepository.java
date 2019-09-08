package com.idcf.hackson.chunfengyingke.repository;

import com.idcf.hackson.chunfengyingke.domain.ActivityInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ActivityInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityInfoRepository extends JpaRepository<ActivityInfo, Long> {

}
