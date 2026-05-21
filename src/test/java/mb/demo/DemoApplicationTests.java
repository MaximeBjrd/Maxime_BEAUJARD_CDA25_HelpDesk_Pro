package mb.demo;

import mb.demo.model.Ticket;
import mb.demo.model.enums.Category;
import mb.demo.model.enums.Priority;
import mb.demo.model.enums.TicketStatus;
import mb.demo.repository.TicketRepository;
import mb.demo.service.CommentService;
import mb.demo.service.TicketService;
import mb.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DemoApplicationTests {

	@Mock private TicketRepository ticketRepository;

	@InjectMocks private TicketService ticketService;

	@Test
	void changeStatusNotAllowedIfNoTechAssigned() {
		Ticket ticket = new Ticket();
		ticket.setId(1L);
		ticket.setStatus(TicketStatus.OUVERT);
		ticket.setTechnician(null);

		when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

		Exception exception = assertThrows(
				RuntimeException.class,
				() -> ticketService.updateStatus(1L, TicketStatus.EN_COURS)
		);

		assertEquals(
				"Un ticket ne peut pas passer EN_COURS sans technicien affecté",
				exception.getMessage()
		);
	}

	@Test
	void shouldCreateTicketWithCorrectData() {
		Ticket ticket = new Ticket();
		ticket.setTitle("monTitre");
		ticket.setDescription("maDescription");
		ticket.setClient("monClient");
		ticket.setPriority(Priority.BASSE);
		ticket.setCategory(Category.LOGICIEL);
		ticket.setStatus(TicketStatus.OUVERT);
		ticket.setTechnician(null);

		when(ticketRepository.save(any(Ticket.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		Ticket created = ticketService.createTicket(ticket);

		assertEquals("monTitre", created.getTitle());
		assertEquals("maDescription", created.getDescription());
		assertEquals("monClient", created.getClient());
		assertEquals(Priority.BASSE, created.getPriority());
		assertEquals(Category.LOGICIEL, created.getCategory());
		assertEquals(TicketStatus.OUVERT, created.getStatus());
        assertNull(created.getTechnician());
	}
}
