package com.driver.bookMyShow.ServiceImpl;

import com.driver.bookMyShow.Dtos.RequestDtos.FacilityEntryDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnFacilityDto;
import com.driver.bookMyShow.Exceptions.AlreadyPresentException;
import com.driver.bookMyShow.Exceptions.NotFoundException;
import com.driver.bookMyShow.Models.Facility;
import com.driver.bookMyShow.Models.TheaterFacility;
import com.driver.bookMyShow.Repositories.FacilityRepository;
import com.driver.bookMyShow.Repositories.TheaterFacilityRepository;
import com.driver.bookMyShow.Services.FacilityService;
import com.driver.bookMyShow.Services.TheaterFacilityService;
import com.driver.bookMyShow.Transformers.FacilityTransformer;
import com.driver.bookMyShow.constant.Messages;
import com.driver.bookMyShow.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacilityServiceImpl implements FacilityService {
    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private TheaterFacilityService theaterFacilityService;

    @Override
    public ReturnFacilityDto addFacilityService(FacilityEntryDto facilityEntryDto) {
        facilityEntryDto.validate();

        if (facilityRepository.existsByName(facilityEntryDto.getName()))
            throw AlreadyPresentException.getExceptionWithDesc(Messages.FACILITY);
        Facility facility = FacilityTransformer.FacilityEntryDtoToFacility(facilityEntryDto);
        facility.setId(Utils.generateUUID(10));
        facility.setIsActive(Messages.TRUE);
        facility.setDeleted(Messages.FALSE);
        facilityRepository.save(facility);
        return FacilityTransformer.FacilityToReturnFacilityDto(facility);
    }

    @Override
    public List<ReturnFacilityDto> getAllFacilityService() {
        List<Facility> facilityList = facilityRepository.findAll();
        if (facilityList.isEmpty())
            throw NotFoundException.getExceptionWithDesc(Messages.FACILITY);

        List<ReturnFacilityDto> returnFacilityDtoList = facilityList.stream()
                .map(FacilityTransformer::FacilityToReturnFacilityDto)
                .toList();
        return returnFacilityDtoList;
    }

    @Override
    public String deleteFacilityService(String facilityId) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.FACILITY));
        facilityRepository.deleteById(facilityId);

        List<TheaterFacility> tfList = theaterFacilityService.findByFacility(facility);
        tfList.forEach(tf -> {
            theaterFacilityService.deleteById(tf.getId());
        });
        return Messages.FACILITY + Messages.ONE_TAB + Messages.DELETED + Messages.DOT;
    }


    @Override
    public ReturnFacilityDto updateFacilityService(String facilityId, FacilityEntryDto facilityEntryDto) {
        Optional<Facility> existing = facilityRepository.findByName(facilityEntryDto.getName());
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.FACILITY));

        facilityEntryDto.validate();

        if (existing.isPresent() && !existing.get().getId().equals(facilityId))
            throw AlreadyPresentException.getExceptionWithDesc(Messages.FACILITY);

        facility.setName(facilityEntryDto.getName());
        facility.setLogo(facilityEntryDto.getLogo());
        facilityRepository.save(facility);
        return FacilityTransformer.FacilityToReturnFacilityDto(facility);
    }

    @Override
    public ReturnFacilityDto getFacilityById(String facilityId) {
        Optional<Facility> optionalFacilityv1 = facilityRepository.findById(facilityId);
        Facility facility = optionalFacilityv1
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.FACILITY));
        return FacilityTransformer.FacilityToReturnFacilityDto(facility);
    }

    @Override
    public ReturnFacilityDto facilityOnAndOffService(String facilityId) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.FACILITY));

        boolean newStatus = !facility.getIsActive();
        facility.setIsActive(newStatus);
        facilityRepository.save(facility);

        List<TheaterFacility> tfList = theaterFacilityService.findByFacility(facility);
        if (!newStatus) //false
        {
            tfList.forEach(tf -> tf.setIsActive(false));
            theaterFacilityService.saveAll(tfList);
        }
        return FacilityTransformer.FacilityToReturnFacilityDto(facility);
    }

    @Override
    public Boolean existsById(String id) {
        return facilityRepository.existsById(id);
    }

    @Override
    public Boolean existsByIdAndIsActiveTrue(String id) {
        return facilityRepository.existsByIdAndIsActiveTrue(id);
    }

    @Override
    public List<Facility> findAllByIdInAndIsActiveTrue(List<String> ids) {
        return facilityRepository.findAllByIdInAndIsActiveTrue(ids);
    }
}
