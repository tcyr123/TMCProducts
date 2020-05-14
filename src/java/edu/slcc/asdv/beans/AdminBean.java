package edu.slcc.asdv.beans;

import edu.slcc.asdv.bl.Item;
import edu.slcc.asdv.bl.Query;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import javax.inject.Named;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.Part;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

@Named(value = "adminBean")
@SessionScoped
public class AdminBean implements Serializable {

     
    private String choice = "all";
    private Collection<Item> allItem;
    private Item currentItem = new Item();
    private Map<String, String> allItemNo = new HashMap<>();
    private String title;
    private String price;
    private String category;
    private String picture_ref;
    private String qty;
    private String item_no;
    private String description;
    private boolean isSuccess = true;
    private Part uploadedFile;
    private String pngTitle;

    //<editor-fold defaultstate="collapsed" desc="Basic Getters & Setters">
    public boolean isIsSuccess() {
        return isSuccess;
    }

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Collection<Item> getAllItem() {
        return allItem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPicture_ref() {
        return picture_ref;
    }

    public void setPicture_ref(String picture_ref) {
        this.picture_ref = picture_ref;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPngTitle() {
        return pngTitle;
    }

    public void setPngTitle(String pngTitle) {
        this.pngTitle = pngTitle;
    }

    public Map<String, String> getAllItemNo() {
        return allItemNo;
    }
//</editor-fold>
    
    public AdminBean() {
    }

    public Collection<Item> findAll() throws SQLException {//launches on admin login
        allItem = Query.pfs.findAll2();
        for (Item it : allItem) {
            allItemNo.put(it.getItem_no(), it.getItem_no());
        }
        return allItem;
    }

    public void valueChangeMethod(ValueChangeEvent e) throws SQLException {
        if (item_no != null) {

            currentItem = Query.pfs.findAll2Arg(item_no);
            this.title = currentItem.getTitle();
            this.price = currentItem.getPrice();
            this.category = currentItem.getCategory();
            this.picture_ref = currentItem.getPicture_ref();
            this.qty = currentItem.getQty();
            this.description = currentItem.getDescription();
        }
    }

    public void saveNewInfo() {
        currentItem.setTitle(title);
        currentItem.setPrice(price);
        currentItem.setCategory(category);
        currentItem.setPicture_ref(picture_ref);
        currentItem.setQty(qty);
        currentItem.setDescription(description);
        currentItem.setItem_no(item_no);
    }

    public void update() throws SQLException {
        saveNewInfo();
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            Query.pfs.update(currentItem);
            context.addMessage(null, new FacesMessage("Update item# " + item_no + " Successful!"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Update item# " + item_no
                    + " Failed... --> " + e.getLocalizedMessage()));
        }
        clear();
        this.setChoice("all");
    }

    public void insert() throws SQLException {
        saveNewInfo();
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            Query.pfs.create(currentItem);
            context.addMessage(null, new FacesMessage("Insert item# " + item_no + " Successful!"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Insert item# " + item_no
                    + " Failed... --> " + e.getLocalizedMessage()));
        }
        clear();
        this.setChoice("all");
    }

    public void delete() throws SQLException {
        saveNewInfo();
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            Query.pfs.delete(currentItem);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Delete item# " + item_no
                    + " Failed... --> " + e.getLocalizedMessage()));
        }
        context.addMessage(null, new FacesMessage("Delete item# " + item_no + " Successful!"));
        clear();
        this.setChoice("all");
    }

    public void clear() {
        this.title = "";
        this.price = "";
        this.category = "";
        this.picture_ref = "";
        this.qty = "";
        this.item_no = "";
        this.description = "";
        allItemNo = new HashMap<>();
    }

    public void saveFile() throws SQLException, InterruptedException {
        FacesContext context = FacesContext.getCurrentInstance();
        try (InputStream input = uploadedFile.getInputStream()) {
            String fileName = uploadedFile.getSubmittedFileName();
            //save locally
            Files.copy(input, new File("D:/School/NetBeans/2020-SPRING/Web_App_III/Mp03/web/resources/files", fileName).toPath());
            //push to db
            Query.uploadPNG(pngTitle, fileName, "D:/School/NetBeans/2020-SPRING/Web_App_III/Mp03/web/resources/files"); 
            context.addMessage(null, new FacesMessage("Upload Successful!"));
            
            Thread.sleep(5000); //wait 5 secs
            //delete local file after user downloads
        File myObj = new File("D:/School/NetBeans/2020-SPRING/Web_App_III/Mp03/web/resources/files/" + fileName); 
    if (myObj.delete()) { 
      System.out.println("Deleted the file: " + fileName + LocalDate.now());
    } else {
      System.out.println("Failed to delete the file. " + fileName);
    }
        } catch (IOException e) {
            isSuccess = false;
            context.addMessage(null, new FacesMessage("Upload failed..."));
        }
    }
    
    public static void fillForm() throws IOException
    {
        String formTemplate = "D:/School/NetBeans/2020-SPRING/Web_App_III/Mp03/invoiceTemplate/lala.pdf";
        
        // load the document
        PDDocument pdfDocument = PDDocument.load(new File(formTemplate));

        // get the document catalog
        PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();

        // as there might not be an AcroForm entry a null check is necessary
        if (acroForm != null)
        {
            // Retrieve an individual field and set its value.
            PDField field =  acroForm.getField( "cNameBX" );
            field.setValue("kekekeke");
        }

         //Save and close the filled out form.
        pdfDocument.save("D:/School/NetBeans/2020-SPRING/Web_App_III/Mp03/invoiceTemplate/updated.pdf");
        pdfDocument.close();
    }
    
//    public static void main(String[] args) throws IOException {
//         File myObj = new File("D:/School/NetBeans/2020-SPRING/Web_App_III/Mp03/web/resources/files/" + "aura.jpg"); 
//    if (myObj.delete()) { 
//      System.out.println("Deleted the file: " + "aura.jpg" + LocalDate.now());
//    } else {
//      System.out.println("Failed to delete the file. " + "aura.jpg");
//    }
//    }

}
