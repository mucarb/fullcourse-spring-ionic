package com.murilorb.coursespringionic.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
import com.murilorb.coursespringionic.domains.enums.CustomerType;
import com.murilorb.coursespringionic.domains.enums.PaymentStatus;
import com.murilorb.coursespringionic.domains.enums.Profile;
import com.murilorb.coursespringionic.repositories.AddressRepository;
import com.murilorb.coursespringionic.repositories.CategoryRepository;
import com.murilorb.coursespringionic.repositories.CityRepository;
import com.murilorb.coursespringionic.repositories.CustomerRepository;
import com.murilorb.coursespringionic.repositories.PaymentRepository;
import com.murilorb.coursespringionic.repositories.ProductRepository;
import com.murilorb.coursespringionic.repositories.PurchaseItemRepository;
import com.murilorb.coursespringionic.repositories.PurchaseRepository;

@Service
public class DBService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

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

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public void instantiateTestDatabase() throws ParseException {
		Category cat1 = new Category(null, "Informática");
		cat1.setImageUrl("https://dl.dropboxusercontent.com/s/a7yw83flop1yhid/cat1.jpg?dl=0");
		Category cat2 = new Category(null, "Escritório");
		cat2.setImageUrl("https://dl.dropboxusercontent.com/s/yflkupcc9edrqb2/cat2.jpg?dl=0");
		Category cat3 = new Category(null, "Cama, Mesa e Banho");
		cat3.setImageUrl("https://dl.dropboxusercontent.com/s/8evcktnl1de1gl2/cat3.jpg?dl=0");
		Category cat4 = new Category(null, "Eletrônicos");
		cat4.setImageUrl("https://dl.dropboxusercontent.com/s/b8ca0o8n68tsb4f/cat4.jpg?dl=0");
		Category cat5 = new Category(null, "Jardinagem");
		cat5.setImageUrl("https://dl.dropboxusercontent.com/s/a8jn81kjtarzpkb/cat5.jpg?dl=0");
		Category cat6 = new Category(null, "Decoração");
		cat6.setImageUrl("https://dl.dropboxusercontent.com/s/m3084ik98r56o4v/cat6.jpg?dl=0");
		Category cat7 = new Category(null, "Perfumaria");
		cat7.setImageUrl("https://dl.dropboxusercontent.com/s/9z5kc67mxnxdvms/cat7.jpg?dl=0");

		Product p1 = new Product(null, "Computador", 2000.00);
		p1.setImageUrl("https://dl.dropboxusercontent.com/s/a9op82k8htmvo9c/prod1.jpg?dl=0");
		Product p2 = new Product(null, "Impressora", 800.00);
		p2.setImageUrl("https://dl.dropboxusercontent.com/s/p4mpj3mch157r5j/prod2.jpg?dl=0");
		Product p3 = new Product(null, "Mouse", 80.00);
		p3.setImageUrl("https://dl.dropboxusercontent.com/s/i0yx22jw0shn5ol/prod3.jpg?dl=0");
		Product p4 = new Product(null, "Mesa de Escritório", 300.00);
		p4.setImageUrl("https://dl.dropboxusercontent.com/s/pjkhz6p6h8r2jo3/prod4.jpg?dl=0");
		Product p5 = new Product(null, "Toalha", 50.00);
		p5.setImageUrl("https://dl.dropboxusercontent.com/s/8f51upenlnateu2/prod5.jpg?dl=0");
		Product p6 = new Product(null, "Colcha", 50.00);
		p6.setImageUrl("https://dl.dropboxusercontent.com/s/gb7hrel0x6lu87g/prod6.jpg?dl=0");
		Product p7 = new Product(null, "TV true color", 1200.00);
		p7.setImageUrl("https://dl.dropboxusercontent.com/s/tt25ii5xkcvvl83/prod7.jpg?dl=0");
		Product p8 = new Product(null, "Roçadeira", 800.00);
		p8.setImageUrl("https://dl.dropboxusercontent.com/s/hiy10gkhviplh2k/prod8.jpg?dl=0");
		Product p9 = new Product(null, "Abajour", 100.00);
		p9.setImageUrl("https://dl.dropboxusercontent.com/s/u7nhf7rzqzga2c7/prod9.jpg?dl=0");
		Product p10 = new Product(null, "Pendente", 180.00);
		p10.setImageUrl("https://dl.dropboxusercontent.com/s/vrhlujmsqkt6023/prod10.jpg?dl=0");
		Product p11 = new Product(null, "Shampoo", 90.00);
		p11.setImageUrl("https://dl.dropboxusercontent.com/s/7m0bno26n6yc4nr/prod11.jpg?dl=0");

		Product p12 = new Product(null, "Product 12", 10.00);
		Product p13 = new Product(null, "Product 13", 10.00);
		Product p14 = new Product(null, "Product 14", 10.00);
		Product p15 = new Product(null, "Product 15", 10.00);
		Product p16 = new Product(null, "Product 16", 10.00);
		Product p17 = new Product(null, "Product 17", 10.00);
		Product p18 = new Product(null, "Product 18", 10.00);
		Product p19 = new Product(null, "Product 19", 10.00);
		Product p20 = new Product(null, "Product 20", 10.00);
		Product p21 = new Product(null, "Product 21", 10.00);
		Product p22 = new Product(null, "Product 22", 10.00);
		Product p23 = new Product(null, "Product 23", 10.00);
		Product p24 = new Product(null, "Product 24", 10.00);
		Product p25 = new Product(null, "Product 25", 10.00);
		Product p26 = new Product(null, "Product 26", 10.00);
		Product p27 = new Product(null, "Product 27", 10.00);
		Product p28 = new Product(null, "Product 28", 10.00);
		Product p29 = new Product(null, "Product 29", 10.00);
		Product p30 = new Product(null, "Product 30", 10.00);
		Product p31 = new Product(null, "Product 31", 10.00);
		Product p32 = new Product(null, "Product 32", 10.00);
		Product p33 = new Product(null, "Product 33", 10.00);
		Product p34 = new Product(null, "Product 34", 10.00);
		Product p35 = new Product(null, "Product 35", 10.00);
		Product p36 = new Product(null, "Product 36", 10.00);
		Product p37 = new Product(null, "Product 37", 10.00);
		Product p38 = new Product(null, "Product 38", 10.00);
		Product p39 = new Product(null, "Product 39", 10.00);
		Product p40 = new Product(null, "Product 40", 10.00);
		Product p41 = new Product(null, "Product 41", 10.00);
		Product p42 = new Product(null, "Product 42", 10.00);
		Product p43 = new Product(null, "Product 43", 10.00);
		Product p44 = new Product(null, "Product 44", 10.00);
		Product p45 = new Product(null, "Product 45", 10.00);
		Product p46 = new Product(null, "Product 46", 10.00);
		Product p47 = new Product(null, "Product 47", 10.00);
		Product p48 = new Product(null, "Product 48", 10.00);
		Product p49 = new Product(null, "Product 49", 10.00);
		Product p50 = new Product(null, "Product 50", 10.00);

		cat1.getProducts()
				.addAll(Arrays.asList(p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24, p25, p26, p27,
						p28, p29, p30, p31, p32, p34, p35, p36, p37, p38, p39, p40, p41, p42, p43, p44, p45, p46, p47,
						p48, p49, p50));

		p12.getCategories().add(cat1);
		p13.getCategories().add(cat1);
		p14.getCategories().add(cat1);
		p15.getCategories().add(cat1);
		p16.getCategories().add(cat1);
		p17.getCategories().add(cat1);
		p18.getCategories().add(cat1);
		p19.getCategories().add(cat1);
		p20.getCategories().add(cat1);
		p21.getCategories().add(cat1);
		p22.getCategories().add(cat1);
		p23.getCategories().add(cat1);
		p24.getCategories().add(cat1);
		p25.getCategories().add(cat1);
		p26.getCategories().add(cat1);
		p27.getCategories().add(cat1);
		p28.getCategories().add(cat1);
		p29.getCategories().add(cat1);
		p30.getCategories().add(cat1);
		p31.getCategories().add(cat1);
		p32.getCategories().add(cat1);
		p33.getCategories().add(cat1);
		p34.getCategories().add(cat1);
		p35.getCategories().add(cat1);
		p36.getCategories().add(cat1);
		p37.getCategories().add(cat1);
		p38.getCategories().add(cat1);
		p39.getCategories().add(cat1);
		p40.getCategories().add(cat1);
		p41.getCategories().add(cat1);
		p42.getCategories().add(cat1);
		p43.getCategories().add(cat1);
		p44.getCategories().add(cat1);
		p45.getCategories().add(cat1);
		p46.getCategories().add(cat1);
		p47.getCategories().add(cat1);
		p48.getCategories().add(cat1);
		p49.getCategories().add(cat1);
		p50.getCategories().add(cat1);

		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2, p4));
		cat3.getProducts().addAll(Arrays.asList(p5, p6));
		cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProducts().addAll(Arrays.asList(p8));
		cat6.getProducts().addAll(Arrays.asList(p9, p10));
		cat7.getProducts().addAll(Arrays.asList(p11));

		p1.getCategories().addAll(Arrays.asList(cat1, cat4));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategories().addAll(Arrays.asList(cat1, cat4));
		p4.getCategories().addAll(Arrays.asList(cat2));
		p5.getCategories().addAll(Arrays.asList(cat3));
		p6.getCategories().addAll(Arrays.asList(cat3));
		p7.getCategories().addAll(Arrays.asList(cat4));
		p8.getCategories().addAll(Arrays.asList(cat5));
		p9.getCategories().addAll(Arrays.asList(cat6));
		p10.getCategories().addAll(Arrays.asList(cat6));
		p11.getCategories().addAll(Arrays.asList(cat7));

		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		productRepository.saveAll(Arrays.asList(p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24, p25,
				p26, p27, p28, p29, p30, p31, p32, p34, p35, p36, p37, p38, p39, p40, p41, p42, p43, p44, p45, p46, p47,
				p48, p49, p50));

//		State st1 = new State(null, "Minas Gerais");
//		State st2 = new State(null, "São Paulo");

		City c1 = cityRepository.findById(2389).get();
		City c2 = cityRepository.findById(5270).get();
		City c3 = cityRepository.findById(4814).get();

		cityRepository.saveAll(Arrays.asList(c1, c2, c3));

		Customer client1 = new Customer(null, "Maria Silva", "murilo.rb60@gmail.com", "36378912377",
				CustomerType.PRIVATE_INDIVIDUAL, passwordEncoder.encode("12345"));
		client1.getPhones().addAll(Arrays.asList("1183267623", "3412445111"));
		client1.setImageUrl("https://dl.dropboxusercontent.com/s/ywlaq6my2lsvhv7/cp1.jpg?dl=0");

		Customer client2 = new Customer(null, "Ana Julia", "rbmuca@gmail.com", "75693260089",
				CustomerType.PRIVATE_INDIVIDUAL, passwordEncoder.encode("54321"));
		client2.getPhones().addAll(Arrays.asList("18981093566", "6732517375"));
		client2.addProfile(Profile.ADMIN);
		client2.setImageUrl("https://dl.dropboxusercontent.com/s/y8g3n32ahsgtj63/cp2.jpg?dl=0");

		Address a1 = new Address(null, "Rua Flores", "300", "Apto 303", "Jardim", "15472107", c1, client1);
		Address a2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "12125722", c2, client1);
		Address a3 = new Address(null, "Avenida Floriano", "2106", null, "Centro", "45346000", c2, client2);

		client1.getAdresses().addAll(Arrays.asList(a1, a2));
		client2.getAdresses().addAll(Arrays.asList(a3));

		customerRepository.saveAll(Arrays.asList(client1, client2));
		addressRepository.saveAll(Arrays.asList(a1, a2, a3));

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
