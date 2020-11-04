package share.money.market.api.controller.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OperationStatus {

    private String operationName;
    private String getOperationStatus;

    public OperationStatus(String operationName, String getOperationStatus) {
        this.operationName = operationName;
        this.getOperationStatus = getOperationStatus;
    }
}
