package com.CBL.CostCalculator.service;

import com.CBL.CostCalculator.dto.ShopRequest;
import com.CBL.CostCalculator.dto.ShopResponse;
import com.CBL.CostCalculator.dtoMapper.ShopDtoMapper;
import com.CBL.CostCalculator.entity.Region;
import com.CBL.CostCalculator.entity.Shop;
import com.CBL.CostCalculator.exception.GeneralBusinessException;
import com.CBL.CostCalculator.repo.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.CBL.CostCalculator.repo.ShopRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShopService {
    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private RegionService regionService;



    public ShopResponse createShop(ShopRequest shopRequest) {
        log.info("Shop Service: Creating shop started");
        try{
            if (isValidation(shopRequest)){
                Shop shop = shopRepository.save(mapToEntity(shopRequest));
                log.info("Shop Service: Shop object created");
                ShopResponse shopResponse = mapToResponse(shop);
                log.info("Shop Service: Creating shop completed");
                return shopResponse;
            }
            throw new GeneralBusinessException("Shop creation failed");
        }catch (GeneralBusinessException ex){
            log.error("Shop Service: Error while creating shop: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Shop Service: Unexpected error while creating shop: "+ ex.getMessage());
            throw ex;
        }
    }

    public ShopResponse getShop(Integer shopId) {
        log.info("Shop Service: Getting shop started");
        try{
            Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new GeneralBusinessException("Shop with id " + shopId + " does not exist"));
            log.info("Shop Service: Shop object retrieved");
            ShopResponse shopResponse = mapToResponse(shop);
            log.info("Shop Service: Getting shop completed");
            return shopResponse;
        }catch (GeneralBusinessException ex){
            log.error("Shop Service: Error while getting shop: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Shop Service: Unexpected error while getting shop: "+ ex.getMessage());
            throw ex;
        }
    }

    public List<ShopResponse> getAllShop(){
        log.info("Shop Service: Getting all shops started");
        try{
            List<Shop> shopList = shopRepository.findAll();
            log.info("Shop Service: Shop object retrieved");
            List<ShopResponse> shopResponseList = shopList.stream().map(this::mapToResponse).collect(Collectors.toList());
            log.info("Shop Service: Getting all shops completed");
            return shopResponseList;
        }catch (GeneralBusinessException ex){
            log.error("Shop Service: Error while getting all shops: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Shop Service: Unexpected error while getting all shops: "+ ex.getMessage());
            throw ex;
        }
    }


    public List<ShopResponse> getAllShopByRegion(Integer regionId){
        log.info("Shop Service: Getting all shops started");
        try{
            Region region = regionService.getRegionEntity(regionId);
            List<Shop> shopList = shopRepository.findAllByRegion(region);
            log.info("Shop Service: Shop object retrieved");
            List<ShopResponse> shopResponseList = shopList.stream().map(this::mapToResponse).collect(Collectors.toList());
            log.info("Shop Service: Getting all shops completed");
            return shopResponseList;
        }catch (GeneralBusinessException ex){
            log.error("Shop Service: Error while getting all shops: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Shop Service: Unexpected error while getting all shops: "+ ex.getMessage());
            throw ex;
        }
    }

    public ShopResponse updateShop(Integer shopId, ShopRequest shopRequest) {
        log.info("Shop Service: Updating shop started");
        try{
            Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new GeneralBusinessException("Shop with id " + shopId + " does not exist"));
            if (isUpdateValidation(shopRequest,shop)){
                log.info("Shop Service: Shop object retrieved");
                shop.setName(shopRequest.getName());
                shop.setRegNumber(shopRequest.getRegNumber());
                shop.setAddress(shopRequest.getAddress());
                shop.setContact(shopRequest.getContact());
                shop.setDistance(shopRequest.getDistance());
                shop.setRegion(regionService.getRegionEntity(shopRequest.getRegionId()));
                shopRepository.save(shop);
                log.info("Shop Service: Shop object updated");
                ShopResponse shopResponse = mapToResponse(shop);
                log.info("Shop Service: Updating shop completed");
                return shopResponse;
            }
            throw new GeneralBusinessException("Shop update failed");
        }catch (GeneralBusinessException ex){
            log.error("Shop Service: Error while updating shop: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Shop Service: Unexpected error while updating shop: "+ ex.getMessage());
            throw ex;
        }
    }

    public String deleteShop(Integer shopId) {
        log.info("Shop Service: Deleting shop started");
        try{
            Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new GeneralBusinessException("Shop with id " + shopId + " does not exist"));
            log.info("Shop Service: Shop object retrieved");
            shopRepository.delete(shop);
            log.info("Shop Service: Deleting shop completed");
            return "Shop deleted successfully";
        }catch (GeneralBusinessException ex){
            log.error("Shop Service: Error while deleting shop: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Shop Service: Unexpected error while deleting shop: "+ ex.getMessage());
            throw ex;
        }
    }

    public Shop getShopEntity(Integer id){
        log.info("Shop Service: Getting shop Entity started");
        try{
            return shopRepository.findById(id).orElseThrow(() -> new GeneralBusinessException("Shop with id " + id + " does not exist"));
        }catch (GeneralBusinessException ex){
            log.error("Shop Service: Error while getting shop: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Shop Service: Unexpected error while getting shop: "+ ex.getMessage());
            throw ex;
        }
    }


    private Shop mapToEntity(ShopRequest shopRequest){
        ShopDtoMapper shopDtoMapper = new ShopDtoMapper();
        return shopDtoMapper.convertToEntity(shopRequest,regionService);
    }

    private ShopResponse mapToResponse(Shop shop){
        ShopDtoMapper shopDtoMapper = new ShopDtoMapper();
        return shopDtoMapper.convertToResponse(shop);
    }


    private void ValidationShopRequest(ShopRequest shopRequest){
        if (shopRequest.getName() == null || shopRequest.getName().isEmpty()){
            throw new GeneralBusinessException("Shop name cannot be empty");
        }
        if (shopRequest.getRegNumber() == null || shopRequest.getRegNumber().isEmpty()){
            throw new GeneralBusinessException("Shop registration number cannot be empty");
        }
        if (shopRequest.getAddress() == null || shopRequest.getAddress().isEmpty()){
            throw new GeneralBusinessException("Shop address cannot be empty");
        }
        if (shopRequest.getContact() == null || shopRequest.getContact().isEmpty()){
            throw new GeneralBusinessException("Shop contact cannot be empty");
        }
        if (shopRequest.getDistance() == null){
            throw new GeneralBusinessException("Shop distance cannot be empty");
        }
        if (shopRequest.getRegionId() == null){
            throw new GeneralBusinessException("Shop region cannot be empty");
        }
    }

    private boolean isValidation(ShopRequest shopRequest) {
        ValidationShopRequest(shopRequest);
        if (shopRepository.existsByRegNumber(shopRequest.getRegNumber())){
            throw new GeneralBusinessException("Shop registration number already exists");
        }
        return true;

    }

    private boolean isUpdateValidation(ShopRequest shopRequest, Shop shop) {
        ValidationShopRequest(shopRequest);
        if (shopRepository.existsByRegNumber(shopRequest.getRegNumber()) && !shop.getRegNumber().equals(shopRequest.getRegNumber())){
            throw new GeneralBusinessException("Shop registration number already exists");
        }
        return true;

    }


}
