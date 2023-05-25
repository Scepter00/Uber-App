package africa.semicolon.uberdeluxe.service;

import africa.semicolon.uberdeluxe.data.dto.request.Location;
import africa.semicolon.uberdeluxe.data.dto.response.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class MockLocationServiceImpl implements MockLocationService{
    @Override
    public GoogleDistanceResponse getDistanceInformation(Location origin, Location destination) {
        return new GoogleDistanceResponse(
                List.of(destination.toString()), List.of(origin.toString()),
                List.of(new DistanceMatrixRow(
                        List.of(new DistanceMatrixElement(DistanceMatrixElementStatus.OK,
                                new GoogleDistance("10 km", 3500L), new GoogleDuration("10 mins", 616L), new Fare()))))
        );
    }
}
