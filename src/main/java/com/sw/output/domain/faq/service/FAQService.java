package com.sw.output.domain.faq.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sw.output.domain.faq.converter.FAQDTOConverter;
import com.sw.output.domain.faq.dto.FAQResponseDTO;
import com.sw.output.domain.faq.entity.Faq;
import com.sw.output.domain.faq.repository.FAQRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FAQService {
    private final FAQRepository faqRepository;

    public List<FAQResponseDTO.FaqDTO> getFAQs() {
        List<Faq> faqs = faqRepository.findAll();
        return faqs.stream()
                .map(FAQDTOConverter::toFaqDTO)
                .collect(Collectors.toList());
    }
}
