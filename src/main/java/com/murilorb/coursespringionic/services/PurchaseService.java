package com.murilorb.coursespringionic.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.murilorb.coursespringionic.domains.BarcodePayment;
import com.murilorb.coursespringionic.domains.Purchase;
import com.murilorb.coursespringionic.domains.PurchaseItem;
import com.murilorb.coursespringionic.domains.enums.PaymentStatus;
import com.murilorb.coursespringionic.repositories.PaymentRepository;
import com.murilorb.coursespringionic.repositories.PurchaseItemRepository;
import com.murilorb.coursespringionic.repositories.PurchaseRepository;
import com.murilorb.coursespringionic.services.exception.ObjectNotFoundException;

@Service
public class PurchaseService {

	@Autowired
	private PurchaseRepository repository;

	@Autowired
	private BarcodeService barcodeService;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private PurchaseItemRepository purchaseItemRepository;

	@Autowired
	private CustomerService customerService;

	public Purchase findById(Integer id) {
		Optional<Purchase> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Purchase.class.getName()));
	}

	@Transactional
	public Purchase insert(Purchase obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		obj.setCustomer(customerService.findById(obj.getCustomer().getId()));
		obj.getPayment().setStatus(PaymentStatus.PENDING);
		obj.getPayment().setPurchase(obj);

		if (obj.getPayment() instanceof BarcodePayment) {
			BarcodePayment payment = (BarcodePayment) obj.getPayment();
			barcodeService.fillInPaymentWithBarcode(payment, obj.getInstant());
		}
		obj = repository.save(obj);
		paymentRepository.save(obj.getPayment());

		for (PurchaseItem item : obj.getItems()) {
			item.setDiscount(0.0);
			item.setProduct(productService.findById(item.getProduct().getId()));
			item.setPrice(item.getProduct().getPrice());
			item.setPurchase(obj);
		}
		purchaseItemRepository.saveAll(obj.getItems());
		System.out.println(obj);
		return obj;
	}

}
