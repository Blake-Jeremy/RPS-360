package blake.rps;
/*******************************************************************
 *  RpsServlet class
 *  Description: Contains the servlet and hibernate runnable code.
 *******************************************************************/

// Imported Libraries
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "Servlet", urlPatterns={"/Servlet"})
public class RpsServlet extends HttpServlet {

    private RpsHibernateConfig theHibernate = new RpsHibernateConfig();
    private String json;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        // read recent database entries
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><head><style>#custom tr:nth-child(odd){background-color: #f2f2f2;}#custom tr:hover{background-color: #ddd;}#custom td{padding: 10px;}td.a{text-transform:uppercase;font-weight:bold;}</style></head><body>");
        out.println("<h1>Recent Plays</h1>");
        // hibernate query
        Session session = theHibernate.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query lastTenPlays = session.createNativeQuery("select * from json_entry ORDER BY id DESC").setMaxResults(11);
        List<Object> recentEntries = (List<Object>) lastTenPlays.list();
        Iterator itr = recentEntries.iterator();
        out.println("<table id=\"custom\">");
        while (itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            Integer id = Integer.parseInt(String.valueOf(obj[0]));
            String attr = String.valueOf(obj[1]);
            // de-serialize json
            JSONObject jsonObj = new JSONObject(attr.trim());
            Iterator<String> keys = jsonObj.keys();
            out.print("\n<tr>");
            jsonObj.keySet().forEach(keyStr ->
            {
                Object keyvalue = jsonObj.get(keyStr);
                //System.out.println("key: "+ keyStr + " value: " + keyvalue);
                out.print("<td class=\"a\">" + keyStr + "</td><td>" + keyvalue + "</td>");
                //for nested objects iteration if required
                //if (keyvalue instanceof JSONObject)
                //    printJsonObject((JSONObject)keyvalue);
            });
            out.print("</tr>");
        }
        out.println("</table>");
        transaction.commit();
        // end hibernate
        out.println("<p>End of results.</p>");
        out.println("<button onclick=\"goBack()\">Go Back</button><script>function goBack(){window.history.back();}</script>");
        out.println("</body></html>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // accept JSON content from rps client
        try{
            StringBuilder sb = new StringBuilder();
            BufferedReader br = request.getReader();
            String str = null;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            //System.out.println("P#1 " + sb.toString());
            JSONObject jObj = new JSONObject(sb.toString());
            addNewJson(jObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewJson(String json) {
        this.json = json;
        Session session = theHibernate.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        RpsHibernateJsonEntry passedJson = new RpsHibernateJsonEntry();
        passedJson.setJson(json);
        session.save(passedJson);
        transaction.commit();
    }

}

