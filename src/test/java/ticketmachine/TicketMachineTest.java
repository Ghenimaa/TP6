package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
		// On vérifie que le prix affiché correspond au paramètre passé lors de
		// l'initialisation
		// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
		// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		// Les montants ont été correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
		//s3:n'imprime pas si pas assez d'argent
	void nImprimepasSiBalanceInsufissante() {
		//GIVEN: une machine vierge (initialisée dans @BeforeEAch)
		//WHEN: On ne met pas assez d'argent
		machine.insertMoney(PRICE - 1);
		//THEN ça n'imprime pas
		assertFalse(machine.printTicket(), "Pas assez d'argent, on ne doit pas imrpimer");
	}

	@Test
		//S4:imprime  si assez d'argent
	void imprimeSiBalanceSuffissante() {
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket(), "Il y a assez d'argent, on doit imprimer");
	}


	@Test
		// S5: La balance est décrémentée du prix du ticket quand un ticket est imprimé
	void balanceIsDecrementedAfterPrintingTicket() {
		machine.insertMoney(PRICE + 20);
		machine.printTicket();
		assertEquals(20, machine.getBalance(), "La balance n'a pas été correctement " +
				"décrémentée après l'impression du ticket");
	}

	@Test
		// S6: Le montant total collecté ne change que lorsque le ticket est imprimé
	void montantCollecteMisAJourAprèsImpressionTicket() {
		machine.insertMoney(PRICE);
		machine.printTicket();
		assertEquals(PRICE, machine.getTotal(), "Le montant collecté n'a pas été mis à jour après l'impression du ticket");
	}

	@Test
		// S7 : refund() retourne correctement l'argent inséré
	void refundReturnsCorrectBalance() {
		machine.insertMoney(PRICE + 30); // On insère plus que le prix du ticket
		int refundedAmount = machine.refund();
		assertEquals(PRICE + 30, refundedAmount, "Le montant rendu par refund() est incorrect");
	}

	@Test
		// S8 :  refund() remet la balance à zéro
	void refundResetsBalanceToZero() {
		machine.insertMoney(PRICE + 30); // On insère plus que le prix du ticket
		machine.refund();
		assertEquals(0, machine.getBalance(), "La balance n'a pas été remise à zéro après refund()");
	}

	@Test
		// S9 : Un montant négatif ne peut pas être inséré dans la machine
	void cannotInsertNegativeAmount() {
		assertThrows(IllegalArgumentException.class, () -> machine.insertMoney(-10), "L'insertion d'un montant négatif aurait dû lever une exception");
	}

	@Test
		// S10 : Il est impossible de créer une machine avec un prix de ticket négatif
	void cannotCreateMachineWithNegativeTicketPrice() {
		assertThrows(IllegalArgumentException.class, () -> new TicketMachine(-PRICE), "La création d'une machine avec un prix de ticket négatif aurait dû lever une exception");
	}

}