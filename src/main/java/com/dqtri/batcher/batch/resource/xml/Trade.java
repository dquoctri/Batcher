package com.dqtri.batcher.batch.resource.xml;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Trade implements Serializable {

    private String isin = "";

    private long quantity = 0;

    private BigDecimal price = BigDecimal.ZERO;

    private String customer = "";

    private Long id;

    private long version = 0;
}
