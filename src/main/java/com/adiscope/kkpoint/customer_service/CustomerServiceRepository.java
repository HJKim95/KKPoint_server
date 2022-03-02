package com.adiscope.kkpoint.customer_service;

import com.adiscope.kkpoint.attendance.Attendance;
import com.adiscope.kkpoint.attendance.AttendanceApiResponse;
import com.adiscope.kkpoint.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerServiceRepository extends JpaRepository<CustomerService, Long> {
}