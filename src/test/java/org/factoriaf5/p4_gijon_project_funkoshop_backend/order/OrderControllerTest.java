package org.factoriaf5.p4_gijon_project_funkoshop_backend.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.factoriaf5.p4_gijon_project_funkoshop_backend.category.Category;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.category.CategoryRepository;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.details.DetailOrder;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.Product;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.ProductDTO;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.product.ProductRepository;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.Role;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.User;
import org.factoriaf5.p4_gijon_project_funkoshop_backend.user.UserRepository;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    List<User> users;
    List<Product> products;
    List<Category> categories;
    List<Order> orders;
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        productRepository.deleteAll();
        orderRepository.deleteAll();
        categoryRepository.deleteAll();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // Inicializar listas
        users = new ArrayList<>();
        products = new ArrayList<>();
        categories = new ArrayList<>();
        orders = new ArrayList<>();

        categories.add(categoryRepository.save(new Category(null, "Electronics", null, false)));
        categories.add(categoryRepository.save(new Category(null, "Clothing", null, false)));
        categories.add(categoryRepository.save(new Category(null, "Books", null, false)));

        products.add(productRepository.save(new Product(null, "Laptop", "Gaming Laptop", new BigDecimal(1000), 5, 5,
                null, null, categories.get(0), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))));
        products.add(productRepository.save(new Product(null, "Shirt", "Cotton Shirt", new BigDecimal(20), 50, 50, null,
                null, categories.get(1), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))));
        products.add(productRepository.save(new Product(null, "Book", "Java Programming", new BigDecimal(30), 15, 15,
                null, null, categories.get(2), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))));

        users.add(userRepository.save(new User(null, "admin", "admin@mail.com", passwordEncoder.encode("password"),
                true, Role.ROLE_ADMIN, "token1", products, "first-name", "last-name", "first-address", "second-address", false , true, "phone-number")));
        users.add(userRepository.save(new User(null, "customer1", "customer1@mail.com",
                passwordEncoder.encode("password"), true, Role.ROLE_USER, "token2", List.of(), "first-name", "last-name", "first-address", "second-address", false , true, "phone-number")));
        users.add(userRepository.save(new User(null, "customer2", "customer2@mail.com",
                passwordEncoder.encode("password"), true, Role.ROLE_USER, "token3", List.of(), "first-name", "last-name", "first-address", "second-address", false , true, "phone-number")));
        
        List<DetailOrder> productListOrderOne = new ArrayList<>();
        productListOrderOne.add(new DetailOrder(null, products.get(0), 2, products.get(0).getPrice().doubleValue()));
        Float totalAmount = (float) productListOrderOne.stream().mapToDouble(x -> x.getPrice() * x.getQuantity()).sum();
        Integer productQuantity = productListOrderOne.stream().mapToInt(x -> x.getQuantity()).sum();
        orders.add(orderRepository.save(new Order(null, users.get(0), LocalDate.now().minusMonths(6), true,
                "Credit Card", Order.Status.PENDING, totalAmount, productQuantity, productListOrderOne)));

        List<DetailOrder> productListOrderTwo = new ArrayList<>();
        productListOrderTwo.add(new DetailOrder(null, products.get(0), 1, products.get(0).getPrice().doubleValue()));
        productListOrderTwo.add(new DetailOrder(null, products.get(1), 1, products.get(1).getPrice().doubleValue()));
        totalAmount = (float) productListOrderTwo.stream().mapToDouble(x -> x.getPrice() * x.getQuantity()).sum();
        productQuantity = productListOrderTwo.stream().mapToInt(x -> x.getQuantity()).sum();
        orders.add(orderRepository.save(new Order(null, users.get(1), LocalDate.now().minusMonths(1), false,
                "Credit Card", Order.Status.PENDING, totalAmount, productQuantity, productListOrderTwo)));

        List<DetailOrder> productListOrderThree = new ArrayList<>();
        productListOrderThree.add(new DetailOrder(null, products.get(0), 1, products.get(0).getPrice().doubleValue()));
        productListOrderThree.add(new DetailOrder(null, products.get(2), 2, products.get(2).getPrice().doubleValue()));
        totalAmount = (float) productListOrderThree.stream().mapToDouble(x -> x.getPrice() * x.getQuantity()).sum();
        productQuantity = productListOrderThree.stream().mapToInt(x -> x.getQuantity()).sum();
        orders.add(orderRepository.save(new Order(null, users.get(2), LocalDate.now(), true, "Credit Card",
                Order.Status.DELIVERED, totalAmount, productQuantity, productListOrderThree)));
    }

    @Test
    void testCreateOrder() throws Exception {
        List<DetailOrder> productList = new ArrayList<>();
        productList.add(new DetailOrder(null, products.get(0), 1, products.get(0).getPrice().doubleValue()));
        productList.add(new DetailOrder(null, products.get(1), 2, products.get(1).getPrice().doubleValue()));
        Float totalAmount = (float) productList.stream().mapToDouble(x -> x.getPrice() * x.getQuantity()).sum();
        Integer productQuantity = productList.stream().mapToInt(x -> x.getQuantity()).sum();
        OrderDTO order = new OrderDTO();
        order.setIsPaid(true);
        order.setOrderDate(LocalDate.now());
        order.setPayment("Credit Card");
        order.setStatus(Order.Status.PENDING);
        order.setUserId(users.get(1).getId());
        order.setProductList(productList);
        order.setTotalAmount(totalAmount);
        order.setProductQuantity(productQuantity);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                .with(SecurityMockMvcRequestPostProcessors.user("admin").password("password").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(order)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testGetAllOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                .with(SecurityMockMvcRequestPostProcessors.user("admin").password("password").roles("ADMIN")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        mapper.writeValueAsString(orders.stream().map(OrderDTO::new).collect(Collectors.toList()))));
    }

    @Test
    void testGetBestSellers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/details/sales")
                .with(SecurityMockMvcRequestPostProcessors.user("admin").password("password").roles("ADMIN")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(mapper.writeValueAsString(List.of(new ProductDTO(products.get(0)),
                                new ProductDTO(products.get(2)), new ProductDTO(products.get(1))))));
    }

    @Test
    void testListByMonth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/details/month")
                .with(SecurityMockMvcRequestPostProcessors.user("admin").password("password").roles("ADMIN")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(mapper.writeValueAsString(List.of(new OrderDTO(orders.get(1))))));
    }

    @Test
    void testListOrdersByUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/user/{id}", users.get(1).getId())
                .with(SecurityMockMvcRequestPostProcessors.user("customer1").password("password").roles("USER")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(mapper.writeValueAsString(List.of(new OrderDTO(orders.get(1))))));
    }

    @Test
    void testUpdateStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/{orderId}", orders.get(0).getOrderId())
                .with(SecurityMockMvcRequestPostProcessors.user("admin").password("password").roles("ADMIN"))
                .param("status", "DELIVERED"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertTrue(orderRepository.findById(orders.get(0).getOrderId()).get().getStatus().name()
                .equals(Order.Status.DELIVERED.name()));
    }
}