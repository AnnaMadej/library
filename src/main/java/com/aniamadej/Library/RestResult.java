package com.aniamadej.Library;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RestResult {
    REST_RESULT_SUCCESS("ok", "success"),
    REST_RESULT_MULTIPLE_NAME("multiple name", "entry of this name already exists in database"),
    REST_RESULT_NO_ENTRY("unknown id", "there is no entry of this id in database"),
    REST_RESULT_NOT_EMPTY("not empty entry", "entry if this id has some content");
    String result;
    String description;
}
