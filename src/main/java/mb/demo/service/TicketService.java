package mb.demo.service;

import mb.demo.model.Ticket;
import mb.demo.model.User;
import mb.demo.model.enums.TicketStatus;
import mb.demo.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

   @Autowired TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    public List<Ticket> getTicketsByStatus(TicketStatus status) {
        return ticketRepository.findByStatus(status);
    }

    public List<Ticket> getTicketsByTechnician(Long technicianId) {
        return ticketRepository.findByTechnicianId(technicianId);
    }

    public Ticket createTicket(Ticket ticket) {
        ticket.setStatus(TicketStatus.OUVERT);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public Ticket updateStatus(Long id, TicketStatus newStatus) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket non trouvé"));

        // Règle 4 : ticket fermé non modifiable
        if (ticket.getStatus() == TicketStatus.FERME) {
            throw new RuntimeException("Un ticket fermé ne peut plus être modifié");
        }

        // Règle 3 : EN_COURS uniquement si technicien affecté
        if (newStatus == TicketStatus.EN_COURS && ticket.getTechnician() == null) {
            throw new RuntimeException("Un ticket ne peut pas passer EN_COURS sans technicien affecté");
        }

        ticket.setStatus(newStatus);
        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public Ticket assignTechnician(Long ticketId, User technician) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket non trouvé"));

        if (ticket.getStatus() == TicketStatus.FERME) {
            throw new RuntimeException("Un ticket fermé ne peut plus être modifié");
        }

        ticket.setTechnician(technician);
        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    public List<Ticket> getLateTickets() {
        return ticketRepository.findAll().stream()
                .filter(Ticket::isLate)
                .toList();
    }
}