package com.sw.output.admin.faq.service;

import com.sw.output.admin.faq.dto.AdminFAQRequestDTO;
import com.sw.output.domain.faq.entity.Faq;
import com.sw.output.domain.faq.repository.FAQRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.CommonErrorCode;
import com.sw.output.global.response.errorcode.FAQErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    @Transactional
    public void deleteFaq(Long faqId) {
        Faq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new BusinessException(FAQErrorCode.FAQ_NOT_FOUND));

        if (faq.getIsDeleted()) {
            throw new BusinessException(CommonErrorCode.ALREADY_DELETED);
        }

        faq.softDelete();
    }

    @Transactional
    public void restoreFaq(Long faqId) {
        Faq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new BusinessException(FAQErrorCode.FAQ_NOT_FOUND));

        if (!faq.getIsDeleted()) {
            throw new BusinessException(CommonErrorCode.NOT_DELETED);
        }

        faq.restore();
    }
}
