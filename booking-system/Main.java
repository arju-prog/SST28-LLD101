import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {

        // --- Setup Cities ---
        City bangalore = new City("C1", "Bangalore");
        City mumbai = new City("C2", "Mumbai");

        // --- Setup Movies ---
        Movie inception = new Movie("M1", "Inception", 148);
        Movie darkKnight = new Movie("M2", "The Dark Knight", 152);
        Movie interstellar = new Movie("M3", "Interstellar", 169);

        // --- Setup Seats for Screen 1 (mix of types) ---
        List<Seat> screen1Seats = Arrays.asList(
                new Seat("S1-1", 1, 1, SeatType.SILVER),
                new Seat("S1-2", 1, 2, SeatType.SILVER),
                new Seat("S1-3", 1, 3, SeatType.SILVER),
                new Seat("S1-4", 2, 1, SeatType.GOLD),
                new Seat("S1-5", 2, 2, SeatType.GOLD),
                new Seat("S1-6", 3, 1, SeatType.PLATINUM),
                new Seat("S1-7", 3, 2, SeatType.PLATINUM)
        );

        // --- Setup Seats for Screen 2 ---
        List<Seat> screen2Seats = Arrays.asList(
                new Seat("S2-1", 1, 1, SeatType.SILVER),
                new Seat("S2-2", 1, 2, SeatType.SILVER),
                new Seat("S2-3", 2, 1, SeatType.GOLD),
                new Seat("S2-4", 2, 2, SeatType.GOLD),
                new Seat("S2-5", 3, 1, SeatType.PLATINUM)
        );

        // --- Setup Screens ---
        Screen screen1 = new Screen("SCR1", screen1Seats);
        Screen screen2 = new Screen("SCR2", screen2Seats);
        Screen screen3 = new Screen("SCR3", screen1Seats);

        // --- Setup Theatres ---
        Theatre pvr = new Theatre("T1", "PVR Cinemas", bangalore, Arrays.asList(screen1, screen2));
        Theatre inox = new Theatre("T2", "INOX Multiplex", bangalore, Arrays.asList(screen3));
        Theatre carnival = new Theatre("T3", "Carnival Cinemas", mumbai, Arrays.asList(screen2));

        // --- Setup Shows ---
        // Weekday evening show
        Show show1 = new Show("SH1", inception, screen1, LocalDateTime.of(2026, 3, 31, 18, 0));
        // Weekend night show (Saturday 9:30 PM) - both surcharges apply
        Show show2 = new Show("SH2", darkKnight, screen2, LocalDateTime.of(2026, 4, 4, 21, 30));
        // Weekday matinee - no surcharges
        Show show3 = new Show("SH3", inception, screen3, LocalDateTime.of(2026, 3, 31, 14, 0));
        Show show4 = new Show("SH4", interstellar, screen2, LocalDateTime.of(2026, 4, 5, 20, 0));

        pvr.addShow(show1);
        pvr.addShow(show2);
        inox.addShow(show3);
        carnival.addShow(show4);

        // --- Setup Pricing ---
        Map<SeatType, Double> basePriceMap = new HashMap<>();
        basePriceMap.put(SeatType.SILVER, 200.0);
        basePriceMap.put(SeatType.GOLD, 400.0);
        basePriceMap.put(SeatType.PLATINUM, 700.0);

        List<PricingStrategy> strategies = Arrays.asList(
                new NightSurgePricing(),
                new WeekendSurgePricing()
        );

        PricingEngine pricingEngine = new PricingEngine(basePriceMap, strategies);
        BookingService bookingService = new BookingService(pricingEngine);

        // --- Setup Controller ---
        Map<City, List<Theatre>> cityTheatreMap = new HashMap<>();
        cityTheatreMap.put(bangalore, Arrays.asList(pvr, inox));
        cityTheatreMap.put(mumbai, Arrays.asList(carnival));

        MovieTicketBookingController controller = new MovieTicketBookingController(cityTheatreMap, bookingService);

        // ==================== DEMO FLOWS ====================

        System.out.println("========== FLOW 1: Browse by Movies ==========");
        List<Movie> moviesInBangalore = controller.showMovies(bangalore);
        System.out.println("Movies in Bangalore: " + moviesInBangalore);

        List<Theatre> theatresForInception = controller.getTheatresForMovie(bangalore, inception);
        System.out.println("Theatres showing Inception: " + theatresForInception);

        List<Show> inceptionShows = controller.getShowsForMovie(pvr, inception);
        System.out.println("Inception shows at PVR: " + inceptionShows);

        System.out.println("\n========== FLOW 2: Browse by Theatres ==========");
        List<Theatre> theatresInBangalore = controller.showTheatres(bangalore);
        System.out.println("Theatres in Bangalore: " + theatresInBangalore);

        List<Movie> moviesAtPVR = pvr.getCurrentMovies();
        System.out.println("Movies at PVR: " + moviesAtPVR);

        System.out.println("\n========== BOOKING: Weekday Evening (no surcharge) ==========");
        List<Seat> seatsToBook = Arrays.asList(screen1Seats.get(3), screen1Seats.get(4)); // 2 GOLD seats
        MovieTicket ticket1 = controller.bookTickets(show1, seatsToBook);
        System.out.println("Booked: " + ticket1);
        System.out.println("Available seats after booking: " + show1.getAvailableSeats().size());

        System.out.println("\n========== DOUBLE BOOKING: Same seats should fail ==========");
        try {
            controller.bookTickets(show1, seatsToBook);
            System.out.println("ERROR: Should not reach here!");
        } catch (RuntimeException e) {
            System.out.println("Correctly rejected: " + e.getMessage());
        }

        System.out.println("\n========== CANCELLATION + REFUND ==========");
        double refund = controller.cancelBooking(ticket1);
        System.out.println("Refund amount: " + String.format("%.2f", refund));
        System.out.println("Ticket after cancel: " + ticket1);
        System.out.println("Available seats after cancel: " + show1.getAvailableSeats().size());

        System.out.println("\n========== RE-BOOKING after cancellation ==========");
        MovieTicket ticket2 = controller.bookTickets(show1, seatsToBook);
        System.out.println("Re-booked: " + ticket2);

        System.out.println("\n========== PRICING: Weekend Night Show (both surcharges) ==========");
        // Saturday 9:30 PM show - Night (1.2x) + Weekend (1.5x) = 1.8x base
        List<Seat> weekendSeats = Arrays.asList(screen2Seats.get(0), screen2Seats.get(4)); // SILVER + PLATINUM
        MovieTicket ticket3 = controller.bookTickets(show2, weekendSeats);
        System.out.println("Weekend night ticket: " + ticket3);
        System.out.println("  SILVER base=200, after night=240, after weekend=360");
        System.out.println("  PLATINUM base=700, after night=840, after weekend=1260");
        System.out.println("  Expected total=1620.00");

        System.out.println("\n========== CONCURRENCY: Two threads race for same seats ==========");
        // Reset: use show3 at INOX
        List<Seat> racingSeats = Arrays.asList(screen1Seats.get(5)); // 1 PLATINUM seat
        AtomicReference<String> winner = new AtomicReference<>("none");
        CountDownLatch ready = new CountDownLatch(2);
        CountDownLatch go = new CountDownLatch(1);

        Thread user1 = new Thread(() -> {
            ready.countDown();
            try { go.await(); } catch (InterruptedException e) { return; }
            try {
                MovieTicket t = controller.bookTickets(show3, racingSeats);
                winner.set("User1");
                System.out.println("  User1 booked: " + t);
            } catch (RuntimeException e) {
                System.out.println("  User1 rejected: " + e.getMessage());
            }
        });

        Thread user2 = new Thread(() -> {
            ready.countDown();
            try { go.await(); } catch (InterruptedException e) { return; }
            try {
                MovieTicket t = controller.bookTickets(show3, racingSeats);
                winner.set("User2");
                System.out.println("  User2 booked: " + t);
            } catch (RuntimeException e) {
                System.out.println("  User2 rejected: " + e.getMessage());
            }
        });

        user1.start();
        user2.start();

        try {
            ready.await();
            go.countDown(); // release both threads simultaneously
            user1.join();
            user2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("  Winner: " + winner.get());
        System.out.println("  Only one thread should have succeeded.");

        System.out.println("\n========== ADMIN: Concurrent Show Addition ==========");
        Show newShow = new Show("SH5", interstellar, screen1, LocalDateTime.of(2026, 4, 1, 10, 0));
        pvr.addShow(newShow);
        System.out.println("Admin added show: " + newShow);
        System.out.println("All PVR shows: " + pvr.getShows());

        try {
            Show overlapping = new Show("SH6", darkKnight, screen1, LocalDateTime.of(2026, 3, 31, 17, 0));
            pvr.addShow(overlapping);
            System.out.println("ERROR: Should not reach here!");
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly rejected overlapping show: " + e.getMessage());
        }

        System.out.println("\n========== DONE ==========");
    }
}
