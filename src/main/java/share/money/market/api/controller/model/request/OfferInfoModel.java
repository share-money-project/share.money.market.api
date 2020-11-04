package share.money.market.api.controller.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class OfferInfoModel {

    @NotNull
    @NotEmpty
    private String userId;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "1000000000")
    private String amount;
}
