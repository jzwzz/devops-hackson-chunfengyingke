package com.idcf.hackson.chunfengyingke.service.impl;

import com.idcf.hackson.chunfengyingke.service.ActivityInfoService;
import com.idcf.hackson.chunfengyingke.domain.ActivityInfo;
import com.idcf.hackson.chunfengyingke.repository.ActivityInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ActivityInfo}.
 */
@Service
@Transactional
public class ActivityInfoServiceImpl implements ActivityInfoService {

    private final Logger log = LoggerFactory.getLogger(ActivityInfoServiceImpl.class);

    private final ActivityInfoRepository activityInfoRepository;

    public ActivityInfoServiceImpl(ActivityInfoRepository activityInfoRepository) {
        this.activityInfoRepository = activityInfoRepository;
    }

    /**
     * Save a activityInfo.
     *
     * @param activityInfo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ActivityInfo save(ActivityInfo activityInfo) {
        log.debug("Request to save ActivityInfo : {}", activityInfo);
        return activityInfoRepository.save(activityInfo);
    }

    /**
     * Get all the activityInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ActivityInfo> findAll(Pageable pageable) {
        log.debug("Request to get all ActivityInfos");
        return activityInfoRepository.findAll(pageable);
    }


    /**
     * Get one activityInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ActivityInfo> findOne(Long id) {
        log.debug("Request to get ActivityInfo : {}", id);
        return activityInfoRepository.findById(id);
    }

    /**
     * Delete the activityInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActivityInfo : {}", id);
        activityInfoRepository.deleteById(id);
    }
}
