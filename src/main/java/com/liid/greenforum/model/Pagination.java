package com.liid.greenforum.model;

public record Pagination(int size,
                         int count,
                         int current) {
}
