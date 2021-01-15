package com.zl.demo.dto;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/11/9 20:37
 */
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessManagerResponse", propOrder={"header", "actions", "results", "outputs"})
@XmlRootElement(name="ProcessManagerResponse")
@Data
public class PMResponseInfo {
    @XmlElement(name="Application_Header")
    private ApplicationHeader header;

    @XmlElement(name="Instinct_Actions")
    private InstinctActions actions;

    @XmlElement(name="PM_Results")
    private PmResults results;

    @XmlElement(name="PM_Outputs")
    private PMOutputs outputs;


    @Data
    public static class ApplicationHeader implements Serializable {
        private Long id;
    }
    @Data
    public static class InstinctActions implements Serializable {
        private String instinct;
    }
    @Data
    public static class PmResults implements Serializable {
        private String pmResults;
    }
    @Data
    public static class PMOutputs implements Serializable {
        private String pmOutputs;
    }

}