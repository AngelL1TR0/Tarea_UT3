package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Article {

    private String articleName;
    private String type;
    private String saleDate;
    private BigDecimal sellingPrice;
    private BigDecimal derivativesCost;
    private BigDecimal productionCost;
    private BigDecimal taxes;
}
