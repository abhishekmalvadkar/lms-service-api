package com.amalvadkar.lms.tags.services;

import com.amalvadkar.lms.common.exceptions.ResourceAlreadyExistsException;
import com.amalvadkar.lms.common.models.response.CustomResponse;
import com.amalvadkar.lms.common.repositories.UserRepo;
import com.amalvadkar.lms.tags.entities.TagEntity;
import com.amalvadkar.lms.tags.models.request.CreateTagRequest;
import com.amalvadkar.lms.tags.models.request.DeleteTagRequest;
import com.amalvadkar.lms.tags.repositories.TagRepo;
import com.amalvadkar.lms.tags.transformer.TagTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.amalvadkar.lms.common.enums.ResponseMessageEnum.CREATED_SUCCESSFULLY_MSG;
import static com.amalvadkar.lms.common.enums.ResponseMessageEnum.DELETED_SUCCESSFULLY_MSG;
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
        if (isTagExists){
            throw new ResourceAlreadyExistsException(TAG_ALREADY_EXISTS_ERR_MSG.value());
        }
    }

    @Transactional
    public CustomResponse deleteTag(DeleteTagRequest deleteTagRequest, String loggedInUserId) {
        int noOfTagsDeleted = tagRepo.deleteTagById(deleteTagRequest.tagId(), loggedInUserId);
        log.info("No of tags deleted :: {}", noOfTagsDeleted);
        return CustomResponse.deleted(DELETED_SUCCESSFULLY_MSG.value());
    }
}
