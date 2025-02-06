package com.amalvadkar.lms.links.services;

import com.amalvadkar.lms.common.exceptions.ResourceAlreadyExistsException;
import com.amalvadkar.lms.common.models.response.CustomResponse;
import com.amalvadkar.lms.common.models.response.PagedResult;
import com.amalvadkar.lms.common.repositories.UserRepo;
import com.amalvadkar.lms.links.entities.LinkEntity;
import com.amalvadkar.lms.links.models.request.CreateLinkRequest;
import com.amalvadkar.lms.links.models.request.DeleteLinkRequest;
import com.amalvadkar.lms.links.models.request.FetchLinkRequest;
import com.amalvadkar.lms.links.models.response.FetchLinkResponse;
import com.amalvadkar.lms.links.repositories.LinkRepo;
import com.amalvadkar.lms.links.specification.LinkSpecification;
import com.amalvadkar.lms.tags.entities.TagEntity;
import com.amalvadkar.lms.tags.repositories.TagRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.amalvadkar.lms.common.enums.ResponseMessageEnum.*;
import static com.amalvadkar.lms.links.enums.LinkErrorMessageEnum.LINK_ALREADY_EXISTS_ERR_MSG;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LinkService {

    public static final String LINK_ID = "linkId";

    private final LinkRepo linkRepo;
    private final TagRepo tagRepo;
    private final UserRepo userRepo;

    @Transactional
    public CustomResponse createLink(CreateLinkRequest createLinkRequest, String loggedInUserId) {
        checkForExistingUrl(createLinkRequest, loggedInUserId);
        LinkEntity savedLinkEntity = saveLink(createLinkRequest, loggedInUserId);
        return prepareLinkCreateResponse(savedLinkEntity);
    }

    private void checkForExistingUrl(CreateLinkRequest createLinkRequest, String loggedInUserId) {
        boolean isUrlExist = linkRepo.isUrlExists(createLinkRequest.url(), loggedInUserId);
        if (isUrlExist) {
            throw new ResourceAlreadyExistsException(LINK_ALREADY_EXISTS_ERR_MSG.value());
        }
    }

    private static CustomResponse prepareLinkCreateResponse(LinkEntity createdLink) {
        return CustomResponse.created(
                Map.of(LINK_ID, createdLink.getId()),
                CREATED_SUCCESSFULLY_MSG.value());
    }

    private LinkEntity saveLink(CreateLinkRequest createLinkRequest, String loggedInUserId) {
        List<TagEntity> tags = tagRepo.findTagsByIds(createLinkRequest.tagIds());
        LinkEntity linkEntity = prepareLinkEntity(createLinkRequest, loggedInUserId, tags);
        return linkRepo.save(linkEntity);
    }

    private LinkEntity prepareLinkEntity(CreateLinkRequest createLinkRequest, String loggedInUserId, List<TagEntity> tags) {
        LinkEntity linkEntity = new LinkEntity();
        linkEntity.setTitle(createLinkRequest.title());
        linkEntity.setUrl(createLinkRequest.url());
        linkEntity.setTags(tags);
        linkEntity.setCreatedBy(userRepo.getReferenceById(loggedInUserId));
        linkEntity.setUpdatedBy(userRepo.getReferenceById(loggedInUserId));
        return linkEntity;
    }

    public CustomResponse deleteLink(DeleteLinkRequest deleteLinkRequest, String loggedInUserId) {
        int noOfLinkDeleted = linkRepo.deleteLink(deleteLinkRequest.linkId(), loggedInUserId);
        log.info("no of link deleted : {}", noOfLinkDeleted);
        return CustomResponse.deleted(DELETED_SUCCESSFULLY_MSG.value());
    }

    public CustomResponse fetchLinks(FetchLinkRequest fetchLinkRequest, String loggedInUserId) {
        Page<LinkEntity> pagedLinkEntity = preparePagedLinkEntity(fetchLinkRequest, loggedInUserId);
        List<FetchLinkResponse> fetchLinkResponseList = prepareFetchLinkResposeList(pagedLinkEntity);
        var fetchLinkResponsePagedResult = PagedResult.preparePagedResponse(pagedLinkEntity, fetchLinkResponseList);
        return CustomResponse.success(fetchLinkResponsePagedResult, FETCHED_SUCCESSFULLY_MSG.value());
    }

    private static List<FetchLinkResponse> prepareFetchLinkResposeList(Page<LinkEntity> pagedLinkEntity) {
        List<LinkEntity> linkEntities = pagedLinkEntity.getContent();
        return linkEntities.stream()
                .map(FetchLinkResponse::new)
                .toList();
    }

    private Page<LinkEntity> preparePagedLinkEntity(FetchLinkRequest fetchLinkRequest, String loggedInUserId) {
        Pageable pageable = fetchLinkRequest.preparePageRequest();
        var linkEntitySpecification = LinkSpecification.getLink(fetchLinkRequest, loggedInUserId);
        return linkRepo.findAll(linkEntitySpecification, pageable);
    }

}

