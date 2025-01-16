package com.amalvadkar.lms.tags.controllers.rest;

import com.amalvadkar.lms.common.AbstractIT;
import com.amalvadkar.lms.tags.entities.TagEntity;
import com.amalvadkar.lms.tags.repositories.TagRepo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

class TagRestControllerTest extends AbstractIT {

    @Autowired
    TagRepo tagRepo;

    @Test
    void should_create_new_tag() throws IOException {
        String requestPayload = """
                {
                    "tagName" : "Spring Boot"
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .header("X-User-Id", "01JHCWNZ8TJT54N2XW130WDS8K")
                .header("X-Role-Id", "01JHCWEFS3D4YMWYGRAMX8FZT1")
                .header("X-Device", "web")
                .body(requestPayload)
                .when()
                .post("/api/lms/tags/create-tag")
                .then()
                .extract()
                .response();

        boolean success = response.path("success");
        assertThat(success).isTrue();

        String message = response.path("message");
        assertThat(message).isEqualTo("Created Successfully");

        int code = response.path("code");
        assertThat(code).isEqualTo(201);

        String newCreatedTagId = response.path("data.tagId");
        TagEntity newCreatedTagEntity = tagRepo.findById(newCreatedTagId).orElseThrow();

        assertThat(newCreatedTagEntity.getId()).isEqualTo(newCreatedTagId);
        assertThat(newCreatedTagEntity.getName()).isEqualTo("spring-boot");
        assertThat(newCreatedTagEntity.getDeleteFlag()).isFalse();
        assertThat(newCreatedTagEntity.getCreatedBy().getId()).isEqualTo("01JHCWNZ8TJT54N2XW130WDS8K");
        assertThat(newCreatedTagEntity.getUpdatedBy().getId()).isEqualTo("01JHCWNZ8TJT54N2XW130WDS8K");
        assertThat(newCreatedTagEntity.getCreatedOn()).isNotNull();
        assertThat(newCreatedTagEntity.getUpdatedOn()).isNotNull();

    }
}