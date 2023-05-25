package africa.semicolon.uberdeluxe.cloud;

import africa.semicolon.uberdeluxe.service.cloud.CloudService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static africa.semicolon.uberdeluxe.util.AppUtilities.UBER_DELUXE_TEST_IMAGE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class CloudinaryCloudServiceImplTest {

    @Autowired
    private CloudService cloudService;

    private MockMultipartFile file;

    @BeforeEach
    void setUp() throws IOException {
        file =
                new MockMultipartFile("puppy",
                        new FileInputStream(UBER_DELUXE_TEST_IMAGE));
    }

    @Test
    void uploadTest() {
        var cloudinaryImageUrl = cloudService.upload(file);
        assertThat(cloudinaryImageUrl).isNotNull();
    }
}