package com.sw.output.global.converter;

import com.sw.output.global.dto.CommonResponseDTO;

public class CommonConverter {
    public static CommonResponseDTO.IdResponseDTO toIdResponseDTO(Long id) {
        return CommonResponseDTO.IdResponseDTO.builder()
                .id(id)
                .build();
    }
}