package com.amalvadkar.lms.links.models.request;

import java.util.Set;

public record CreateLinkRequest(String title, String url, Set<String> tagIds) {
}
