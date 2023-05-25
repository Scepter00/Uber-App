package africa.semicolon.uberdeluxe.mapper;

import africa.semicolon.uberdeluxe.data.dto.request.RegisterPassengerRequest;
import africa.semicolon.uberdeluxe.data.models.AppUser;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ParaMapper {

    public static AppUser map(RegisterPassengerRequest request){
        AppUser appUser = new AppUser();
        appUser.setName(request.getName());
        appUser.setPassword(request.getPassword());
        appUser.setEmail(request.getEmail());
        return appUser;
    }

}
