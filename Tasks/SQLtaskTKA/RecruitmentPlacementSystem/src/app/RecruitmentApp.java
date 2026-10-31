package app;

import exception.CandidateNotEligibleException;
import exception.CandidateNotFoundException;
import exception.DuplicateApplicationException;
import exception.JobNotFoundException;
import model.Application;
import model.Candidate;
import model.Company;
import model.Interview;
import model.Job;
import model.Offer;
import model.Placement;
import service.ApplicationService;
import service.CandidateService;
import service.CompanyService;
import service.InterviewService;
import service.JobService;
import service.OfferService;
import service.PlacementService;
import service.ReportService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RecruitmentApp {
    private static final Scanner sc = new Scanner(System.in);
    private static final CandidateService candidateService = new CandidateService();
    private static final CompanyService companyService = new CompanyService();
    private static final JobService jobService = new JobService();
    private static final ApplicationService applicationService = new ApplicationService();
    private static final InterviewService interviewService = new InterviewService();
    private static final OfferService offerService = new OfferService();
    private static final PlacementService placementService = new PlacementService();
    private static final ReportService reportService = new ReportService();

    public static void main(String[] args) {
        while (true) {
            System.out.println();
            System.out.println("==================================");
            System.out.println("RECRUITMENT & PLACEMENT SYSTEM");
            System.out.println("==================================");
            System.out.println("1. Candidates");
            System.out.println("2. Companies");
            System.out.println("3. Jobs");
            System.out.println("4. Applications");
            System.out.println("5. Interviews");
            System.out.println("6. Offers");
            System.out.println("7. Placements");
            System.out.println("8. Reports");
            System.out.println("9. Exit");
            int choice = readInt("Enter choice: ");
            if (choice == 9) {
                System.out.println("Thank you. Goodbye!");
                break;
            }
            try {
                switch (choice) {
                    case 1: candidateMenu(); break;
                    case 2: companyMenu(); break;
                    case 3: jobMenu(); break;
                    case 4: applicationMenu(); break;
                    case 5: interviewMenu(); break;
                    case 6: offerMenu(); break;
                    case 7: placementMenu(); break;
                    case 8: reportMenu(); break;
                    default: System.out.println("Invalid choice, please try again.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            } catch (CandidateNotFoundException | JobNotFoundException | DuplicateApplicationException
                     | CandidateNotEligibleException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (DateTimeParseException e) {
                System.out.println("Error: Date must be in yyyy-MM-dd format");
            }
        }
    }

    private static void candidateMenu() throws SQLException, CandidateNotFoundException {
        System.out.println("\n--- Candidate Management ---");
        System.out.println("1. Add  2. Update  3. Search  4. View All  5. Deactivate  6. Back");
        int ch = readInt("Enter choice: ");
        switch (ch) {
            case 1: {
                String name = readString("Name: ");
                String email = readString("Email: ");
                String mobile = readString("Mobile: ");
                String tech = readString("Technology: ");
                double per = readDouble("Percentage: ");
                int year = readInt("Passing year: ");
                int backlogs = readInt("Backlogs: ");
                int id = candidateService.addCandidate(name, email, mobile, tech, per, year, backlogs);
                System.out.println("Candidate added with id " + id);
                break;
            }
            case 2: {
                int id = readInt("Candidate id: ");
                Candidate old = candidateService.getCandidate(id);
                System.out.println("Current: " + old);
                String name = readString("Name: ");
                String email = readString("Email: ");
                String mobile = readString("Mobile: ");
                String tech = readString("Technology: ");
                double per = readDouble("Percentage: ");
                int year = readInt("Passing year: ");
                int backlogs = readInt("Backlogs: ");
                candidateService.updateCandidate(id, name, email, mobile, tech, per, year, backlogs);
                System.out.println("Candidate updated");
                break;
            }
            case 3: {
                String keyword = readString("Search (name / technology / email): ");
                printList(candidateService.searchCandidates(keyword));
                break;
            }
            case 4:
                printList(candidateService.getAllCandidates());
                break;
            case 5: {
                int id = readInt("Candidate id: ");
                candidateService.deactivateCandidate(id);
                System.out.println("Candidate deactivated");
                break;
            }
            default:
                break;
        }
    }

    private static void companyMenu() throws SQLException {
        System.out.println("\n--- Company Management ---");
        System.out.println("1. Add  2. View All  3. Back");
        int ch = readInt("Enter choice: ");
        if (ch == 1) {
            String name = readString("Company name: ");
            String industry = readString("Industry: ");
            String location = readString("Location: ");
            String email = readString("Email: ");
            int id = companyService.addCompany(name, industry, location, email);
            System.out.println("Company added with id " + id);
        } else if (ch == 2) {
            List<Company> list = companyService.getAllCompanies();
            printList(list);
        }
    }

    private static void jobMenu() throws SQLException, JobNotFoundException {
        System.out.println("\n--- Job Management ---");
        System.out.println("1. Create  2. Update  3. View Open Jobs  4. Close  5. Search  6. Back");
        int ch = readInt("Enter choice: ");
        switch (ch) {
            case 1: {
                int companyId = readInt("Company id: ");
                String title = readString("Job title: ");
                String tech = readString("Technology: ");
                int openings = readInt("Openings: ");
                String location = readString("Location: ");
                double pkg = readDouble("Package (LPA): ");
                double minPer = readDouble("Minimum percentage: ");
                int year = readInt("Passing year: ");
                int maxBacklogs = readInt("Maximum backlogs: ");
                LocalDate deadline = readDate("Deadline (yyyy-MM-dd): ");
                int id = jobService.createJob(companyId, title, tech, openings, location, pkg, minPer, year, maxBacklogs, deadline);
                System.out.println("Job created with id " + id);
                break;
            }
            case 2: {
                int id = readInt("Job id: ");
                System.out.println("Current: " + jobService.getJob(id));
                String title = readString("Job title: ");
                String tech = readString("Technology: ");
                int openings = readInt("Openings: ");
                String location = readString("Location: ");
                double pkg = readDouble("Package (LPA): ");
                double minPer = readDouble("Minimum percentage: ");
                int year = readInt("Passing year: ");
                int maxBacklogs = readInt("Maximum backlogs: ");
                LocalDate deadline = readDate("Deadline (yyyy-MM-dd): ");
                jobService.updateJob(id, title, tech, openings, location, pkg, minPer, year, maxBacklogs, deadline);
                System.out.println("Job updated");
                break;
            }
            case 3:
                printList(jobService.getOpenJobs());
                break;
            case 4: {
                int id = readInt("Job id: ");
                jobService.closeJob(id);
                System.out.println("Job closed");
                break;
            }
            case 5: {
                String keyword = readString("Search (title / technology / location): ");
                printList(jobService.searchJobs(keyword));
                break;
            }
            default:
                break;
        }
    }

    private static void applicationMenu() throws SQLException, CandidateNotFoundException, JobNotFoundException,
            DuplicateApplicationException, CandidateNotEligibleException {
        System.out.println("\n--- Applications ---");
        System.out.println("1. Check Eligibility  2. Apply  3. Shortlist  4. Reject  5. View All  6. View by Candidate  7. Back");
        int ch = readInt("Enter choice: ");
        switch (ch) {
            case 1: {
                int cid = readInt("Candidate id: ");
                int jid = readInt("Job id: ");
                System.out.println(applicationService.checkEligibility(cid, jid));
                break;
            }
            case 2: {
                int cid = readInt("Candidate id: ");
                int jid = readInt("Job id: ");
                int id = applicationService.apply(cid, jid);
                System.out.println("Application created with id " + id);
                break;
            }
            case 3: {
                int id = readInt("Application id: ");
                applicationService.changeStatus(id, "SHORTLISTED");
                System.out.println("Application shortlisted");
                break;
            }
            case 4: {
                int id = readInt("Application id: ");
                applicationService.changeStatus(id, "REJECTED");
                System.out.println("Application rejected");
                break;
            }
            case 5:
                printList(applicationService.getAllApplications());
                break;
            case 6: {
                int cid = readInt("Candidate id: ");
                List<Application> list = applicationService.getApplicationsOfCandidate(cid);
                printList(list);
                break;
            }
            default:
                break;
        }
    }

    private static void interviewMenu() throws SQLException {
        System.out.println("\n--- Interviews ---");
        System.out.println("1. Schedule  2. Save Feedback  3. View All  4. Back");
        int ch = readInt("Enter choice: ");
        if (ch == 1) {
            int appId = readInt("Application id: ");
            LocalDate date = readDate("Interview date (yyyy-MM-dd): ");
            String mode = readString("Mode (ONLINE/OFFLINE): ").toUpperCase();
            String interviewer = readString("Interviewer: ");
            int id = interviewService.schedule(appId, date, mode, interviewer);
            System.out.println("Interview scheduled with id " + id);
        } else if (ch == 2) {
            int id = readInt("Interview id: ");
            int tech = readInt("Technical rating (1-10): ");
            int comm = readInt("Communication rating (1-10): ");
            String remarks = readString("Remarks: ");
            String result = readString("Result (SELECTED/NEXT_ROUND/REJECTED): ");
            interviewService.saveFeedback(id, tech, comm, remarks, result);
            System.out.println("Feedback saved");
        } else if (ch == 3) {
            List<Interview> list = interviewService.getAllInterviews();
            printList(list);
        }
    }

    private static void offerMenu() throws SQLException {
        System.out.println("\n--- Offers ---");
        System.out.println("1. Create Offer  2. Accept Offer  3. Decline Offer  4. View All  5. Back");
        int ch = readInt("Enter choice: ");
        if (ch == 1) {
            int appId = readInt("Application id: ");
            String designation = readString("Designation: ");
            double pkg = readDouble("Package (LPA): ");
            LocalDate joining = readDate("Joining date (yyyy-MM-dd): ");
            int id = offerService.createOffer(appId, designation, pkg, joining);
            System.out.println("Offer created with id " + id);
        } else if (ch == 2) {
            int id = readInt("Offer id: ");
            offerService.respondToOffer(id, true);
            System.out.println("Offer accepted");
        } else if (ch == 3) {
            int id = readInt("Offer id: ");
            offerService.respondToOffer(id, false);
            System.out.println("Offer declined");
        } else if (ch == 4) {
            List<Offer> list = offerService.getAllOffers();
            printList(list);
        }
    }

    private static void placementMenu() throws SQLException {
        System.out.println("\n--- Placements ---");
        System.out.println("1. Confirm Joining  2. View All  3. Back");
        int ch = readInt("Enter choice: ");
        if (ch == 1) {
            int appId = readInt("Application id: ");
            LocalDate date = readDate("Actual joining date (yyyy-MM-dd): ");
            int id = placementService.confirmJoining(appId, date);
            System.out.println("Placement recorded with id " + id);
        } else if (ch == 2) {
            List<Placement> list = placementService.getAllPlacements();
            printList(list);
        }
    }

    private static void reportMenu() throws SQLException {
        System.out.println("\n--- Reports ---");
        System.out.println("1. Candidates by Technology");
        System.out.println("2. Open Jobs");
        System.out.println("3. Applications by Status");
        System.out.println("4. Company-wise Placements");
        System.out.println("5. Placed Candidates");
        System.out.println("6. Unplaced Candidates");
        System.out.println("7. Back");
        int ch = readInt("Enter choice: ");
        switch (ch) {
            case 1:
                printMap(reportService.candidatesByTechnology());
                break;
            case 2:
                printList(reportService.openJobs());
                break;
            case 3:
                printMap(reportService.applicationsByStatus());
                break;
            case 4:
                printMap(reportService.companyWisePlacements());
                break;
            case 5:
                printList(reportService.placedCandidates());
                break;
            case 6:
                printList(reportService.unplacedCandidates());
                break;
            default:
                break;
        }
    }

    private static <T> void printList(List<T> list) {
        if (list.isEmpty()) {
            System.out.println("No records found");
            return;
        }
        for (T item : list) {
            System.out.println(item);
        }
    }

    private static void printMap(Map<String, Integer> map) {
        if (map.isEmpty()) {
            System.out.println("No records found");
            return;
        }
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            System.out.printf("%-25s : %d%n", e.getKey(), e.getValue());
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readString(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readString(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }

    private static LocalDate readDate(String prompt) {
        return LocalDate.parse(readString(prompt));
    }
}
