package com.idcf.hackson.chunfengyingke.service;

import com.idcf.hackson.chunfengyingke.domain.ActivityInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ActivityInfo}.
 */
public interface ActivityInfoService {

    /**
     * Save a activityInfo.
     *
     * @param activityInfo the entity to save.
     * @return the persisted entity.
     */
    ActivityInfo save(ActivityInfo activityInfo);

    /**
     * Get all the activityInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ActivityInfo> findAll(Pageable pageable);


    /**
     * Get the "id" activityInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ActivityInfo> findOne(Long id);

    /**
     * Delete the "id" activityInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
