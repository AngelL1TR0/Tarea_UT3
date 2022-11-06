package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersToExcel {

    private String name;
    private String type;
    private Date saleDate;
    private Date deliverDate;
    private String ProductStatus;
    private String deliverStatus;
}
