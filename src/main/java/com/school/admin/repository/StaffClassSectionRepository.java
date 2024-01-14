package com.school.admin.repository;

import com.school.admin.entity.Staff;
import com.school.admin.entity.StaffClassSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffClassSectionRepository extends JpaRepository<StaffClassSection, Long> {
    List<StaffClassSection> findByClassNameAndSection(String className, String section);
    List<StaffClassSection> findAllByStaff(Staff staff);
}
