package model.services;

import java.time.Duration;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

	private Double pricePerDay;
	private Double pricePerHour;
	
	private TaxService taxService;

	public RentalService(Double pricePerDay, Double pricePerHour, TaxService taxService) {
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) {
		
		double minutos = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();		
		double horas = minutos / 60.0;
		
		double basicPayment;
		if (horas <= 12.0) {
			basicPayment = pricePerHour * Math.ceil(horas);
		}
		else {
			basicPayment = pricePerDay * Math.ceil(horas / 24);
		}

		double tax = taxService.tax(basicPayment);

		carRental.setInvoice(new Invoice(basicPayment, tax));
	}
}