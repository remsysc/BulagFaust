package com.sysc.bulag_faust.category.dto;

import java.util.UUID;

public record CategoryResponse(
    UUID id, String name, long postCount) {
}
