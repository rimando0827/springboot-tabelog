package com.example.springboot_tabelog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.model.Subscription;
import com.stripe.param.SubscriptionListParams;

@Service
public class StripeService {
	
	@Value("${stripe.api-key}")
	private String stripeApiKey;

	public Customer createCustomer(String name, String email, String paymentMethodId) {
		Stripe.apiKey = stripeApiKey;
		Customer customer = null;

		Map<String, Object> customerParams = new HashMap<>();
		customerParams.put("name", name);
		customerParams.put("email", email);

		try {
			customer = Customer.create(customerParams);

			Map<String, Object> params = new HashMap<>();
			params.put("customer", customer.getId());
			PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);
			paymentMethod.attach(params);

			Map<String, Object> updateParams = new HashMap<>();
			updateParams.put("invoice_settings", Map.of("default_payment_method", paymentMethod.getId()));
			customer.update(updateParams);
		} catch (StripeException e) {
			throw new RuntimeException(e);
		}

		return customer;
	}

	public Subscription createSubscription(String customerId, String priceId) {
		Stripe.apiKey = stripeApiKey;

		Map<String, Object> item = new HashMap<>();
		item.put("price", priceId);

		Map<String, Object> items = new HashMap<>();
		items.put("0", item);

		Map<String, Object> params = new HashMap<>();

		params.put("customer", customerId);
		params.put("items", items);

		try {
			return Subscription.create(params);
		} catch (StripeException e) {
			throw new RuntimeException(e);
		}
	}

	public PaymentMethod getDefaultPaymentMethod(String customerId) {
		try {
			Stripe.apiKey = stripeApiKey;
			Map<String, Object> params = new HashMap<>();
			params.put("customer", customerId);
			params.put("type", "card");
			PaymentMethodCollection paymentMethods = PaymentMethod.list(params);

			// 顧客が支払い方法を追加しているか確認する
			if (!paymentMethods.getData().isEmpty()) {
				return paymentMethods.getData().get(0);
			}
		} catch (StripeException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void attachPaymentMethodToCustomer(String customerId, String paymentMethodId) throws StripeException {
		Stripe.apiKey = stripeApiKey;
		PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);
		Map<String, Object> params = new HashMap<>();
		params.put("customer", customerId);
		paymentMethod.attach(params);
	}

	public void updateSubscription(String customerId, String paymentMethodId) throws StripeException {
		Stripe.apiKey = stripeApiKey;
		attachPaymentMethodToCustomer(customerId, paymentMethodId);
		Map<String, Object> params = new HashMap<>();
		params.put("invoice_settings", Map.of("default_payment_method", paymentMethodId));
		Customer customer = Customer.retrieve(customerId);
		customer.update(params);

	}

	public void detachPaymentMethod(String paymentMethodId) throws StripeException {
		Stripe.apiKey = stripeApiKey;
		PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);
		paymentMethod.detach();

	}

	public Subscription getSubscription(String customerId) {
		Stripe.apiKey = stripeApiKey;
		try {
			SubscriptionListParams params = SubscriptionListParams.builder()
					.setCustomer(customerId)
					.build();

			List<Subscription> subscriptions = Subscription.list(params).getData();

			for (Subscription subscription : subscriptions) {
				if ("active".equals(subscription.getStatus())) {
					return subscription;
				}
			}

			return null;
		} catch (StripeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void cancelSubscription(Subscription subscription) {
		Stripe.apiKey = stripeApiKey;
		try {
			subscription.cancel();
		} catch (StripeException e) {
			e.printStackTrace();
		}
	}

/*	// 期間別月間売上を取得する
	public List<Revenue> getTotalRevenueByPeriod(LocalDate startMonth, LocalDate endMonth) {
		Stripe.apiKey = stripeApiKey;

		List<Revenue> totalRevenues = new ArrayList<>();

		Instant targetMonthInstant = startMonth.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
		Instant endMonthInstant = endMonth.plusMonths(1).withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

		LocalDate targetMonth = startMonth;

		while (targetMonthInstant.getEpochSecond() != endMonthInstant.getEpochSecond()) {
			Instant lastDayOfStartMonth = targetMonth.plusMonths(1).withDayOfMonth(1)
					.atStartOfDay(ZoneId.systemDefault())
					.toInstant();
			long startTime = targetMonthInstant.getEpochSecond();
			long endTime = lastDayOfStartMonth.getEpochSecond();
			Map<String, Object> params = new HashMap<>();
			params.put("created", Map.of(
					"gte", startTime,
					"lt", endTime));

			InvoiceCollection invoices;
			try {
				invoices = Invoice.list(params);
				long totalRevenueCents = 0;

				for (Invoice invoice : invoices.getData()) {
					totalRevenueCents += invoice.getAmountPaid();
				}
				Revenue revenue = new Revenue();
				revenue.setItem(targetMonth.format(DateTimeFormatter.ofPattern("yyyy年M月")));
				revenue.setRevenue(totalRevenueCents);
				totalRevenues.add(revenue);
				targetMonth = targetMonth.plusMonths(1);
				targetMonthInstant = targetMonth.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
			} catch (StripeException e) {
				e.printStackTrace();
			}

		}
		return totalRevenues;
	}
*/
}
