package com.sw.output.admin.complaint.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sw.output.admin.complaint.service.AdminComplaintService;
import com.sw.output.domain.complaint.entity.Complaint;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/complaints")
public class AdminComplaintController {
    private final AdminComplaintService adminComplaintService;

    @GetMapping
    public String getComplaints(@RequestParam(required = false) String status, Model model) {
        List<Complaint> complaints = adminComplaintService.getComplaints(status);
        model.addAttribute("complaints", complaints);
        model.addAttribute("status", status);
        return "admin/complaint/complaint-list";
    }

    @PostMapping("/{complaintId}/process")
    public String processComplaint(@PathVariable Long complaintId) {
        adminComplaintService.processComplaint(complaintId);
        return "redirect:/admin/complaints";
    }

    @PostMapping("/{complaintId}/cancel")
    public String cancelComplaint(@PathVariable Long complaintId) {
        adminComplaintService.cancelComplaint(complaintId);
        return "redirect:/admin/complaints";
    }
}
