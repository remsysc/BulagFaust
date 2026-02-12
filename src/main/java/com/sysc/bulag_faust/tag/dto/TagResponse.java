package com.sysc.bulag_faust.tag.dto;

import java.util.UUID;

public record TagResponse(UUID id, String name, long postCount) {
}
