package model.services;

import java.time.Duration;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

	private Double pricePerHour;
	private Double pricePerDay;
	
	private BrazilTaxService brasilTaxService;

	public RentalService(Double pricePerHour, Double pricePerDay, BrazilTaxService brasilTaxService) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.brasilTaxService = brasilTaxService;
	}

	public Double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(Double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public BrazilTaxService getBrasilTaxService() {
		return brasilTaxService;
	}

	public void setBrasilTaxService(BrazilTaxService brasilTaxService) {
		this.brasilTaxService = brasilTaxService;
	}
	
	public void processInvoice(CarRental carRental) {
		double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
		double horas = minutes / 60;
		
		double pagamentoBasico;
		if(horas <= 12.0) {
			pagamentoBasico = pricePerHour * Math.ceil(horas); 
		} else {
			pagamentoBasico = pricePerDay * Math.ceil(horas / 24); 
		}
		
		double tax = brasilTaxService.tax(pagamentoBasico);
		
		carRental.setInvoice(new Invoice(pagamentoBasico, tax));
		
	}
	
}
