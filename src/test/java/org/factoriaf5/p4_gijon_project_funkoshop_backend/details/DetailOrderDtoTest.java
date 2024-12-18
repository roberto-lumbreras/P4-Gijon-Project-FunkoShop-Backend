pa
 * kage org.factoriaf5.p4_gijon_project_funkoshop_backend.details;
 * 
 * import static org.junit.jupiter.api.Assertions.as
 * 
 * import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.Prod
 * import org.junit.jupiter.api.Test;
 * 
 * 
 * 
 * 
 * }
 * 
 * @Test
 * 
 *     System.
 *     DetailOrde
 * Long expResult = null;
 * Long result = instance.getDet
 * 
 * 
 * 
 * 
 * ic void testGetProduct() {
 * System.out.println("getProduct")
 * DetailOrderDTO instance = new DetailOrde
 * Product expResult = null;
 * Product result = instance.get
 * assertEquals(expResult, result);
 * }
 * 
 * @Test
 * public void testGetProductQuant
 * System.out.println("getProductQuantity");
 * DetailOrderDTO instance = new 
 *  
 * 
 *     a
 * }
 * 
 * 
 * ic void testGetPrice() {
 * System.out.println("getPrice");
 * DetailOrderDTO instance = new DetailOrderDTO()
 * Double expResult = null;
 * Double result = instance.getPrice();
 *  
 * 
 * 
 * @Test
 * ic void testSetDetailId() {
 * 
 * Long detailId = 1L;
 * DetailOrderDTO instance = new DetailOrderDTO();
 * instance.setDetailId(detailId);
 * assertEquals(detailId, instance.getDetailId(
 * 
 * 
 * 
 * publi
 *     System.out.println("setProduct");
 * Product product = new Product();
 * DetailOrderDTO instance = new Det
 * 
 * assertEquals(product, instance.ge
 * 
 * 
 * t
 * ic void testSetProductQuantity() {
 * System.out.println("setProductQuantity");
 * Integer productQuantity = 5;
 * DetailOrderDTO instance = new De
 * instance.setProductQuantity(product
 * 
 * 
 * 
 * t
 * ic void testSetPrice() {
 * System.out.println("setPrice");
 * Double price = 100.0;
 * DetailOrderDTO instance = new DetailOrderDTO();
 *  
 * 
 * }
 * 
 * t
 * ic void testConstructorWithDetailOrder() {
 * 
 * DetailOrder detailOrder = new DetailOrder
 * detailOrder.setId(1L);
 * detailOrder.setProduct(new Product());
 * detailOrder.setQuantity(10);
 * detailOrder.setPrice(200.0);
 * 
 *  
       DetailOrderDTO instance = new DetailOrderDTO(detailOrder);
        assertEquals(1L, instance.getDetailId());
        assertEquals(detailOrder.getProduct(), instance.getProduct());
        assertEquals(10, instance.getProductQuantity());
        assertEquals(200.0, instance.getPrice());
    }
}