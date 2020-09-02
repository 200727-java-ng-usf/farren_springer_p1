package com.revature.ers.jsonservlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JSON means Javascript Object Notation
 * -a JSON is a data format used to transfer data in a platform independent way
 * -a JSON consists of key-value pairs (when it is an object)
 *      >an array IS a valid JSON so the "key-value pair" comment may not make as much sense in
 *          this case
 *
 * JSON =/= JavaScript Object
 *
 * (background: in JS   {} is an object
 *                      function{} is not an object
 *                      [] is an array
 *                      "" is a string
 *
 * Examples of JSONs (a way to send information forward)
 * {
 *     "child1":
 *              {
 *                  "grandchild1": 35,
 *                  "grandchild2": barnacles,
 *                  "grandchild3": hello
 *              }
 *     "child2": 50
 * }
 *
 * Example of XML
 * <xml>
 *     <child1>
 *         <grandchild1> 35</grandchild1>
 *         <grandchild2> barnacles</grandchild2>
 *         <grandchild3> hello</grandchild3>
 *     </child1>
 *     <child2> 50 </child2>
 * </xml>
 */

@WebServlet("/json/*")
public class JsonMasterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonRequestHelper.process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonRequestHelper.process(req, resp);
    }
}
