package africa.semicolon.uberdeluxe.service;

import africa.semicolon.uberdeluxe.data.dto.request.Location;
import africa.semicolon.uberdeluxe.data.dto.response.GoogleDistanceResponse;

public interface MockLocationService {
    GoogleDistanceResponse getDistanceInformation(Location origin, Location destination);
}
