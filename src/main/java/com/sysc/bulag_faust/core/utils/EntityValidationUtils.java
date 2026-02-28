package com.sysc.bulag_faust.core.utils;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.sysc.bulag_faust.core.exceptions.base_exception.NotFoundException;

public final class EntityValidationUtils {
  private EntityValidationUtils() {
  }

  public static <T, ID> void vaidateAllFound(Set<ID> requestedIds, Collection<T> foundEntities,
      Function<T, ID> idExtractor, String entityName) {

    if (foundEntities.size() != requestedIds.size()) {
      Set<ID> foundIds = foundEntities.stream()
          .map(idExtractor).collect(Collectors.toSet());

      Set<ID> missingIds = requestedIds.stream().filter(id -> !foundIds.contains(id)).collect(Collectors.toSet());

      throw new NotFoundException(entityName, missingIds);
    }
  }
}
