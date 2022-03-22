package com.nic.ODF_Thittam.model;

import android.graphics.Bitmap;

/**
 * Created by AchanthiSundar on 01-11-2017.
 */

public class ODF_Thittam {

    private String distictCode;
    private String districtName;

    private String blockCode;
    private String SchemeGroupName;
    private String SchemeName;
    private String Description;
    private String Latitude;
    private String selectedBlockCode;

    private String FinancialYear;
    private String AgencyName;
    private String WorkGroupNmae;
    private String WorkName;
    private String PvCode;
    private String PvName;

    private String blockName;
    private String Gender;
    private Integer CurrentStage;

    private String Name;
    private String BeneficiaryName;
    private String workGroupID;
    private String workTypeID;

    private int motivatorCategoryId;
    private String motivatorCategoryName;
    private String genderCode;
    private String genderEn;
    private String genderTa;

    private String educationCode;
    private String educationName;

    private int Bank_Id;
    private String OMC_Name;
    private int Branch_Id;
    private String Branch_Name;

    private String Bank_Name;
    private String IFSC_Code;


    private String VillageListPvName;
    private String VillageListPvCode;
    private String VillageListDistrictCode;
    private String VillageListBlockCode;


    public String getVillageListDistrictCode() {
        return VillageListDistrictCode;
    }

    public ODF_Thittam setVillageListDistrictCode(String villageListDistrictCode) {
        VillageListDistrictCode = villageListDistrictCode;
        return this;
    }

    public String getVillageListBlockCode() {
        return VillageListBlockCode;
    }

    public ODF_Thittam setVillageListBlockCode(String villageListBlockCode) {
        VillageListBlockCode = villageListBlockCode;
        return this;
    }

    public String getVillageListPvName() {
        return VillageListPvName;
    }

    public ODF_Thittam setVillageListPvName(String villageListPvName) {
        VillageListPvName = villageListPvName;
        return this;
    }

    public String getVillageListPvCode() {
        return VillageListPvCode;
    }

    public ODF_Thittam setVillageListPvCode(String villageListPvCode) {
        VillageListPvCode = villageListPvCode;
        return this;
    }

    public int getBank_Id() {
        return Bank_Id;
    }

    public ODF_Thittam setBank_Id(int bank_Id) {
        Bank_Id = bank_Id;
        return this;
    }

    public String getOMC_Name() {
        return OMC_Name;
    }

    public ODF_Thittam setOMC_Name(String OMC_Name) {
        this.OMC_Name = OMC_Name;
        return this;
    }

    public int getBranch_Id() {
        return Branch_Id;
    }

    public ODF_Thittam setBranch_Id(int branch_Id) {
        Branch_Id = branch_Id;
        return this;
    }

    public String getBranch_Name() {
        return Branch_Name;
    }

    public ODF_Thittam setBranch_Name(String branch_Name) {
        Branch_Name = branch_Name;
        return this;
    }

    public String getBank_Name() {
        return Bank_Name;
    }

    public ODF_Thittam setBank_Name(String bank_Name) {
        Bank_Name = bank_Name;
        return this;
    }

    public String getIFSC_Code() {
        return IFSC_Code;
    }

    public ODF_Thittam setIFSC_Code(String IFSC_Code) {
        this.IFSC_Code = IFSC_Code;
        return this;
    }

    public int getMotivatorCategoryId() {
        return motivatorCategoryId;
    }

    public ODF_Thittam setMotivatorCategoryId(int motivatorCategoryId) {
        this.motivatorCategoryId = motivatorCategoryId;
        return this;
    }

    public String getMotivatorCategoryName() {
        return motivatorCategoryName;
    }

    public ODF_Thittam setMotivatorCategoryName(String motivatorCategoryName) {
        this.motivatorCategoryName = motivatorCategoryName;
        return this;
    }

    public String getGenderCode() {
        return genderCode;
    }

    public ODF_Thittam setGenderCode(String genderCode) {
        this.genderCode = genderCode;
        return this;
    }

    public String getGenderEn() {
        return genderEn;
    }

    public ODF_Thittam setGenderEn(String genderEn) {
        this.genderEn = genderEn;
        return this;
    }

    public String getGenderTa() {
        return genderTa;
    }

    public ODF_Thittam setGenderTa(String genderTa) {
        this.genderTa = genderTa;
        return this;
    }

    public String getEducationCode() {
        return educationCode;
    }

    public ODF_Thittam setEducationCode(String educationCode) {
        this.educationCode = educationCode;
        return this;
    }

    public String getEducationName() {
        return educationName;
    }

    public ODF_Thittam setEducationName(String educationName) {
        this.educationName = educationName;
        return this;
    }

    public String getDesignation_code() {
        return designation_code;
    }

    public ODF_Thittam setDesignation_code(String designation_code) {
        this.designation_code = designation_code;
        return this;
    }

    public String getDesignation_name() {
        return designation_name;
    }

    public ODF_Thittam setDesignation_name(String designation_name) {
        this.designation_name = designation_name;
        return this;
    }

    public ODF_Thittam setId(Integer id) {
        this.id = id;
        return this;
    }

    private String designation_code;
    private String designation_name;



    public String getBeneficiaryFatherName() {
        return BeneficiaryFatherName;
    }

    public void setBeneficiaryFatherName(String beneficiaryFatherName) {
        BeneficiaryFatherName = beneficiaryFatherName;
    }

    private String BeneficiaryFatherName;
    private String Community;
    private String IntialAmount;

    private String AmountSpendSoFar;
    private String LastVisitedDate;

    private Integer pendingActivity;
    public String workStageName;
    private String workStageCode;
    private String workStageOrder;
    private Integer schemeSequentialID;

    private Integer schemeGroupID;
    private Integer schemeID;
    private Integer asValue;
    private String stageName;
    private String workTypeName;
    private String ynCompleted;
    private String cdProtWorkYn;
    private Integer stateCode;

    private String roadName;
    private Integer cdWorkNo;
    private Integer cdCode;
    private String cdName;
    private String chainageMeter;
    private Integer cdTypeId;

    public Integer getCdTypeId() {
        return cdTypeId;
    }

    public void setCdTypeId(Integer cdTypeId) {
        this.cdTypeId = cdTypeId;
    }

    public String getWorkTypeFlagLe() {
        return workTypeFlagLe;
    }

    public void setWorkTypeFlagLe(String workTypeFlagLe) {
        this.workTypeFlagLe = workTypeFlagLe;
    }

    private String workTypeFlagLe;

    public String getChainageMeter() {
        return chainageMeter;
    }

    public void setChainageMeter(String chainageMeter) {
        this.chainageMeter = chainageMeter;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public Integer getCdWorkNo() {
        return cdWorkNo;
    }

    public void setCdWorkNo(Integer cdWorkNo) {
        this.cdWorkNo = cdWorkNo;
    }

    public Integer getCdCode() {
        return cdCode;
    }

    public void setCdCode(Integer cdCode) {
        this.cdCode = cdCode;
    }

    public String getCdName() {
        return cdName;
    }

    public void setCdName(String cdName) {
        this.cdName = cdName;
    }

    public String getYnCompleted() {
        return ynCompleted;
    }

    public void setYnCompleted(String ynCompleted) {
        this.ynCompleted = ynCompleted;
    }

    public String getCdProtWorkYn() {
        return cdProtWorkYn;
    }

    public void setCdProtWorkYn(String cdProtWorkYn) {
        this.cdProtWorkYn = cdProtWorkYn;
    }

    public Integer getStateCode() {
        return stateCode;
    }

    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public Integer getCurrentStage() {
        return CurrentStage;
    }

    public void setCurrentStage(Integer currentStage) {
        CurrentStage = currentStage;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public Integer getAsValue() {
        return asValue;
    }

    public void setAsValue(Integer asValue) {
        this.asValue = asValue;
    }

    public Integer getSchemeID() {
        return schemeID;
    }

    public void setSchemeID(Integer schemeID) {
        this.schemeID = schemeID;
    }

    public Integer getSchemeGroupID() {
        return schemeGroupID;
    }

    public void setSchemeGroupID(Integer schemeGroupID) {
        this.schemeGroupID = schemeGroupID;
    }

    public Integer getSchemeSequentialID() {
        return schemeSequentialID;
    }

    public void setSchemeSequentialID(Integer schemeSequentialID) {
        this.schemeSequentialID = schemeSequentialID;
    }

    private Integer noOfPhotos;

    public String getSchemeGroupName() {
        return SchemeGroupName;
    }

    public void setSchemeGroupName(String schemeGroupName) {
        SchemeGroupName = schemeGroupName;
    }

    public String getSchemeName() {
        return SchemeName;
    }

    public void setSchemeName(String schemeName) {
        SchemeName = schemeName;
    }

    public String getFinancialYear() {
        return FinancialYear;
    }

    public void setFinancialYear(String financialYear) {
        FinancialYear = financialYear;
    }

    public String getAgencyName() {
        return AgencyName;
    }

    public void setAgencyName(String agencyName) {
        AgencyName = agencyName;
    }

    public String getWorkGroupNmae() {
        return WorkGroupNmae;
    }

    public void setWorkGroupNmae(String workGroupNmae) {
        WorkGroupNmae = workGroupNmae;
    }

    public String getWorkName() {
        return WorkName;
    }

    public void setWorkName(String workName) {
        WorkName = workName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBeneficiaryName() {
        return BeneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        BeneficiaryName = beneficiaryName;
    }

    public String getCommunity() {
        return Community;
    }

    public void setCommunity(String community) {
        Community = community;
    }

    public String getIntialAmount() {
        return IntialAmount;
    }

    public void setIntialAmount(String intialAmount) {
        IntialAmount = intialAmount;
    }

    public String getAmountSpendSoFar() {
        return AmountSpendSoFar;
    }

    public void setAmountSpendSoFar(String amountSpendSoFar) {
        AmountSpendSoFar = amountSpendSoFar;
    }

    public String getLastVisitedDate() {
        return LastVisitedDate;
    }

    public void setLastVisitedDate(String lastVisitedDate) {
        LastVisitedDate = lastVisitedDate;
    }

    public Integer getWorkId() {
        return WorkId;
    }

    public void setWorkId(Integer workId) {
        WorkId = workId;
    }

    private Integer WorkId;
    private String typeOfWork;
    private String imageRemark;
    private String dateTime;
    private String imageAvailable;
    private String createdDate;
    private String workTypeCode;
    private String min_no_of_photos;
    private String max_no_of_photos;

    public String getHide_show_flag() {
        return hide_show_flag;
    }

    public void setHide_show_flag(String hide_show_flag) {
        this.hide_show_flag = hide_show_flag;
    }

    private String hide_show_flag;


    public String getMin_no_of_photos() {
        return min_no_of_photos;
    }

    public void setMin_no_of_photos(String min_no_of_photos) {
        this.min_no_of_photos = min_no_of_photos;
    }

    public String getMax_no_of_photos() {
        return max_no_of_photos;
    }

    public void setMax_no_of_photos(String max_no_of_photos) {
        this.max_no_of_photos = max_no_of_photos;
    }

    public String getWorkTypeCode() {
        return workTypeCode;
    }

    public void setWorkTypeCode(String workTypeCode) {
        this.workTypeCode = workTypeCode;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getTypeOfWork() {
        return typeOfWork;
    }

    public void setTypeOfWork(String typeOfWork) {
        this.typeOfWork = typeOfWork;
    }

    public String getImageAvailable() {
        return imageAvailable;
    }

    public void setImageAvailable(String imageAvailable) {
        this.imageAvailable = imageAvailable;
    }

    public String getImageRemark() {
        return imageRemark;
    }

    public void setImageRemark(String imageRemark) {
        this.imageRemark = imageRemark;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }



    public Integer getNoOfPhotos() {
        return noOfPhotos;
    }

    public void setNoOfPhotos(Integer noOfPhotos) {
        this.noOfPhotos = noOfPhotos;
    }


    public Integer getPendingActivity() {
        return pendingActivity;
    }

    public void setPendingActivity(Integer pendingActivity) {
        this.pendingActivity = pendingActivity;
    }




    public String getPvName() {
        return PvName;
    }

    public void setPvName(String pvName) {
        PvName = pvName;
    }



    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public String getDistictCode() {
        return distictCode;
    }

    public void setDistictCode(String distictCode) {
        this.distictCode = distictCode;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getSelectedBlockCode() {
        return selectedBlockCode;
    }

    public void setSelectedBlockCode(String selectedBlockCode) {
        this.selectedBlockCode = selectedBlockCode;
    }




    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    private String Longitude;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Bitmap getImage() {
        return Image;
    }

    public void setImage(Bitmap image) {
        Image = image;
    }

    private Bitmap Image;



    public String getPvCode() {
        return PvCode;
    }

    public void setPvCode(String pvCode) {
        this.PvCode = pvCode;
    }

    public void setWorkStageName(String workStageName) {
        this.workStageName = workStageName;
    }

    public String getWorkStageName() {
        return workStageName;
    }

    public void setWorkGroupID(String workGroupID) {
        this.workGroupID = workGroupID;
    }

    public String getWorkGroupID() {
        return workGroupID;
    }

    public void setWorkTypeID(String workTypeID) {
        this.workTypeID = workTypeID;
    }

    public String getWorkTypeID() {
        return workTypeID;
    }

    public void setWorkStageCode(String workStageCode) {
        this.workStageCode = workStageCode;
    }

    public String getWorkStageCode() {
        return workStageCode;
    }

    public void setWorkStageOrder(String workStageOrder) {
        this.workStageOrder = workStageOrder;
    }

    public String getWorkStageOrder() {
        return workStageOrder;
    }

    public Integer getId() {
        return id;
    }
    private Integer id;

}