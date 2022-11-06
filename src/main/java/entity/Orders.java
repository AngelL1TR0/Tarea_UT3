package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Orders {

    private String name;
    private String type;
    private String saleDate;
    private String deliverDate;
    private String Status;
}
