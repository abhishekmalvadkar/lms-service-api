package com.amalvadkar.lms.tags.services;

import com.amalvadkar.lms.common.exceptions.ResourceAlreadyExistsException;
import com.amalvadkar.lms.common.models.response.CustomResponse;
import com.amalvadkar.lms.common.models.response.PagedResult;
import com.amalvadkar.lms.common.repositories.UserRepo;
import com.amalvadkar.lms.tags.entities.TagEntity;
import com.amalvadkar.lms.tags.models.request.CreateTagRequest;
import com.amalvadkar.lms.tags.models.request.DeleteTagRequest;
import com.amalvadkar.lms.tags.models.request.FetchTagsRequest;
import com.amalvadkar.lms.tags.models.response.FetchTagResponse;
import com.amalvadkar.lms.tags.repositories.TagRepo;
import com.amalvadkar.lms.tags.specification.TagSpecification;
import com.amalvadkar.lms.tags.transformer.TagTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.amalvadkar.lms.common.enums.ResponseMessageEnum.*;
import static com.amalvadkar.lms.tags.enums.TagErrorMessageEnum.TAG_ALREADY_EXISTS_ERR_MSG;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TagService {

    private static final String TAG_ID = "tagId";

    private final TagRepo tagRepo;
    private final UserRepo userRepo;

    @Transactional
    public CustomResponse createTag(CreateTagRequest createTagRequest, String loggedInUserId) {
        String dashedTagName = TagTransformer.transformTag(createTagRequest.tagName());
        checkForExistingTagName(dashedTagName);
        TagEntity savedTagEntity = saveTag(loggedInUserId, dashedTagName);
        return prepareCreateTagResponse(savedTagEntity);
    }

    private static CustomResponse prepareCreateTagResponse(TagEntity savedTagEntity) {
        return CustomResponse.created(
                Map.of(TAG_ID, savedTagEntity.getId()),
                CREATED_SUCCESSFULLY_MSG.value()
        );
    }

    private TagEntity saveTag(String loggedInUserId, String dashedTagName) {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setName(dashedTagName);
        tagEntity.setCreatedBy(userRepo.getReferenceById(loggedInUserId));
        tagEntity.setUpdatedBy(userRepo.getReferenceById(loggedInUserId));
        return tagRepo.save(tagEntity);
    }

    private void checkForExistingTagName(String dashedTagName) {
        boolean isTagExists = tagRepo.existsByNameAndDeleteFlagIsFalse(dashedTagName);
        if (isTagExists) {
            throw new ResourceAlreadyExistsException(TAG_ALREADY_EXISTS_ERR_MSG.value());
        }
    }

    @Transactional
    public CustomResponse deleteTag(DeleteTagRequest deleteTagRequest, String loggedInUserId) {
        int noOfTagsDeleted = tagRepo.deleteTagById(deleteTagRequest.tagId(), loggedInUserId);
        log.info("No of tags deleted :: {}", noOfTagsDeleted);
        return CustomResponse.deleted(DELETED_SUCCESSFULLY_MSG.value());
    }

    public CustomResponse fetchTags(FetchTagsRequest fetchTagsRequest, String loggedInUserId) {
        Page<TagEntity> pagedTagEntity = fetchPagedTagEntity(fetchTagsRequest, loggedInUserId);
        PagedResult<FetchTagResponse> pageResultFetchTagResponse = preparePageResultFetchTagResponse(pagedTagEntity);
        return CustomResponse.success(pageResultFetchTagResponse, FETCHED_SUCCESSFULLY_MSG.value());

    }

    private Page<TagEntity> fetchPagedTagEntity(FetchTagsRequest fetchTagsRequest, String loggedInUserId) {
        Sort sortBy = Sort.by("updatedOn").descending();
        int pageNo = fetchTagsRequest.pageNO() <= 1 ? 0 : fetchTagsRequest.pageNO() - 1;
        Pageable pageable = PageRequest.of(pageNo, 2, sortBy);
        Specification<TagEntity> spec = Specification.where(TagSpecification.hasCategory(fetchTagsRequest.searchText()))
                .and(TagSpecification.withCreatedBy(loggedInUserId));
        return tagRepo.findAll(spec, pageable);
    }

    private static PagedResult<FetchTagResponse> preparePageResultFetchTagResponse(Page<TagEntity> pagedTagEntity) {
        List<FetchTagResponse> tagResponseList = convertToFetchTagResponse(pagedTagEntity);
        return PagedResult.preparePagedResponse(pagedTagEntity, tagResponseList);
    }

    private static List<FetchTagResponse> convertToFetchTagResponse(Page<TagEntity> tagPage) {
        return tagPage.getContent().stream()
                .map(FetchTagResponse::new)
                .toList();
    }
}
