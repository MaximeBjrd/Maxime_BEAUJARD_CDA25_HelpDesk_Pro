package mb.demo.service;

import mb.demo.model.Comment;
import mb.demo.model.Ticket;
import mb.demo.model.User;
import mb.demo.model.enums.TicketStatus;
import mb.demo.repository.CommentRepository;
import mb.demo.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired CommentRepository commentRepository;
    @Autowired TicketRepository ticketRepository;

    public CommentService() {}

    public List<Comment> getCommentsByTicket(Long ticketId) {
        return commentRepository.findByTicketId(ticketId);
    }

    public Comment addComment(Long ticketId, String content, User author) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket non trouvé"));

        if (ticket.getStatus() == TicketStatus.FERME) {
            throw new RuntimeException("Impossible d'ajouter un commentaire à un ticket fermé");
        }

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setAuthor(author);
        comment.setTicket(ticket);
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }
}