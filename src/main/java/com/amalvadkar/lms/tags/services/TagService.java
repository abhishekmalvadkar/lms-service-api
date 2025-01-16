package com.amalvadkar.lms.tags.services;

import com.amalvadkar.lms.common.models.response.CustomResModel;
import com.amalvadkar.lms.common.repositories.UserRepo;
import com.amalvadkar.lms.tags.entities.TagEntity;
import com.amalvadkar.lms.tags.models.request.CreateTagRequest;
import com.amalvadkar.lms.tags.repositories.TagRepo;
import com.amalvadkar.lms.tags.transformer.TagTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepo tagRepo;
    private final UserRepo userRepo;

    @Transactional
    public CustomResModel createTag(CreateTagRequest createTagRequest, String loggedInUserId) {
        String incomingTagName = createTagRequest.tagName();
        String dashedTagName = TagTransformer.transformTag(incomingTagName);

        TagEntity tagEntity = new TagEntity();
        tagEntity.setName(dashedTagName);
        tagEntity.setCreatedBy(userRepo.getReferenceById(loggedInUserId));
        tagEntity.setUpdatedBy(userRepo.getReferenceById(loggedInUserId));

        TagEntity savedTagEntity = tagRepo.save(tagEntity);

        return CustomResModel.created(
                Map.of("tagId" , savedTagEntity.getId()),
                "Created Successfully"
        );
    }

}
