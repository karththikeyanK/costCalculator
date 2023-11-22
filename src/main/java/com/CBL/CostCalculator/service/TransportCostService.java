package com.CBL.CostCalculator.service;


import com.CBL.CostCalculator.dto.CostRequest;
import com.CBL.CostCalculator.dto.CostResponse;
import com.CBL.CostCalculator.dtoMapper.TransportCostDtoMapper;
import com.CBL.CostCalculator.entity.TransportCost;
import com.CBL.CostCalculator.entity.TransportCostManger;
import com.CBL.CostCalculator.exception.GeneralBusinessException;
import com.CBL.CostCalculator.repo.TransportCostManagerRepository;
import com.CBL.CostCalculator.repo.TransportCostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TransportCostService {
    @Autowired
    private TransportCostRepository transportCostRepository;

    @Autowired
    private TransportCostManagerRepository transportCostMangerRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ShopService shopService;


    public CostResponse createTransportCost(CostRequest costRequest) {
        log.info("Transport Cost Service::Creating transport cost started");
        try{
            if (isValidation(costRequest)) {
                TransportCost transportCost = mapToEntity(costRequest);
                transportCost = transportCostRepository.save(transportCost);
                log.info("Transport Cost Service::Transport cost created successfully");
                return mapToResponse(transportCost);
            }
            throw new GeneralBusinessException("Transport cost creation failed");
        }catch (GeneralBusinessException ex){
            throw ex;
        }
        catch (Exception e) {
            log.error("Error while creating transport cost: {}", e.getMessage());
            throw e;
        }

    }

    public CostResponse updateTransportCost(Integer id, CostRequest costRequest) {
        log.info("Transport Cost Service::Updating transport cost started");
        try{
            TransportCost transportCost = transportCostRepository.findById(id).orElseThrow(() -> new GeneralBusinessException("Transport cost not found with id: " + id ));
            if (isUpdateValidation(costRequest,transportCost)) {
                transportCost = mapToEntity(costRequest);
                transportCost.setId(id);
                transportCost = transportCostRepository.save(transportCost);
                log.info("Transport Cost Service::Transport cost updated successfully");
                return mapToResponse(transportCost);
            }
            throw new GeneralBusinessException("Transport cost updation failed");
        }catch (GeneralBusinessException ex){
            throw ex;
        }
        catch (Exception e) {
            log.error("Error while updating transport cost: {}", e.getMessage());
            throw e;
        }

    }


    public List<CostResponse> getAllTransportCost() {
        log.info("Transport Cost Service::Getting all transport cost started");
        try{
            List<TransportCostManger> transportCostMangers = transportCostMangerRepository.findAll();
            log.info("Transport Cost Service::Transport cost retrieved successfully"+transportCostMangers.toString());
            if (transportCostMangers.isEmpty()) {
                log.error("Transport Cost Service::Transport cost not found");
                throw new GeneralBusinessException("Transport cost not found");
            }
            log.info("Transport Cost Service::Transport cost retrieved successfully");
            return transportCostMangers.stream().map(this::mapToManagerResponse).toList();
        }catch (GeneralBusinessException ex){
            throw ex;
        }
        catch (Exception e) {
            log.error("Error while getting transport cost: {}", e.getMessage());
            throw e;
        }

    }


    public CostResponse getTransportCost(Integer id) {
        log.info("Transport Cost Service::Getting transport cost started");
        try{
            TransportCost transportCost = transportCostRepository.findById(id).orElseThrow(() -> new GeneralBusinessException("Transport cost not found with id: " + id ));
            log.info("Transport Cost Service::Transport cost retrieved successfully");
            return mapToResponse(transportCost);
        }catch (GeneralBusinessException ex){
            throw ex;
        }
        catch (Exception e) {
            log.error("Error while getting transport cost: {}", e.getMessage());
            throw e;
        }

    }

    public String deleteTransportCost(Integer id) {
        log.info("Transport Cost Service::Deleting transport cost started");
        try{
            TransportCost transportCost = transportCostRepository.findById(id).orElseThrow(() -> new GeneralBusinessException("Transport cost not found with id: " + id ));
            transportCostRepository.delete(transportCost);
            log.info("Transport Cost Service::Transport cost deleted successfully");
            return "Transport cost deleted successfully";
        }catch (GeneralBusinessException ex){
            throw ex;
        }
        catch (Exception e) {
            log.error("Error while deleting transport cost: {}", e.getMessage());
            throw e;
        }

    }


    private void validation(CostRequest costRequest){
        log.info("Transport Cost Service::Validation started"+costRequest.toString());
        try{

            if (costRequest.getStatus() == null || costRequest.getStatus().isEmpty()) {
                throw new GeneralBusinessException("Status should not be empty");
            }
            if (costRequest.getDate() == null) {
                throw new GeneralBusinessException("Date should not be empty");
            }
            if (costRequest.getVehicleId() == null || costRequest.getVehicleId() <= 0) {
                throw new GeneralBusinessException("Vehicle id should be greater than zero");
            }
            if (costRequest.getShopId().isEmpty()) {
                throw new GeneralBusinessException("Shop id should not be empty");
            }
            log.info("Transport Cost Service::Validation completed");

        }catch (GeneralBusinessException ex){
            throw ex;
        }
        catch (Exception e) {
            log.error("Error while validating: {}", e.getMessage());
            throw e;
        }
    }


    private boolean isUpdateValidation(CostRequest costRequest, TransportCost transportCost) {
        validation(costRequest);
        return true;
    }

    private boolean isValidation(CostRequest costRequest) {

        validation(costRequest);
        log.info("Transport Cost Service::Request Validation started");

        return true;

    }



    private TransportCost mapToEntity(CostRequest costRequest) {
        log.info("Transport Service::Mapping to entity started");
        try{
            TransportCostDtoMapper transportCostDtoMapper = new TransportCostDtoMapper();
            TransportCost transportCost =transportCostDtoMapper.ConvertToEntity(costRequest,shopService,vehicleService);
            log.info("Transport Service::Mapping to entity completed");
            return transportCost;
        }catch (Exception e){
            log.error("Transport Cost Service::Error while mapping to entity: {}", e.getMessage());
            throw e;
        }
    }


    private CostResponse mapToResponse(TransportCost transportCost) {
        log.info("Transport Service::Mapping to response started");
        try{
            TransportCostDtoMapper transportCostDtoMapper = new TransportCostDtoMapper();
            CostResponse costResponse =transportCostDtoMapper.ConvertToResponse(transportCost);
            log.info("Transport Service::Mapping to response completed");
            return costResponse;
        }catch (Exception e){
            log.error("Transport Cost Service::Error while mapping to response: {}", e.getMessage());
            throw e;
        }
    }

    private CostResponse mapToManagerResponse(TransportCostManger transportCostManger) {
        log.info("Transport Cost Service::Mapping to response started");
        try{
            TransportCostDtoMapper transportCostDtoMapper = new TransportCostDtoMapper();
            CostResponse costResponse =  transportCostDtoMapper.ConvertToManagerResponse(transportCostManger);
            log.info("Transport Cost Service::Mapping to response completed");
            return costResponse;
        }catch (Exception e){
            log.error("Transport Cost Service::Error while mapping to response: {}", e.getMessage());
            throw e;
        }
    }



}
