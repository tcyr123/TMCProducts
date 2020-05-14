package edu.slcc.asdv.beans;

import edu.slcc.asdv.bl.Query;
import java.io.File;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Named(value = "callServletBean")
@SessionScoped
public class CallServletBean implements Serializable
{
    String option;
   String song = "20200324.pdf";
    //String[] songs = {"20200324", "20200325", "20200326", "20200327", "20200328"};
    Map<String, String> songs = new HashMap<>();
    

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Map<String, String> getSongs() {
        songs.put("03/24/2020", "20200324");
        songs.put("03/25/2020", "20200325");
        songs.put("03/26/2020", "20200326");
        return songs;
    }

    public void setSongs(Map<String, String> songs) {
        this.songs = songs;
    }

    

    public CallServletBean() throws SQLException
    { 
    }
    
    
 


  

    public void callServlet()
    {
        System.out.println("inside callServelet() of bean");
        FacesContext context = FacesContext.getCurrentInstance();
        try
          {

            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            HttpSession session = request.getSession();
            //String choice = (String)session.getAttribute("song"); //taylor added this line
              //System.out.println("CHOICE: " +choice);
            //session.setAttribute("song", songChoice);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/DownloadServlet");
            dispatcher.forward(request, response);

          }
        catch (Exception e)
          {
            e.printStackTrace();
          }
        finally
          {
            context.responseComplete();
          }
    }
    
    public void selectSong() throws SQLException, IOException, InterruptedException
    {
      boolean locallyStored = Query.daySale(song, option);
      Thread.sleep(5000); //wait 5sec for it to download locally
        if(locallyStored)   
            download();
        else
        {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("download error "));
        }
        
        //delete local file after user downloads
        File myObj = new File("D:/School/NetBeans/2020-SPRING/Web_App_III/Mp03/web/resources/files/" + song + ".pdf"); 
    if (myObj.delete()) { 
      System.out.println("Deleted the file: " + myObj.getName() + LocalDate.now());
    } else {
      System.out.println("Failed to delete the file.");
    }
    }
    
    public void download() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("DownloadServlet?name=" + song +".pdf");
    }
    

}
