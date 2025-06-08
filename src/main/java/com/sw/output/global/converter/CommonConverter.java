package com.sw.output.global.converter;

import com.sw.output.global.dto.CommonResponseDTO;

import java.time.LocalDateTime;

public class CommonConverter {
    public static CommonResponseDTO.IdResponseDTO toIdResponseDTO(Long id) {
        return CommonResponseDTO.IdResponseDTO.builder()
                .id(id)
                .build();
    }

    public static CommonResponseDTO.CursorDTO toCursorDTO(Long entityId, LocalDateTime entityCreatedAt) {
        return CommonResponseDTO.CursorDTO.builder()
                .id(entityId)
                .createdAt(entityCreatedAt)
                .build();
    }
}
