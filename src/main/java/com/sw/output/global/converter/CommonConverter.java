package com.sw.output.global.converter;

import com.sw.output.global.dto.CommonResponseDTO;

import java.time.LocalDateTime;

public class CommonConverter {
    public static CommonResponseDTO.IdResponseDTO toIdResponseDTO(Long id) {
        return CommonResponseDTO.IdResponseDTO.builder()
                .id(id)
                .build();
    }

    public static CommonResponseDTO.CursorDTO toCreatedAtCursorDTO(Long entityId, LocalDateTime entityCreatedAt) {
        return CommonResponseDTO.CursorDTO.builder()
                .id(entityId)
                .createdAt(entityCreatedAt)
                .build();
    }

    public static CommonResponseDTO.CursorDTO toTitleCursorDTO(Long entityId, String entityTitle) {
        return CommonResponseDTO.CursorDTO.builder()
                .id(entityId)
                .title(entityTitle)
                .build();
    }
}
