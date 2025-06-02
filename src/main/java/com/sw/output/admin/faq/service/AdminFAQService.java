package com.sw.output.admin.faq.service;

import com.sw.output.admin.faq.dto.AdminFAQRequestDTO;
import com.sw.output.domain.faq.entity.Faq;
import com.sw.output.domain.faq.repository.FAQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sw.output.admin.faq.converter.AdminFAQConverter.toFAQ;

@Service
@RequiredArgsConstructor
public class AdminFAQService {
    private final FAQRepository faqRepository;

    public List<Faq> getFAQs() {
        return faqRepository.findAll();
    }

    public void createFaq(AdminFAQRequestDTO.FaqDTO request) {
        Faq faq = toFAQ(request);
        faqRepository.save(faq);
    }
}
