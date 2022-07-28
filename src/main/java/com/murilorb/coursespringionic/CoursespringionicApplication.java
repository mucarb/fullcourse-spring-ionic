package com.murilorb.coursespringionic;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.murilorb.coursespringionic.domains.Address;
import com.murilorb.coursespringionic.domains.BarcodePayment;
import com.murilorb.coursespringionic.domains.CardPayment;
import com.murilorb.coursespringionic.domains.Category;
import com.murilorb.coursespringionic.domains.City;
import com.murilorb.coursespringionic.domains.Customer;
import com.murilorb.coursespringionic.domains.Payment;
import com.murilorb.coursespringionic.domains.Product;
import com.murilorb.coursespringionic.domains.Purchase;
import com.murilorb.coursespringionic.domains.PurchaseItem;
import com.murilorb.coursespringionic.domains.State;
import com.murilorb.coursespringionic.domains.enums.CustomerType;
import com.murilorb.coursespringionic.domains.enums.PaymentStatus;
import com.murilorb.coursespringionic.repositories.AddressRepository;
import com.murilorb.coursespringionic.repositories.CategoryRepository;
import com.murilorb.coursespringionic.repositories.CityRepository;
import com.murilorb.coursespringionic.repositories.CustomerRepository;
import com.murilorb.coursespringionic.repositories.PaymentRepository;
import com.murilorb.coursespringionic.repositories.ProductRepository;
import com.murilorb.coursespringionic.repositories.PurchaseItemRepository;
import com.murilorb.coursespringionic.repositories.PurchaseRepository;
import com.murilorb.coursespringionic.repositories.StateRepository;

@SpringBootApplication
public class CoursespringionicApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private PurchaseRepository purchaseRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private PurchaseItemRepository purchaseItemRepository;

	public static void main(String[] args) {
		SpringApplication.run(CoursespringionicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");
		Category cat3 = new Category(null, "Cama, Mesa e Banho");
		Category cat4 = new Category(null, "Eletrônicos");
		Category cat5 = new Category(null, "Jardinagem");
		Category cat6 = new Category(null, "Decoração");
		Category cat7 = new Category(null, "Perfumaria");

		Product p1 = new Product(null, "Computador", 2000.00);
		Product p2 = new Product(null, "Impressora", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);

		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2));

		p1.getCategories().addAll(Arrays.asList(cat1));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2));
		p3.getCategories().addAll(Arrays.asList(cat1));

		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		productRepository.saveAll(Arrays.asList(p1, p2, p3));

		State st1 = new State(null, "Minas Gerais");
		State st2 = new State(null, "São Paulo");

		City c1 = new City(null, "Uberlândia", st1);
		City c2 = new City(null, "São Paulo", st2);
		City c3 = new City(null, "Campinas", st2);

		st1.getCities().addAll(Arrays.asList(c1));
		st2.getCities().addAll(Arrays.asList(c2, c3));

		stateRepository.saveAll(Arrays.asList(st1, st2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));

		Customer client1 = new Customer(null, "maria Silva", "maria@gmail.com", "36378912377",
				CustomerType.PRIVATE_INDIVIDUAL);
		client1.getPhones().addAll(Arrays.asList("1183267623", "3412445111"));

		Address a1 = new Address(null, "Rua Flores", "300", "Apto 303", "Jardim", "15472107", c1, client1);
		Address a2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "12125722", c2, client1);

		client1.getAdresses().addAll(Arrays.asList(a1, a2));

		customerRepository.saveAll(Arrays.asList(client1));
		addressRepository.saveAll(Arrays.asList(a1, a2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Purchase buy1 = new Purchase(null, sdf.parse("30/09/2017 10:32"), a1, client1);
		Purchase buy2 = new Purchase(null, sdf.parse("10/10/2017 19:35"), a2, client1);

		Payment pay1 = new CardPayment(null, PaymentStatus.SETTLED, buy1, 6);
		buy1.setPayment(pay1);
		Payment pay2 = new BarcodePayment(null, PaymentStatus.PENDING, buy2, sdf.parse("20/10/2017 00:00"), null);
		buy2.setPayment(pay2);

		client1.getPurchases().addAll(Arrays.asList(buy1, buy2));

		purchaseRepository.saveAll(Arrays.asList(buy1, buy2));
		paymentRepository.saveAll(Arrays.asList(pay1, pay2));

		PurchaseItem pi1 = new PurchaseItem(buy1, p1, 0.0, 1, 2000.00);
		PurchaseItem pi2 = new PurchaseItem(buy1, p3, 0.0, 2, 80.00);
		PurchaseItem pi3 = new PurchaseItem(buy2, p2, 100.0, 1, 800.00);

		buy1.getItems().addAll(Arrays.asList(pi1, pi2));
		buy2.getItems().addAll(Arrays.asList(pi3));

		p1.getItems().addAll(Arrays.asList(pi1));
		p2.getItems().addAll(Arrays.asList(pi3));
		p3.getItems().addAll(Arrays.asList(pi2));

		purchaseItemRepository.saveAll(Arrays.asList(pi1, pi2, pi3));
	}

}
