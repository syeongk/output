package com.sw.output.admin.interviewset.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sw.output.admin.interviewset.service.AdminInterviewSetService;
import com.sw.output.domain.interviewset.entity.InterviewSet;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/interview-sets")
public class AdminInterviewSetController {
    private final AdminInterviewSetService adminInterviewSetService;

    @GetMapping
    public String getInterviewSets(Model model) {
        List<InterviewSet> interviewSets = adminInterviewSetService.getInterviewSets();
        model.addAttribute("interviewSets", interviewSets);
        return "admin/interview-set/interview-set-list";
    }

    @PostMapping("/{interviewSetId}/hide")
    public String hideInterviewSet(@PathVariable Long interviewSetId) {
        adminInterviewSetService.hideInterviewSet(interviewSetId);
        return "redirect:/admin/interview-sets";
    }

    @PostMapping("/{interviewSetId}/unhide")
    public String unhideInterviewSet(@PathVariable Long interviewSetId) {
        adminInterviewSetService.unhideInterviewSet(interviewSetId);
        return "redirect:/admin/interview-sets";
    }

    @GetMapping("/{interviewSetId}")
    public String getInterviewSetDetail(@PathVariable Long interviewSetId, Model model) {
        InterviewSet interviewSet = adminInterviewSetService.getInterviewSet(interviewSetId);
        model.addAttribute("questionAnswers", interviewSet.getQuestionAnswers());
        return "admin/interview-set/interview-set-detail";
    }
}
