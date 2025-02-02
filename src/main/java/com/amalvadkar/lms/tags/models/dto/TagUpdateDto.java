package com.amalvadkar.lms.tags.models.dto;

public record TagUpdateDto(String tableName, String columnName, Object value, String tagId, String userId) {
}