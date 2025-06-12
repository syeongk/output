package com.sw.output.domain.faq.service;

import com.sw.output.domain.faq.dto.FAQResponseDTO;
import com.sw.output.domain.faq.entity.Faq;
import com.sw.output.domain.faq.entity.FaqCategory;
import com.sw.output.domain.faq.repository.FAQRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.FAQErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sw.output.domain.faq.converter.FAQDTOConverter.toFaqCursorDTO;

@Service
@RequiredArgsConstructor
public class FAQService {
    private final FAQRepository faqRepository;

    public FAQResponseDTO.FaqCursorDTO getFAQs(Long cursorId, int pageSize, FaqCategory faqCategory) {
        Faq faq = null;
        if (cursorId != null) {
            faq = faqRepository.findById(cursorId)
                    .orElseThrow(() -> new BusinessException(FAQErrorCode.FAQ_NOT_FOUND));
        }

        Pageable pageable = PageRequest.of(0, pageSize);
        Slice<Faq> faqSlice;
        if (cursorId == null) {
            faqSlice = faqRepository.findFaqFirstPage(pageable, faqCategory);
        } else {
            faqSlice = faqRepository.findFaqNextPage(pageable, faq.getId(), faq.getCreatedAt(), faqCategory);
        }

        List<Faq> faqs = faqSlice.getContent();
        if (faqs.isEmpty()) {
            return toFaqCursorDTO(new ArrayList<>(), null);
        }

        if (!faqSlice.hasNext()) {
            return toFaqCursorDTO(faqs, null);
        } else {
            Faq lastFaq = faqs.get(faqs.size() - 1);
            return toFaqCursorDTO(faqs, lastFaq.getId());
        }
    }
}
