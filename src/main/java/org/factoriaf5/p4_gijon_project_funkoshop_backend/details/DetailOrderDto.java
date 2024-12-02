package org.factoriaf5.p4_gijon_project_funkoshop_backend.details;

public class DetailOrderDto {
    private Long detailId;
    private Long productId;
    private Long orderId;
    private Integer amount;
    private Double price;
    
    public DetailOrderDto(DetailOrder detailOrder) {
        this.detailId = detailOrder.getDetailId();
        this.productId = detailOrder.getProduct().getProductId();
        this.orderId = detailOrder.getOrder().getOrderId();
        this.amount = detailOrder.getAmount();
        this.price = detailOrder.getPrice();
    }

    public DetailOrderDto() {

    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    

    
}
