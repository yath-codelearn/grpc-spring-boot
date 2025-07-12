package asia.igsaas.customerservice.customer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto {
    private String id;
    private String name;
    private String email;
}
