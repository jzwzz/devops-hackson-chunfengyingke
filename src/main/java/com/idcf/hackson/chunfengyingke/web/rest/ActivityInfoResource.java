package com.idcf.hackson.chunfengyingke.web.rest;

import com.idcf.hackson.chunfengyingke.domain.ActivityInfo;
import com.idcf.hackson.chunfengyingke.service.ActivityInfoService;
import com.idcf.hackson.chunfengyingke.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.idcf.hackson.chunfengyingke.domain.ActivityInfo}.
 */
@RestController
@RequestMapping("/api")
public class ActivityInfoResource {

    private final Logger log = LoggerFactory.getLogger(ActivityInfoResource.class);

    private static final String ENTITY_NAME = "activityInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivityInfoService activityInfoService;

    public ActivityInfoResource(ActivityInfoService activityInfoService) {
        this.activityInfoService = activityInfoService;
    }

    /**
     * {@code POST  /activity-infos} : Create a new activityInfo.
     *
     * @param activityInfo the activityInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activityInfo, or with status {@code 400 (Bad Request)} if the activityInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/activity-infos")
    public ResponseEntity<ActivityInfo> createActivityInfo(@RequestBody ActivityInfo activityInfo) throws URISyntaxException {
        log.debug("REST request to save ActivityInfo : {}", activityInfo);

        activityInfo.setAddDate("2019-9-8");
        activityInfo.setPayStatus("UnPayed");
        if (activityInfo.getId() != null) {
            throw new BadRequestAlertException("A new activityInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivityInfo result = activityInfoService.save(activityInfo);
        return ResponseEntity.created(new URI("/api/activity-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /activity-infos} : Updates an existing activityInfo.
     *
     * @param activityInfo the activityInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityInfo,
     * or with status {@code 400 (Bad Request)} if the activityInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activityInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/activity-infos")
    public ResponseEntity<ActivityInfo> updateActivityInfo(@RequestBody ActivityInfo activityInfo) throws URISyntaxException {
        log.debug("REST request to update ActivityInfo : {}", activityInfo);
        if (activityInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActivityInfo result = activityInfoService.save(activityInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activityInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /activity-infos} : get all the activityInfos.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activityInfos in body.
     */
    @GetMapping("/activity-infos")
    public ResponseEntity<List<ActivityInfo>> getAllActivityInfos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ActivityInfos");
        Page<ActivityInfo> page = activityInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /activity-infos/:id} : get the "id" activityInfo.
     *
     * @param id the id of the activityInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activityInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/activity-infos/{id}")
    public ResponseEntity<ActivityInfo> getActivityInfo(@PathVariable Long id) {
        log.debug("REST request to get ActivityInfo : {}", id);
        Optional<ActivityInfo> activityInfo = activityInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activityInfo);
    }

    /**
     * {@code DELETE  /activity-infos/:id} : delete the "id" activityInfo.
     *
     * @param id the id of the activityInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/activity-infos/{id}")
    public ResponseEntity<Void> deleteActivityInfo(@PathVariable Long id) {
        log.debug("REST request to delete ActivityInfo : {}", id);
        activityInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
