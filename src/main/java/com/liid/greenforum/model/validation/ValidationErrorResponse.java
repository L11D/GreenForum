package com.liid.greenforum.model.validation;

import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {
}
