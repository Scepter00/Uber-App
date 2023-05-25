package africa.semicolon.uberdeluxe.controller;

import africa.semicolon.uberdeluxe.data.dto.request.RegisterDriverRequest;
import africa.semicolon.uberdeluxe.data.dto.response.ApiResponse;
import africa.semicolon.uberdeluxe.exception.BusinessLogicException;
import africa.semicolon.uberdeluxe.service.DriverService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/driver")
@AllArgsConstructor
public class DriverController {

    private final DriverService driverService;


    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> register(@Valid @ModelAttribute RegisterDriverRequest registerDriverRequest){
        try{
            var response =driverService.register(registerDriverRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (BusinessLogicException exception){
            return ResponseEntity.badRequest()
                                 .body(ApiResponse.builder()
                                 .message(exception.getMessage())
                                 .build());

        }
    }
}
