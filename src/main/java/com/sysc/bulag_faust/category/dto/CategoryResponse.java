package com.sysc.bulag_faust.category.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {

  private UUID id;
  private String name;
  private long postCount;
}
