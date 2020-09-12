//package com.revature.ers.servlets;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.exc.MismatchedInputException;
//import com.revature.ers.dtos.ErrorResponse;
//import com.revature.ers.exceptions.InvalidRequestException;
//import com.revature.ers.exceptions.ResourceNotFoundException;
//import com.revature.ers.models.ErsReimbursement;
//import com.revature.ers.models.Status;
//import com.revature.ers.services.ReimbService;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Set;
//
//@WebServlet("pending/*")
//public class PendingServlet extends HttpServlet {
//
//    private final ReimbService reimbService = new ReimbService();
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        ObjectMapper mapper = new ObjectMapper();
//        PrintWriter respWriter = resp.getWriter();
//        resp.setContentType("application/json");
//
//
//
//        System.out.println(req.getParameter("reimb_status_id"));
//
//        try {
//
//            String statusIdParam = req.getParameter("reimb_status_id");
//
//            if (statusIdParam != null) {
//
//                int id = Integer.parseInt(statusIdParam);
//                ErsReimbursement reimb = reimbService.getReimbById(id);
//                String reimbJSON = mapper.writeValueAsString(reimb);
//                respWriter.write(reimbJSON);
//
//                // TODO get Reimbs by Author ID
//
//            }
//
//            else {
//
//                Set<ErsReimbursement> reimbs = reimbService.getAllByStatus(Status.PENDING);
//
//                String reimbsJSON = mapper.writeValueAsString(reimbs);
//                respWriter.write(reimbsJSON);
//
//                resp.setStatus(200); // 200 = OK
//                System.out.println(resp.getStatus());
//                System.out.println(req.getRequestURI());
//
//            }
//
//        } catch (ResourceNotFoundException rnfe) {
//
//            resp.setStatus(404);
//
//            ErrorResponse err = new ErrorResponse(404, rnfe.getMessage());
//            respWriter.write(mapper.writeValueAsString(err));
//
//        } catch(NumberFormatException | InvalidRequestException e) {
//            resp.setStatus(400); // 400 Bad Request
//            ErrorResponse err = new ErrorResponse(400, "Malformed reimb id parameter value provided.");
//            String errJSON = mapper.writeValueAsString(err);
//            respWriter.write(errJSON);
//        } catch (Exception e) {
//            e.printStackTrace();
//            resp.setStatus(500); // 500 = INTERNAL SERVER ERROR
//            ErrorResponse err = new ErrorResponse(500, "It's not you, it's us. Our bad");
//            respWriter.write(mapper.writeValueAsString(err));
//        } // don't set message for err response unless you know EXACTLY what it says
//
//
//
//    }
//
//
//}
