package com.acme.task.model.itunes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EntityType {

  ALBUM("album");

  final String value;

}
