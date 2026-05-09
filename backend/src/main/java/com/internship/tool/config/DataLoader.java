package com.internship.tool.config;

import com.internship.tool.entity.ComplianceRecord;
import com.internship.tool.repository.ComplianceRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ComplianceRecordRepository repository;

    @Override
    public void run(String... args) {

        // avoid re-seeding on every restart
        if (repository.count() > 0) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        List<ComplianceRecord> records = List.of(

                // PENDING
                ComplianceRecord.builder().title("Security Audit Q2")
                        .description("Quarterly security audit across infra and apps")
                        .status("PENDING").score(64).dueDate(now.plusDays(10)).build(),

                ComplianceRecord.builder().title("Vendor Risk Review")
                        .description("Review third-party vendor compliance & contracts")
                        .status("PENDING").score(58).dueDate(now.plusDays(14)).build(),

                ComplianceRecord.builder().title("Access Review - Admin Accounts")
                        .description("Validate admin accounts and least-privilege policy")
                        .status("PENDING").score(62).dueDate(now.plusDays(7)).build(),

                ComplianceRecord.builder().title("Incident Response Drill")
                        .description("Run IR tabletop exercise and collect evidence")
                        .status("PENDING").score(70).dueDate(now.plusDays(12)).build(),

                ComplianceRecord.builder().title("Data Retention Policy Update")
                        .description("Update retention rules for logs and customer data")
                        .status("PENDING").score(66).dueDate(now.plusDays(20)).build(),

                ComplianceRecord.builder().title("Password Policy Enforcement")
                        .description("Enforce strong password policy and rotation checks")
                        .status("PENDING").score(60).dueDate(now.plusDays(9)).build(),

                ComplianceRecord.builder().title("S3 Bucket Permissions Audit")
                        .description("Audit public access and IAM policies for buckets")
                        .status("PENDING").score(55).dueDate(now.plusDays(6)).build(),

                ComplianceRecord.builder().title("Backup Restore Verification")
                        .description("Validate backup restore procedure and RTO/RPO")
                        .status("PENDING").score(68).dueDate(now.plusDays(16)).build(),


                // IN_PROGRESS
                ComplianceRecord.builder().title("SOC2 Evidence Collection")
                        .description("Collect audit evidence for SOC2 controls")
                        .status("IN_PROGRESS").score(78).dueDate(now.plusDays(18)).build(),

                ComplianceRecord.builder().title("Firewall Rule Cleanup")
                        .description("Remove unused firewall rules and tighten inbound access")
                        .status("IN_PROGRESS").score(74).dueDate(now.plusDays(8)).build(),

                ComplianceRecord.builder().title("Cloud Security Review - AWS")
                        .description("Review IAM, VPC, SG rules and misconfigurations")
                        .status("IN_PROGRESS").score(80).dueDate(now.plusDays(15)).build(),

                ComplianceRecord.builder().title("Database Encryption Check")
                        .description("Verify encryption at rest and key rotation status")
                        .status("IN_PROGRESS").score(72).dueDate(now.plusDays(11)).build(),

                ComplianceRecord.builder().title("Patch Management Cycle")
                        .description("Apply pending OS patches and verify vulnerability closure")
                        .status("IN_PROGRESS").score(76).dueDate(now.plusDays(5)).build(),

                ComplianceRecord.builder().title("Penetration Test - Web App")
                        .description("Perform web app pentest and track remediation")
                        .status("IN_PROGRESS").score(69).dueDate(now.plusDays(13)).build(),

                ComplianceRecord.builder().title("SIEM Alert Tuning")
                        .description("Reduce false positives and tune detection rules")
                        .status("IN_PROGRESS").score(73).dueDate(now.plusDays(9)).build(),

                ComplianceRecord.builder().title("Audit Log Integrity Review")
                        .description("Ensure audit logs are immutable and retained properly")
                        .status("IN_PROGRESS").score(77).dueDate(now.plusDays(17)).build(),


                // COMPLETED
                ComplianceRecord.builder().title("GDPR Compliance Check")
                        .description("Validated GDPR requirements and DPA documentation")
                        .status("COMPLETED").score(94).dueDate(now.minusDays(2)).build(),

                ComplianceRecord.builder().title("MFA Rollout")
                        .description("Enabled MFA for all users including admins")
                        .status("COMPLETED").score(96).dueDate(now.minusDays(5)).build(),

                ComplianceRecord.builder().title("Secure SDLC Review")
                        .description("Reviewed SDLC gates and security testing coverage")
                        .status("COMPLETED").score(90).dueDate(now.minusDays(1)).build(),

                ComplianceRecord.builder().title("PII Data Mapping")
                        .description("Completed PII inventory and data flow mapping")
                        .status("COMPLETED").score(92).dueDate(now.minusDays(7)).build(),

                ComplianceRecord.builder().title("TLS Configuration Hardening")
                        .description("Removed weak ciphers and enforced TLS 1.2+")
                        .status("COMPLETED").score(95).dueDate(now.minusDays(3)).build(),

                ComplianceRecord.builder().title("Security Awareness Training")
                        .description("Completed org-wide security awareness program")
                        .status("COMPLETED").score(88).dueDate(now.minusDays(10)).build(),

                ComplianceRecord.builder().title("Endpoint Protection Deployment")
                        .description("Deployed EDR and verified policy compliance")
                        .status("COMPLETED").score(91).dueDate(now.minusDays(6)).build(),

                ComplianceRecord.builder().title("CVE Remediation Sprint")
                        .description("Patched critical CVEs and verified with re-scan")
                        .status("COMPLETED").score(93).dueDate(now.minusDays(4)).build(),


                // FAILED
                ComplianceRecord.builder().title("PCI DSS Gap Assessment")
                        .description("Failed due to missing segmentation controls and evidence")
                        .status("FAILED").score(42).dueDate(now.minusDays(1)).build(),

                ComplianceRecord.builder().title("Secrets Rotation")
                        .description("Rotation missed; multiple secrets expired and untracked")
                        .status("FAILED").score(38).dueDate(now.minusDays(3)).build(),

                ComplianceRecord.builder().title("Vulnerability Scan - Monthly")
                        .description("Failed due to high severity findings not remediated in SLA")
                        .status("FAILED").score(45).dueDate(now.minusDays(2)).build(),

                ComplianceRecord.builder().title("Data Loss Prevention Review")
                        .description("DLP policies not applied on endpoints; audit failed")
                        .status("FAILED").score(40).dueDate(now.minusDays(6)).build(),

                ComplianceRecord.builder().title("RBAC Verification")
                        .description("Role permissions found overly permissive; needs redesign")
                        .status("FAILED").score(47).dueDate(now.minusDays(5)).build(),

                ComplianceRecord.builder().title("Backup Policy Compliance")
                        .description("Backups missing for key systems; restore test failed")
                        .status("FAILED").score(35).dueDate(now.minusDays(8)).build()

        );

        repository.saveAll(records);
        System.out.println("✅ Seeded 30 demo compliance records");
    }
}