package com.spring.shoestore.controllers.offline;

import com.spring.shoestore.dto.AccountAdminDTO;
import com.spring.shoestore.entity.AccountAdmin;
import com.spring.shoestore.entity.Admin;
import com.spring.shoestore.repo.AccountAdminRepository;
import com.spring.shoestore.repo.AdminRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;



@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final AdminRepository adminRepository;

    private final AccountAdminRepository accountAdminRepository;

    private final JavaMailSender emailSender;

    @Value("${app.default-password}")
    private String defaultPassword;

    private static final int PASSWORD_LENGTH = 64;
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@#$%&*()_+-=[]|,./?><";
    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
    private static final SecureRandom random = new SecureRandom();

    public EmployeeController(AdminRepository adminRepository, AccountAdminRepository accountAdminRepository, JavaMailSender emailSender) {
        this.adminRepository = adminRepository;
        this.accountAdminRepository = accountAdminRepository;
        this.emailSender = emailSender;
    }

    public static String generateStrongPassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomCharIndex = random.nextInt(PASSWORD_ALLOW_BASE.length());
            password.append(PASSWORD_ALLOW_BASE.charAt(randomCharIndex));
        }
        return password.toString();
    }


    @GetMapping("/")
    public String getEmployees(@ModelAttribute("employee") AccountAdminDTO employee, Model model,
                               @RequestParam(defaultValue = "0") Integer pageNumber,
                               @RequestParam(defaultValue = "5") Integer pageSize) {

        if (pageNumber < 0) {
            pageNumber = 0;
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        model.addAttribute("listemployee", accountAdminRepository.findAll(pageable).getContent());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        return "offline/employees/listEmployess";
    }

    private final AtomicInteger counter = new AtomicInteger(26);

    public String generateAdminCode() {
        int currentNumber = counter.incrementAndGet();
        return String.format("%03d", currentNumber);
    }

    @PostMapping("/create")
    public String createEmployee(@ModelAttribute("employee") AccountAdminDTO accountAdminDTO) {
        Admin admin = new Admin();
        admin.setAdminCode("ADM" + generateAdminCode());
        admin.setFirstName(accountAdminDTO.getFirstName());
        admin.setLastName(accountAdminDTO.getLastName());
        admin.setEmail(accountAdminDTO.getEmail());
        admin.setAddress(accountAdminDTO.getAddress());
        admin.setPhoneNumber(accountAdminDTO.getPhoneNumber());
        admin.setBirthday(LocalDate.parse(accountAdminDTO.getBirthday()));
        admin.setGender(accountAdminDTO.getGender());

        Admin savedAdmin = adminRepository.save(admin);

        AccountAdmin accountAdmin = new AccountAdmin();
        accountAdmin.setIdAdmin(savedAdmin);
        accountAdmin.setUserName(accountAdminDTO.getUserName());
        accountAdmin.setStatus(accountAdminDTO.getStatus());
        accountAdmin.setRole(accountAdminDTO.getRole());
        accountAdmin.setPassword(generateStrongPassword().getBytes());
        accountAdminRepository.save(accountAdmin);

        sendAccountCreationEmail(accountAdmin);
        return "redirect:/employees/";
    }

    private void sendAccountCreationEmail(AccountAdmin accountAdmin) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(accountAdmin.getIdAdmin().getEmail());
        message.setSubject("Thông tin tài khoản mới");
        message.setText("Dear " + accountAdmin.getIdAdmin().getFirstName() + ",\n\n"
                + "Bạn vừa được tạo tài khoản thành công trong hệ thống.\n"
                + "Tài khoản của bạn:\n"
                + "Username: " + accountAdmin.getUserName() + "\n"
                + "Password đã được tạo: " + defaultPassword + "\n\n"
                + "Vui lòng đăng nhập và thay đổi mật khẩu sau khi đăng nhập thành công.\n\n"
                + "Trân trọng,\n"
                + "Đội ngũ quản trị hệ thống");
        emailSender.send(message);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editEmployee(@PathVariable Integer id, Model model,
                                     @RequestParam(defaultValue = "0") Integer pageNumber,
                                     @RequestParam(defaultValue = "5") Integer pageSize
    ) {

        if (pageNumber < 0) {
            pageNumber = 0;
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        AccountAdmin accountAdmin = accountAdminRepository.findById(id).orElse(null);

        AccountAdminDTO accountAdminDTO = new AccountAdminDTO();
        accountAdminDTO.setId(accountAdmin.getId());
        accountAdminDTO.setUserName(accountAdmin.getUserName());
        accountAdminDTO.setStatus(accountAdmin.getStatus());
        accountAdminDTO.setRole(accountAdmin.getRole());

        Admin admin = accountAdmin.getIdAdmin();
        if (admin != null) {
            accountAdminDTO.setFirstName(admin.getFirstName());
            accountAdminDTO.setLastName(admin.getLastName());
            accountAdminDTO.setEmail(admin.getEmail());
            accountAdminDTO.setAddress(admin.getAddress());
            accountAdminDTO.setPhoneNumber(admin.getPhoneNumber());
            accountAdminDTO.setBirthday(String.valueOf(admin.getBirthday()));
            accountAdminDTO.setGender(admin.getGender());
        }

        model.addAttribute("employee", accountAdminDTO);
        model.addAttribute("listemployee", accountAdminRepository.findAll(pageable).getContent());
        return new ModelAndView("offline/employees/listEmployess");
    }


    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Integer id, Model model) {
        accountAdminRepository.deleteById(id);
        adminRepository.deleteById(id);
        return "redirect:/employees/";
    }


    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute("employee") AccountAdminDTO accountAdminDTO) {
        AccountAdmin accountAdmin = accountAdminRepository.findById(accountAdminDTO.getId()).orElse(null);
        if (accountAdmin == null) {
            return "redirect:/employees/";
        }

        Admin admin = accountAdmin.getIdAdmin();
        if (admin != null) {
            admin.setFirstName(accountAdminDTO.getFirstName());
            admin.setLastName(accountAdminDTO.getLastName());
            admin.setEmail(accountAdminDTO.getEmail());
            admin.setAddress(accountAdminDTO.getAddress());
            admin.setPhoneNumber(accountAdminDTO.getPhoneNumber());
            admin.setBirthday(LocalDate.parse((accountAdminDTO.getBirthday())));
        }

        accountAdmin.setUserName(accountAdminDTO.getUserName());
        accountAdmin.setStatus(accountAdminDTO.getStatus());
        accountAdmin.setRole(accountAdminDTO.getRole());

        adminRepository.save(admin);
        accountAdminRepository.save(accountAdmin);

        return "redirect:/employees/";
    }
//    @GetMapping("/filter")
//    public String filterEmployees(
//            @RequestParam(value = "userName", required = false) String userName,
//            @RequestParam(value = "role", required = false) String role,
//            @RequestParam(value = "status", required = false) String status,
//            @RequestParam(defaultValue = "0") Integer pageNumber,
//            @RequestParam(defaultValue = "5") Integer pageSize,
//            Model model
//    ) {
//        // Xử lý trang và sắp xếp
//        if (pageNumber < 0) {
//            pageNumber = 0;
//        }
//        Sort sort = Sort.by(Sort.Direction.DESC, "id");
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
//
//        // Lọc dữ liệu
//        Page<AccountAdmin> pageResult;
//
//        if (userName != null && !userName.isEmpty()) {
//            pageResult = accountAdminRepository.findByUserNameContaining(userName, pageable);
//        } else if (role != null && !role.isEmpty()) {
//            pageResult = accountAdminRepository.findByRole(role, pageable);
//        } else if (status != null && !status.isEmpty()) {
//            pageResult = accountAdminRepository.findByStatus(status, pageable);
//        } else {
//            pageResult = accountAdminRepository.findAll(pageable);
//        }
//
//        // Chuyển đổi dữ liệu sang DTO
//        List<AccountAdminDTO> accountAdminDTOList = pageResult.stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//
//        // Thêm dữ liệu vào mô hình
//        model.addAttribute("listemployee", accountAdminDTOList);
//        model.addAttribute("pageNumber", pageNumber);
//        model.addAttribute("pageSize", pageSize);
//        model.addAttribute("totalPages", pageResult.getTotalPages());
//        model.addAttribute("totalElements", pageResult.getTotalElements());
//
//        return "offline/employees/listEmployess";
//    }
//
//    private AccountAdminDTO convertToDTO(AccountAdmin accountAdmin) {
//        if (accountAdmin == null) {
//            // Logging thông báo lỗi
//            System.err.println("AccountAdmin object is null.");
//            return null;
//        }
//
//        AccountAdminDTO dto = new AccountAdminDTO();
//        dto.setId(accountAdmin.getId());
//        dto.setUserName(accountAdmin.getUserName());
//        dto.setStatus(accountAdmin.getStatus());
//        dto.setRole(accountAdmin.getRole());
//
//        if (accountAdmin.getIdAdmin() != null) {
//            dto.setFirstName(accountAdmin.getIdAdmin().getFirstName());
//            dto.setLastName(accountAdmin.getIdAdmin().getLastName());
//            dto.setEmail(accountAdmin.getIdAdmin().getEmail());
//            dto.setAddress(accountAdmin.getIdAdmin().getAddress());
//            dto.setPhoneNumber(accountAdmin.getIdAdmin().getPhoneNumber());
//            dto.setBirthday(accountAdmin.getIdAdmin().getBirthday() != null ? accountAdmin.getIdAdmin().getBirthday().toString() : null);
//            dto.setGender(accountAdmin.getIdAdmin().getGender());
//        } else {
//            // Logging thông báo lỗi
//            System.err.println("IdAdmin object is null for AccountAdmin ID: " + accountAdmin.getId());
//        }
//        return dto;
//    }

}
