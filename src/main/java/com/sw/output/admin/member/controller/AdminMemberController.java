package com.sw.output.admin.member.controller;

import com.sw.output.admin.member.service.AdminMemberService;
import com.sw.output.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/members")
public class AdminMemberController {
    private final AdminMemberService adminMemberService;

    @GetMapping
    public String getMembers(Model model) {
        List<Member> members = adminMemberService.getMembers();
        model.addAttribute("members", members);
        return "admin/member/member-list";
    }

    @PostMapping("/{memberId}/warn")
    public String warnMember(@PathVariable Long memberId) {
        adminMemberService.warnMember(memberId);
        return "redirect:/admin/members";
    }

    @PostMapping("/{memberId}/delete")
    public String deleteMember(@PathVariable Long memberId) {
        adminMemberService.deleteMember(memberId);
        return "redirect:/admin/members";
    }

    @PostMapping("/{memberId}/restore")
    public String restoreMember(@PathVariable Long memberId) {
        adminMemberService.restoreMember(memberId);
        return "redirect:/admin/members";
    }
}
