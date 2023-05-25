package africa.semicolon.uberdeluxe.service;

import africa.semicolon.uberdeluxe.data.dto.request.InviteAdminRequest;
import africa.semicolon.uberdeluxe.data.dto.response.AdminResponse;
import africa.semicolon.uberdeluxe.data.dto.response.ApiResponse;

import java.util.Set;

public interface AdminService {
    ApiResponse sendInviteRequests(Set<InviteAdminRequest> inviteAdminRequestList);

}
