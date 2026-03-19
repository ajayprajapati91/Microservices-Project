package com.example.HospitalMicroservices.service.implementation;

import com.example.HospitalMicroservices.customizedException.HospitalNotFoundException;
import com.example.HospitalMicroservices.entity.Hospital;
import com.example.HospitalMicroservices.helper.MapperHelper;
import com.example.HospitalMicroservices.proxy.HospitalProxy;
import com.example.HospitalMicroservices.repository.HospitalRepo;
import com.example.HospitalMicroservices.service.HospitalService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private MapperHelper mapperHelper;

    @Autowired
    private HospitalRepo hospitalRepo;

//    @Autowired
//    private UsersRepo usersRepo;

//    @Autowired
//    private BloodRequestRepo bloodRequestRepo;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Boolean validate(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String s = authorization.split("Bearer ")[1];
        String URL="http://localhost:8082/auth/verifyToken";
        return Boolean.TRUE.equals(restTemplate.postForObject(URL, s, Boolean.class));
    }

    @Override
    public HospitalProxy registerHospital(HospitalProxy hospitalProxy,HttpServletRequest req) {
        if (validate(req)){
            Hospital hospital = mapperHelper.proxyToEntityHospital(hospitalProxy);
//        hospital.setUsers(users);
            Hospital save = hospitalRepo.save(hospital);

            return mapperHelper.entityToProxyHospital(save);
        }
        throw new HospitalNotFoundException("Unauthorize access",HttpStatus.NOT_FOUND.value());
//        Optional<Users> byId = usersRepo.findById(hospitalProxy.getUserId());
//        if (byId.isEmpty()){
//            throw new UserNotFoundException("user not found", HttpStatus.NOT_FOUND.value());
//        }
//        Users users = byId.get();


    }

//    @Override
//    public String requestBlood(BloodRequestRequestDto dto, HttpServletRequest req) {
//        String authToken = req.getHeader("Authorization");
//        String token = authToken.substring(7);
//        String username = jwtUtil.extractUsername(token);
//        Optional<Users> byUsername = userRepo.findByUsername(username);
//        Users users = byUsername.get();
//        Long id = users.getId();
//
////        Optional<Hospital> byId = hospitalRepo.findById(dto.getHospitalId());
//        Optional<Hospital> byId = hospitalRepo.findByUserId(id);
//        if(byId.isEmpty())throw new NoUserFoundException("No hospital sign up found", HttpStatus.NOT_FOUND.value());
//        Hospital hospital = byId.get();
//        BloodRequest bloodRequest = mapper.dtoToEntityBloodRequest(dto);
//        bloodRequest.setRequestDate(LocalDateTime.now());
//        bloodRequest.setHospital(hospital);
//        bloodRequest.setStatus("Pending");
//        bloodRequestRepo.save(bloodRequest);
//        return "Blood Request is completed";
//    }
//
//    @Override
//    public HospitalResponseDto getHospital(Long id, HttpServletRequest req) {
//        String authToken = req.getHeader("Authorization");
//        String token = authToken.substring(7);
//        String username = jwtUtil.extractUsername(token);
//        Optional<Hospital> byId = hospitalRepo.findById(id);
//
//        if(byId.isEmpty()) throw new NoUserFoundException("No hospital found", HttpStatus.NOT_FOUND.value());
//        if(!username.equals(byId.get().getUser().getUsername()))
//        {
//            throw new NoUserFoundException("Hospital does not match with logged in hospital",HttpStatus.NOT_FOUND.value());
//        }
//        Hospital hospital = byId.get();
//        return mapper.entityToDtoHospital(hospital);
//    }
//
//    @Override
//    public String updateHospital(Long id, HospitalResponseDto dto) {
//        Hospital hospital=hospitalRepo.findById(id).orElseThrow(() -> new  NoUserFoundException("donor not found", HttpStatus.NOT_FOUND.value()));
//        if(dto.getHospitalName()!=null) hospital.setHospitalName(dto.getHospitalName());
//        if(dto.getAddress()!=null) hospital.setAddress(dto.getAddress());
//        if(dto.getLicenseNo()!=null) hospital.setLicenseNo(dto.getLicenseNo());
//        if(dto.getContactNo()!=null) hospital.setContactNo(dto.getContactNo());
//        if(dto.getUserId()!=null)
//        {
//            Users user=userRepo.findById(dto.getUserId()).orElseThrow(() -> new NoUserFoundException("User not found", HttpStatus.NOT_FOUND.value()));
//            hospital.setUser(user);
//        }
//        hospitalRepo.save(hospital);
//        return "Hospital updated successfully";
//    }
//
//    }

//    @Override
//    public String requestBlood(BloodRequestRequestProxy bloodRequestRequestProxy) {
//        Optional<Hospital> byId = hospitalRepo.findById(bloodRequestRequestProxy.getHospitalId());
//        if (byId.isEmpty()){
//            throw new HospitalNotFoundException("Hospital not found with the given Hospital id : "+byId,HttpStatus.NO_CONTENT.value());
//        }
//        Hospital hospital = byId.get();
////        if(!isEligible(bloodRequestRequestProxy)){
////            throw new UserNotFoundException("User not found with this username ", HttpStatus.NOT_FOUND.value());
////        }
//
//        BloodRequest bloodRequest = mapperHelper.proxyToEntityBloodRequest(bloodRequestRequestProxy);
//        bloodRequest.setRequestDate(LocalDateTime.now());
//        bloodRequest.setHospital(hospital);
//        bloodRequest.setStatus(StatusEnum.PENDING);
//        bloodRequestRepo.save(bloodRequest);
//
//        return "Blood request has been done";
//    }

//    @Override
//    public HospitalProxy getCurrentHospitalProfile(Long hospitalId) {
//        Optional<Hospital> byId = hospitalRepo.findById(hospitalId);
//        if (byId.isEmpty()) {
//            throw new HospitalNotFoundException("User not found with this username ", HttpStatus.NOT_FOUND.value());
//        }
//        Hospital hospital = byId.get();
//        return mapperHelper.entityToProxyHospital(hospital);
//    }

//    public Boolean isEligible(BloodRequestRequestProxy bloodRequestRequestProxy) {
//
//        if (bloodRequestRequestProxy.getLastDonationDate() == null) {
//            return bloodRequestRequestProxy.getAge() >= 18 && bloodRequestRequestProxy.getAge() <= 65;
//        }
//
//        long donatedSinceMonths = ChronoUnit.MONTHS.between(
//                bloodRequestRequestProxy.getLastDonationDate(),
//                LocalDateTime.now()
//        );
//
//        return bloodRequestRequestProxy.getAge() >= 18 &&
//                bloodRequestRequestProxy.getAge() <= 65 &&
//                donatedSinceMonths >= 3;
//    }
}
