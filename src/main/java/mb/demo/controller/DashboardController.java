package mb.demo.controller;

import mb.demo.model.enums.TicketStatus;
import mb.demo.service.TicketService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class DashboardController {

    private final TicketService ticketService;

    public DashboardController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalTickets", ticketService.getAllTickets().size());
        model.addAttribute("openTickets", ticketService.getTicketsByStatus(TicketStatus.OUVERT).size());
        model.addAttribute("inProgressTickets", ticketService.getTicketsByStatus(TicketStatus.EN_COURS).size());
        model.addAttribute("closedTickets", ticketService.getTicketsByStatus(TicketStatus.FERME).size());
        model.addAttribute("lateTickets", ticketService.getLateTickets());
        model.addAttribute("allTickets", ticketService.getAllTickets());
        return "admin/dashboard";
    }

}