package mb.demo.controller;

import mb.demo.model.Ticket;
import mb.demo.model.User;
import mb.demo.model.enums.TicketStatus;
import mb.demo.service.CommentService;
import mb.demo.service.TicketService;
import mb.demo.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final UserService userService;
    private final CommentService commentService;

    public TicketController(TicketService ticketService, UserService userService, CommentService commentService) {
        this.ticketService = ticketService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping
    public String listTickets(@RequestParam(required = false) TicketStatus status, Model model) {
        if (status != null) {
            model.addAttribute("tickets", ticketService.getTicketsByStatus(status));
        } else {
            model.addAttribute("tickets", ticketService.getAllTickets());
        }
        model.addAttribute("statuses", TicketStatus.values());
        return "tickets/list";
    }

    @GetMapping("/{id}")
    public String ticketDetail(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Ticket ticket = ticketService.getTicketById(id)
                .orElseThrow(() -> new RuntimeException("Ticket non trouvé"));
        model.addAttribute("ticket", ticket);
        model.addAttribute("comments", commentService.getCommentsByTicket(id));
        model.addAttribute("technicians", userService.getTechnicians());
        model.addAttribute("statuses", TicketStatus.values());
        return "tickets/detail";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/new")
    public String newTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "tickets/form";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/new")
    public String createTicket(@ModelAttribute Ticket ticket) {
        ticketService.createTicket(ticket);
        return "redirect:/tickets";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam TicketStatus status) {
        ticketService.updateStatus(id, status);
        return "redirect:/tickets/" + id;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/{id}/assign")
    public String assignTechnician(@PathVariable Long id, @RequestParam Long technicianId) {
        User technician = userService.getUserById(technicianId)
                .orElseThrow(() -> new RuntimeException("Technicien non trouvé"));
        ticketService.assignTechnician(id, technician);
        return "redirect:/tickets/" + id;
    }

    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @RequestParam String content,
                             @AuthenticationPrincipal UserDetails userDetails) {
        User author = userService.getUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        commentService.addComment(id, content, author);
        return "redirect:/tickets/" + id;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/{id}/delete")
    public String deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return "redirect:/tickets";
    }

}