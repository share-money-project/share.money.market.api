package share.money.market.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import share.money.market.api.controller.model.request.OfferInfoModel;
import share.money.market.api.controller.model.response.OperationStatus;
import share.money.market.api.service.MarketService;
import share.money.commons.dto.OfferDto;
import share.money.market.api.shared.ModelMapper;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/market")
public class MarketController {

    private final MarketService marketService;

    @Autowired
    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @PostMapping(path = "/offer")
    public ResponseEntity<OperationStatus> newOffer(@Valid @RequestBody OfferInfoModel offerInfoModel) {
        marketService.newOffer(ModelMapper.map(offerInfoModel, OfferDto.class));
        return new ResponseEntity<>(new OperationStatus("New offer request", "Pending"), HttpStatus.OK);
    }

    @DeleteMapping(path = "/offer/{offerId}")
    public ResponseEntity<OperationStatus> deleteOffer(@PathVariable(name = "offerId") String offerId) {
        marketService.deleteOffer(offerId);
        return new ResponseEntity<>(new OperationStatus("Offer deletion", "Ok"), HttpStatus.OK);
    }
}
