package com.automation.web.elementrepository;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.automation.ui.selenium.BasePage;

public class AdminPageFactory extends BasePage {

    public AdminPageFactory(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath ="//a[contains(@href,'admin_add.php')]")
    public WebElement adminPage_btn_addAdmin;
    
    @FindBy(name ="first_name")
    public WebElement adminPage_input_firstName;
    
    @FindBy(name ="last_name")
    public WebElement adminPage_input_lastName;
    
    @FindBy(name ="contact_number")
    public WebElement adminPage_input_phone;
    
    @FindBy(name ="email")
    public WebElement adminPage_input_email;
    
    @FindBy(name ="level_role_id")
    public WebElement adminPage_dropdown_role;
    
    @FindBy(xpath ="//button[@data-target='#AddAdminPerModal']")
    public WebElement adminPage_btn_permission;
    
    @FindBy(id ="SaveBtn")
    public WebElement adminPage_btn_save;
    
    @FindBy(id ="edituser")
    public WebElement adminPage_btn_saveEditDetails;
    
    @FindBy(id ="Admintable")
    public WebElement adminPage_table_adminList;
    
    @FindBy(xpath ="//tr[contains(@id,'detail_row')]")
    public List<WebElement> adminPage_tableRow_adminList;
    
    @FindBy(id ="first_name-error")
    public WebElement adminPage_txt_firstNameError;
    
    @FindBy(id ="last_name-error")
    public WebElement adminPage_txt_lastNameError;
    
    @FindBy(id ="email-error")
    public WebElement adminPage_txt_emailError;
    
    @FindBy(id ="contact_number-error")
    public WebElement adminPage_txt_phoneNumberError;
    
    @FindBy(id ="level_role_id-error")
    public WebElement adminPage_txt_roleError;
    
    public WebElement adminPage_tableItems_adminName(String firstName, String lastName) {
        return getElement(String.format("//td[@class='firstName' and contains(text(),'%s')]/..//td[contains(@class,'lastName') and contains(text(),'%s')]", StringUtils.capitalize(firstName), StringUtils.capitalize(lastName)));
    }
    
    @FindBy(id ="Admintable_next")
    public WebElement adminPage_btn_next;
    
    @FindBy(id ="Admintable_previous")
    public WebElement adminPage_btn_previous;
    
    public WebElement adminPage_dropdownOptions_role(String role) {
        return getElement(String.format("//option[text()='%s']", role));
    }
    
    @FindBy(className ="deleteAdmin")
    public WebElement adminPage_btn_delete;
    
    @FindBy(id ="adminEdit")
    public WebElement adminPage_btn_edit;
    
    @FindBy(id ="firstNameInfo")
    public WebElement adminPage_txt_firstNameInInfo;
    
    @FindBy(id ="lastNameInfo")
    public WebElement adminPage_txt_lastNameInInfo;
    
    @FindBy(id ="emailInfo")
    public WebElement adminPage_txt_emailInInfo;
    
    @FindBy(id ="phoneInfo")
    public WebElement adminPage_txt_phoneNumberInInfo;
    
    @FindBy(id ="roleInfo")
    public WebElement adminPage_txt_roleInInfo;
    
    @FindBy(id ="groupsInfo")
    public WebElement adminPage_txt_groupsInInfo;
    
    public WebElement adminPage_checkbox_permission(String permissionId) {
        return getElement(String.format("//input[@name='perm_id[]' and @value='%s']", permissionId));
    }
    
    @FindBy(xpath ="//div[@id='assignedPermissions']//input")
    public List<WebElement> adminPage_checkbox_permission;
}
