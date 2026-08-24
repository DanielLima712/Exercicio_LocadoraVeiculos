package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrazilTaxService;
import model.services.RentalService;


public class Main {
	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		
		System.out.println("Entre com os dados do veículo: ");
		System.out.print("Modelo: ");
		String modelo = sc.nextLine();
		System.out.print("Retirada (dd/MM/yyyy hh:mm): ");
		LocalDateTime retirada = LocalDateTime.parse(sc.nextLine(), fmt);
		System.out.print("Retorno (dd/MM/yyyy hh:mm): ");
		LocalDateTime retorno = LocalDateTime.parse(sc.nextLine(), fmt);
		
		CarRental cr = new CarRental(retirada, retorno, new Vehicle(modelo));
		
		System.out.print("Entre com o preço por hora: ");
		double precoHora = sc.nextDouble();
		System.out.print("Entre com o preço por dia: ");
		double precoDia = sc.nextDouble();
		
		RentalService rentalService = new RentalService(precoHora, precoDia, new BrazilTaxService());
		
		rentalService.processInvoice(cr);
		
		System.out.println("FATURA: ");
		System.out.println("Pagamento básico: R$ " + String.format("%.2f", cr.getInvoice().getBasicPayment()));
		System.out.println("Imposto: R$ " + String.format("%.2f", cr.getInvoice().getTax()));
		System.out.println("Pagamento Total: R$ " + String.format("%.2f", cr.getInvoice().getTotalPayment()));
		
		sc.close();
	}
	
}
