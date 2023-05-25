package africa.semicolon.uberdeluxe.service;

import africa.semicolon.uberdeluxe.data.dto.response.ApiResponse;
import africa.semicolon.uberdeluxe.data.models.AppUser;
import org.springframework.web.multipart.MultipartFile;

public interface AppUserService {

    ApiResponse uploadProfileImage(MultipartFile profileImage, Long userId);

    ApiResponse verifyAccount(Long userId, String token);


    AppUser getByEmail(String email);


}
