package com.amalvadkar.lms.tags.controllers.rest;

import com.amalvadkar.lms.common.models.response.CustomResModel;
import com.amalvadkar.lms.tags.models.request.CreateTagRequest;
import com.amalvadkar.lms.tags.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lms/tags")
@RequiredArgsConstructor
public class TagRestController {

    private final TagService tagService;

    private static final String ENDPOINT_CREATE_TAG = "/create-tag";

    @PostMapping(ENDPOINT_CREATE_TAG)
    public ResponseEntity<CustomResModel> createTag(@RequestBody CreateTagRequest createTagRequest,
                                                    @RequestHeader("X-User-Id") String loggedInUserId){
        return ResponseEntity.ok(tagService.createTag(createTagRequest, loggedInUserId));
    }

}
