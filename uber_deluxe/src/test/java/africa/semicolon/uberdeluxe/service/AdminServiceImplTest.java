package africa.semicolon.uberdeluxe.service;

import africa.semicolon.uberdeluxe.data.dto.request.InviteAdminRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;
    private Set<InviteAdminRequest> inviteAdminRequests;
    @BeforeEach
    void setUp() {
        inviteAdminRequests=Set.of(
                new InviteAdminRequest("bonel60110@orgria.com", "test_name")
        );
    }

    @Test
    void sendInviteRequestsTest() {
        var response = adminService.sendInviteRequests(inviteAdminRequests);
        assertThat(response).isNotNull();
    }
}