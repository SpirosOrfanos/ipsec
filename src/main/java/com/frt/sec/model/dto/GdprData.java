package com.frt.sec.model.dto;

import java.util.List;

public record GdprData(List<String> opaque, List<String> travelerData) {
}
