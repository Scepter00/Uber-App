package africa.semicolon.uberdeluxe.service;

import africa.semicolon.uberdeluxe.service.cloud.CloudService;
import africa.semicolon.uberdeluxe.config.distance.DistanceConfig;
import africa.semicolon.uberdeluxe.data.dto.request.BookRideRequest;
import africa.semicolon.uberdeluxe.data.dto.request.Location;
import africa.semicolon.uberdeluxe.data.dto.request.RegisterPassengerRequest;
import africa.semicolon.uberdeluxe.data.dto.response.ApiResponse;
import africa.semicolon.uberdeluxe.data.dto.response.DistanceMatrixElement;
import africa.semicolon.uberdeluxe.data.dto.response.GoogleDistanceResponse;
import africa.semicolon.uberdeluxe.data.dto.response.RegisterResponse;
import africa.semicolon.uberdeluxe.data.models.AppUser;
import africa.semicolon.uberdeluxe.data.models.Passenger;
import africa.semicolon.uberdeluxe.data.models.Role;
import africa.semicolon.uberdeluxe.data.repositories.PassengerRepository;
import africa.semicolon.uberdeluxe.exception.BusinessLogicException;
import africa.semicolon.uberdeluxe.mapper.ParaMapper;
import africa.semicolon.uberdeluxe.util.AppUtilities;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

import static africa.semicolon.uberdeluxe.util.AppUtilities.NUMBER_OF_ITEMS_PER_PAGE;

@Service
@AllArgsConstructor
@Slf4j
public class PassengerServiceImpl implements PassengerService{
    private final PassengerRepository passengerRepository;
    private final CloudService cloudService;

    private MockLocationService mockLocationService;
    private final PasswordEncoder passwordEncoder;
    private final DistanceConfig directionConfig;

    @Override
    public RegisterResponse register(RegisterPassengerRequest registerRequest) {
        AppUser appUser = ParaMapper.map(registerRequest);
        appUser.setRoles(new HashSet<>());
        appUser.getRoles().add(Role.PASSENGER);
        appUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        appUser.setCreatedAt(LocalDateTime.now().toString());
        Passenger passenger = new Passenger();
        passenger.setUserDetails(appUser);
        Passenger savedPassenger = passengerRepository.save(passenger);
        RegisterResponse registerResponse = getRegisterResponse(savedPassenger);
        return registerResponse;
    }

    @Override
    public Passenger getPassengerById(Long passengerId) {
        return passengerRepository.findById(passengerId).orElseThrow(()->
                new BusinessLogicException(
                        String.format("Passenger with id %d not found", passengerId)));
    }

    @Override
    public void savePassenger(Passenger passenger) {
        passengerRepository.save(passenger);
    }

    @Override
    public Optional<Passenger> getPassengerBy(Long passengerId) {
        return passengerRepository.findById(passengerId);
    }

    @Override
    public Passenger updatePassenger(Long passengerId, JsonPatch updatePayload) {
        ObjectMapper mapper = new ObjectMapper();
        Passenger foundPassenger = getPassengerById(passengerId);
        AppUser passengerDetails = foundPassenger.getUserDetails();
        //Passenger Object to node
        JsonNode node = mapper.convertValue(foundPassenger, JsonNode.class);
        try {
            //apply patch
            JsonNode updatedNode = updatePayload.apply(node);
            //node to Passenger Object
            var updatedPassenger = mapper.convertValue(updatedNode, Passenger.class);
            updatedPassenger = passengerRepository.save(updatedPassenger);
            return updatedPassenger;

        } catch (JsonPatchException e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public Page<Passenger> getAllPassenger(int pageNumber) {
        if (pageNumber<1) pageNumber = 0;
        else pageNumber=pageNumber-1;
        Pageable pageable = PageRequest.of(pageNumber, NUMBER_OF_ITEMS_PER_PAGE);
        return passengerRepository.findAll(pageable);
    }

    @Override
    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }

    @Override
    public ApiResponse bookRide(BookRideRequest bookRideRequest) {
        //1. find passenger
       Passenger foundPassenger = getPassengerById(bookRideRequest.getPassengerId());
       if (foundPassenger==null) throw new BusinessLogicException(
               String.format("passenger with id %d not found", bookRideRequest.getPassengerId())
       );
        //2. calculate distance between origin and destination
        var response = mockLocationService.getDistanceInformation(bookRideRequest.getOrigin(), bookRideRequest.getDestination());
       DistanceMatrixElement distanceInformation = response.getRows().get(0).getElements().get(0);
//               getDistanceInformation(bookRideRequest.getOrigin(), bookRideRequest.getDestination());
        log.info("response!!->{}", distanceInformation);
        //3. calculate eta
        String eta = distanceInformation.getDuration().getText();
        //4. calculate price
        BigDecimal fare = AppUtilities.calculateRideFare(distanceInformation.getDistance().getText());
        return ApiResponse.builder().fare(fare).estimatedTimeOfArrival(eta).build();
    }

    private DistanceMatrixElement getDistanceInformation(Location origin, Location destination) {
        RestTemplate restTemplate = new RestTemplate();
        String url = buildDistanceRequestUrl(origin, destination);
        ResponseEntity<GoogleDistanceResponse> response =
                restTemplate.getForEntity(url, GoogleDistanceResponse.class);
        return Objects.requireNonNull(response.getBody()).getRows().stream()
                .findFirst().orElseThrow()
                .getElements().stream()
                .findFirst()
                .orElseThrow();
    }

    private  String buildDistanceRequestUrl(Location origin, Location destination){
        return directionConfig.getGoogleDistanceUrl()+"/"+AppUtilities.JSON_CONSTANT+"?"
                +"destinations="+AppUtilities.buildLocation(destination)+"&origins="
                +AppUtilities.buildLocation(origin)+"&mode=driving"+"&traffic_model=pessimistic"
                +"&departure_time="+ LocalDateTime.now().toEpochSecond(ZoneOffset.of("+01:00"))
                +"&key="+directionConfig.getGoogleApiKey();
    }

    private static RegisterResponse getRegisterResponse(Passenger savedPassenger) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(savedPassenger.getId());
        registerResponse.setSuccess(true);
        registerResponse.setMessage("User Registration Successful");
        return registerResponse;
    }
}
